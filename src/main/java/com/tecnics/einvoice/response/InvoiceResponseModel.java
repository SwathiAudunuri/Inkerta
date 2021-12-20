package com.tecnics.einvoice.response;

public class InvoiceResponseModel {
	
		private String InvoiceNumber ;
		private String 	Doc_Ref_No;
		private boolean hasError ;
		private String errorCode ;
		private String errorMessage;
		
	
		
		public String getInvoiceNumber() {
			return InvoiceNumber;
		}
		public void setInvoiceNumber(String invoiceNumber) {
			InvoiceNumber = invoiceNumber;
		}
		public String getDoc_Ref_No() {
			return Doc_Ref_No;
		}
		public void setDoc_Ref_No(String doc_Ref_No) {
			Doc_Ref_No = doc_Ref_No;
		}
		public boolean getHasError() {
			return hasError;
		}
		public void setHasError(boolean hasError) {
			this.hasError = hasError;
		}
		public String getErrorCode() {
			return errorCode;
		}
		public void setErrorCode(String errorCode) {
			this.errorCode = errorCode;
		}
		public String getErrorMessage() {
			return errorMessage;
		}
		public void setErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
		}
		
		
		@Override
		public String toString() {
			return "InvoiceResponseModel [InvoiceNumber=" + InvoiceNumber + ", Doc_Ref_No=" + Doc_Ref_No + ", hasError="
					+ hasError + ", errorCode=" + errorCode + ", errorMessage=" + errorMessage + "]";
		}
		
}
