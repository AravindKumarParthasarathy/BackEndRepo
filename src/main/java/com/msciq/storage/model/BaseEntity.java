package com.msciq.storage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.msciq.storage.common.Constants;
import com.msciq.storage.common.FieldConstants;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * The persistent class for the Base Entity.
 * 
 * @author Rajkumar created on Jun 30, 2022
 */
@Data
@MappedSuperclass
public class BaseEntity {


	@Column(name = FieldConstants.CREATED_BY, updatable = false, nullable = false)
	@ApiModelProperty(hidden = true)
	private Long createdBy = Long.valueOf(1);

	@Column(name = FieldConstants.UPDATED_BY, nullable = true)
	@ApiModelProperty(hidden = true)
	private Long updatedBy =  Long.valueOf(1);

	@Column(name = FieldConstants.CREATED_AT, columnDefinition = Constants.TIMESTAMP, nullable = false, updatable = false)
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(hidden = true)
	private Date createdAt;

	@Column(name = FieldConstants.UPDATED_AT, columnDefinition = Constants.TIMESTAMP)
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(hidden = true)
	private Date updatedAt;

	@Column(name = FieldConstants.IS_ACTIVE)
	private boolean isActive = true;

	@Column(name = FieldConstants.IS_DELETED)
	@JsonIgnore
	@ApiModelProperty(hidden = true)
	private boolean isDeleted = false;


}
