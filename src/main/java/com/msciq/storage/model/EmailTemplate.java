package com.msciq.storage.model;

// Importing required classes
import com.google.cloud.spring.data.datastore.core.mapping.Entity;
import lombok.*;

// Annotations
@Entity
@ToString
@AllArgsConstructor
@Data
@EqualsAndHashCode
@NoArgsConstructor
@Builder
public class EmailTemplate {

    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;
    private String recipientName;

    private String tenantName;
}

