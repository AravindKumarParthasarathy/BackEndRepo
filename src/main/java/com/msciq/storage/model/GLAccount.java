package com.msciq.storage.model;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.msciq.storage.common.FieldConstants;
import com.msciq.storage.common.TableConstants;
import com.msciq.storage.common.ErrorMessage;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;

@Data
@Entity
@Table(name = TableConstants.TABLE_GL_ACCOUNT)
public class GLAccount extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = ErrorMessage.ERROR_GL_ACCOUNT_IMPORT)
    @Column(name = FieldConstants.GL_ACCOUNT_GROUP_CODE_NAME)
    private String glGroupCodeName;
    @Column(name = FieldConstants.GL_ACCOUNT)
    private Long glAccount;
    @Column(name = FieldConstants.ACCOUNT_DESCRIPTION)
    private String accountDescription;
    @Column(name = FieldConstants.ACCOUNT_TYPE)
    private String accountType;
    @Column(name = FieldConstants.ACTIVE_FROM_DATE)
    private Timestamp activeFromDate;
    @Column(name = FieldConstants.INACTIVE_FLAG)
    private boolean inactiveFlag = false;
    @Column(name = FieldConstants.INACTIVE_DATE)
    private Timestamp inactiveDate;
    @Column(name = FieldConstants.FORECASTING_RELEVANT)
    private boolean forecastingRelevant = false;
    @Column(name = FieldConstants.FORECASTED_AT_HIGHER_GROUP)
    private boolean forecastedAtHigherGroup = false;
    @Column(name = FieldConstants.FORECASTING_SPECIFIC_ACCOUNT)
    private boolean forecastingSpecificAccount = false;

    @Column(name = FieldConstants.SOURCE_SYSTEM)
    private String sourceSystem;

}
