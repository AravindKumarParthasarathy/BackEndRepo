package com.msciq.storage.service.impl;

import com.google.cloud.datastore.*;
import com.msciq.storage.model.EmailTemplate;
import com.msciq.storage.model.Organization;
import com.msciq.storage.model.response.ResponseDTO;
import com.msciq.storage.service.EmailService;
import com.msciq.storage.service.GCPStorageService;
import com.msciq.storage.service.OrganizationService;
import com.msciq.storage.common.Constants;
import com.msciq.storage.common.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private GCPStorageService gcpStorageService;

    @Autowired
    private EmailService emailService;

    @Override
    public ResponseDTO createOrganization(Organization organization) {
        try {

            /*
             * Google cloud expects the bucket name can only contain lowercase letters, numeric characters,
             * dashes ( - ), underscores ( _ ), and dots ( . ) and name should start with a letter
             * So we are adding a prefix to make sure the bucket name starts with a letter and converting the input
             * tenant name to lower case and replaced all the spaces with `_`
             */
            String bucketName = Constants.ORGANIZATION_PREFIX + organization.getOrganizationName().toLowerCase().replaceAll("\\s", "_");

            /* Creating bucket in google cloud */
            gcpStorageService.createBucket(bucketName);

            /* Create a namespace in google data store */
            Datastore tenantDataStore = gcpStorageService.createOrGetNamespace(bucketName);

            /* Creating an entity in the tenant namespace */
            createOrganizationEntity(tenantDataStore, organization);
            /* Once the tenant is created a confirmation mail is sent to teh tenant */
            sendMailToOrganization(organization);
            return ResponseDTO.builder()
                    .message(organization.getOrganizationName() + " created successfully")
                    .isError(false)
                    .build();
        } catch (Exception exception) {
            if (exception.getMessage().contains("you already own it")) {
                return ResponseDTO.builder()
                        .message(String.format(ErrorMessage.ORGANIZATION_ALREADY_EXISTS, organization.getOrganizationName()))
                        .isError(true)
                        .build();
            }
            return ResponseDTO.builder()
                    .message("Error while creating organization - " + exception.getMessage())
                    .isError(true)
                    .build();
        }
    }

    private void sendMailToOrganization(Organization organization) {
        EmailTemplate emailTemplate = EmailTemplate.builder()
                .recipient(organization.getEmail())
                .recipientName(organization.getFirstName())
                .subject(Constants.WELCOME_TO_MSCIQ)
                .build();
        emailService.sendSimpleMail(emailTemplate);
    }

    private void createOrganizationEntity(Datastore tenantDataStore, Organization organization) {
        Key taskKey = tenantDataStore.newKeyFactory()
                .setKind("Organization")
                .newKey("organization");
        Entity createdTenant = Entity.newBuilder(taskKey)
                .set("name", organization.getOrganizationName())
                .set("email", organization.getEmail())
                .set("firstName", organization.getFirstName())
                .set("lastName", organization.getLastName())
                .set("isActive", true)
                .set("createdBy", "msciq") // TODO: Take user email from the token and set as createdBy and updatedBy
                .set("createdDt", String.valueOf(LocalDateTime.now(ZoneOffset.UTC)))
                .set("lastUpdatedBy", "msciq") // TODO: Take user email from the token and set as createdBy and updatedBy
                .set("lastUpdatedDt", String.valueOf(LocalDateTime.now(ZoneOffset.UTC)))
                .build();
        tenantDataStore.put(createdTenant);
    }
}
