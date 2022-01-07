package com.tecnics.einvoice.rnd;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlToHtmlTransformer {

	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException,
			TransformerFactoryConfigurationError, TransformerException,Exception {
		String xml="<?xml version=\"1.0\" ?> "
				+ "<soap-env:Envelope "
				+ "    xmlns:soap-env=\"http://schemas.xmlsoap.org/soap/envelope/\"> "
				+ "    <soap-env:Header/> "
				+ "    <soap-env:Body> "
				+ "        <n0:ZapPodatafetchResponse"
				+ "            xmlns:n0=\"urn:sap-com:document:sap:soap:functions:mc-style\"> "
				+ "            <EDummy3/> "
				+ "            <EDummy4/> "
				+ "            <EDummy5/> "
				+ "            <EMessage/> "
				+ "            <EPaymentDays>0</EPaymentDays> "
				+ "            <EPoDetails> "
				+ "                <item> "
				+ "                    <PoNumber>4500022515</PoNumber> "
				+ "                    <GrnNumber/> "
				+ "                    <GrnYear>0000</GrnYear> "
				+ "                    <PoType>NB</PoType> "
				+ "                    <VendorCode>CIN-VEN</VendorCode> "
				+ "                    <VendorName>Mr. ABC Pvt. Ltd.</VendorName> "
				+ "                    <VendorEmail>abcpvtltd@gmail.com</VendorEmail> "
				+ "                    <VendorGstin>24C08813440YU</VendorGstin> "
				+ "                    <PurchaseGrp>000</PurchaseGrp> "
				+ "                    <BuyerEmail>Zero Input Tax Code</BuyerEmail> "
				+ "                    <Currency>INR</Currency> "
				+ "                    <PlantCode>IN47</PlantCode> "
				+ "                    <PlantName>Services</PlantName> "
				+ "                    <PaymentTerms>0001</PaymentTerms> "
				+ "                    <TaxCode>V0</TaxCode> "
				+ "                    <CompanyCode>IN47</CompanyCode> "
				+ "                    <BusinessPlace>GUJ</BusinessPlace> "
				+ "                    <SectionCode/> "
				+ "                    <RtNumber>India Model Company, IN</RtNumber> "
				+ "                    <RtDate>Marketing</RtDate> "
				+ "                    <Region>10</Region> "
				+ "                    <Costcenter/> "
				+ "                    <PurchOrg>IN47</PurchOrg> "
				+ "                    <PurchOrgDesc/> "
				+ "                    <HsnCode/> "
				+ "                    <Requester/> "
				+ "                </item> "
				+ "            </EPoDetails> "
				+ "            <ESesPostDate/> "
				+ "        </n0:ZapPodatafetchResponse> "
				+ "    </soap-env:Body> "
				+ "</soap-env:Envelope>";
		String xslt="<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?> "
				+ "<xsl:stylesheet version=\"1.0\" "
				+ "xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" "
				+ "xmlns:hm=\"http://www.hotelbeds.com/schemas/2005/06/messages\" "
				+ "exclude-result-prefixes=\"hm\"> "
				+ "<xsl:template match=\"/\"> "
				+ "  <html> "
				+ "  <body> "
				+ "    <h3>PO Details</h3> "
				+ "  <b> PONumber </b> : <xsl:value-of select=\"//EPoDetails/item/PoNumber\"/>  <br> </br> "
				+ " <b> GrnYear </b>: <xsl:value-of select=\"//EPoDetails/item/GrnYear\"/><br> </br> "
				+ "<b> PoType </b>: <xsl:value-of select=\"//EPoDetails/item/PoType\"/><br> </br> "
				+ "<b> VendorCode </b>: <xsl:value-of select=\"//EPoDetails/item/VendorCode\"/> <br> </br> "
				+ "<b> VendorName </b>: <xsl:value-of select=\"//EPoDetails/item/VendorName\"/> <br> </br> "
				+ "<b> VendorGstin </b>: <xsl:value-of select=\"//EPoDetails/item/VendorGstin\"/> <br> </br> "
				+ " "
				+ "  </body> "
				+ "  </html> "
				+ "</xsl:template> "
				+ "</xsl:stylesheet> ";
		transform(xml, xslt);
	}

	public static void transform(final String xml, final String xslt) throws SAXException, IOException,
			ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException, Exception {

		ClassLoader classloader = XmlToHtmlTransformer.class.getClassLoader();
		//InputStream xmlData = classloader.getResourceAsStream(xml);
		//URL xsltURL = classloader.getResource(xslt);

		//Document xmlDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlData);
		Document xmlDocument = loadXMLFromString(xml);
		
		//Source stylesource = new StreamSource(xsltURL.openStream(), xsltURL.toExternalForm());
		Source stylesource = new StreamSource(new StringReader(xslt));
		Transformer transformer = TransformerFactory.newInstance().newTransformer(stylesource);

		StringWriter stringWriter = new StringWriter();
		transformer.transform(new DOMSource(xmlDocument), new StreamResult(stringWriter));

		// write to file
		File file = new File("C:/Users/Gopal/Desktop/tmp1/htmltransforation/books.html");
		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(stringWriter.toString());
		bw.close();
	}
	
	public static Document loadXMLFromString(String xml) throws Exception
	{
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    InputSource is = new InputSource(new StringReader(xml));
	    return builder.parse(is);
	}

}