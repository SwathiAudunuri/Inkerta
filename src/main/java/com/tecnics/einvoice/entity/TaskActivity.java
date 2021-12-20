package com.tecnics.einvoice.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the task_activities database table.
 * 
 */
@Entity
@Table(name="task_activities", schema = "einvoicing")
public class TaskActivity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(length=25)
	private String action;

	@Column(name="action_on")
	private Timestamp actionOn;

	@Column(length=250)
	private String comments;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable=false)
	private Integer id;
	
	@Column(name="action_by")
	private String actionBy;
	
	
	public String getActionBy() {
		return actionBy;
	}

	public void setActionBy(String actionBy) {
		this.actionBy = actionBy;
	}

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="task_ref_id")
	private TodoTask todoTask;
//
//	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@JoinColumn(name="action_by")
//	private UserManagement userManagement;

	public TaskActivity() {
	}

//	public TodoTask getTodoTask() {
//		return todoTask;
//	}

	public void setTodoTask(TodoTask todoTask) {
		this.todoTask = todoTask;
	}

	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Timestamp getActionOn() {
		return this.actionOn;
	}

	public void setActionOn(Timestamp actionOn) {
		this.actionOn = actionOn;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

//	public UserManagement getUserManagement() {
//		return this.userManagement;
//	}
//
//	public void setUserManagement(UserManagement userManagement) {
//		this.userManagement = userManagement;
//	}

}