package de.kit.bpmn.ccit.entities;

import java.io.Serializable;
import java.util.Date;

public class ApplicationEntity implements Serializable {

	private static final long serialVersionUID = 5662586968100171746L;
	// vvvvvvv DON'T CHANGE vvvvvvv //
	private String caseId;
	private String patientName;
	private Integer patientAge;
        private Boolean hasPreconditions;
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
	public String getCaseId() {
		return caseId;
	}

	//
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

        public String getPatientName() {
            return patientName;
        }

        public void setPatientName(String patientName) {
            this.patientName = patientName;
        }

        public Integer getPatientAge() {
            return patientAge;
        }

        public void setPatientAge(Integer patientAge) {
            this.patientAge = patientAge;
        }

        public Boolean getHasPreconditions() {
            return hasPreconditions;
        }

        public void setHasPreconditions(Boolean hasPreconditions) {
            this.hasPreconditions = hasPreconditions;
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
