/**
 * 
 */
package com.ffg.rrn.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

/**
 * @author FFGRRNTeam
 * 
 *         RESIDENT_ID BIGINT PRIMARY KEY NOT NULL, ACTIVE BOOLEAN DEFAULT
 *         FALSE, FIRST_NAME VARCHAR(20), MIDDLE VARCHAR(20), LAST_NAME
 *         VARCHAR(20), PROP_ID INT REFERENCES PROERTY(PROP_ID), VOICEMAIL_NO
 *         VARCHAR(20), TEXT_NO VARCHAR(20), EMAIL VARCHAR(128), ALLOW_CONTACT
 *         BOOLEAN DEFAULT FALSE, WANTS_SURVEY BOOLEAN DEFAULT FALSE,
 *         PHOTO_RELEASE BOOLEAN DEFAULT FALSE, DATE_ADDED TIMESTAMP DEFAULT
 *         NOW(), DATE_MODIFIED TIMESTAMP, MODIFIED_BY INT REFERENCES
 *         SERVICE_COORDINATOR(SC_ID), SERVICE_COORD INT REFERENCES
 *         SERVICE_COORDINATOR(SC_ID)
 **/

@Data
@JsonView
public class Resident {

	private Long residentId;
	private Boolean active;
	private Boolean isResident;
	@NotEmpty
	@Size(max = 20)
	private String firstName;
	private String middle;
	@NotEmpty
	@Size(max = 20)
	private String lastName;

	@NotNull
	private Integer propertyId;
	private String propertyName;

	@NotNull
	private Integer refId;
	private String refValue;

	private Integer aId;
	private String aValue;

	private String voiceMail;
	private String text;

	@Email
	@Size(max = 128)
	private String email;
	private String address;

	@AssertTrue
	private Boolean ackRightToPrivacy;

	private Boolean viaVoicemail;
	private Boolean viaText;
	private Boolean viaEmail;

	private Boolean allowContact;
	private Boolean wantSurvey;
	private Boolean photoRelease;

	private Date dateAdded;
	private Date dateModified;
	private String modifiedBy;
	private String serviceCoord;

	private String childList;

	@Size(max = 50)
	private String child1;
	@Size(max = 50)
	private String child2;
	@Size(max = 50)
	private String child3;
	@Size(max = 50)
	private String child4;
	@Size(max = 50)
	private String child5;
	@Size(max = 50)
	private String child6;
	@Size(max = 50)
	private String child7;
	@Size(max = 50)
	private String child8;

	private Boolean pvrChild1;
	private Boolean pvrChild2;
	private Boolean pvrChild3;
	private Boolean pvrChild4;
	private Boolean pvrChild5;
	private Boolean pvrChild6;
	private Boolean pvrChild7;
	private Boolean pvrChild8;

	private WizardStepCounter wsCounter;

	@JsonView
	private List<Property> propertyList;

	@JsonView
	private List<AssessmentType> atList;

	@JsonView
	private List<Referral> refList;

	@JsonView
	private List<ResidentAssessmentQuestionnaire> housingQuestionnaire;

	@JsonView
	private List<ResidentAssessmentQuestionnaire> moneyMgmtQuestionnaire;

	@JsonView
	private List<ResidentAssessmentQuestionnaire> employmentQuestionnaire;

	@JsonView
	private List<ResidentAssessmentQuestionnaire> educationQuestionnaire;

	@JsonView
	private List<ResidentAssessmentQuestionnaire> netSupportQuestionnaire;

	@JsonView
	private List<ResidentAssessmentQuestionnaire> householdMgmtQuestionnaire;

	@JsonView
	private List<ResidentScoreGoal> residentScoreGoalList;

	@JsonView
	private List<String> housingDates;
	@JsonView
	private List<String> moneymgmtDates;
	@JsonView
	private List<String> employmentDates;
	@JsonView
	private List<String> educationDates;
	@JsonView
	private List<String> netSupportDates;
	@JsonView
	private List<String> householdDates;

	private String selectedDate;

	private Integer currentScore;

	private Integer goal;
	
	private String lifeDomain;
	
	private String housingScoreGoal;
	
	private String moneyMgmtScoreGoal;
	
	private String employmentScoreGoal;
	
	private String educationScoreGoal;
		
	private String netSupportScoreGoal;
	
	private String householdScoreGoal;
	
	private String mostRecentSSMDate;
	
	// ActionPlan Fields -Begin

	// This is JSON String with selected ActionPlan
	private String focusOnActionPlan;

	private String residentReportedConcern;

	// This contains JSON String for each Domain
	private String planOfAction;

	// This contains JSON String for each Domain
	private String anticipatedOutcome;

	private String followUpNotes;
	// This contains JSON String for each Domain
	private String outcomesAchieved;

	private Date outcomeDate;

	private Date actionPlanAddedDate;

	// ActionPlan Fields -End

	public Resident() {

	}

	public Resident(List<Property> propertyList, List<AssessmentType> atList, List<Referral> refList,
			String serviceCoord) {
		this.propertyList = propertyList;
		this.atList = atList;
		this.refList = refList;
		this.serviceCoord = serviceCoord;
	}

	public Resident(Long residentId, Boolean active, String firstName, String middle, String lastName,
			String propertyName, String voiceMail, String text, String email, Boolean allowContact, Boolean wantSurvey,
			Boolean photoRelease, Date dateAdded, String serviceCoord) {
		super();
		this.residentId = residentId;
		this.active = active;
		this.firstName = firstName;
		this.middle = middle;
		this.lastName = lastName;
		this.propertyName = propertyName;
		this.voiceMail = voiceMail;
		this.text = text;
		this.email = email;
		this.allowContact = allowContact;
		this.wantSurvey = wantSurvey;
		this.photoRelease = photoRelease;
		this.dateAdded = dateAdded;
		this.serviceCoord = serviceCoord;
	}

}
