package com.tecnics.einvoice.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;
import java.sql.Timestamp;


@Entity
@Table(name="todo_tasks", schema = "einvoicing")
public class TodoTask implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="task_ref_id")
	private Integer taskRefId;

	@Column(name="assigned_to")
	private String assignedTo;
	
	@CreationTimestamp
	@Column(name = "created_on",nullable = false, updatable = false, insertable = false)
	private Timestamp createdOn;

	private String description;

	@Temporal(TemporalType.DATE)
	@Column(name="due_date")
	private Date dueDate;

	private boolean flag;

	private String priority;

	private String status;

	private String title;
	
	@Column(name="created_by")
	private String createdBy;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "todoTask")
	private List<TaskActivity> taskActivities;

	public TodoTask() {
	}

	public String getAssignedTo() {
		return this.assignedTo;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}


	public Timestamp getCreatedOn() {
		return this.createdOn;
	}

	public String getDescription() {
		return this.description;
	}

	public Date getDueDate() {
		return this.dueDate;
	}

	public String getPriority() {
		return this.priority;
	}

	public String getStatus() {
		return this.status;
	}

	public List<TaskActivity> getTaskActivities() {
		return taskActivities;
	}

	public Integer getTaskRefId() {
		return this.taskRefId;
	}

	public String getTitle() {
		return this.title;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setTaskActivities(List<TaskActivity> taskActivities) {
		this.taskActivities = taskActivities;
	}

	public void setTaskRefId(Integer taskRefId) {
		this.taskRefId = taskRefId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "TodoTask [taskRefId=" + taskRefId + ", assignedTo=" + assignedTo + ", createdOn=" + createdOn
				+ ", description=" + description + ", dueDate=" + dueDate + ", flag=" + flag + ", priority=" + priority
				+ ", status=" + status + ", title=" + title + ", createdBy=" + createdBy + ", taskActivities="
				+ taskActivities + "]";
	}



}