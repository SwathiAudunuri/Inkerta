package com.tecnics.einvoice.entity;

import java.io.Serializable;
import javax.persistence.*;


import org.hibernate.annotations.DynamicUpdate;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the budget_details database table.
 * 
 */
/**
 * @author Gopal
 *
 */
@Entity
@Table(name = "budget_details", schema = "einvoicing")
@DynamicUpdate
@JsonIgnoreProperties
public class BudgetDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	private Integer id;

	
	@Column(name="partner_id")   
	private String partnerId;
	@Column(name="budget_name")
	   private String budgetName;
	   @Column(name="budget_category") 
	   private String budgetCategory;
	   private String budget_type;
	   private BigDecimal budget_value;
	   private String budget_occurs;
	   
		@Temporal(TemporalType.DATE)
		private Date budget_starts_on;
		
		@Temporal(TemporalType.DATE)
		private Date budget_ends_on;
		
		private String created_by;
		
		@Temporal(TemporalType.DATE)
		private Date created_on;
		
		private String budget_currency;
		

		public String getBudget_currency() {
			return budget_currency;
		}

		public void setBudget_currency(String budget_currency) {
			this.budget_currency = budget_currency;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}


		public String getBudgetName() {
			return budgetName;
		}

		public void setBudgetName(String budgetName) {
			this.budgetName = budgetName;
		}

		public String getPartnerId() {
			return partnerId;
		}

		public void setPartnerId(String partnerId) {
			this.partnerId = partnerId;
		}

		public String getBudgetCategory() {
			return budgetCategory;
		}

		public void setBudgetCategory(String budgetCategory) {
			this.budgetCategory = budgetCategory;
		}

		public String getBudget_type() {
			return budget_type;
		}

		public void setBudget_type(String budget_type) {
			this.budget_type = budget_type;
		}

		public BigDecimal getBudget_value() {
			return budget_value;
		}

		public void setBudget_value(BigDecimal budget_value) {
			this.budget_value = budget_value;
		}

		public String getBudget_occurs() {
			return budget_occurs;
		}

		public void setBudget_occurs(String budget_occurs) {
			this.budget_occurs = budget_occurs;
		}

		public Date getBudget_starts_on() {
			return budget_starts_on;
		}

		public void setBudget_starts_on(Date budget_starts_on) {
			this.budget_starts_on = budget_starts_on;
		}

		public Date getBudget_ends_on() {
			return budget_ends_on;
		}

		public void setBudget_ends_on(Date budget_ends_on) {
			this.budget_ends_on = budget_ends_on;
		}

		public String getCreated_by() {
			return created_by;
		}

		public void setCreated_by(String created_by) {
			this.created_by = created_by;
		}

		public Date getCreated_on() {
			return created_on;
		}

		public void setCreated_on(Date created_on) {
			this.created_on = created_on;
		}
	 
	
}
