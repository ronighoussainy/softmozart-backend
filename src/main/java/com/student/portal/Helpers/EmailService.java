package com.student.portal.Helpers;

import com.student.portal.Expense.ExpenseDto;
import com.student.portal.Invoice.*;
import com.student.portal.Session.SessionDetailDto;
import com.student.portal.Session.SessionDto;
import com.student.portal.Student.StudentDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;


    public void sendEmail() throws MessagingException {
        String from = "softmozartleb@gmail.com";
        String to = "ghossainym@gmail.com";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);


            helper.setSubject("This is an HTML email");
            helper.setFrom(from);
            helper.setTo(to);
            boolean html = true;
            helper.setText("<b>Hey guys</b>,<br><i>Welcome to my new home</i>", html);

        mailSender.send(message);
    }

    @Async
    public void SendInvoicePaidEmail(InvoiceDto invoiceDto ) throws MessagingException {
        String from = "softmozartleb@gmail.com";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setSubject("Soft Mozart-Payment done for the month "+ invoiceDto.getMonthofyear());
        helper.setFrom(from);

        if(invoiceDto.getInstructorId().equals(1))
        {
            helper.setBcc(new String[] {"lara.molaeb@gmail.com", "ghossainym@gmail.com"});
        }
        if(invoiceDto.getInstructorId().equals(2))
        {
            helper.setBcc(new String[] {"lara.molaeb@gmail.com", "ghossainym@gmail.com","yaramolaeb4@gmail.com"});
        }
        if(invoiceDto.getInstructorId().equals(3))
        {
            helper.setBcc(new String[] {"lara.molaeb@gmail.com", "ghossainym@gmail.com", "Leen.khaddaj02@gmail.com"});
        }

        helper.setTo(new String[] {invoiceDto.getStudentEmail()});
        boolean html = true;
        helper.setText("<b>Hello "+ invoiceDto.getFullName()+" </b>,<br>" +
                "<i>Thank you for making a payment for the month "+ invoiceDto.getMonthofyear()+". </i>" +
                "<br><br>" +
                        "<i>Find below the invoice details : </i>" +
                        "<br><br>" +
                "<b>Invoice ID: "+ invoiceDto.getId()+" </b><br>" +
                "<b>Invoice Month: "+ invoiceDto.getMonthofyear()+" </b><br>" +
                "<b>Student Name: "+ invoiceDto.getFullName()+" </b><br>" +
                        "<b>Instructor Name: "+ invoiceDto.getInstructorName()+" </b><br>" +
                        "<b>Amount : "+ invoiceDto.getAmount()+" </b><br>"+
                        "<b>Date of Payment : "+ invoiceDto.getDateofpayment()+" </b><br>"+
                        "<b>Notes : "+ invoiceDto.getNote()+" </b><br>"
                , html);

        mailSender.send(message);
    }

    @Async
    public void SendSessionAddedEmail(SessionDto sessionDto ) throws MessagingException {
        String from = "softmozartleb@gmail.com";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setSubject("Soft Mozart- New Session Logged - Date " + sessionDto.getDate()+ " Time "+ sessionDto.getTime() +" Duration " +sessionDto.getDuration() );
        helper.setFrom(from);
        helper.setTo(new String[] {"lara.molaeb@gmail.com"});
        if(sessionDto.getInstructorId().equals("2"))
        {
            helper.setBcc(new String[] {"yaramolaeb4@gmail.com"});
        }
        boolean html = true;
        helper.setText("<b>Hello </b>,<br>" +
                        "<i>A new session was logged with the following details</i>" +
                        "<br><br>" +
                        "<b>Session ID: "+ sessionDto.getId()+" </b><br>" +
                        "<b>Session Date: "+ sessionDto.getDate()+" </b><br>" +
                        "<b>Session Time: "+ sessionDto.getTime()+" </b><br>" +
                        "<b>Session Duration: "+ sessionDto.getDuration()+" </b><br>" +
                        "<b>Instructor Name: "+ sessionDto.getInstructorName()+" </b><br>"+
                        "<b>Student Name: "+ sessionDto.getStudents()+" </b><br>"
                , html);

        mailSender.send(message);
    }

    @Async
    public void SendSessionDetailAddedEmail(SessionDetailDto sessionDto ) throws MessagingException {
        String from = "softmozartleb@gmail.com";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setSubject("Soft Mozart- New Session Logged - Date " + sessionDto.getDate()+ " Time "+ sessionDto.getTime() +" Duration " +sessionDto.getDuration() );
        helper.setFrom(from);
        helper.setTo(new String[] {"lara.molaeb@gmail.com"});
        boolean html = true;
        helper.setText("<b>Hello </b>,<br>" +
                        "<i>A new session was logged with the following details</i>" +
                        "<br><br>" +
                        "<b>Session ID: "+ sessionDto.getId()+" </b><br>" +
                        "<b>Student Name: "+ sessionDto.getStudentName()+" </b><br>"+
                        "<b>Session Date: "+ sessionDto.getDate()+" </b><br>" +
                        "<b>Session Time: "+ sessionDto.getTime()+" </b><br>" +
                        "<b>Session Duration: "+ sessionDto.getDuration()+" </b><br>" +
                        "<b>Instructor Name: "+ sessionDto.getInstructorName()+" </b><br>"
                , html);

        mailSender.send(message);
    }

    @Async
    public void SendExpenseAddedEmail(ExpenseDto expenseDto ) throws MessagingException {
        String from = "softmozartleb@gmail.com";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setSubject("Soft Mozart-Expense added for the amount "+ expenseDto.getAmount());
        helper.setFrom(from);
        helper.setTo(new String[] {"lara.molaeb@gmail.com", "ghossainym@gmail.com"});

        boolean html = true;
        helper.setText("<b>Hello "+" </b>,<br>" +
                        "<i>A new Expense is added with the below details "+". </i>" +
                        "<br><br>" +
                        "<b>Expense ID: "+ expenseDto.getId()+" </b><br>" +
                        "<b>Expense Type: "+ expenseDto.getType()+" </b><br>" +
                        "<b>Expense Date: "+ expenseDto.getDate()+" </b><br>" +
                        "<b>Lab Name: "+ expenseDto.getLabName()+" </b><br>" +
                        "<b>Paid To: "+ expenseDto.getPaidto()+" </b><br>" +
                        "<b>Amount : "+ expenseDto.getAmount()+" </b><br>"+
                        "<b>Notes : "+ expenseDto.getNotes()+" </b><br>"
                , html);

        mailSender.send(message);
    }

    @Async
    public void SendEBirthdayEmail(StudentDto studentDto ) throws MessagingException {
        String from = "softmozartleb@gmail.com";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setSubject("Happy Birthday "+ studentDto.getFullName() +"!!");
        helper.setFrom(from);
        helper.setBcc(new String[] {"lara.molaeb@gmail.com", "yaramolaeb4@gmail.com"});
        helper.setTo(new String[] {studentDto.getEmail()});
        boolean html = true;
        helper.setText("<b>Hello "+ studentDto.getFirstName() +" </b>,<br>" +
                        "<p>The entire team would like to wish you a very Happy Birthday! "+". </p>" +
                        "<p>May this year bring you more opportunities & achievements. "+". </p>,<br>" +
                        "<p>Enjoy your day"+"</p>" +
                        "<b>Soft Mozart Academy - Lebanon"+"</b>" +
                        "<br><br>"
                , html);

        mailSender.send(message);
    }


}
