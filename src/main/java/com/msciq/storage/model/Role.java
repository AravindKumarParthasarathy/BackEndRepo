package com.msciq.storage.model;

import com.msciq.storage.common.FieldConstants;
import com.msciq.storage.common.TableConstants;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <p>
 * User role for an employee is used to authorise.
 * </p>
 * 
 * @author Rajkumar created on Jun 30, 2022
 */

@Data
@Entity
@Table(name = TableConstants.TABLE_ROLE)
public class Role extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 2254198222527717773L;

	@Id
	@Column(name = FieldConstants.ID)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	// @Column(name = FieldConstants.DESCRIPTION)
	// private String description;
	@Column(unique=true)
	private String name;

	@Column(name = FieldConstants.LEVEL)
	private String level;



}
