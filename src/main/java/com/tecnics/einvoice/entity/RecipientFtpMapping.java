package com.tecnics.einvoice.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the recipient_ftp_mapping database table.
 * 
 */
@Entity
@Table(name="recipient_ftp_mapping", schema = "einvoicing")
public class RecipientFtpMapping implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="ftp_location")
	private String ftpLocation;

	@Column(name="ftp_server")
	private String ftpServer;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String password;

	@Column(name="user_name")
	private String userName;

	//bi-directional many-to-one association to RecipientMapping
	@ManyToOne
	@JoinColumn(name="recipient_id")
	private RecipientMapping recipientMapping;

	public RecipientFtpMapping() {
	}

	public String getFtpLocation() {
		return this.ftpLocation;
	}

	public void setFtpLocation(String ftpLocation) {
		this.ftpLocation = ftpLocation;
	}

	public String getFtpServer() {
		return this.ftpServer;
	}

	public void setFtpServer(String ftpServer) {
		this.ftpServer = ftpServer;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
//
//	public RecipientMapping getRecipientMapping() {
//		return this.recipientMapping;
//	}

	public void setRecipientMapping(RecipientMapping recipientMapping) {
		this.recipientMapping = recipientMapping;
	}

}