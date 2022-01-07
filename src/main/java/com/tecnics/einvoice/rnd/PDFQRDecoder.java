package com.tecnics.einvoice.rnd;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Collectors;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.GlobalHistogramBinarizer;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.multi.GenericMultipleBarcodeReader;
import com.google.zxing.multi.MultipleBarcodeReader;

public class PDFQRDecoder {
	
	public static Map<String,String> qrCodeExists=new HashMap<String,String>();
	public static Map<String,String> qrCodeNotExists=new HashMap<String,String>();
	
	public static void main(String args[])
	{
		try
		{
			
			  disableDebug();
			  
			  String dirLocation = "D:/eInvoicing/references/ExcelFormTemplates/V2";
			  
		        try {
		            List<File> files = Files.list(Paths.get(dirLocation))
		                                    .filter(Files::isRegularFile)
		                                    .filter(path -> path.toString().endsWith(".pdf"))
		                                    .map(Path::toFile)
		                                    .collect(Collectors.toList());
		             
		         // Dump file list values
		            
		            for (File fileName : files){
		                System.out.println(fileName.getAbsolutePath());
		                readQRCodeFromImage1(fileName.getAbsolutePath());
		            }
		            
		            System.out.println("No of PDF files found =" + files.size());
		            System.out.println("Total QR Codes found in  =" + qrCodeExists.size());
		            
		            System.out.println("Below Files failed to read QR Code ");
		            for (Map.Entry<String, String> qrCodeNotExists_temp : qrCodeNotExists.entrySet()) {
		                System.out.println(qrCodeNotExists_temp.getKey() );
		            }
		            
		        } catch (IOException e) {
		            // Error while reading the directory
		        }
		        
			String filePath = "D:\\eInvoicing\\references\\ExcelFormTemplates\\V2\\2000784533.pdf";
			String filePath1 = "D:\\eInvoicing\\references\\ExcelFormTemplates\\V2\\0197_002.pdf";
			//readQRCodeFromImage1(filePath);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
     * Returns the basename of the given file, that is the filename without the
     * file-extension.
     */
    public static String getBasename(Path file) {
        if (file != null) {
            String filename = file.getFileName().toString();
            String[] tokens = filename.split("\\.(?=[^\\.]+$)");
            return tokens[0];
        }
        return null;
    }
	
	public static void disableDebug()
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
	}
	
	
	public static String parseQrCode(BufferedImage image ) {
		//
		String code = "";
	
		if (null == image || image.getHeight() < 10 || image.getWidth() < 10) {
			return code;
		}
        //
        Map<DecodeHintType,Object>  HINTS = new EnumMap<DecodeHintType,Object>(DecodeHintType.class);
        HINTS.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
        HINTS.put(DecodeHintType.POSSIBLE_FORMATS, EnumSet.allOf(BarcodeFormat.class));
        Map<DecodeHintType,Object> HINTS_PURE = new EnumMap<DecodeHintType,Object>(HINTS);
        HINTS_PURE.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);

        LuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new GlobalHistogramBinarizer(source));
        Collection<Result> results = new ArrayList<Result>(1);

        try {
            Reader reader = new MultiFormatReader();
            ReaderException savedException = null;
            try {
                // Look for multiple barcodes
                MultipleBarcodeReader multiReader = new GenericMultipleBarcodeReader(reader);
                Result[] theResults = multiReader.decodeMultiple(bitmap, HINTS);
                if (theResults != null) {
                	System.out.println("Found in parseQrCode " + theResults[0].getText());
                    results.addAll(Arrays.asList(theResults));
                }
            } catch (ReaderException re) {
                savedException = re;
            }

            if (results.isEmpty()) {
                try {
                    // Look for pure barcode
                    Result theResult = reader.decode(bitmap, HINTS_PURE);
                    if (theResult != null) {
                    	System.out.println("Found in parseQrCode " + theResult.getText());
                        results.add(theResult);
                    }
                } catch (ReaderException re) {
                    savedException = re;
                }
            }

            if (results.isEmpty()) {
                try {
                    // Look for normal barcode in photo
                    Result theResult = reader.decode(bitmap, HINTS);
                    if (theResult != null) {
                    	System.out.println("Found in parseQrCode " + theResult.getText());
                        results.add(theResult);
                    }
                } catch (ReaderException re) {
                    savedException = re;
                }
            }

            if (results.isEmpty()) {
                try {
                    // Try again with other binarizer
                    BinaryBitmap hybridBitmap = new BinaryBitmap(new HybridBinarizer(source));
                    Result theResult = reader.decode(hybridBitmap, HINTS);
                    if (theResult != null) {
                    	System.out.println("Found in parseQrCode " + theResult.getText());
                        results.add(theResult);
                    }
                } catch (ReaderException re) {
                    savedException = re;
                }
            }

            if (results.isEmpty()) {
                try {
                    throw savedException == null ? NotFoundException.getNotFoundInstance() : savedException;
                } catch (FormatException e) {
                	System.out.println(e.toString());
                } catch (ChecksumException e) {
                    System.out.println(e.toString());
                } catch (ReaderException e) { // Including NotFoundException
                	System.out.println(e.toString());
                }
                return "";
            }
            // 结果
            for(Result theResult: results){
                if (theResult != null) {
                    code = theResult.getText();
                    System.out.println("Found in parseQrCode For loop " + code);
                }
                if(null != code && false==code.isEmpty()){
                    return code;
                }
            }

        } catch (RuntimeException re) {
            // Call out unexpected errors in the logger clearly
        	System.out.println("Unexpected exception from library");
        }
		//
		return code;
	}
	
	public static String readQRCodeFromImage1(String fileName)
	{
		String text="";
		try
		{
			// Hints for scanning
	        Vector<BarcodeFormat> decodeFormat = new Vector<BarcodeFormat>();
	        decodeFormat.add(BarcodeFormat.QR_CODE);
	 
	        Hashtable<DecodeHintType, Object> hintMap = new Hashtable<DecodeHintType, Object>();
	        hintMap.put(DecodeHintType.TRY_HARDER, true);
	        hintMap.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormat);
	        hintMap.put(DecodeHintType.CHARACTER_SET, "UTF8");
	 
	        MultiFormatReader qrcodeReader = new MultiFormatReader();
	        qrcodeReader.setHints(hintMap);
	        // We try for several images of the PDF page at several DPI settings,
	        // starting at the lowest setting, this might help for speed...
	        int[] dpiSettings = {72,96,55,60,92, 100, 110, 125, 145,300, 175, 200, 250, 350};
	        for (int i = 0; i < dpiSettings.length; i++) {
	            try {
	                // Try lowest DPI first.
	            	System.out.println("Processing at dpi " + dpiSettings[i]);
	                BufferedImage pageImage = getPageImage(fileName, dpiSettings[i]);
	               
			                if (pageImage != null) {
			                    LuminanceSource source = new BufferedImageLuminanceSource(pageImage);
			                    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
			                    // By using decodeWithState, we keep the Hints that we set earlier.
			                   
			                    Result result = qrcodeReader.decodeWithState(bitmap);
			                    //Result result = qrcodeReader.decode(bitmap);
			                    text = result.getText();
			                   
			                   
			                    if(!text.equals(""))
			                    {
			                    	System.out.println(" Sucess at DPI -> " + dpiSettings[i]);
			                    	System.out.println("text = " + text);
			                    	qrCodeExists.put(fileName, text);
			                    	return text;
			                    }
			                    pageImage.flush();
			                }
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
	        	}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		qrCodeNotExists.put(fileName, text);
		return text;
	}
	
	  public static String readQRCodeFromImage(BufferedImage image)
	    {
	    	String text=null;
	    	try
	    	{
	    	  Vector<BarcodeFormat> decodeFormat = new Vector<BarcodeFormat>();
	          decodeFormat.add(BarcodeFormat.QR_CODE);
	   
	          Hashtable<DecodeHintType, Object> hintMap = new Hashtable<DecodeHintType, Object>();
	          hintMap.put(DecodeHintType.TRY_HARDER, true);
	          hintMap.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormat);
	   
	          MultiFormatReader qrcodeReader = new MultiFormatReader();
	          qrcodeReader.setHints(hintMap);
	          
	          if (image != null) {
	        	
	              
	        	 // ImageIOUtil.writeImage(image, "d:/resizedQRCodes/"+ UUID.randomUUID(), 72);
	              LuminanceSource source = new BufferedImageLuminanceSource(image);
	              BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
	              // By using decodeWithState, we keep the Hints that we set earlier.
	              Result result = qrcodeReader.decodeWithState(bitmap);
	              text = result.getText();
	              System.out.println("Found text " + text);
	              
	              // System.out.println(" Sucess at DPI -> " + dpiSettings[i]);
	              image.flush();
	              return text;
	          }
	    	}
	    	catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}
	    	return text;
	    }
	
	public static BufferedImage getPageImage(String fileName, int dpi) throws IOException {
	    BufferedImage image = null;
	    BufferedImage cropedImage = null;
	    Path docPath = Paths.get(fileName);
	    PDDocument pdfDoc = PDDocument.load(docPath.toFile());
	    String outputPrefix="d:/resizedQRCodes/pdftoimages/"+getBasename(docPath)+"_"+dpi+".png";
	    System.out.println("image File name of pdf page =" +outputPrefix );
	    try {
	        PDFRenderer renderer = new PDFRenderer(pdfDoc);
	    
	        image = renderer.renderImageWithDPI(0, dpi); // entire page info
	        
	      // cropedImage = image.getSubimage(0, 0, 914, 300);
	       
	       // ImageIOUtil.writeImage(image,outputPrefix,dpi);
	    } 
	    catch(FileNotFoundException e)
	    {
	    	System.out.println("Invoice pdf file is not found in the repository , Document" + fileName);
	    }
	    finally {
	        pdfDoc.close();
	    }       
	    return image;
	}
	
	public static final BufferedImage copyImage(BufferedImage bi, int type) {
		BufferedImage result = new BufferedImage(bi.getWidth(), bi.getHeight(), type);
		Graphics2D g = result.createGraphics();
		g.drawRenderedImage(bi, null);
		g.dispose();
		return result;
	}

}
