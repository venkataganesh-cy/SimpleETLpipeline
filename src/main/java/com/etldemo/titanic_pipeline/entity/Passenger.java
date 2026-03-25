package com.etldemo.titanic_pipeline.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
@Data
@Entity
@Table(name = "passengers")
public class Passenger {

	@Id
    private Integer passengerId;

    private Boolean survived;
    private Integer pclass;

    @Column(length = 512)
    private String name;

    private String sex;
    private BigDecimal age;

    @Column(name = "sib_sp")
    private Integer sibSp;

    private Integer parch;
    private String ticket;
    private BigDecimal fare;
    private String cabin;
    private String embarked;

    @Column(name = "age_group")
    private String ageGroup;

    @Column(name = "transformed_at")
    private LocalDateTime transformedAt;
	
}
