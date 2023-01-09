package com.msciq.storage.model;

import com.msciq.storage.common.FieldConstants;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;

@Entity
@Table(name = "role_permission", uniqueConstraints = { @UniqueConstraint(columnNames = { "role_name", "permission" }) })
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@ToString
@AllArgsConstructor
@Data
@EqualsAndHashCode
@NoArgsConstructor
public class RolePermissionMapping extends BaseEntity{

   @Id
   @Column(name = FieldConstants.ID)
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;
    @Column(name = FieldConstants.ROLE_NAME)
    private String roleName;

    @Column(name = FieldConstants.PERMISSION_OBJECT)
    private String permissionObject;
    /*@Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Role role;
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private PermissionObject permissionObject;*/
    @Column(name = FieldConstants.CAN_READ)
    private boolean read;
    @Column(name = FieldConstants.CAN_UPDATE)
    private boolean update;
    @Column(name = FieldConstants.CAN_CREATE)
    private boolean create;
    @Column(name = FieldConstants.CAN_DELETE)
    private boolean delete;
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private ControlData controlData;
}
