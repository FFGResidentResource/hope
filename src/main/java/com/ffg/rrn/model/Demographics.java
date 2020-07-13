package com.ffg.rrn.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Data
@Component
public class Demographics {

    private Long residentId;
    private Long propertyId;
    private Long questionId;
    private Boolean isResident;
    private String address;
    private Long choiceId;
    private String type;
    private String choice;
    private Boolean active;
    private Integer refId;
    private Boolean ackRightToPrivacy;
    private Boolean viaVoicemail;
    private Boolean viaText;
    private Boolean viaEmail;
    private Boolean allowContact;
    private Boolean wantSurvey;
    private Boolean photoRelease;
    private Date dateAdded;
    private Date dateModified;
    private String serviceCoord;

    public Demographics(Long questionId, Long choiceId, String type, String choice, Long residentId, Boolean isResident, Boolean active, String firstName, String lastName, String middle, Long propertyId, String propertyName, String voiceMail, String text, Integer refId, String email, String address, Boolean ackRightToPrivacy, Boolean viaVoicemail, Boolean viaText, Boolean viaEmail, Boolean allowContact, Boolean wantSurvey, Boolean photoRelease, Date dateAdded, Date dateModified, String serviceCoord) {
        this.questionId = questionId;
        this.choiceId = choiceId;
        this.type = type;
        this.choice = choice;
        this.residentId = residentId;
        this.isResident = isResident;
        this.active = active;
        this.propertyId = propertyId;
        this.refId = refId;
        this.ackRightToPrivacy = ackRightToPrivacy;
        this.viaVoicemail = viaVoicemail;
        this.viaText = viaText;
        this.viaEmail = viaEmail;
        this.allowContact = allowContact;
        this.wantSurvey = wantSurvey;
        this.photoRelease = photoRelease;
        this.dateAdded = dateAdded;
        this.dateModified = dateModified;
        this.serviceCoord = serviceCoord;
    }


    public Demographics(){}

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(Long choiceId) {
        this.choiceId = choiceId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public Long getResidentId() {
        return residentId;
    }

    public void setResidentId(Long residentId) {
        this.residentId = residentId;
    }

    public Boolean getResident() {
        return isResident;
    }

    public void setResident(Boolean resident) {
        isResident = resident;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public Integer getRefId() {
        return refId;
    }

    public void setRefId(Integer refId) {
        this.refId = refId;
    }

    public Boolean getAckRightToPrivacy() {
        return ackRightToPrivacy;
    }

    public void setAckRightToPrivacy(Boolean ackRightToPrivacy) {
        this.ackRightToPrivacy = ackRightToPrivacy;
    }

    public Boolean getViaVoicemail() {
        return viaVoicemail;
    }

    public void setViaVoicemail(Boolean viaVoicemail) {
        this.viaVoicemail = viaVoicemail;
    }

    public Boolean getViaText() {
        return viaText;
    }

    public void setViaText(Boolean viaText) {
        this.viaText = viaText;
    }

    public Boolean getViaEmail() {
        return viaEmail;
    }

    public void setViaEmail(Boolean viaEmail) {
        this.viaEmail = viaEmail;
    }

    public Boolean getAllowContact() {
        return allowContact;
    }

    public void setAllowContact(Boolean allowContact) {
        this.allowContact = allowContact;
    }

    public Boolean getWantSurvey() {
        return wantSurvey;
    }

    public void setWantSurvey(Boolean wantSurvey) {
        this.wantSurvey = wantSurvey;
    }

    public Boolean getPhotoRelease() {
        return photoRelease;
    }

    public void setPhotoRelease(Boolean photoRelease) {
        this.photoRelease = photoRelease;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public String getServiceCoord() {
        return serviceCoord;
    }

    public void setServiceCoord(String serviceCoord) {
        this.serviceCoord = serviceCoord;
    }

    public boolean typeEqualsLanguage(){
       return this.getType().equalsIgnoreCase("Language");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Demographics)) return false;
        Demographics that = (Demographics) o;
        return getResidentId().equals(that.getResidentId()) &&
                Objects.equals(getQuestionId(), that.getQuestionId()) &&
                Objects.equals(getChoiceId(), that.getChoiceId()) &&
                Objects.equals(getType(), that.getType()) &&
                Objects.equals(getChoice(), that.getChoice()) &&
                Objects.equals(getAddress(), that.getAddress()) &&
                Objects.equals(getIsResident(), that.getIsResident()) &&
                Objects.equals(getActive(), that.getActive()) &&
                getPropertyId().equals(that.getPropertyId()) &&
                Objects.equals(getRefId(), that.getRefId()) &&
                Objects.equals(getAckRightToPrivacy(), that.getAckRightToPrivacy()) &&
                Objects.equals(getViaVoicemail(), that.getViaVoicemail()) &&
                Objects.equals(getViaText(), that.getViaText()) &&
                Objects.equals(getViaEmail(), that.getViaEmail()) &&
                Objects.equals(getAllowContact(), that.getAllowContact()) &&
                Objects.equals(getWantSurvey(), that.getWantSurvey()) &&
                Objects.equals(getPhotoRelease(), that.getPhotoRelease()) &&
                Objects.equals(getDateAdded(), that.getDateAdded()) &&
                Objects.equals(getDateModified(), that.getDateModified()) &&
                Objects.equals(getServiceCoord(), that.getServiceCoord());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getResidentId(), getQuestionId(), getChoiceId(), getType(), getChoice(), getIsResident(), getActive(), getPropertyId(), getRefId(), getAckRightToPrivacy(), getViaVoicemail(), getViaText(), getViaEmail(), getAllowContact(), getWantSurvey(), getPhotoRelease(), getDateAdded(), getDateModified(), getServiceCoord());
    }

    @Override
    public String toString() {
        return "Demographics{" +
                "residentId=" + residentId +
                ", questionId=" + questionId +
                ", choiceId=" + choiceId +
                ", type='" + type + '\'' +
                ", choice='" + choice + '\'' +
                ", isResident=" + isResident +
                ", active=" + active +
                ", address=" + address +
                ", propertyId=" + propertyId +
                ", refId=" + refId +
                ", ackRightToPrivacy=" + ackRightToPrivacy +
                ", viaVoicemail=" + viaVoicemail +
                ", viaText=" + viaText +
                ", viaEmail=" + viaEmail +
                ", allowContact=" + allowContact +
                ", wantSurvey=" + wantSurvey +
                ", photoRelease=" + photoRelease +
                ", dateAdded=" + dateAdded +
                ", dateModified=" + dateModified +
                ", serviceCoord='" + serviceCoord + '\'' +
                '}';
    }
}
