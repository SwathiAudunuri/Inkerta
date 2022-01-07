package com.tecnics.einvoice.rnd;

import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.Matrix;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import org.apache.pdfbox.contentstream.operator.DrawObject;
import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.contentstream.operator.OperatorName;

import org.apache.pdfbox.contentstream.PDFStreamEngine;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.Vector;

import javax.imageio.ImageIO;

import org.apache.pdfbox.contentstream.operator.state.Concatenate;
import org.apache.pdfbox.contentstream.operator.state.Restore;
import org.apache.pdfbox.contentstream.operator.state.Save;
import org.apache.pdfbox.contentstream.operator.state.SetGraphicsStateParameters;
import org.apache.pdfbox.contentstream.operator.state.SetMatrix;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
/**
 * This is an example on how to get the x/y coordinates of image locations.
 *
 * @author Ben Litchfield
 */
public class PrintImageLocations extends PDFStreamEngine
{
    /**
     * Default constructor.
     *
     * @throws IOException If there is an error loading text stripper properties.
     */
	List<BufferedImage> buffered_images = new ArrayList<>();	
    public PrintImageLocations() throws IOException
    {
        addOperator(new Concatenate());
        addOperator(new DrawObject());
        addOperator(new SetGraphicsStateParameters());
        addOperator(new Save());
        addOperator(new Restore());
        addOperator(new SetMatrix());
    }
    
    public static BufferedImage resizeImage(int x, int y, BufferedImage bfi){
        BufferedImage bufferedImage = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
        bufferedImage.getGraphics().drawImage(
                bfi.getScaledInstance(x, y, Image.SCALE_SMOOTH), 0, 0, null);
        return bufferedImage;
    }
    
    public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }
    
    /**
     * This will print the documents data.
     *
     * @param args The command line arguments.
     *
     * @throws IOException If there is an error parsing the document.
     */
    public static void main( String[] args ) throws IOException
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
        
    	//String filePath = "D:\\eInvoicing\\references\\ExcelFormTemplates\\V2\\2000795331.pdf";
    	//String filePath = "D:\\eInvoicing\\references\\ExcelFormTemplates\\V2\\20210530.000002.01.pdf";
    	String filePath = "D:\\eInvoicing\\references\\ExcelFormTemplates\\V2\\2000784533.pdf";
    	
        //	String filePath = "D:\\eInvoicing\\references\\ExcelFormTemplates\\V2\\2000795384.pdf";
        	InputStream is=new FileInputStream(filePath);
            try (PDDocument document = PDDocument.load(is))
            {
                PrintImageLocations printer = new PrintImageLocations();
                int pageNum = 0;
                if(document.getNumberOfPages()>0)
                {
                	PDPage page=document.getPage(0);
               
                    System.out.println( "Processing page: "  );
                    printer.processPage(page);
                    String text="";
                    System.out.println("No Of Images found in the the file = " + filePath + " : " + printer.buffered_images.size());
                    for (BufferedImage image : printer.buffered_images){
                    	System.out.println("Processing QR Code from image");
                    	text=readQRCodeFromImage(image);
                    	
                    	                      	
                    	 // OutputStream fileOutputStream = new FileOutputStream("d:/resizedQRCodes/"+ System.nanoTime() + "_96.jpeg");
                        //  boolean b = ImageIOUtil.writeImage(image, "jpg",fileOutputStream,96);                          
                          image=resizeImage(image,350,350);
                          text=readQRCodeFromImage(image);
                          
                          BufferedImage img1 = ImageIO.read(new File("d:/resizedQRCodes/593605785096300_90.jpeg"));
                          text=readQRCodeFromImage(img1);
                          
                    }
              
                }
            }
        
    }
    
   /* public BufferedImage preProcessBufferedImage (BufferedImage bufferedImage)throws IOException{
        //get subimage that cuts out the QR code, speeds up the QR recognition process
        BufferedImage subImage = bufferedImage.getSubimage(x, y,width,height);
        //gaussian blur the result , leads to better QR code recognition
        ImagePlus imagePlus = new ImagePlus("process-qr-code", subImage);
        imagePlus.getProcessor().blurGaussian(2);
        return imagePlus.getBufferedImage();
    } */
    
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
    /**
     * This is used to handle an operation.
     *
     * @param operator The operation to perform.
     * @param operands The list of arguments.
     *
     * @throws IOException If there is an error processing the operation.
     */
    @Override
    protected void processOperator( Operator operator, List<COSBase> operands) throws IOException
    {
        String operation = operator.getName();
        if (OperatorName.DRAW_OBJECT.equals(operation))
        {
            COSName objectName = (COSName) operands.get( 0 );
            PDXObject xobject = getResources().getXObject( objectName );
            if( xobject instanceof PDImageXObject)
            {
                PDImageXObject image = (PDImageXObject)xobject;
                buffered_images.add(image.getImage());
                int imageWidth = image.getWidth();
                int imageHeight = image.getHeight();
                System.out.println("*******************************************************************");
                System.out.println("Found image [" + objectName.getName() + "]");
        
                Matrix ctmNew = getGraphicsState().getCurrentTransformationMatrix();
                float imageXScale = ctmNew.getScalingFactorX();
                float imageYScale = ctmNew.getScalingFactorY();
                // position in user space units. 1 unit = 1/72 inch at 72 dpi
                System.out.println("position in PDF = " + ctmNew.getTranslateX() + ", " + ctmNew.getTranslateY() + " in user space units");
                // raw size in pixels
                System.out.println("raw image size  = " + imageWidth + ", " + imageHeight + " in pixels");
                // displayed size in user space units
                System.out.println("displayed size  = " + imageXScale + ", " + imageYScale + " in user space units");
                // displayed size in inches at 72 dpi rendering
                imageXScale /= 72;
                imageYScale /= 72;
                System.out.println("displayed size  = " + imageXScale + ", " + imageYScale + " in inches at 72 dpi rendering");
                // displayed size in millimeters at 72 dpi rendering
                imageXScale *= 25.4;
                imageYScale *= 25.4;
                System.out.println("displayed size  = " + imageXScale + ", " + imageYScale + " in millimeters at 72 dpi rendering");
                System.out.println();
            }
            else if(xobject instanceof PDFormXObject)
            {
                PDFormXObject form = (PDFormXObject)xobject;
                showForm(form);
            }
        }
        else
        {
            super.processOperator( operator, operands);
        }
    }
    /**
     * This will print the usage for this document.
     */
    private static void usage()
    {
        System.err.println( "Usage: java " + PrintImageLocations.class.getName() + " <input-pdf>" );
    }
}