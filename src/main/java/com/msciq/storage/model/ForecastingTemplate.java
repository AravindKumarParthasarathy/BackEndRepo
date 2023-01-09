package com.msciq.storage.model;

import com.msciq.storage.common.FieldConstants;
import com.msciq.storage.common.TableConstants;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;

@Data
@Table(name = TableConstants.FORECASTING_TEMPLATE)
@Entity
@EqualsAndHashCode
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
public class ForecastingTemplate extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = com.msciq.storage.common.FieldConstants.ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = FieldConstants.TEMPLATE_CODE)
    private String templateCode;

    @Column(name = FieldConstants.TEMPLATE_NAME)
    private String templateName;

    @Column(name = FieldConstants.TEMPLATE_TYPE)
    private String type;

    @Column(name = FieldConstants.VERSION)
    private String version;

    @Column(name = FieldConstants.TEMPLATE_VALUE, columnDefinition = "jsonb")
    @Type(type = "jsonb")
    private String templateValue;

}
