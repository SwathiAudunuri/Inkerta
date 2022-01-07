package com.tecnics.einvoice.model;

import java.util.List;

import com.tecnics.einvoice.entity.AttachmentDetails;
import com.tecnics.einvoice.entity.PartnerContactDetails;
import com.tecnics.einvoice.entity.PartnerDetails;
import com.tecnics.einvoice.entity.PartnerGSTINDetails;

public class PartnerProfileModel {
	
	private PartnerDetails partnerDetails;
	private List<PartnerContactDetails> partnerContactDetails;
	private List<PartnerGSTINDetails> partnerGSTINDetails;
	private List<AttachmentDetails> attachmentDetails;
	
	public List<AttachmentDetails> getAttachmentDetails() {
		return attachmentDetails;
	}
	public void setAttachmentDetails(List<AttachmentDetails> attachmentDetails) {
		this.attachmentDetails = attachmentDetails;
	}
	public List<PartnerGSTINDetails> getPartnerGSTINDetails() {
		return partnerGSTINDetails;
	}
	public void setPartnerGSTINDetails(List<PartnerGSTINDetails> partnerGSTINDetails) {
		this.partnerGSTINDetails = partnerGSTINDetails;
	}
	public PartnerDetails getPartnerDetails() {
		return partnerDetails;
	}
	public void setPartnerDetails(PartnerDetails partnerDetails) {
		this.partnerDetails = partnerDetails;
	}
	public List<PartnerContactDetails> getPartnerContactDetails() {
		return partnerContactDetails;
	}
	public void setPartnerContactDetails(List<PartnerContactDetails> partnerContactDetails) {
		this.partnerContactDetails = partnerContactDetails;
	}
	

}
