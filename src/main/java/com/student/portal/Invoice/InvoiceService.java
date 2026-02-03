package com.student.portal.Invoice;

import com.student.portal.Helpers.EmailService;
import com.student.portal.Instructor.InstructorDto;
import com.student.portal.Instructor.InstructorInterface;
import com.student.portal.Instructor.InstructorRepository;
import com.student.portal.Student.Student;
import com.student.portal.Student.StudentDto;
import com.student.portal.Student.StudentInterface;
import com.student.portal.Student.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceService implements InvoiceInterface {

    private final InvoiceRepository repo;
    private final InvoiceMapper mapper;

    private StudentInterface studentInterface;
    private EmailService emailservice;
    private StudentDto studentDto;
    private InvoiceDto invoiceDto;
    private Invoice invoice;
    private InstructorInterface instructorInterface;
    private InstructorDto instructorDto;
    private final InstructorRepository instructorRepository;
    private List<StudentDto> studentDtoList;
    private final StudentRepository studentRepository;


    public Page<Map<String, Object>> getInvoices(Map<String, Object> filters, int page, int size) {
        Integer studentId = getInteger(filters, "studentId");
        Integer instructorId = getInteger(filters, "instructorId");
        String studentName = getString(filters, "fullName");
        String status = getString(filters, "status");
        String monthofyear = getString(filters, "monthofyear");
        LocalDate fromDate = getLocalDate(filters, "fromDate");
        LocalDate toDate = getLocalDate(filters, "toDate");

        Pageable pageable = PageRequest.of(page, size, Sort.by("monthofyear").descending());


        return repo.findInvoicesWithFilters(
                studentId,
                studentName,
                instructorId,
                status,
                monthofyear,
                fromDate,
                toDate,
                pageable
        );
    }

    public Page<InvoiceDto> convertInvoicePageToDto(Page<Invoice> invoicesPage) {

        List<InvoiceDto> invoiceDtos = invoicesPage.getContent()
                .stream()
                .map(this::convertInvoiceEntitytoDTO) // call your existing method
                .collect(Collectors.toList());

        return new PageImpl<>(
                invoiceDtos,
                invoicesPage.getPageable(),
                invoicesPage.getTotalElements()
        );
    }
    /* ---------- Helpers ---------- */

    private Integer getInteger(Map<String, Object> map, String key) {
        Object value = map.get(key);
        return value == null ? null : Integer.valueOf(value.toString());
    }

    private String getString(Map<String, Object> map, String key) {
        Object value = map.get(key);
        return value == null ? null : value.toString();
    }

    private LocalDate getLocalDate(Map<String, Object> map, String key) {
        Object value = map.get(key);
        return value == null ? null : LocalDate.parse(value.toString());
    }

    public InvoiceDto getInvoicebyId(Integer id) {
        Optional<Invoice> invoice = repo.findById(id);
        return convertInvoiceEntitytoDTO(invoice.get());
    }

    public List<Map<String, Object>> getInvoiceSummarybyMonth() {
        return repo.getInvoiceSummarybyMonth();
    }

    public List<Map<String, Object>> getRevenueSummarybyMonth() {
        return repo.getRevenueSummarybyMonth();
    }

    public List<InvoiceDto> getInvoicebyStudentId(Integer id) {
        return repo.getInvoicebyStudentId(id)
                .stream()
                .map(this::convertInvoiceEntitytoDTO)
                .collect(Collectors.toList());
    }

    public List<Map<String, Object>> getInvoicebyFilter(Map<String, Object> invoiceDto) {
        String monthofyear = "";
        String status = "";
        if (invoiceDto.containsKey("monthofyear"))
            monthofyear = invoiceDto.get("monthofyear").toString();
        if (invoiceDto.containsKey("status"))
            status = invoiceDto.get("status").toString();

        if (monthofyear != "" && status != "") {

            return repo.getInvoicebyMonthbyStatus(monthofyear, status);
        }
        if (monthofyear != "" && status == "") {

            return repo.getInvoicebyMonth(monthofyear);
        }

        if (monthofyear == "" && status != "") {

            return repo.getInvoicebyStatus(status);
        }

        return repo.getAllInvoices();
    }

//    public List<InvoiceDto> getInvoicebyFilter(Map<String, Object> invoiceDto){
//        String monthofyear = "";
//        String status =  "";
//
//        if (invoiceDto.containsKey("monthofyear") )
//            monthofyear = invoiceDto.get("monthofyear").toString();
//        if (invoiceDto.containsKey("status") )
//            status=invoiceDto.get("status").toString();
//
//        if(monthofyear!= "" && status!= "") {
//
//            return repo.getInvoicebyMonthbyStatus(monthofyear, status)
//                    .stream()
//                    .map(this::convertInvoiceEntitytoDTO)
//                    .collect(Collectors.toList());
//        }
//        if(monthofyear!= "" && status== "") {
//
//            return repo.getInvoicebyMonth(monthofyear)
//                    .stream()
//                    .map(this::convertInvoiceEntitytoDTO)
//                    .collect(Collectors.toList());
//        }
//
//        if(monthofyear== "" && status!= "") {
//
//            return repo.getInvoicebyStatus(status)
//                    .stream()
//                    .map(this::convertInvoiceEntitytoDTO)
//                    .collect(Collectors.toList());
//        }
//
//        return repo.findAll()
//                .stream()
//                .map(this::convertInvoiceEntitytoDTO)
//                .collect(Collectors.toList());
//    }

    private InvoiceDto convertInvoiceEntitytoDTO(Invoice invoice) {
        InvoiceDto invoiceDto = mapper.invoiceToInvoiceDto(invoice);
        Map<String, String> student = studentRepository.getStudentData(invoiceDto.getStudentId());
        invoiceDto.setFullName(student.get("fullName"));
        invoiceDto.setStudentEmail(student.get("email"));
        invoiceDto.setInstructorName(instructorRepository.getInstructorNameByInstructorId(invoiceDto.getInstructorId()));
        return invoiceDto;
    }

    public Integer CreateInvoice(InvoiceDto invoiceDto) {
        Invoice invoice = mapper.invoiceDtoToInvoice(invoiceDto);
        repo.saveAndFlush(invoice);

        if (invoice.getStatus().equals("Paid") && invoice.getAmount().doubleValue() > 0) {
            InvoiceDto invoiceDto2 = this.getInvoicebyId(repo.getMaxInvoiceId());
            try {
                this.emailservice.SendInvoicePaidEmail(invoiceDto2);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return repo.getMaxInvoiceId();
    }

    public boolean DeleteInvoicebyId(Integer id) {
        repo.deleteById(id);
        repo.flush();
        return true;
    }

    public InvoiceDto UpdateInvoiceFields(Map<String, Object> invoiceDto, Integer id) {
        Invoice invoice = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("invoice not found on :: " + id));
        Boolean sendemail = false;
        if (invoiceDto.containsKey("monthofyear"))
            invoice.setMonthofyear(invoiceDto.get("monthofyear").toString());
        if (invoiceDto.containsKey("amount"))
            invoice.setAmount(Double.valueOf(invoiceDto.get("amount").toString()));
        if (invoiceDto.containsKey("status")) {
            if (!invoice.getStatus().equals("Paid") && invoiceDto.get("status").toString().equals("Paid") && !invoiceDto.get("amount").toString().equals("0"))
                sendemail = true;
            invoice.setStatus(invoiceDto.get("status").toString());
        }

        if (invoiceDto.containsKey("note"))
            invoice.setNote(invoiceDto.get("note").toString());
        if (invoiceDto.containsKey("instructorId"))
            invoice.setInstructorId(Integer.parseInt(invoiceDto.get("instructorId").toString()));
        if (invoiceDto.containsKey("studentId"))
            invoice.setStudentId(Integer.parseInt(invoiceDto.get("studentId").toString()));
        if (invoiceDto.containsKey("dateofpayment") && invoiceDto.get("dateofpayment").toString().length() != 0)
            invoice.setDateofpayment(LocalDate.parse(invoiceDto.get("dateofpayment").toString()));
        repo.saveAndFlush(invoice);

        //If Invoice is paid send an email
        if (sendemail) {
            InvoiceDto invoiceDto2 = this.getInvoicebyId(id);
            try {
                this.emailservice.SendInvoicePaidEmail(invoiceDto2);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        return convertInvoiceEntitytoDTO(invoice);
    }

    public Integer GenerateInvoices(String monthofyear) {
        //Check if we generated invoices for this month
        if (monthofyear.length() == 0 || monthofyear == null)
            return 0;

        Integer counter = 0;
        studentDtoList = studentInterface.getallStudentsOld();
        for (int i = 0; i < studentDtoList.size(); i++) {
            //Check if this student should be billed fixed monthly fees and have no previous invoices
            studentDto = studentDtoList.get(i);
            invoice = new Invoice();
            Integer count = repo.getCountInvoicesbyMonthbyStudent(monthofyear, studentDto.getId());
            String billing = studentDto.getBilling();
            String status = studentDto.getStatus();
            if (count == 0) {
                if (billing.equalsIgnoreCase("Monthly") && status.equalsIgnoreCase("Active")) {
                    invoice.setStudentId(studentDto.getId());
                    invoice.setInstructorId(studentDto.getInstructorID());
                    invoice.setMonthofyear(monthofyear);
                    invoice.setStatus("Pending");
                    invoice.setAmount(studentDto.getFees() * studentDto.getSessionsperweek());
                    repo.saveAndFlush(invoice);
                    counter = counter + 1;
                }
            }
        }
        return counter;
    }

}