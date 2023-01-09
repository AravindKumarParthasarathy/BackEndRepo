package com.msciq.storage.service.impl;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.resource.Emailv31;
import com.msciq.storage.model.EmailTemplate;
import com.msciq.storage.service.EmailService;
import com.msciq.storage.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.internet.MimeMessage;
import java.util.Base64;

// Annotation
@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public String sendSimpleMail(EmailTemplate details) {
        try {
            log.info(String.valueOf(details));
            // Creating a simple mail message
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("organizationName",details.getRecipientName());
            jsonObject.put("email",details.getRecipient());
            String emailLink
                    =Constants.UI_BASE_URL+ Base64.getEncoder()
                    .encodeToString(String.valueOf(jsonObject).getBytes());

            log.info(emailLink);
            String htmlMsg = "<html>\n" +
                    "<head></head>\n" +
                    "<body>\n" +
                    "    <p>Dear " + details.getRecipientName() +",</p>\n" +
                    "    <p>Welcome to MSCIQ.  Here is the invite link for you to register your Global Admin account:</b></p>\n" +
                    "<div style=margin-left: 25%;\n><a href=\""+emailLink+"\" style=\"display:inline-block;background:#41B8FF;color:#ffffff;font-family:Ubuntu, Helvetica, Arial, sans-serif;font-size:13px;font-weight:normal;line-height:120%;margin:0;text-decoration:none;text-transform:none;padding:10px 25px;mso-padding-alt:0px;border-radius:10px;\" target=\"_blank\"><span style=\"font-size:14px;\">Click here to activate</span></a></div>"+
                    "    <p> Once your account is activated, you can begin the process of inviting users and configuring the system.</b></p>\n" +
                    "    <p>If you would like more detailed information about the setup process, you can refer to our <a href=\"https://msciq.io/setup\" target=\"_blank\" data-saferedirecturl=\"https://www.google.com/url?q=https://msciq.io/setup&amp;source=gmail&amp;ust=1672597036296000&amp;usg=AOvVaw0-eLC1pvk-df4HBQ2_Fe1p\">Setup Guide</a>.&nbsp; If you have any issues or questions that aren’t addressed in the setup guide, you can also <a href=\"https://support.msciq.io/\" target=\"_blank\" data-saferedirecturl=\"https://www.google.com/url?q=https://support.msciq.io/&amp;source=gmail&amp;ust=1672597036296000&amp;usg=AOvVaw2O3NrRy6h124iC8u91YMBr\">Open a Support Case</a> or send an email to: <a href=\"mailto:support@msciq.io\" target=\"_blank\">support@msciq.io</a>.<u></u><u></u></p>\n" +
                    "    <p style=\"font-weight: bold;\">We look forward to working with you!</p>\n" +
                    "    <p style=\"font-weight: bold;\">The Team at MSCIQ</p>\n"+
                    "\n" +
                    "</body>\n" +
                    "</html>";
            mimeMessage.setContent(htmlMsg, "text/html");
            helper.setTo(details.getRecipient());
            helper.setSubject(details.getSubject());
            helper.setFrom(Constants.FROM_EMAIL);
            javaMailSender.send(mimeMessage);
            return "Mail Sent Successfully...";
        }
        // Catch block to handle the exceptions
        catch (Exception e) {
            return "Error while Sending Mail";
        }
    }

    public String sendEmail(EmailTemplate emailTemplate)throws MailjetException {
        final String mailjetApiKey = "fddc421d28f80a084e3005cb1a846ceb";
        final String mailjetSecretKey = "626ff08980eae7c1c97d09a67e4f8128";
        ClientOptions options =
                ClientOptions.builder().apiKey(mailjetApiKey).apiSecretKey(mailjetSecretKey).build();
        MailjetClient client = new MailjetClient(options);

        MailjetRequest request;
        MailjetResponse response;
        request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray()
                        .put(new JSONObject()
                                .put(Emailv31.Message.FROM, new JSONObject()
                                        .put("Email",Constants.FROM_EMAIL)
                                        .put("Name", "Admin"))
                                .put(Emailv31.Message.TO, new JSONArray()
                                        .put(new JSONObject()
                                                .put("Email",emailTemplate.getRecipient())
                                                .put("Name", emailTemplate.getRecipientName())))
                                .put(Emailv31.Message.TEMPLATEID,Constants.WELCOME_EMAIL_TEMPLATE)
                                .put(Emailv31.Message.TEMPLATELANGUAGE, true)
                                .put(Emailv31.Message.SUBJECT,emailTemplate.getSubject())
                                .put(Emailv31.Message.VARIABLES, new JSONObject()
                                        .put("name",emailTemplate.getRecipientName())
                                        .put("singup_link", "undefined"))));
        response = client.post(request);
        System.out.println(response.getStatus());
        System.out.println(response.getData());
        return "Mail Sent Successfully...";
    }

    @Override
    public String sendResetPasswordEmail(EmailTemplate emailTemplate) {
        try {
            log.info(String.valueOf(emailTemplate));
            // Creating a simple mail message
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("organizationName",emailTemplate.getRecipientName());
            jsonObject.put("email",emailTemplate.getRecipient());
            String emailLink
                    =Constants.UI_BASE_URL+ Base64.getEncoder()
                    .encodeToString(String.valueOf(jsonObject).getBytes());

            log.info(emailLink);
            String htmlMsg = "<html>\n" +
                    "<head></head>\n" +
                    "<body>\n" +
                    "    <p>Dear " + emailTemplate.getRecipientName() +",</p>\n" +
                    "    <p>Welcome to MSCIQ. You have been successfully registered to the system\nPlease click the below link to reset your password" +
                    "<div style=margin-left: 25%;\n><a href=\""+emailLink+"\" style=\"display:inline-block;background:#41B8FF;color:#ffffff;font-family:Ubuntu, Helvetica, Arial, sans-serif;font-size:13px;font-weight:normal;line-height:120%;margin:0;text-decoration:none;text-transform:none;padding:10px 25px;mso-padding-alt:0px;border-radius:10px;\" target=\"_blank\"><span style=\"font-size:14px;\">Click here to reset your password</span></a></div>"+
                    "    <p style=\"font-weight: bold;\">Thanks</p>\n" +
                    "    <p style=\"font-weight: bold;\">MSCIQ Team</p>\n"+
                    "\n" +
                    "</body>\n" +
                    "</html>";
            mimeMessage.setContent(htmlMsg, "text/html");
            helper.setTo(emailTemplate.getRecipient());
            helper.setSubject(emailTemplate.getSubject());
            helper.setFrom(Constants.FROM_EMAIL);
            javaMailSender.send(mimeMessage);
            return "Mail Sent Successfully...";
        }
        // Catch block to handle the exceptions
        catch (Exception e) {
            return "Error while Sending Mail";
        }
    }
}
