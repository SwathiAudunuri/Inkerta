package com.tecnics.einvoice.util;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;

import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import com.tecnics.einvoice.model.InvoiceQRCodeData;
import com.tecnics.einvoice.model.InvoiceQRCode;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

@Component
public class PDFUtilForQRCodeBackup {
	
	@Autowired
	private Environment env;
	
 

    
    public InvoiceQRCodeData verifyQRCode(String invoicePdfPath)
    {
    	InvoiceQRCodeData invoiceQRCodeData=new InvoiceQRCodeData();;
    	try{
            File file = new File(invoicePdfPath);
            PDDocument document = PDDocument.load(file);
            if(document==null)
            {
            	invoiceQRCodeData.setIsqrveified(true);
            	invoiceQRCodeData.setQrverifiedstatus("failed");
            	invoiceQRCodeData.setReasonforfailure("Failed to load PDF Document");
            	return invoiceQRCodeData;
            	
            }
            else
            {
            	System.out.println("Processing First page of the PDF for QRCode");
            	List<BufferedImage> imagesInPage=getImages(document.getPage(0));
            	System.out.println("Number of Images found in the First page of the PDF " + imagesInPage.size());
            	if(imagesInPage.size()<=0)
            	{
            		invoiceQRCodeData.setIsqrveified(true);
                	invoiceQRCodeData.setQrverifiedstatus("failed");
                	invoiceQRCodeData.setReasonforfailure("Failed to find any images in the first page of PDF Document");
                	return invoiceQRCodeData;
            	}
            	
            	for(BufferedImage image :imagesInPage){
            		String decodedImageText=decodeQRCode(image);
            		
            		//start
            		System.out.println(" decodedImageText = " + decodedImageText);
            		if(decodedImageText!=null && !decodedImageText.equals(""))
            		{
            		Jws<Claims> jwt=parseJwt(decodedImageText);
            		String decText=JwtHelper.decode(decodedImageText).getClaims();
            		System.out.println("decText ="+decText);
            		//Map<String, Object> claimsMap=getMapFromIoJsonwebtokenClaims(jwt);
                    	
                       System.out.println("encrypted body=" + jwt.getBody());
                        System.out.println("encrypted body string=" + jwt.getBody().toString());
                        System.out.println("encrypted siignature=" + jwt.getSignature());
                        System.out.println("encrypted header=" + jwt.getHeader());
                      
                        
                        String header = jwt.getHeader().toString();
                        
                        System.out.println("Header: \n" + header);
                        // payload
                        String payload = jwt.getBody().toString();
                        System.out.println("body data=" + jwt.getBody().get("data"));
                        
                       
                        System.out.println("Payload: \n" + payload);
                        
                                  
                        
                     ObjectMapper objectMapper = new ObjectMapper();
                     
                                      
                     String data=jwt.getBody().get("data").toString();
                     System.out.println("data =" + data);
                  
                   
                     InvoiceQRCode jsonToMap= objectMapper.readValue(data, InvoiceQRCode.class);
                                          
                        System.out.println("jsonMap="+jsonToMap);
                        System.out.println("\n2. Convert JSON to Map :" + jsonToMap.getBuyerGstin() + " : "+ jsonToMap.getIrnDt());
                    	invoiceQRCodeData.setIsqrveified(true);
                    	invoiceQRCodeData.setQrcodedata(jsonToMap);
                    	String imgStr=encodeImageToString(image,"PNG");
                    	invoiceQRCodeData.setQrcodebase64(imgStr);
                        boolean verified=verify(decodedImageText);
                      if(verified)
                      {
                    	invoiceQRCodeData.setQrverifiedstatus("success");
                      	invoiceQRCodeData.setReasonforfailure(""); 
                      }
                      else
                      {
                    	invoiceQRCodeData.setQrverifiedstatus("failed");
                      	invoiceQRCodeData.setReasonforfailure("Verification Failed"); 
                      }
                      if(jsonToMap!=null)
                    	  return invoiceQRCodeData;
            		
            		//end
            		}
            		
            		
            		
            	  }
            	document.close();
            }
          

        }catch(Exception e){
        	System.out.println("issue is verifyQRCode of PDFUtilForQRCode");
            e.printStackTrace();
        }
    	return invoiceQRCodeData;
    }
    
    public Map<String, Object>  getMapFromIoJsonwebtokenClaims(Claims claims){
        Map<String, Object> expectedMap = new HashMap<String, Object>();
        for(Entry<String, Object> entry : claims.entrySet()) {
            expectedMap.put(entry.getKey() , entry.getValue());
        }
        return expectedMap;
    } 
    
    public String encodeImageToString(BufferedImage image, String type) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
 
        try {
            ImageIO.write(image, type, bos);
            byte[] imageBytes = bos.toByteArray();
 
            Base64 encoder = new Base64();
            imageString = encoder.encodeAsString(imageBytes);
 
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageString;
    }
    
   
    
    public List<BufferedImage> getImages(PDPage page)
    {
    	List<BufferedImage> imagesPages = new ArrayList<>();
    	
    try {

    	PDResources pdResources = page.getResources();
        for (COSName c : pdResources.getXObjectNames()) {
            PDXObject o = pdResources.getXObject(c);
            if (o instanceof org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject) {            	
            	// Image
                PDImageXObject image = (PDImageXObject) o;
                imagesPages.add(image.getImage());
                
            }
                
            }
    }
    catch(Exception e)
    {
    	System.out.println("issue in getImages of PDFUtilForQRCode");
    	e.printStackTrace();
    	
    }
    	
    	return imagesPages;
    }

   
    
    private String decodeQRCode(File qrCodeimage) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(qrCodeimage);
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        try {
            Result result = new MultiFormatReader().decode(bitmap);
            return result.getText();
        } catch (NotFoundException e) {
            System.out.println("There is no QR code in the image");
            return null;
        }
    }
    
    public String decodeQRCode(BufferedImage bufferedImage) throws IOException {
        
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        try {
            Result result = new MultiFormatReader().decode(bitmap);
            return result.getText();
        } catch (NotFoundException e) {
            System.out.println("There is no QR code in the image");
            return null;
        }
    }
    
    
  
    
    public boolean verify(String jwtToken) {
        
    	
    	
    	RSAPublicKey publicKey = null;
    	try
    	{
    	//publicKey = (RSAPublicKey)getPublicKeyFromPem(pkeyFile);
    		publicKey = (RSAPublicKey) getPublicKey(env.getProperty("einv.public.key.pem"));
        	
    	System.out.println("Public Key Algorithm =" + publicKey.getAlgorithm());
    	}
    	catch(Exception e)
    	{
    		System.out.println("Issue with Public Key");
    		e.printStackTrace();
    	}
    	
        try {
        	
            JwtHelper.decodeAndVerify(jwtToken, new RsaVerifier(publicKey));
            System.out.println("Verified Successfully");
           
        } catch (Exception e) {
            System.out.println("Error in verifying token{}" + e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    
  	 
	public PemObject getPemObjectFromFile(String filename) throws FileNotFoundException, IOException {
		PemReader pemReader = new PemReader(new InputStreamReader(new FileInputStream(filename)));
		
		try {
			return pemReader.readPemObject();
		} finally {
			pemReader.close();
		}
	}
 
public PublicKey getPublicKey(String filename) throws FileNotFoundException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		

	PemObject pemObject=getPemObjectFromFile(filename);
		byte[] encoded = pemObject.getContent();  
		
	    X509EncodedKeySpec  keySpec = new X509EncodedKeySpec(encoded);
	    KeyFactory kf = KeyFactory.getInstance("RSA");	  
	    PublicKey publicKey = kf.generatePublic(keySpec);
	    return publicKey;
	}    


    public String decode(final String base64) {
        return StringUtils.newStringUtf8(Base64.decodeBase64(base64));
    }
    
    public Jws<Claims> parseJwt(String jwtString) throws InvalidKeySpecException, NoSuchAlgorithmException, Exception {


        PublicKey publicKey = getPublicKey(env.getProperty("einv.public.key.pem"));

        Jws<Claims> jwt = Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(jwtString);

        return jwt;
    }
    
    public static void PDFBoxExtractImages() throws Exception {
        PDDocument document = PDDocument.load(new File("D:/inkretainvoices/V_10031/20210924/I210924000087/invoice.pdf"));
        PDPageTree list = document.getPages();
        for (PDPage page : list) {
            PDResources pdResources = page.getResources();
            for (COSName c : pdResources.getXObjectNames()) {
                PDXObject o = pdResources.getXObject(c);
                if (o instanceof org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject) {
                    File file = new File("D:/inkretainvoices/V_10031/20210924/I210924000087/pngs/" + System.nanoTime() + ".png");
                    ImageIO.write(((org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject)o).getImage(), "png", file);
                }
            }
        }
    }
    
    public static void PDFBoxExtractImages1() throws Exception {
    
    	try (final PDDocument document = PDDocument.load(new File("D:/inkretainvoices/V_10031/20210924/I210924000087/invoice.pdf")))
    	{

            PDPageTree list = document.getPages();
            for (PDPage page : list) {
                PDResources pdResources = page.getResources();
                int i = 1;
                for (COSName name : pdResources.getXObjectNames()) {
                    PDXObject o = pdResources.getXObject(name);
                    if (o instanceof PDImageXObject) {
                        PDImageXObject image = (PDImageXObject)o;
                        String filename = "D:/inkretainvoices/V_10031/20210924/I210924000087/pngs/" + "extracted-image-" + i + ".png";
                        ImageIO.write(image.getImage(), "png", new File(filename));
                        i++;
                    }
                }
            }

        } catch (IOException e){
            System.err.println("Exception while trying to create pdf document - " + e);
        }
    }
    
    
    public void PDFBoxExtractImages2() throws Exception 
    {
    	try {	
    	      PDDocument document = PDDocument.load(new File("D:/inkretainvoices/V_10031/20210924/I210924000087/invoice.pdf"));
    	      // get resources for a page
    	      PDResources pdResources = document.getPage(0).getResources();
    	     
    	      int i = 0;
    	      for(COSName csName : pdResources.getXObjectNames()) {
    	        System.out.println("csName =" + csName);
    	        PDXObject pdxObject = pdResources.getXObject(csName);	
    	        if(pdxObject instanceof PDImageXObject) {
    	          PDStream pdStream = pdxObject.getStream();
    	          PDImageXObject image = new PDImageXObject(pdStream, pdResources);
    	          i++;
    	          // image storage location and image name
    	          File imgFile = new File("D:/inkretainvoices/V_10031/20210924/I210924000087/pngs/img"+i+".png");
    	          ImageIO.write(image.getImage(), "png", imgFile);
    	        }
    				}
    	      document.close();
    	    } catch (IOException e) {
    	      // TODO Auto-generated catch block
    	      e.printStackTrace();
    	    }	
    }
    
    /**
     * 
     * @param filePath
     * @param charset
     * @param hintMap
     * 
     * @return Qr Code value 
     * 
     * @throws FileNotFoundException
     * @throws IOException
     * @throws NotFoundException
     */
    public static String readQRCode(String filePath, String charset, Map hintMap)
        throws FileNotFoundException, IOException, NotFoundException {
    	 
      BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
          new BufferedImageLuminanceSource(ImageIO.read(new FileInputStream(filePath)))));
      Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap, hintMap);
      return qrCodeResult.getText();
    }
    
    public static String readQRCodeFromImage(BufferedImage image, String charset, Map hintMap)
            throws IOException, NotFoundException {
        	 
          BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
              new BufferedImageLuminanceSource(image)));
          Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap, hintMap);
          return qrCodeResult.getText();
        }
    
    public static String readQRCode(String fileName) {
    	
    	
    	
    	
		File file = new File(fileName);
		BufferedImage image = null;
		BinaryBitmap bitmap = null;
		Result result = null;

		try {
			image = ImageIO.read(file);
			int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
			RGBLuminanceSource source = new RGBLuminanceSource(image.getWidth(), image.getHeight(), pixels);
			bitmap = new BinaryBitmap(new HybridBinarizer(source));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (bitmap == null)
			return null;

		QRCodeReader reader = new QRCodeReader();	
		try {
			result = reader.decode(bitmap);
			return result.getText();
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ChecksumException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}


    public static void main(String[] args){
    	
    	try
    	{
    	//PDFUtilForQRCode.PDFBoxExtractImages();
    		PDFUtilForQRCodeBackup pdfUtilForQRCode=new PDFUtilForQRCodeBackup();
    		//pdfUtilForQRCode.PDFBoxExtractImages1();
    		pdfUtilForQRCode.PDFBoxExtractImages2();
    		String fileName="D:/inkretainvoices/V_10031/20210924/I210924000087/pngs/image-001.png";
    	String qrCode=PDFUtilForQRCodeBackup.readQRCode(fileName);
    	System.out.println("qrCode retrieved=" + qrCode);
    	
    	
    	File file = new File(fileName);
		BufferedImage image = null;
		BinaryBitmap bitmap = null;
		Result result = null;

		try {
			image = ImageIO.read(file);
			System.out.println("Image reading");
			System.out.println("Image reading 1 " + image.getHeight());
			String decodedText=pdfUtilForQRCode.decodeQRCode(image);
			System.out.println("Decoded Text from pdfUtilForQRCode ="+ decodedText);
			
			// new code
			
			 String filePath = fileName;
			    String charset = "UTF-8"; // or "ISO-8859-1"
			    Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
			    hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

		
			    
			    System.out.println("Data read from readQRCodeFromImage");
			    System.out.println("Data returned from readQRCodeFromImage: "  + readQRCodeFromImage(image, charset, hintMap));
				    
			    
			    System.out.println("Data read from readQRCode"); 
			    System.out.println("Data returnedf from readQRCode: "  + readQRCode(filePath, charset, hintMap));
			    
			    

			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    	
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    }


    
    public static void main1(String[] args){
        String filePath = "D:\\eInvoicing\\source\\sample_pdfs\\qrcodes\\2000784482.pdf";
        String outputDir = "D:\\eInvoicing\\source\\sample_pdfs\\qrcodes\\Output";

        //PDFUtilForQRCode useCase = new PDFUtilForQRCode(filePath, outputDir);
        PDFUtilForQRCodeBackup useCase = new PDFUtilForQRCodeBackup();
        //useCase.execute();
        
        
        try {
            File file = new File("D:\\eInvoicing\\source\\sample_pdfs\\qrcodes\\Output\\QRCode1.png");
            String decodedText = useCase.decodeQRCode(file);
            if(decodedText == null) {
                System.out.println("No QR Code found in the image");
            } else {
                System.out.println("Decoded text = " + decodedText);
               /* byte[] bytes = Base64Utils.decodeFromString(decodedText);
                System.out.println("Decoded QR code = " + new String(bytes));
                byte[] decodedBytes = Base64.getDecoder().decode(decodedText);
                String decodedString = new String(decodedBytes);
                ObjectMapper mapper = new ObjectMapper();
                byte[] encoded = mapper.convertValue("Some text", byte[].class);
                String decoded = mapper.convertValue(decodedText.getBytes(), String.class);
                System.out.println("Decoded QR code = " + decoded);
                */
                String[] chunks = decodedText.split("\\.");
              /*  Base64.Decoder decoder = Base64.getDecoder();

                String header = new String(decoder.decode(chunks[0]));
                String payload = new String(decoder.decode(chunks[1]));
                System.out.println("header from QR code = " + header);
                System.out.println("payload from QR code = " + payload); */
                
               // String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXUyJ9.eyJpc3MiOiJhdXRoMCJ9.AbIJTDMFc7yUa5MhvcP03nJPyCPzZtQcGEp-zWfOkEE";
               String token=decodedText;
               
                try {
                	
                	
                	Jws<Claims> jwt=useCase.parseJwt(token);
                /*
                    String algorithm = jwt.getAlgorithm();
                    String type = jwt.getType();
                    String contentType = jwt.getContentType();
                    String keyId = jwt.getKeyId(); */
                	
                	 String algorithm = "test";
                     String type = "test";
                     String contentType = "test";
                     String keyId = "test";
                	
                	
                    System.out.println("algorithm=" + algorithm + "; type=" + type + ";contentType "+contentType+";keyId"+keyId);
                    System.out.println("encrypted body=" + jwt.getBody());
                    System.out.println("encrypted body string=" + jwt.getBody().toString());
                    System.out.println("encrypted siignature=" + jwt.getSignature());
                    System.out.println("encrypted header=" + jwt.getHeader());
                   // System.out.println("encrypted payload=" + jwt.getPayload());
                    
                    String header = jwt.getHeader().toString();
                    
                    System.out.println("Header: \n" + header);
                    // payload
                    String payload = jwt.getBody().toString();
                    System.out.println("body data=" + jwt.getBody().get("data"));
                    
                   
                    System.out.println("Payload: \n" + payload);
                    
                              
                    
                 ObjectMapper objectMapper = new ObjectMapper();
                 
                
                 /*String payload1=encPayload.replaceAll("\"\\{", "\\{");
                 payload1=payload1.replaceAll("\"\\}\"", "\"\\}");
                 payload1=payload1.replaceAll("\\\\\"", "\""); */
                
                 String data=jwt.getBody().get("data").toString();
                 System.out.println("data =" + data);
              
                  //InvoiceQRCodeData jsonToMap= objectMapper.readValue(jwt.getBody(), InvoiceQRCodeData.class);
                 
                 
                 InvoiceQRCode jsonToMap= objectMapper.readValue(data, InvoiceQRCode.class);
                 //InvoiceQRCodeData jsonToMap= objectMapper.readValue(jwt.getBody().toString(), InvoiceQRCodeData.class);
                 
                //InvoiceQRCodeData jsonToMap= objectMapper.convertValue(jwt.getBody().get("data"), InvoiceQRCodeData.class);
                // InvoiceQRCode jsonToMap= objectMapper.convertValue(jwt.getBody().get("data"), InvoiceQRCode.class);
                 
                 
                    System.out.println("jsonMap="+jsonToMap);
                    System.out.println("\n2. Convert JSON to Map :" + jsonToMap.getBuyerGstin() + " : "+ jsonToMap.getIrnDt());
                    
                  //  System.out.println("\n2. Convert JSON to Map :" + jsonToMap.getData().getBuyerGstin() + " : "+ jsonToMap.getData().getIrnDt());
                   // System.out.println("\n2. Convert JSON to Map  1:" + jsonToMap.getIss());
                    
                    //useCase.verifyToken(decodedText);
                    useCase.verify(decodedText);
                } catch (Exception exception){
                	System.out.println("Issue while verifyih jwt token");
                    exception.printStackTrace();
                }
                
                
            }
        } catch (IOException e) {
        	e.printStackTrace();
            System.out.println("Could not decode QR Code, IOException :: " + e.getMessage());
        }
        
    } 
}