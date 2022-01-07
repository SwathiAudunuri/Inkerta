package com.tecnics.einvoice.entity;


import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the partner_details database table.
 * 
 */
@Entity
@Table(name = "partner_gstin_details", schema = "einvoicing")

public class PartnerGSTINDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String gstin;

	private String bnm;

	private String bno;

	private String city;

	private String ctb;

	private String ctj;

	private String ctjcd;

	private String cxdt;

	private String dst;

	private String dty;

	private String filingfrequency;

	private String flno;

	private String lg;

	private String lgnm;

	private String loc;

	private String lstupdt;

	private String lt;

	private String nba;

	private String panno;

	@Column(name="partner_id")
	private String partnerId;

	private String pncd;

	private String rgdt;

	private String st;

	private String stcd;

	private String stj;

	private String stjcd;

	private String sts;

	private String tradename;
	
	@Transient
	private String[] natureofbusiness_arr;
	
	
	public String[] getNatureofbusiness_arr() {
		return natureofbusiness_arr;
	}

	public void setNatureofbusiness_arr(String[] natureofbusiness_arr) {
		this.natureofbusiness_arr = natureofbusiness_arr;
		if(this.natureofbusiness_arr.length>=1)
			this.setNba(String.join(",", this.getNatureofbusiness_arr()));			
	}
	
	
	public PartnerGSTINDetails() {
	}

	public String getGstin() {
		return this.gstin;
	}

	public void setGstin(String gstin) {
		this.gstin = gstin;
	}

	public String getBnm() {
		return this.bnm;
	}

	public void setBnm(String bnm) {
		this.bnm = bnm;
	}

	public String getBno() {
		return this.bno;
	}

	public void setBno(String bno) {
		this.bno = bno;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCtb() {
		return this.ctb;
	}

	public void setCtb(String ctb) {
		this.ctb = ctb;
	}

	public String getCtj() {
		return this.ctj;
	}

	public void setCtj(String ctj) {
		this.ctj = ctj;
	}

	public String getCtjcd() {
		return this.ctjcd;
	}

	public void setCtjcd(String ctjcd) {
		this.ctjcd = ctjcd;
	}

	public String getCxdt() {
		return this.cxdt;
	}

	public void setCxdt(String cxdt) {
		this.cxdt = cxdt;
	}

	public String getDst() {
		return this.dst;
	}

	public void setDst(String dst) {
		this.dst = dst;
	}

	public String getDty() {
		return this.dty;
	}

	public void setDty(String dty) {
		this.dty = dty;
	}

	public String getFilingfrequency() {
		return this.filingfrequency;
	}

	public void setFilingfrequency(String filingfrequency) {
		this.filingfrequency = filingfrequency;
	}

	public String getFlno() {
		return this.flno;
	}

	public void setFlno(String flno) {
		this.flno = flno;
	}

	public String getLg() {
		return this.lg;
	}

	public void setLg(String lg) {
		this.lg = lg;
	}

	public String getLgnm() {
		return this.lgnm;
	}

	public void setLgnm(String lgnm) {
		this.lgnm = lgnm;
	}

	public String getLoc() {
		return this.loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	public String getLstupdt() {
		return this.lstupdt;
	}

	public void setLstupdt(String lstupdt) {
		this.lstupdt = lstupdt;
	}

	public String getLt() {
		return this.lt;
	}

	public void setLt(String lt) {
		this.lt = lt;
	}

	public String getNba() {
		return this.nba;
	}

	public void setNba(String nba) {
		this.nba = nba;
	}

	public String getPanno() {
		return this.panno;
	}

	public void setPanno(String panno) {
		this.panno = panno;
	}

	public String getPartnerId() {
		return this.partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getPncd() {
		return this.pncd;
	}

	public void setPncd(String pncd) {
		this.pncd = pncd;
	}

	public String getRgdt() {
		return this.rgdt;
	}

	public void setRgdt(String rgdt) {
		this.rgdt = rgdt;
	}

	public String getSt() {
		return this.st;
	}

	public void setSt(String st) {
		this.st = st;
	}

	public String getStcd() {
		return this.stcd;
	}

	public void setStcd(String stcd) {
		this.stcd = stcd;
	}

	public String getStj() {
		return this.stj;
	}

	public void setStj(String stj) {
		this.stj = stj;
	}

	public String getStjcd() {
		return this.stjcd;
	}

	public void setStjcd(String stjcd) {
		this.stjcd = stjcd;
	}

	public String getSts() {
		return this.sts;
	}

	public void setSts(String sts) {
		this.sts = sts;
	}

	public String getTradename() {
		return this.tradename;
	}

	public void setTradename(String tradename) {
		this.tradename = tradename;
	}

}