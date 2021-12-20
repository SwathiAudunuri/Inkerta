package com.tecnics.einvoice.test;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="test", schema = "einvoicing")
public class TestEntity {
	
	
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.DATE)
	private Date dob;

	private String name;

	@Id
	private BigDecimal rollnumber;

	public TestEntity() {
	}

	public Date getDob() {
		return this.dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getRollnumber() {
		return this.rollnumber;
	}

	public void setRollnumber(BigDecimal rollnumber) {
		this.rollnumber = rollnumber;
	}
	
	
	
	

}
