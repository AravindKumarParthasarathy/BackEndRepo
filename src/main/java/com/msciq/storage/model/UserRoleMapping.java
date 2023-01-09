package com.msciq.storage.model;

import com.msciq.storage.common.FieldConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@Table(name = "user_role", uniqueConstraints = { @UniqueConstraint(columnNames = { "userId", "roleName"}) })
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleMapping extends BaseEntity{

    @Id
    @Column(name = FieldConstants.ID)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userId;

    private String roleName;
}
