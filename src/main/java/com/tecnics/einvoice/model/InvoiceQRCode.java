package com.tecnics.einvoice.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

//@JsonRootName(value = "data")
//@JsonIgnoreProperties(ignoreUnknown = true)

//@JsonIgnoreProperties(ignoreUnknown = true) 
public class InvoiceQRCode implements Serializable{
	private static final long serialVersionUID = 1L;
	

	private String SellerGstin;

	private String BuyerGstin;

	private String DocNo;

	private String DocTyp;

	private String DocDt;

	private Integer TotInvVal;

	private Integer ItemCnt;

	private String MainHsnCode;

	private String Irn;

	private String IrnDt;
	
	private String jwtQRCode;
	
	
	
	public String getJwtQRCode() {
		return jwtQRCode;
	}

	public void setJwtQRCode(String jwtQRCode) {
		this.jwtQRCode = jwtQRCode;
	}

	public InvoiceQRCode()
	{
		System.out.println("Here 2");
	}
	
	@JsonProperty("SellerGstin")
	public String getSellerGstin() {
		return SellerGstin;
	}
	public void setSellerGstin(String SellerGstin) {
		this.SellerGstin = SellerGstin;
	}
	@JsonProperty("BuyerGstin")
	public String getBuyerGstin() {
		return BuyerGstin;
	}
	public void setBuyerGstin(String BuyerGstin) {
		this.BuyerGstin = BuyerGstin;
	}
	@JsonProperty("DocNo")
	public String getDocNo() {
		return DocNo;
	}
	public void setDocNo(String DocNo) {
		this.DocNo = DocNo;
	}
	@JsonProperty("DocTyp")
	public String getDocTyp() {
		return DocTyp;
	}
	public void setDocTyp(String DocTyp) {
		this.DocTyp = DocTyp;
	}
	@JsonProperty("DocDt")
	public String getDocDt() {
		return DocDt;
	}
	public void setDocDt(String DocDt) {
		this.DocDt = DocDt;
	}
	@JsonProperty("TotInvVal")
	public Integer getTotInvVal() {
		return TotInvVal;
	}
	public void setTotInvVal(Integer TotInvVal) {
		this.TotInvVal = TotInvVal;
	}
	@JsonProperty("ItemCnt")
	public Integer getItemCnt() {
		return ItemCnt;
	}
	public void setItemCnt(Integer ItemCnt) {
		this.ItemCnt = ItemCnt;
	}
	@JsonProperty("MainHsnCode")
	public String getMainHsnCode() {
		return MainHsnCode;
	}
	public void setMainHsnCode(String MainHsnCode) {
		this.MainHsnCode = MainHsnCode;
	}
	@JsonProperty("Irn")
	public String getIrn() {
		return Irn;
	}
	public void setIrn(String Irn) {
		this.Irn = Irn;
	}
	@JsonProperty("IrnDt")
	public String getIrnDt() {
		return IrnDt;
	}
	public void setIrnDt(String IrnDt) {
		this.IrnDt = IrnDt;
	}

	
	
	
	  @Override
	    public String toString(){
	        return "Json Response [SellerGstin ="+ SellerGstin +", BuyerGstin =" + BuyerGstin + 
	        		", DocNo = " + DocNo + ", DocTyp =" + DocTyp + 
	        		", DocDt = " + DocDt + ", TotInvVal =" + TotInvVal + 
	        		", ItemCnt = " + ItemCnt + ", MainHsnCode =" + MainHsnCode + 
	        		", Irn = " + Irn + ", IrnDt =" + IrnDt + " ]";
	    }

	

}
