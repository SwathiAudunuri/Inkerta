package com.tecnics.einvoice.util;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;


public class SOAPWebServiceClient {

	//This method sends a SOAP request and prints response, and parses and prints texts of all <balance> elements in the response 
	public String postSOAPXML(String soapBody,String url,String soapAction,String userName,String password) throws Exception {
        String resp = null;
        try {
        
           // System.out.println("SOAP Body=" + soapBody );
            CredentialsProvider provider = new BasicCredentialsProvider();
            UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(userName, password);
            provider.setCredentials(AuthScope.ANY, credentials);
             
            HttpClient httpclient = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
            
          // HttpClient httpclient = new DefaultHttpClient();
            // You can get below parameters from SoapUI's Raw request if you are using that tool
            StringEntity strEntity = new StringEntity(soapBody, "text/xml", "UTF-8");
            // URL of request
            HttpPost post = new HttpPost(url);
            post.setHeader("SOAPAction", "soapAction");
            post.setEntity(strEntity);

            // Execute request
      
            HttpResponse response = httpclient.execute(post);
            
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println("status code = " + statusCode);
            if (statusCode != 200) 
            {
                throw new RuntimeException("Failed with HTTP error code : " + statusCode);
            }
            
            HttpEntity respEntity = response.getEntity();

            if (respEntity != null) {
                resp = EntityUtils.toString(respEntity);
				
                //prints whole response
                System.out.println("Response from SOAP Service= "+ resp);
               
                //Regular expression to parse texts of <balance> elements in the response assuming they have no child elements
              /*  Matcher m = Pattern.compile("<balance>([^<]*)").matcher(resp); //groups all characters except < (tag closing character)
                while (m.find()) {
                 
                  System.out.println(m.group(1));
                } */
            } else {
                System.err.println("No Response");
            }

        } catch (Exception e) {
            System.err.println("WebService SOAP exception = " + e.toString());
            e.printStackTrace();
            throw new Exception(e.getLocalizedMessage());
        }
        
        return resp;
    }

	public static void main(String[] args) throws Exception {
		
	/*	POSTING commented
	  String soapBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:sap-com:document:sap:soap:functions:mc-style\">\r\n"
    			+ "   <soapenv:Header/>\r\n"
    			+ "   <soapenv:Body>\r\n"
    			+ "      <urn:ZapPoautopost>\r\n"
    			+ "         <IAmount>16999</IAmount>\r\n"
    			+ "         <IAmountType></IAmountType>\r\n"
    			+ "         <IAssignmnt></IAssignmnt>\r\n"
    			+ "         <IBaselineDate>25.04.2016</IBaselineDate>\r\n"
    			+ "         <IBusinessPlace></IBusinessPlace>\r\n"
    			+ "         <ICalculateTax>X</ICalculateTax>\r\n"
    			+ "         <ICaseid></ICaseid>\r\n"
    			+ "         <ICompanyCode>IN47</ICompanyCode>\r\n"
    			+ "         <ICurrency>INR</ICurrency>\r\n"
    			+ "         <IDeleveryNotes>\r\n"
    			+ "            <item>\r\n"
    			+ "               <DeleveryNote>5000001470-2016</DeleveryNote>\r\n"
    			+ "               <PoNum>4500022515</PoNum>\r\n"
    			+ "               <PoItem></PoItem>\r\n"
    			+ "               <Quantity></Quantity>\r\n"
    			+ "               <Taxcode>V0</Taxcode>\r\n"
    			+ "               <HsnCode></HsnCode>\r\n"
    			+ "               <Dummy1></Dummy1>\r\n"
    			+ "               <Dummy2></Dummy2>\r\n"
    			+ "               <Dummy3></Dummy3>\r\n"
    			+ "            </item>\r\n"
    			+ "         </IDeleveryNotes>\r\n"
    			+ "         <IDocType>KR</IDocType>\r\n"
    			+ "         <IDueOnDate></IDueOnDate>\r\n"
    			+ "         <IDummy1></IDummy1>\r\n"
    			+ "         <IDummy2></IDummy2>\r\n"
    			+ "         <IDummy3></IDummy3>\r\n"
    			+ "         <IDummy4></IDummy4>\r\n"
    			+ "         <IDummy5></IDummy5>\r\n"
    			+ "         <IDummy6></IDummy6>\r\n"
    			+ "         <IExchRate></IExchRate>\r\n"
    			+ "         <IGstPartnr>CIN-VEN</IGstPartnr>\r\n"
    			+ "         <IHeaderTxt>HEADER</IHeaderTxt>\r\n"
    			+ "         <IHouseBank></IHouseBank>\r\n"
    			+ "         <IInputType></IInputType>\r\n"
    			+ "         <IInvParty></IInvParty>\r\n"
    			+ "         <IInvoiceDate>24.04.2016</IInvoiceDate>\r\n"
    			+ "         <IInvoiceType></IInvoiceType>\r\n"
    			+ "         <IPmtMethod></IPmtMethod>\r\n"
    			+ "         <IPmtRef></IPmtRef>\r\n"
    			+ "         <IPoNum>4500022515</IPoNum>\r\n"
    			+ "         <IPostingDate>24.04.2016</IPostingDate>\r\n"
    			+ "         <IReconAc></IReconAc>\r\n"
    			+ "         <IRefDocNum>REF-001</IRefDocNum>\r\n"
    			+ "         <IRefKey1></IRefKey1>\r\n"
    			+ "         <IRefKey2></IRefKey2>\r\n"
    			+ "         <IRefKey3></IRefKey3>\r\n"
    			+ "         <ISectionCode></ISectionCode>\r\n"
    			+ "         <ISupplyPlace></ISupplyPlace>\r\n"
    			+ "         <ITaxcode>V0</ITaxcode>\r\n"
    			+ "         <IText>TEST</IText>\r\n"
    			+ "         <IUnplndDlvCost></IUnplndDlvCost>\r\n"
    			+ "         <IVendorCode>CIN-VEN</IVendorCode>\r\n"
    			+ "         <IWithHoldingTax>\r\n"
    			+ "            <item>\r\n"
    			+ "               <WiTaxCode></WiTaxCode>\r\n"
    			+ "            </item>\r\n"
    			+ "         </IWithHoldingTax>\r\n"
    			+ "      </urn:ZapPoautopost>\r\n"
    			+ "   </soapenv:Body>\r\n"
    			+ "</soapenv:Envelope>"; 
    			
    	String url="http://SAPEHP7.SAPZINDIA.COM:8000/sap/bc/srt/rfc/sap/zappoautopost01/800/zappoautopost01/zappoautopost01";
		String soapAction="urn:sap-com:document:sap:soap:functions:mc-style:ZAPPOAUTOPOST01:ZapPoautopostRequest";
		String userName="sapuser";
		String password="India@123";
		
		SOAPWebServiceClient soapWebServiceClientObject = new SOAPWebServiceClient();
		String response=soapWebServiceClientObject.postSOAPXML(soapBody,url,soapAction,userName,password);
		System.out.println("Response = " + response);
    			
    			*/
		
		/*  FETCHING
		 * 
		 */
		String soapBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:sap-com:document:sap:soap:functions:mc-style\">\r\n"
				+ "   <soapenv:Header/>\r\n"
				+ "   <soapenv:Body>\r\n"
				+ "      <urn:ZapPodatafetch>\r\n"
				+ "         <!--Optional:-->\r\n"
				+ "         <IDummy1></IDummy1>\r\n"
				+ "         <!--Optional:-->\r\n"
				+ "         <IDummy2></IDummy2>\r\n"
				+ "         <!--Optional:-->\r\n"
				+ "         <IDummy3></IDummy3>\r\n"
				+ "         <!--Optional:-->\r\n"
				+ "         <IDummy4></IDummy4>\r\n"
				+ "         <!--Optional:-->\r\n"
				+ "         <IDummy5></IDummy5>\r\n"
				+ "         <!--Optional:-->\r\n"
				+ "         <IGrnNumber></IGrnNumber>\r\n"
				+ "         <!--Optional:-->\r\n"
				+ "         <IGrnYear></IGrnYear>\r\n"
				+ "         <!--Optional:-->\r\n"
				+ "         <IInputType></IInputType>\r\n"
				+ "         <!--Optional:-->\r\n"
				+ "         <IPonumber>4500022515</IPonumber>\r\n"
				+ "         <!--Optional:-->\r\n"
				+ "         <ISesNumber></ISesNumber>\r\n"
				+ "      </urn:ZapPodatafetch>\r\n"
				+ "   </soapenv:Body>\r\n"
				+ "</soapenv:Envelope>";
		
		
		String url="http://SAPEHP7.SAPZINDIA.COM:8000/sap/bc/srt/rfc/sap/zappodetailsfetch01/800/zappodetailsfetch01/zappodetailsfetch01";
		            
		String soapAction="urn:sap-com:document:sap:soap:functions:mc-style:zappodetailsfetch01:ZapPodatafetchRequest";
		String userName="sapuser";
		String password="India@123";
		
		SOAPWebServiceClient soapWebServiceClientObject = new SOAPWebServiceClient();
		String response=soapWebServiceClientObject.postSOAPXML(soapBody,url,soapAction,userName,password);
		System.out.println("Response = " + response);
    }
}