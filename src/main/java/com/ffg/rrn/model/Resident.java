/**
 * 
 */
package com.ffg.rrn.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.ffg.rrn.utils.AppConstants;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.*;
import java.text.ParseException;
import java.util.Date;
import java.util.List;


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
}
