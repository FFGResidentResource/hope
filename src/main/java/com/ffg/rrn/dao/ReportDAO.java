package com.ffg.rrn.dao;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ReportDAO extends JdbcDaoSupport {
    /* to prepare query for report variables */
    private static final String SQL_LIST_PROPERTY = "" ;
    private static final String SQL_TOTAL_PROPERTY ="";
    private static final String SQL_SHOW_AVAILABLE_YEAR = "";
    private static final String SQL_GET_QUARTER = "";
    private static final String SQL_GET_LANGUAGES_SPOKEN = "";
    private static final String SQL_GET_LANGUAGES_COUNT = "";
    private static final String GET_HOUSEHOLD_TYPE = "";
    private static final String GET_HOUSEHOLD_COUNT_BY_TYPE = "";
    private static final String GET_RACE_TYPE = "";
    private static final String GET_COUNT_RACE_BY_TYPE = "";
    private static final String GET_ETHNICITY_BY_TYPE = "";
    private static final String GET_ETHNICITY_COUNT_BY_TYPE = "";
    private static final String GET_AGE_RANGE = "";
    private static final String GET_GENDER_COUNT="";
    private static final String GET_DISABILITY_STATUS = "";
    private static final String GET_TRANSPORTATION_MODE_COUNT="";
    private static final String GET_FOOD_SHORTAGE_PERIOD = "";
    private static final String GET_INTERNET_ACCESS_MODE = "";
    private static final String GET_PREFERRED_MODE_OF_CONTACT = "";
}
