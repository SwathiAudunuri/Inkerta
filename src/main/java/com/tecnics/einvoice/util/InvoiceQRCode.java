package com.tecnics.einvoice.util;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;

//@JsonRootName(value = "data")
//@JsonIgnoreProperties(ignoreUnknown = true)

@JsonIgnoreProperties(ignoreUnknown = true) 
public class InvoiceQRCode implements Serializable {
	
	public String SellerGstin;
	public String BuyerGstin;
	public String DocNo;
	public String DocTyp;
	public String DocDt;
	public Integer TotInvVal;
	public Integer ItemCnt;
	public String MainHsnCode;
	public String Irn;
	public String IrnDt;
	
	/*public InvoiceQRCode(String jsonStr)
	{
		
		System.out.println("json Str= " +jsonStr );
	}*/
	
	/*public InvoiceQRCode(String SellerGstin,String BuyerGstin, String DocNo, String DocTyp, String DocDt,  Integer TotInvVal, 
			Integer ItemCnt ,String MainHsnCode,String Irn,String IrnDt,String iss)
	{
		System.out.println("Here 1");
		this.SellerGstin=SellerGstin;
		this.BuyerGstin=BuyerGstin;
		this.DocNo=DocNo;
		this.DocTyp=DocTyp;
		this.DocDt=DocDt;
		this.TotInvVal=TotInvVal;
		this.ItemCnt=ItemCnt;
		this.MainHsnCode=MainHsnCode;
		this.Irn=Irn;
		this.IrnDt=IrnDt;
		
		
				
	}
	*/
	
	public InvoiceQRCode()
	{
		System.out.println("Here 2");
	}
	
	public String getSellerGstin() {
		return SellerGstin;
	}
	public void setSellerGstin(String sellerGstin) {
		SellerGstin = sellerGstin;
	}
	public String getBuyerGstin() {
		return BuyerGstin;
	}
	public void setBuyerGstin(String buyerGstin) {
		BuyerGstin = buyerGstin;
	}
	public String getDocNo() {
		return DocNo;
	}
	public void setDocNo(String docNo) {
		DocNo = docNo;
	}
	public String getDocTyp() {
		return DocTyp;
	}
	public void setDocTyp(String docTyp) {
		DocTyp = docTyp;
	}
	public String getDocDt() {
		return DocDt;
	}
	public void setDocDt(String docDt) {
		DocDt = docDt;
	}
	public Integer getTotInvVal() {
		return TotInvVal;
	}
	public void setTotInvVal(Integer totInvVal) {
		TotInvVal = totInvVal;
	}
	public Integer getItemCnt() {
		return ItemCnt;
	}
	public void setItemCnt(Integer itemCnt) {
		ItemCnt = itemCnt;
	}
	public String getMainHsnCode() {
		return MainHsnCode;
	}
	public void setMainHsnCode(String mainHsnCode) {
		MainHsnCode = mainHsnCode;
	}
	public String getIrn() {
		return Irn;
	}
	public void setIrn(String irn) {
		Irn = irn;
	}
	public String getIrnDt() {
		return IrnDt;
	}
	public void setIrnDt(String irnDt) {
		IrnDt = irnDt;
	}

	
	public String toString() {
        return "[" + SellerGstin + " " + BuyerGstin +
               " " + Irn +"]";
    }
	
	

}
