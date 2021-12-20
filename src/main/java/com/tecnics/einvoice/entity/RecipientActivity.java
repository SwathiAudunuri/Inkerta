package com.tecnics.einvoice.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

/**
 * The persistent class for the recipient_activities database table.
 * 
 */
@Entity
@Table(name = "recipient_activities", schema = "einvoicing")
public class RecipientActivity implements Serializable {

	@Column(name = "action_by")
	private String actionBy;

	@Column(name = "action_comments")
	private String actionComments;

	@CreationTimestamp
	@Column(name = "action_on", nullable = false, updatable = false, insertable = false)
	private Timestamp actionOn;

	@Column(name = "activity_type")
	private String activityType;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	// bi-directional many-to-one association to RecipientMapping
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "recipient_id")
	private RecipientMapping recipientMapping;

	public RecipientActivity() {
	}

	public String getActionBy() {
		return this.actionBy;
	}

	public void setActionBy(String actionBy) {
		this.actionBy = actionBy;
	}

	public String getActionComments() {
		return this.actionComments;
	}

	public void setActionComments(String actionComments) {
		this.actionComments = actionComments;
	}

	public Timestamp getActionOn() {
		return this.actionOn;
	}

	public void setActionOn(Timestamp actionOn) {
		this.actionOn = actionOn;
	}

	public String getActivityType() {
		return this.activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setRecipientMapping(RecipientMapping recipientMapping) {
		this.recipientMapping = recipientMapping;
	}

}