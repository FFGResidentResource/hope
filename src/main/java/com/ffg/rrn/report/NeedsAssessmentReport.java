package com.ffg.rrn.report;

import com.ffg.rrn.model.Property;

import java.util.List;


public class NeedsAssessmentReport {

    private int numberOfResidents;
    private int percentage;

    Property properties;
    private Demographic demographic;
    private EmploymentAndEducation employmentAndEducation;
    private Health health;
    private Safety safety;
    private DesiredProgramsAndServices desiredProgramsAndServices;

    private class Demographic{
        private String primaryLanguage;
        private String householdType;
        private boolean headOfHousehold;
        private String Race;
        private String Ethnicity;
        private String Gender;
        private int income;
        private int age;
        private boolean DisabilityStatus;
        private int ageRange;
        private String ModeOfTransportation;
        private boolean ExperienceFoodShortages;
        private String periodExperienceFoodShortages;
        private boolean internetAccess;
        private String internetAccessType;

        /*consider percantage of assessment completion  and average household size*/

    }
    private class EmploymentAndEducation{
        public String EmploymentStatus;
        public String PrimaryReasonForUnemployment;
        private boolean headOfHousehold;
        private String HighestLevelOfEducation;
        private String primaryBarrierToCollegeOrTraining;
    }
    private class Health{
        private boolean hasHealthInsurance;
        private String HealthConditions;
    }
    private class Safety{
        private String feelsSafeAtHomeDay;
        private String feelsSafeAtHomeNight;
        private boolean interestedInCouncil;
        private int lengthOfOccupancy;
    }
    private class DesiredProgramsAndServices{

        private class programsAndServicesDesiredForYouth{
            private String artsAndCrafts;
            private String afterschoolProgram;
            private String computerEducation;
            private String delinqencyPrevention;
            private String drugPrevention;
            private String fieldTrips;
            private String gedEsl;
            private String jobs;
            private String mentoring;
            private String music;
            private String sports;
            private String tutoring;
        }
        private class programsAndServicesDesiredForAdult{
            private String collegePrep;
            private String counselling;
            private String emergencyAssist;
            private String gedEsl;
            private String homeOwnershipEducation;
            private String jobs;
            private String smallBusinessDevelopment;
            private String vocationalEducation;

        }
    }

}
