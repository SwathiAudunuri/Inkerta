package com.tecnics.einvoice.model;

import java.util.List;

import com.tecnics.einvoice.Repo.DshbrdOpenInvoicesDueRepo.OpenDueInvoicesResults;
public class DshbrdOpenInvoicesDue {

	private List<OpenDueInvoicesResults> unpaidtotals;
	private List<OpenDueInvoicesResults> overduetotals;
	private List<OpenDueInvoicesResults> overduelate01to30daystotals;
	private List<OpenDueInvoicesResults> overduelate31to90daystotals;
	private List<OpenDueInvoicesResults> overdueover90daystotals;
	public List<OpenDueInvoicesResults> getUnpaidtotals() {
		return unpaidtotals;
	}
	public void setUnpaidtotals(List<OpenDueInvoicesResults> unpaidtotals) {
		this.unpaidtotals = unpaidtotals;
	}
	public List<OpenDueInvoicesResults> getOverduetotals() {
		return overduetotals;
	}
	public void setOverduetotals(List<OpenDueInvoicesResults> overduetotals) {
		this.overduetotals = overduetotals;
	}

	public List<OpenDueInvoicesResults> getOverdueover90daystotals() {
		return overdueover90daystotals;
	}
	public void setOverdueover90daystotals(List<OpenDueInvoicesResults> overdueover90daystotals) {
		this.overdueover90daystotals = overdueover90daystotals;
	}
	public List<OpenDueInvoicesResults> getOverduelate01to30daystotals() {
		return overduelate01to30daystotals;
	}
	public void setOverduelate01to30daystotals(List<OpenDueInvoicesResults> overduelate01to30daystotals) {
		this.overduelate01to30daystotals = overduelate01to30daystotals;
	}
	public List<OpenDueInvoicesResults> getOverduelate31to90daystotals() {
		return overduelate31to90daystotals;
	}
	public void setOverduelate31to90daystotals(List<OpenDueInvoicesResults> overduelate31to90daystotals) {
		this.overduelate31to90daystotals = overduelate31to90daystotals;
	}
	
	
	

}
