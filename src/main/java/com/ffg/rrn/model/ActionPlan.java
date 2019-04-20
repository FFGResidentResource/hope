package com.ffg.rrn.model;

import lombok.Data;

@Data
public class ActionPlan {

	private int actionPlanId;
	
	private int residentId;
	
	private String residentConcern;
	
	private Boolean active;

	public ActionPlan(int actionPlanId, int residentId, String residentConcern, Boolean active) {
		super();
		this.actionPlanId = actionPlanId;
		this.residentId = residentId;
		this.residentConcern = residentConcern;
		this.active = active;
	}
	
	
	
}
