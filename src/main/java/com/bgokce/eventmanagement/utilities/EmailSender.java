package com.bgokce.eventmanagement.utilities;


import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Properties;

@Service
public class EmailSender {

    //TODO; enter credentials
    private static String username = "your-gmail-account@gmail.com";
    private static String password = "your password";
    private static String nameOfQRCode = ".\\qrcode.png";

    @Async
    public void sendMail(String recipient) throws MessagingException {
        Properties properties = prepareProperties();

        Session session = Session.getInstance(properties, new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username,password);
            }
        });

        Message message = prepareMessage(session, username, recipient);

        Transport.send(message);
        System.out.printf("email sent successfully");
    }

    private static Properties prepareProperties(){
        Properties properties = new Properties();

        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.port","587");

        return properties;
    }

    private static Message prepareMessage(Session session, String mailAddress,
                                          String recipient) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(mailAddress));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        message.setSubject("Etkinliğe Kaydınız Hk.");

        MimeMultipart mimeMultipart = prepareContentOfMail();

        message.setContent(mimeMultipart);

        return message;
    }

    private static MimeMultipart prepareContentOfMail() throws MessagingException {
        MimeMultipart multipart = new MimeMultipart("related");
        BodyPart messageBodyPart = new MimeBodyPart();
        String htmlText = "<H1>Merhaba</H1><H4>Etkinliğe kaydınız başarıyla oluşturuldu."
                +" Aşağıdaki QR kod ile etkinliğe giriş yapabilirsiniz.</H4><img src=\"cid:image\">";
        messageBodyPart.setContent(htmlText, "text/html; charset=UTF-8");
        multipart.addBodyPart(messageBodyPart);

        messageBodyPart = new MimeBodyPart();
        DataSource fds = new FileDataSource(nameOfQRCode);
        messageBodyPart.setDataHandler(new DataHandler(fds));
        messageBodyPart.setHeader("Content-ID", "<image>");
        multipart.addBodyPart(messageBodyPart);

        return multipart;
    }
}