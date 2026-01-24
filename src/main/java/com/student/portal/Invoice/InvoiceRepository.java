package com.student.portal.Invoice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    @Query(value = "SELECT max(id) FROM Invoice")
    Integer getMaxInvoiceId();

    @Query(value = "SELECT u FROM Invoice u where u.studentId=?1 order by u.monthofyear desc")
    List<Invoice> getInvoicebyStudentId(Integer studentId);

    @Query(value = "SELECT count(id) FROM Invoice where monthofyear=?1 ")
    Integer getCountInvoicesbyMonth(Integer monthofyear);

    @Query(value = "SELECT count(id) FROM Invoice where monthofyear=?1 and studentId=?2")
    Integer getCountInvoicesbyMonthbyStudent(String monthofyear, Integer studentId);

//    @Query(value = "SELECT u FROM Invoice u where u.monthofyear=?1 and u.status=?2")
//    List<Invoice> getInvoicebyMonthbyStatus(String monthofyear, String status);

//    @Query(value = "SELECT u FROM Invoice u where u.status=?1")
//    List<Invoice> getInvoicebyStatus(String status);

//    @Query(value = "SELECT u FROM Invoice u where u.monthofyear=?1")
//    List<Invoice> getInvoicebyMonth(String monthofyear);

    @Query(value = "select monthofyear,\n" +
            "            count(CASE WHEN status='Pending' THEN 1 ELSE null END) as PendingInvoices,\n" +
            "            count(CASE WHEN status='Paid' THEN 1 ELSE null END) as PaidInvoices,\n" +
            "            sum(CASE WHEN status='Pending' THEN amount ELSE 0 END) as PendingAmount,\n" +
            "            sum(CASE WHEN status='Paid' THEN amount ELSE 0 END) as PaidAmount,\n" +
            "            sum(amount) as totalAmount,\n" +
            "            count(1) as totalinvoices\n" +
            "            from invoices\n" +
            "            group by monthofyear\n" +
            "            order by monthofyear desc", nativeQuery = true)
    List<Map<String, Object>> getInvoiceSummarybyMonth();

    @Query(value = "select invoicesummary.monthofyear, invoicesummary.totalAmount as Revenue, invoicesummary.PaidAmout as Receivables,\n" +
            "IFNULL(expensesummary.amount,0) as Payables,\n" +
            "invoicesummary.totalAmount - IFNULL(expensesummary.amount,0) as GrossProfit\n" +
            "from invoicesummary \n" +
            "LEFT JOIN expensesummary  on invoicesummary.monthofyear=expensesummary.monthofyear\n" +
            "order by invoicesummary.monthofyear DESC", nativeQuery = true)
    List<Map<String, Object>> getRevenueSummarybyMonth();

    @Query(value = "select i.id id,i.student_Id studentId,i.monthofyear monthofyear,i.amount amount,i.status status, i.note note,i.instructor_Id instructorId,\n" +
            "  i.dateofpayment dateofpayment,\n" +
            "   std.fullname fullName, ins.fullname instructorName, std.email studentEmail\n" +
            "            from invoices i, instructors ins, students std\n" +
            "            where i.instructor_id = ins.id and i.student_id = std.id", nativeQuery = true)
    List<Map<String, Object>> getAllInvoices();

    @Query(value = "select i.id id,i.student_Id studentId,i.monthofyear monthofyear,i.amount amount,i.status status, i.note note,i.instructor_Id instructorId,\n" +
            "  i.dateofpayment dateofpayment,\n" +
            "   std.fullname fullName, ins.fullname instructorName, std.email studentEmail\n" +
            "            from invoices i, instructors ins, students std\n" +
            "            where i.instructor_id = ins.id and i.student_id = std.id and i.monthofyear = ?1", nativeQuery = true)
    List<Map<String, Object>> getInvoicebyMonth(String monthofyear);


    @Query(value = "select i.id id,i.student_Id studentId,i.monthofyear monthofyear,i.amount amount,i.status status, i.note note,i.instructor_Id instructorId,\n" +
            "  i.dateofpayment dateofpayment,\n" +
            "   std.fullname fullName, ins.fullname instructorName, std.email studentEmail\n" +
            "            from invoices i, instructors ins, students std\n" +
            "            where i.instructor_id = ins.id and i.student_id = std.id and i.status = ?1", nativeQuery = true)
    List<Map<String, Object>> getInvoicebyStatus(String status);

    @Query(value = "select i.id id,i.student_Id studentId,i.monthofyear monthofyear,i.amount amount,i.status status, i.note note,i.instructor_Id instructorId,\n" +
            "  i.dateofpayment dateofpayment,\n" +
            "   std.fullname fullName, ins.fullname instructorName, std.email studentEmail\n" +
            "            from invoices i, instructors ins, students std\n" +
            "            where i.instructor_id = ins.id and i.student_id = std.id and i.monthofyear = ?1 and i.status = ?2", nativeQuery = true)
    List<Map<String, Object>> getInvoicebyMonthbyStatus(String monthofyear, String status);
}

