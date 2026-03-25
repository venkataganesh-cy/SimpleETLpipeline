package com.etldemo.titanic_pipeline.model;

import lombok.Data;

@Data
public class PassengerRecord {
	
	private Integer passengerId;
	private String survived;
	private String pClass;
	private String name;
    private String sex;
    private String age;
    private String sibSp;
    private String parch;
    private String ticket;
    private String fare;
    private String cabin;
    private String embarked;

}
