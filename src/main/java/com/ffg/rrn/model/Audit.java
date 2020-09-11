/**
 * 
 */
package com.ffg.rrn.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

/**
 * @author risha
 *
 */
@Data
@JsonView
public class Audit {
	
	private Long resIdForAudit;
	
	private List<ResidentAudit> auditListPerResident;

}
