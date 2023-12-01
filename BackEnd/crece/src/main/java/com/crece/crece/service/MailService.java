package com.crece.crece.service;


import com.crece.crece.model.MailStructure;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Objects;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromMail;

    public void sendMail(List<String> mails, MailStructure mailStructure){
        for (String mail : mails) {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(fromMail);
            simpleMailMessage.setSubject(mailStructure.getSubject());
            simpleMailMessage.setText(mailStructure.getMessage());
            simpleMailMessage.setTo(mail);

            mailSender.send(simpleMailMessage);
        }
    }
    public void sendMailAttach(List<String> mails, MailStructure mailStructure,String file) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);


        for (String mail : mails){
            FileSystemResource fileSystemResource = new FileSystemResource(new File(file));
            helper.setFrom(fromMail);
            helper.setTo(mail);
            helper.setText(mailStructure.getMessage());
            helper.setSubject(mailStructure.getSubject());
            helper.addAttachment(Objects.requireNonNull(fileSystemResource.getFilename()),fileSystemResource);
            mailSender.send(mimeMessage);
            System.out.println("mail enviado con attach");
        }
    }

/*
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromMail;

    public void sendMail(String mail, MailStructure mailStructure){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromMail);
        simpleMailMessage.setSubject(mailStructure.getSubject());
        simpleMailMessage.setText(mailStructure.getMessage());
        simpleMailMessage.setTo(mail);

        mailSender.send(simpleMailMessage);*/

}
