package com.thoughtworks.kanjuice.restService.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private static final String EMP_ID = "empId";
    private static final String INTERNAL_NUMBER = "internalNumber";
    private static final String EMPLOYEE_NAME = "employeeName";

    @Id
    @JsonProperty
    private String _id;

    @Field(EMP_ID)
    @JsonProperty
    public String empId;

    @Field(INTERNAL_NUMBER)
    @JsonProperty
    public String internalNumber;

    @Field(EMPLOYEE_NAME)
    @JsonProperty
    public String employeeName;

    public User() {
    }

    public User(String empId, String internalNumber, String employeeName) {
        this.empId = empId;
        this.internalNumber = internalNumber;
        this.employeeName = employeeName;
    }

    public String getEmpId() {
        return empId;
    }

    public String getInternalNumber() {
        return internalNumber;
    }

    public String getEmployeeName() {
        return employeeName;
    }
}
