package com.tecnics.einvoice.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the company_rating database table.
 * 
 */
@Entity
@Table(name = "company_rating", schema = "einvoicing")
public class CompanyRating implements Serializable {
private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Integer id;


	@Column(name = "to_partner_id")
	private String toPartnerId;
	 
		@Column(name = "from_partner_id")
		private String fromPartnerId;
		
		@Column(name = "system_rating")
		private String systemRating;
		
		@Column(name = "partner_rating")
		private String partnerRating;
		
		private Timestamp last_updated_on;
		
		private String updated_by;
		
		public CompanyRating() {
			super();
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getToPartnerId() {
			return toPartnerId;
		}

		public void setToPartnerId(String toPartnerId) {
			this.toPartnerId = toPartnerId;
		}

		public String getFromPartnerId() {
			return fromPartnerId;
		}

		public void setFromPartnerId(String fromPartnerId) {
			this.fromPartnerId = fromPartnerId;
		}

		public String getSystemRating() {
			return systemRating;
		}

		public void setSystemRating(String systemRating) {
			this.systemRating = systemRating;
		}

		public String getPartnerRating() {
			return partnerRating;
		}

		public void setPartnerRating(String partnerRating) {
			this.partnerRating = partnerRating;
		}

		public Timestamp getLast_updated_on() {
			return last_updated_on;
		}

		public void setLast_updated_on(Timestamp last_updated_on) {
			this.last_updated_on = last_updated_on;
		}

		public String getUpdated_by() {
			return updated_by;
		}

		public void setUpdated_by(String updated_by) {
			this.updated_by = updated_by;
		}
		
		

	
}
