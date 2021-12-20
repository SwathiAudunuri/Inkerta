package com.tecnics.einvoicejson.model;

public class InvoiceEwayBillDetails {
	 public String transporter_id;
	 public String transportername;
	 public int transdistance;
	 public String transdocno;
	 public String transdocdate;
	 public String vehicleno;
	 public String vehicle_type;
	 public String transmode;
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
	public int getTransdistance() {
		return transdistance;
	}
	public void setTransdistance(int transdistance) {
		this.transdistance = transdistance;
	}
	public String getTransdocno() {
		return transdocno;
	}
	public void setTransdocno(String transdocno) {
		this.transdocno = transdocno;
	}
	public String getTransdocdate() {
		return transdocdate;
	}
	public void setTransdocdate(String transdocdate) {
		this.transdocdate = transdocdate;
	}
	public String getVehicleno() {
		return vehicleno;
	}
	public void setVehicleno(String vehicleno) {
		this.vehicleno = vehicleno;
	}
	public String getVehicle_type() {
		return vehicle_type;
	}
	public void setVehicle_type(String vehicle_type) {
		this.vehicle_type = vehicle_type;
	}
	public String getTransmode() {
		return transmode;
	}
	public void setTransmode(String transmode) {
		this.transmode = transmode;
	}
	@Override
	public String toString() {
		return "InvoiceEwayBillDetails [transporter_id=" + transporter_id + ", transportername=" + transportername
				+ ", transdistance=" + transdistance + ", transdocno=" + transdocno + ", transdocdate=" + transdocdate
				+ ", vehicleno=" + vehicleno + ", vehicle_type=" + vehicle_type + ", transmode=" + transmode + "]";
	}
	public InvoiceEwayBillDetails(String transporter_id, String transportername, int transdistance, String transdocno,
			String transdocdate, String vehicleno, String vehicle_type, String transmode) {
		super();
		this.transporter_id = transporter_id;
		this.transportername = transportername;
		this.transdistance = transdistance;
		this.transdocno = transdocno;
		this.transdocdate = transdocdate;
		this.vehicleno = vehicleno;
		this.vehicle_type = vehicle_type;
		this.transmode = transmode;
	}
	public InvoiceEwayBillDetails() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
