package com.tecnics.einvoice.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "task_activities", schema = "einvoicing")
public class TodoTaskActivity {
	
	private int task_ref_id;
	
	private String action_by;
	
	private Timestamp action_on;
	
	private String action;
	
	private String comments;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	public int getTask_ref_id() {
		return task_ref_id;
	}
	

	public Timestamp getAction_on() {
		return action_on;
	}


	public void setAction_on(Timestamp action_on) {
		this.action_on = action_on;
	}


	public void setTask_ref_id(int task_ref_id) {
		this.task_ref_id = task_ref_id;
	}

	public String getAction_by() {
		return action_by;
	}

	public void setAction_by(String action_by) {
		this.action_by = action_by;
	}


	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TodoTaskActivity(int task_ref_id, String action_by, Timestamp action_on, String action, String comments,
			int id) {
		super();
		this.task_ref_id = task_ref_id;
		this.action_by = action_by;
		this.action_on = action_on;
		this.action = action;
		this.comments = comments;
		this.id = id;
	}

	public TodoTaskActivity() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "TodoTaskActivity [task_ref_id=" + task_ref_id + ", action_by=" + action_by + ", action_on=" + action_on
				+ ", action=" + action + ", comments=" + comments + ", id=" + id + "]";
	}

}
