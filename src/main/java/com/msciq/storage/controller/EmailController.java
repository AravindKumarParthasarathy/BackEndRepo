package com.msciq.storage.controller;

// Importing required classes

import com.mailjet.client.errors.MailjetException;
import com.msciq.storage.model.EmailTemplate;
import com.msciq.storage.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Annotation
@RestController
@RequestMapping("/fpa")
public class EmailController {

    @Autowired
    private EmailService emailService;

    /**
     *
     * This method is used to send out the email to the respective user
     *
     * @param emailTemplate - emailTemplate model
     * @return success or failure status message
     */
    @PostMapping("/sendMail")
    public String sendMail(@RequestBody EmailTemplate emailTemplate) throws MailjetException {

        String status  = emailService.sendSimpleMail(emailTemplate);

        return status;
    }
}
