/**
 * 
 */
package com.ffg.rrn.model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonView;
import com.ffg.rrn.utils.AppConstants;

import lombok.Data;


/**
 * @author FFGRRNTeam
 * 
 */
@Data
@JsonView
public class Resident {

	private Long residentId;
	private Boolean active;
	@NotNull(message = "'Is Resident' is Required!")
	private Boolean isResident;
	@NotEmpty(message = "First Name is Required!")
	@Size(max = 20,message = "The length of First Name can not be more than 20.")
	private String firstName;
	private String middle;
	@NotEmpty(message = "Last Name is Required!")
	@Size(max = 20,message = "The length of Last Name can not be more than 20.")
	private String lastName;

	@NotNull(message = "Property is Required!")
	private Integer propertyId;
	private String propertyName;

	@NotNull(message = "Referral is Required!")
	private Integer refId;
	private String refValue;

	private Integer aId;
	private String aValue;

	private String voiceMail;
	private String text;

	@Email(message = "Email must be a valid format.")
	@Size(max = 128,message = "The length of Last Name can not be more than 128.")
	private String email;

	@NotEmpty(message = "Address is Required!")
	private String address;

	@AssertTrue(message = "Must check \"I acknowledge that I was informed of my right to privacy\".")
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

	@Size(max = 50,message = "The length of child1 full name can not be more than 50.")
	private String child1;
	@Size(max = 50,message = "The length of child2 full name can not be more than 50.")
	private String child2;
	@Size(max = 50,message = "The length of child3 full name can not be more than 50.")
	private String child3;
	@Size(max = 50,message = "The length of child4 full name can not be more than 50.")
	private String child4;
	@Size(max = 50,message = "The length of child5 full name can not be more than 50.")
	private String child5;
	@Size(max = 50,message = "The length of child6 full name can not be more than 50.")
	private String child6;
	@Size(max = 50,message = "The length of child7 full name can not be more than 50.")
	private String child7;
	@Size(max = 50,message = "The length of child8 full name can not be more than 50.")
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

	@JsonView
	private List<String> actionPlanDates;

	@JsonView
	private List<String> contactNoteDates;

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

	// This contains JSON String for each Domain
	private String planOfAction;
	// This contains JSON String for each Domain
	private String anticipatedOutcome;
	@JsonView
	private List<String> anticipatedOutcomesList;
	// --this variable is used in referral form as well.
	private String followUpNotes;
	// This contains JSON String for each Domain
	private String outcomesAchieved;
	private String anticipatedDates;
	private String completionDates;
	private String planDetails;
	private String referralPartner;
	private String achievedGoals;
	private Date actionPlanDateAdded;
	private Date actionPlanDateModified;

	// ActionPlan Fields -End
	
	// Case Notes Fields - Begin
	private String description;
	private String assessment;
	private String plan;
	private Date cnDateAdded;
	private Date cnDateModified;
	// Case Notes Fields - End

	// Referral Form fields - Begin
	private boolean interpretation;
	private String referredBy;
	private String referralReason;
	private String commentsOrExplanation;
	private String previousAttempts;
	private String selfSufficiency;
	private String housingStability;
	private String safeSupportiveCommunity;
	private String rfFollowUpNotes;
	private String residentAppointmentScheduled;
	private Date referralFormDateAdded;
	private Date referralFormDateModified;
	
	private List<String> refPartners;

	// Referral Form fields - End

	//Onboarding Form Fields
	//private List<String> raceList= Arrays.asList("Asian","Black/African", "Caucasian", "Hispanic/Latino", "Native American", "Mixed Race", "Other");
	private List<String> raceList;
	private String race;
	private String age;
	//Onboarding Form Fields

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

    private String getEmptyStrOrLatestDateOfAssessment(List<String> dates) {
        return CollectionUtils.isEmpty(dates) ? StringUtils.EMPTY:dates.get(0);
    }

    private boolean ifLatestAssessmentExistsAndEarlierThanSixMonths(List<String> dates) {
        if (CollectionUtils.isEmpty(dates)) {
            return true;
        }
        return isDateBeforeSixMonths(dates.get(0));
    }
	public String getDateOfLatestHousingAssessment(){
        return getEmptyStrOrLatestDateOfAssessment(this.getHousingDates());
    }

    public boolean isNewHousingAssessmentAllowed(){
        return ifLatestAssessmentExistsAndEarlierThanSixMonths(this.getHousingDates());
    }


    public String getDateOfLatestMoneymgmtAssessment(){
        return getEmptyStrOrLatestDateOfAssessment(this.getMoneymgmtDates());
	}
	public boolean isMoneymgmtAssessmentAllowed(){
        return ifLatestAssessmentExistsAndEarlierThanSixMonths(this.getMoneymgmtDates());
    }

    public String getDateOfLatestEmploymentAssessment(){
        return getEmptyStrOrLatestDateOfAssessment(this.getEmploymentDates());
    }
    public boolean isEmploymentAssessmentAllowed(){
        return ifLatestAssessmentExistsAndEarlierThanSixMonths(this.getEmploymentDates());
    }

    public String getDateOfLatestEducationAssessment(){
        return getEmptyStrOrLatestDateOfAssessment(this.getEducationDates());
    }
    public boolean isEducationAssessmentAllowed(){
        return ifLatestAssessmentExistsAndEarlierThanSixMonths(this.getEducationDates());
    }

    public String getDateOfLatestNetSupportAssessment(){
        return getEmptyStrOrLatestDateOfAssessment(this.getNetSupportDates());
    }
    public boolean isNetSupportAssessmentAllowed(){
        return ifLatestAssessmentExistsAndEarlierThanSixMonths(this.getNetSupportDates());
    }

    public String getDateOfLatestHouseholdAssessment(){
        return getEmptyStrOrLatestDateOfAssessment(this.getHouseholdDates());
    }
    public boolean isHouseholdAssessmentAllowed(){
        return ifLatestAssessmentExistsAndEarlierThanSixMonths(this.getHouseholdDates());
    }

	/**
	 * This method was written as per Requirement but later that requirement got
	 * changed SC do wants to fill another Assessment ASAP (doesn't matter if it is
	 * within 6 month or next day)
	 * 
	 * @param dateOfLatestHouseAssessment
	 * @return
	 */
	private boolean isDateBeforeSixMonths(String dateOfLatestHouseAssessment) {
		try {
			Date lastestAssessDate = DateUtils.parseDate(dateOfLatestHouseAssessment, AppConstants.DATE_PATTERN_JAVA);
			Date today = new Date();
			return DateUtils.addMonths(lastestAssessDate,6).before(today);
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean equals(Object obj) {
		Resident other = (Resident) obj;

		if (this.residentId == other.residentId)
			return true;

		return false;
	}
}
