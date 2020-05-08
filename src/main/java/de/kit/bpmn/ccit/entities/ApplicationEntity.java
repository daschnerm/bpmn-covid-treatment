package de.kit.bpmn.ccit.entities;

import java.io.Serializable;
import java.util.Date;

public class ApplicationEntity implements Serializable {

	private static final long serialVersionUID = 5662586968100171746L;
	// vvvvvvv DON'T CHANGE vvvvvvv //
	private String applicationId;
	private String applicantName;
	private Integer applicantAge;
	private Date potentialStartDate;
	// ============================ //

	// vvv ADD NEW FIELDS HERE vvv //
	// vvv Permitted types: java.util.String, java.util.Integer, java.util.Date///

	// ============================ //

	public ApplicationEntity() {
		super();
	}

	// vvv Right Click under this Comment => Source => Generate Getters and
	// Setters... => Select All => OK//
	// vvv

	// vvvvvvv DON'T CHANGE vvvvvvv //
	public String getApplicationId() {
		return applicationId;
	}

	//
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	//
	public String getApplicantName() {
		return applicantName;
	}

	//
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	//
	public Integer getApplicantAge() {
		return applicantAge;
	}

	//
	public void setApplicantAge(Integer applicantAge) {
		this.applicantAge = applicantAge;
	}

	//
	public Date getPotentialStartDate() {
		return potentialStartDate;
	}

	//
	public void setPotentialStartDate(Date potentialStartDate) {
		this.potentialStartDate = potentialStartDate;
	}
	// ============================ //

}
