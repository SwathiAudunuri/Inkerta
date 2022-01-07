package com.tecnics.einvoice.util;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
 
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
 
public class QRCodeZxingUtil {
 
    public static Map<String, String> scanQRCode(String fileName, String textKey) {
    	System.out.println("Processing file : " + fileName);
        Map<String, String> mp = new HashMap<String, String>();
        // Hints for scanning
        Vector<BarcodeFormat> decodeFormat = new Vector<BarcodeFormat>();
        decodeFormat.add(BarcodeFormat.QR_CODE);
 
        Hashtable<DecodeHintType, Object> hintMap = new Hashtable<DecodeHintType, Object>();
        hintMap.put(DecodeHintType.TRY_HARDER, true);
        hintMap.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormat);
 
        MultiFormatReader qrcodeReader = new MultiFormatReader();
        qrcodeReader.setHints(hintMap);
        // We try for several images of the PDF page at several DPI settings,
        // starting at the lowest setting, this might help for speed...
        int[] dpiSettings = { 96, 150, 200, 250, 300, 350 };
        for (int i = 0; i < dpiSettings.length; i++) {
            try {
                // Try lowest DPI first.
                BufferedImage pageImage = getPageImage(fileName, dpiSettings[i]);
                if (pageImage != null) {
                    LuminanceSource source = new BufferedImageLuminanceSource(pageImage);
                    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                    // By using decodeWithState, we keep the Hints that we set earlier.
                    Result result = qrcodeReader.decodeWithState(bitmap);
                    String text = result.getText();
                    // System.out.println(" Sucess at DPI -> " + dpiSettings[i]);
                    pageImage.flush();
 
                    if (text != null && text.length() > 0) {
                        mp.put(textKey, text);
                        mp.put("DPI", Integer.toString(dpiSettings[i]));
                        System.out.println("Found text " + text + " at DP " + Integer.toString(dpiSettings[i]) );
                    }
                }
                if (mp.containsKey(textKey)) {
                    return mp;
                }
            } catch (IOException e) {
                System.out.print(" IOException at DPI -> " + dpiSettings[i]);
                e.printStackTrace();
            } catch (NotFoundException e) {
                System.out.print(" Not able to extract at DPI -> " + dpiSettings[i] + "\n");
                e.printStackTrace();
            }
            catch (Exception e) {
                System.out.print(" Exception at DPI -> " + dpiSettings[i]);
                e.printStackTrace();
            }
        }
        return null;
    }
 
    private static BufferedImage getPageImage(String fileName, int dpi) throws IOException {
        BufferedImage image = null;
        Path docPath = Paths.get(fileName);
        PDDocument pdfDoc = PDDocument.load(docPath.toFile());
        try {
            PDFRenderer renderer = new PDFRenderer(pdfDoc);
            image = renderer.renderImageWithDPI(0, dpi, ImageType.BINARY); // entire page info
        } finally {
            pdfDoc.close();
        }       
        return image;
    }
    
    public static PublicKey getPublicKey(String filename) throws FileNotFoundException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		

    	PemObject pemObject=getPemObjectFromFile(filename);
    		byte[] encoded = pemObject.getContent();  
    		
    	    X509EncodedKeySpec  keySpec = new X509EncodedKeySpec(encoded);
    	    KeyFactory kf = KeyFactory.getInstance("RSA");	  
    	    PublicKey publicKey = kf.generatePublic(keySpec);
    	    return publicKey;
    	}  
    
	public static PemObject getPemObjectFromFile(String filename) throws FileNotFoundException, IOException {
		PemReader pemReader = new PemReader(new InputStreamReader(new FileInputStream(filename)));
		
		try {
			return pemReader.readPemObject();
		} finally {
			pemReader.close();
		}
	}
    
    public static Jws<Claims> parseJwt(String jwtString) throws InvalidKeySpecException, NoSuchAlgorithmException, Exception {


        PublicKey publicKey = getPublicKey("D:/eInvoicing/source/sample_pdfs/qrcodes/einv_public_key.pem");

        Jws<Claims> jwt = Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(jwtString);

        return jwt;
    }
    
    public static void main(String args[])
    {
    	try
    	{
    		
    		
    		        Set<String> artifactoryLoggers = new HashSet<>(Arrays.asList("org.apache.fontbox.util.autodetect.FontFileFinder",
    		        		"org.apache.pdfbox.pdmodel.font.PDFont",
    		        		"org.apache.fontbox.ttf.GlyphSubstitutionTable",
    		        		"org.apache.pdfbox.pdmodel.font.FileSystemFontProvider",
    		        		"org.apache.pdfbox.io.ScratchFileBuffer",
    		        		 "org.apache.fontbox.ttf.PostScriptTable",
    		        		"org.apache.pdfbox.pdmodel.font.FontMapperImpl",
    		        		"org.apache.pdfbox.pdfparser.PDFObjectStreamParser",
    		        		"org.apache.http", "groovyx.net.http"));
    		        for(String log:artifactoryLoggers) {
    		            ch.qos.logback.classic.Logger artLogger = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger(log);
    		            artLogger.setLevel(ch.qos.logback.classic.Level.INFO);
    		            artLogger.setAdditive(false);
    		        }
    		        
    		        
    		String dir="D:/eInvoicing/references/ExcelFormTemplates/V2/";
    		String fileName="20007844821.pdf";
    		Map<String,String> map1=QRCodeZxingUtil.scanQRCode(dir+fileName, "data");
    		
    		Jws<Claims> jwt=parseJwt(map1.get("data"));      	
            System.out.println("body=" + jwt.getBody());     
            System.out.println("header=" + jwt.getHeader());
    		
    		
    		fileName="2000784484.pdf";
    		map1=QRCodeZxingUtil.scanQRCode(dir+fileName, "data");
    		jwt=parseJwt(map1.get("data"));      	
            System.out.println("body=" + jwt.getBody());     
            System.out.println("header=" + jwt.getHeader());
    		
    		
    		
    		fileName="2000784485.pdf";
    		map1=QRCodeZxingUtil.scanQRCode(dir+fileName, "data");
    		jwt=parseJwt(map1.get("data"));      	
            System.out.println("body=" + jwt.getBody());     
            System.out.println("header=" + jwt.getHeader());
    		
    		
    		fileName="2000784486.pdf";
    		map1=QRCodeZxingUtil.scanQRCode(dir+fileName, "data");
    		jwt=parseJwt(map1.get("data"));      	
            System.out.println("body=" + jwt.getBody());     
            System.out.println("header=" + jwt.getHeader());
    		
    	
    		
    	}
    	catch(Exception e)
    	{
    		System.out.println("Error in main");
    		e.printStackTrace();
    	}
    }
}