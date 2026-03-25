package com.etldemo.titanic_pipeline.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "staging_passengers")
public class StagingPassenger {
	
	@jakarta.persistence.Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@Column(name = "passenger_id")
    private Integer passengerId;

    private String survived;
    private String pclass;

    @Column(length = 512)
    private String name;

    private String sex;
    private String age;

    @Column(name = "sib_sp")
    private String sibSp;

    private String parch;
    private String ticket;
    private String fare;
    private String cabin;
    private String embarked;

    @Column(name = "loaded_at")
    private LocalDateTime loadedAt;

    @Column(name = "is_valid")
    private Boolean isValid;

}
