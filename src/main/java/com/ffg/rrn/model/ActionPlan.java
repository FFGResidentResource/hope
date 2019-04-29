package com.ffg.rrn.model;

import lombok.Data;
import org.postgresql.util.PGobject;

import java.util.Date;

@Data
public class ActionPlan {

	private int actionPlanId;
	
	private long residentId;
	
	private String residentConcern;
	
	private Boolean active;

	private PGobject focusOnDomain;

	private PGobject planOfAction;

	private PGobject anticipatedOutcomes;

	private PGobject outcomeAchieved;

	private Date outcomeDate;

	private String followupNotes;

	private Date dateAdded;

	private Date dateModified;

    private String serviceCoord;

	public ActionPlan(int actionPlanId, long residentId, String residentConcern, Boolean active, PGobject focusOnDomain, PGobject planOfAction, PGobject anticipatedOutcomes, PGobject outcomeAchieved, Date outcomeDate, String followupNotes, Date dateAdded, Date dateModified, String serviceCoord) {
		this.actionPlanId = actionPlanId;
		this.residentId = residentId;
		this.residentConcern = residentConcern;
		this.active = active;
		this.focusOnDomain = focusOnDomain;
		this.planOfAction = planOfAction;
		this.anticipatedOutcomes = anticipatedOutcomes;
		this.outcomeAchieved = outcomeAchieved;
		this.outcomeDate = outcomeDate;
		this.followupNotes = followupNotes;
		this.dateAdded = dateAdded;
		this.dateModified = dateModified;
		this.serviceCoord = serviceCoord;
	}
}
