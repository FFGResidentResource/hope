package com.ffg.rrn.model;

import lombok.Data;

import java.util.Date;

@Data
public class ActionPlan {

	private int actionPlanId;
	
	private long residentId;
	
	private String residentConcern;
	
	private Boolean active;

	private String focusOnDomain;

	private String planOfAction;

	private String anticipatedOutcomes;

	private String outcomeAchieved;

	private Date outcomeDate;

	private String followupNotes;

	private Date dateAdded;

	private Date dateModified;

    private String serviceCoord;

	public ActionPlan(int actionPlanId, long residentId,
					  String residentConcern, Boolean active,
					  String focusOnDomain, Date outcomeDate,
					  String followupNotes, Date dateAdded,
					  Date dateModified, String serviceCoord) {
		this.actionPlanId = actionPlanId;
		this.residentId = residentId;
		this.residentConcern = residentConcern;
		this.active = active;
		this.focusOnDomain = focusOnDomain;
		this.outcomeDate = outcomeDate;
		this.followupNotes = followupNotes;
		this.dateAdded = dateAdded;
		this.dateModified = dateModified;
		this.serviceCoord = serviceCoord;
	}
}
