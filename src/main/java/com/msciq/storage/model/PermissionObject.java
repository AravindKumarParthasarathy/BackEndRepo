package com.msciq.storage.model;

import com.msciq.storage.common.FieldConstants;
import com.msciq.storage.common.TableConstants;
import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = TableConstants.TABLE_PERMISSION_OBJECT)
public class PermissionObject extends BaseEntity{
    @Id
    @Column(name = FieldConstants.ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true)
    private String name;
}
