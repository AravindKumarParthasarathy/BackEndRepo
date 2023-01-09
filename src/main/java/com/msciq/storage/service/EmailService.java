package com.msciq.storage.service;

// Importing required classes
import com.mailjet.client.errors.MailjetException;
import com.msciq.storage.model.EmailTemplate;

// Interface

public interface EmailService {

    /**
     * This method will be used to Send mail to the onboarded user
     *
     * @param emailTemplate - email template model which contains all the recipient details
     *
     * @return String
     *      Successfull message on sending the email
     */
    String sendSimpleMail(EmailTemplate emailTemplate);

    //The below method is not used currently - it will be used if necessary
    String sendEmail(EmailTemplate details) throws MailjetException;

    String sendResetPasswordEmail(EmailTemplate emailTemplate);
}
