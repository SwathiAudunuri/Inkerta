package com.tecnics.einvoice.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the invoice_eway_bill_details database table.
 * 
 */
@Entity
@Table(name="invoice_eway_bill_details", schema = "einvoicing")
public class InvoiceEwayBillDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer transdistance;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	int id; 

	@Temporal(TemporalType.DATE)
	private Date transdocdate;
	private String transdocno;
	private String transmode;
	private String transporter_id;
	private String transportername;
	private String vehicle_type;
	private String vehicleno;
	private String document_ref_id;
	
	public InvoiceEwayBillDetail() {
		super();
	}

	public InvoiceEwayBillDetail(Integer transdistance, int id, Date transdocdate, String transdocno, String transmode,
			String transporter_id, String transportername, String vehicle_type, String vehicleno,
			String document_ref_id) {
		super();
		this.transdistance = transdistance;
		this.id = id;
		this.transdocdate = transdocdate;
		this.transdocno = transdocno;
		this.transmode = transmode;
		this.transporter_id = transporter_id;
		this.transportername = transportername;
		this.vehicle_type = vehicle_type;
		this.vehicleno = vehicleno;
		this.document_ref_id = document_ref_id;
	}

	public Integer getTransdistance() {
		return transdistance;
	}

	public void setTransdistance(Integer transdistance) {
		this.transdistance = transdistance;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getTransdocdate() {
		return transdocdate;
	}

	public void setTransdocdate(Date transdocdate) {
		this.transdocdate = transdocdate;
	}

	public String getTransdocno() {
		return transdocno;
	}

	public void setTransdocno(String transdocno) {
		this.transdocno = transdocno;
	}

	public String getTransmode() {
		return transmode;
	}

	public void setTransmode(String transmode) {
		this.transmode = transmode;
	}

	public String getTransporter_id() {
		return transporter_id;
	}

	public void setTransporter_id(String transporter_id) {
		this.transporter_id = transporter_id;
	}

	public String getTransportername() {
		return transportername;
	}

	public void setTransportername(String transportername) {
		this.transportername = transportername;
	}

	public String getVehicle_type() {
		return vehicle_type;
	}

	public void setVehicle_type(String vehicle_type) {
		this.vehicle_type = vehicle_type;
	}

	public String getVehicleno() {
		return vehicleno;
	}

	public void setVehicleno(String vehicleno) {
		this.vehicleno = vehicleno;
	}

	public String getDocument_ref_id() {
		return document_ref_id;
	}

	public void setDocument_ref_id(String document_ref_id) {
		this.document_ref_id = document_ref_id;
	}

	@Override
	public String toString() {
		return "InvoiceEwayBillDetail [transdistance=" + transdistance + ", id=" + id + ", transdocdate=" + transdocdate
				+ ", transdocno=" + transdocno + ", transmode=" + transmode + ", transporter_id=" + transporter_id
				+ ", transportername=" + transportername + ", vehicle_type=" + vehicle_type + ", vehicleno=" + vehicleno
				+ ", document_ref_id=" + document_ref_id + "]";
	}

}