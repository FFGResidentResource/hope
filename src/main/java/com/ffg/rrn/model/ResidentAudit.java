/**
 * 
 */
package com.ffg.rrn.model;

import java.util.Date;

import lombok.Data;

/**
 * @author FFGTeam
 *
 */
@Data
public class ResidentAudit {

	public ResidentAudit(Long auditId, Long residentId, Boolean active, Boolean isResident, Integer refType,
			String firstName, String middle, String lastName, Integer propId, Boolean viaVoicemail, Boolean viaText,
			Boolean viaEmail, String voicemail, String text, String email, String address, Boolean ackRightsToPrivacy,
			Boolean allowContact, Boolean wantsSurvey, Boolean photoRelease, Date auditDate, String modifiedBy,
			String serviceCoord, String age, String primaryLanguage, String maritalStatus, String annualGross,
			String gender, String ethnicity, String race, String hoh, String veteran, String disability,
			String returningCzOrExOffender, String ssi, String ssdi, String healthCoverage, String highestEdu,
			String safeDay, String safeNight, String occupancyLength, String interestedInResCouncil,
			String modeOfTransport, String experiencingFoodShortage, String internetAccess, String hohType,
			String unempReason, String barrierToEducation, String healthCondition, String psdyouth, String psdadult) {

		super();
		this.auditId = auditId;
		this.residentId = residentId;
		this.active = active;
		this.isResident = isResident;
		this.refType = refType;
		this.firstName = firstName;
		this.middle = middle;
		this.lastName = lastName;
		this.propId = propId;
		this.viaVoicemail = viaVoicemail;
		this.viaText = viaText;
		this.viaEmail = viaEmail;
		this.voicemail = voicemail;
		this.text = text;
		this.email = email;
		this.address = address;
		this.ackRightsToPrivacy = ackRightsToPrivacy;
		this.allowContact = allowContact;
		this.wantsSurvey = wantsSurvey;
		this.photoRelease = photoRelease;
		this.auditDate = auditDate;
		this.modifiedBy = modifiedBy;
		this.serviceCoord = serviceCoord;
		this.age = age;
		this.primaryLanguage = primaryLanguage;
		this.maritalStatus = maritalStatus;
		this.annualGross = annualGross;
		this.gender = gender;
		this.ethnicity = ethnicity;
		this.race = race;
		this.hoh = hoh;
		this.veteran = veteran;
		this.disability = disability;
		this.returningCzOrExOffender = returningCzOrExOffender;
		this.ssi = ssi;
		this.ssdi = ssdi;
		this.healthCoverage = healthCoverage;
		this.highestEdu = highestEdu;
		this.safeDay = safeDay;
		this.safeNight = safeNight;
		this.occupancyLength = occupancyLength;
		this.interestedInResCouncil = interestedInResCouncil;
		this.modeOfTransport = modeOfTransport;
		this.experiencingFoodShortage = experiencingFoodShortage;
		this.internetAccess = internetAccess;
		this.hohType = hohType;
		this.unempReason = unempReason;
		this.barrierToEducation = barrierToEducation;
		this.healthCondition = healthCondition;
		this.psdyouth = psdyouth;
		this.psdadult = psdadult;
	}

	private Long auditId;
	private Long residentId;
	private Boolean active;
	private Boolean isResident;
	private Integer refType;
	private String firstName;
	private String middle;
	private String lastName;
	private Integer propId;
	private Boolean viaVoicemail;
	private Boolean viaText;
	private Boolean viaEmail;
	private String voicemail;
	private String text;
	private String email;
	private String address;
	private Boolean ackRightsToPrivacy;
	private Boolean allowContact;
	private Boolean wantsSurvey;
	private Boolean photoRelease;
	private Date auditDate;
	private String modifiedBy;
	private String serviceCoord;
	private String age;
	private String primaryLanguage;
	private String maritalStatus;
	private String annualGross;
	private String gender;
	private String ethnicity;
	private String race;
	private String hoh;
	private String veteran;
	private String disability;
	private String returningCzOrExOffender;
	private String ssi;
	private String ssdi;
	private String healthCoverage;
	private String highestEdu;
	private String safeDay;
	private String safeNight;
	private String occupancyLength;
	private String interestedInResCouncil;
	private String modeOfTransport;
	private String experiencingFoodShortage;
	private String internetAccess;
	private String hohType;
	private String unempReason;
	private String barrierToEducation;
	private String healthCondition;
	private String psdyouth;
	private String psdadult;

}
