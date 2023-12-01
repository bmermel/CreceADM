package com.crece.crece.controller;

import com.crece.crece.model.MailRequest;
import com.crece.crece.service.MailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
@CrossOrigin("*")
public class MailController {

    @Autowired
    private MailService mailService;

    @PostMapping("/send")

    public String sendMail(@RequestBody MailRequest mailRequest){
        mailService.sendMail(mailRequest.getMails(), mailRequest.getMailStructure());
        return "Successfully sent the mail";
    }
    @PostMapping("/sendAttach")
    public String sendMailAttach(@RequestBody MailRequest mailRequest) throws MessagingException {
        mailService.sendMailAttach(mailRequest.getMails(), mailRequest.getMailStructure(), mailRequest.getFilePath());
        return "Successfully sent the mail";
    }
    /*
    @PostMapping("/send")
    public String sendMail(@RequestBody List<String> mails, @RequestBody MailStructure mailStructure){
        mailService.sendMail(mails, mailStructure);
        return "Successfully sent the mail";
    */
    /*

    @PostMapping("/send/{mail}")
    public String sendMail(@PathVariable String mail, @RequestBody MailStructure mailStructure){
        mailService.sendMail(mail, mailStructure);
        return "Succesfully sent the mail";*/
    }
