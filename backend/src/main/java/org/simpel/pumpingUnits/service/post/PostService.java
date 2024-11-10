package org.simpel.pumpingUnits.service.post;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.simpel.pumpingUnits.model.Users;
import org.simpel.pumpingUnits.service.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class PostService {
    private final JavaMailSender mailSender;
    private final UserService userService;

    public PostService(final JavaMailSender mailSender, UserService userService) {
        this.mailSender = mailSender;
        this.userService = userService;
    }

    public void sendUsers() throws MessagingException {
        List<Users> users = userService.getAllUsers();
        StringBuilder userInfo = new StringBuilder();
        userInfo.append("<h1>Отчет о пользователях</h1>");
        userInfo.append("<table style='border-collapse: collapse; width: 100%;'>");
        userInfo.append("<tr><th style='border: 1px solid #000; padding: 8px;'>Имя</th>")
                .append("<th style='border: 1px solid #000; padding: 8px;'>Фамилия</th>")
                .append("<th style='border: 1px solid #000; padding: 8px;'>Отчество</th>")
                .append("<th style='border: 1px solid #000; padding: 8px;'>Email</th>")
                .append("<th style='border: 1px solid #000; padding: 8px;'>Телефон</th>")
                .append("<th style='border: 1px solid #000; padding: 8px;'>Компания</th>")
                .append("<th style='border: 1px solid #000; padding: 8px;'>Должность</th>")
                .append("<th style='border: 1px solid #000; padding: 8px;'>Роль</th></tr>");

        for (Users user : users) {
            userInfo.append("<tr>")
                    .append("<td style='border: 1px solid #000; padding: 8px;'>").append(user.getName()).append("</td>")
                    .append("<td style='border: 1px solid #000; padding: 8px;'>").append(user.getSurname()).append("</td>")
                    .append("<td style='border: 1px solid #000; padding: 8px;'>").append(user.getPatronymic()).append("</td>")
                    .append("<td style='border: 1px solid #000; padding: 8px;'>").append(user.getEmail()).append("</td>")
                    .append("<td style='border: 1px solid #000; padding: 8px;'>").append(user.getPhoneNumber()).append("</td>")
                    .append("<td style='border: 1px solid #000; padding: 8px;'>").append(user.getCompany()).append("</td>")
                    .append("<td style='border: 1px solid #000; padding: 8px;'>").append(user.getJobTitle()).append("</td>")
                    .append("<td style='border: 1px solid #000; padding: 8px;'>").append(user.getRole()).append("</td>")
                    .append("</tr>");
        }

        userInfo.append("</table>");

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("noreply@spartaspb.ru");
        helper.setTo("Playstationstoreplus@yandex.ru");
        helper.setSubject("Отчет о пользователях");
        helper.setText(userInfo.toString(), true);

        mailSender.send(message);
    }

    public void sendPdf(byte[] pdfBytes) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(userService.getEmailForCurrentUser());
        helper.setFrom("noreply@spartaspb.ru");
//        helper.setTo("post8888@list.ru");
        helper.setSubject("Подбор насосной установки Стрела по Вашему проекту ");
        helper.setText("Технический лист подбора во вложении.   \n\n\n\n\n\n\n\n\n\n\n\n\n\n\nДанное письмо созданно автоматически отвечать на него не нужно" );

        // Создаем вложение из PDF-файла
        ByteArrayResource pdfResource = new ByteArrayResource(pdfBytes);
        helper.addAttachment("generated_report.pdf", pdfResource, MediaType.APPLICATION_PDF_VALUE);
        mailSender.send(message);
    }
}
