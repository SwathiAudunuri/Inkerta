package com.tecnics.einvoice.rnd;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
 

public class QRCodePdModelUtil {
	
	
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
			
			
			String filePath = "D:/inkretainvoices/V_10031/20210924/I210924000087/invoice.pdf";
			Map<String,String> qCodes=scanQRCode(filePath,"data");
			System.out.println("qCodes data printing");
			System.out.println(qCodes.toString());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
    
 
    @SuppressWarnings("unchecked")
    public static Map<String, String> scanQRCode(String fileName, String textKey) {
 
        Map<String, String> map = new HashMap<String, String>();
 
        List<PDPage> pages = null;
        BufferedImage qrCodeImg = null;
        String qrCode = null;
        PDDocument pdfDoc = null;
 
        try {
            Path docPath = Paths.get(fileName);
            pdfDoc = PDDocument.load(docPath.toFile());
            QRCodeDecoder decoder = new QRCodeDecoder();
            PDPage page = (PDPage)pdfDoc.getPages().get( 0 );
           
            int dpi = 0;
            int dpiEffective = 0;
            int dpiLow = 0;
            PDFRenderer renderer = new PDFRenderer(pdfDoc);
 

                try {
                    if (null == qrCode) {
                    	
                    	qrCodeImg = renderer.renderImageWithDPI(0,100,ImageType.GRAY);
                       // qrCodeImg = page.convertToImage(BufferedImage.TYPE_BYTE_GRAY, 100);
                        qrCode = decoder.getCompressedQRCode(qrCodeImg);
                    }
                    if (null == qrCode) {
                        dpi = detectPageDPI(page);
                        dpiEffective = dpi;
                        dpiLow = dpi - 5;
                        for (; dpiLow < dpiEffective; dpiEffective--) {
                        	qrCodeImg = renderer.renderImageWithDPI(0,dpiEffective,ImageType.GRAY);
                            //qrCodeImg = page.convertToImage(BufferedImage.TYPE_BYTE_GRAY, dpiEffective);
                            qrCode = decoder.getCode(qrCodeImg);
                            if (null != qrCode) {
                                break;
                            }
                        }
                    }
                    if (null == qrCode) {
                    	qrCodeImg = renderer.renderImageWithDPI(0,400,ImageType.GRAY);
                        //qrCodeImg = page.convertToImage(BufferedImage.TYPE_BYTE_GRAY, 400);
                        qrCode = decoder.getCompressedQRCode(qrCodeImg);
                    }
                    System.out.println("Code:::" + qrCode);
                    if (null != qrCode) {
                        map.put("DPI", Integer.toString(dpi));
                        map.put(textKey, qrCode);
                        return map;
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
 
                } finally {
                    qrCodeImg = null;
                    qrCode = null;
                }
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != pdfDoc) {
                try {
                    pdfDoc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            pages = null;
        }
        return null;
    }
 
    private static int detectPageDPI(PDPage page) {
    	int dpi = 0;
    	try
    	{
        PDResources res = page.getResources();
        
     
        int imgHeight = 0;
        
        PDResources pdResources = page.getResources();
        for (COSName c : pdResources.getXObjectNames()) {
            PDXObject o = pdResources.getXObject(c);
            if (o instanceof org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject) {            	
            	// Image
                PDImageXObject image = (PDImageXObject) o;
                imgHeight = ((PDImageXObject) o).getHeight();
                
            }
                
            }
        
 
        float pageHeight = page.getMediaBox().getHeight() / 72;
        dpi = (int) (imgHeight / pageHeight);
        if (200 > dpi) {
            dpi = 200;
        }
        if (300 < dpi) {
            dpi = 300;
        }
    	}
    	catch(Exception e)
    	{
    		System.out.println("error at detect PageDPI");
    		e.printStackTrace();
    	}
        return dpi;
    }
 
}