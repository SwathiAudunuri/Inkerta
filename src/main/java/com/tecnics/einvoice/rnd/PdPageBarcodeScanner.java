package com.tecnics.einvoice.rnd;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.datamatrix.DataMatrixReader;
import com.google.zxing.multi.GenericMultipleBarcodeReader;

/**
 * free to use or copy
 * @author David KELLER <david.keller.fr@gmail.com>
 */

public class PdPageBarcodeScanner {
	protected PDPage pdPage;
	protected int pageNumber;
	protected int maximumBlankPixelDelimiterCount = 20;
	protected List<Result> resultList;
	protected DataMatrixReader dataMatrixReader;
	protected Hashtable<DecodeHintType, Object> decodeHintTypes;
	protected GenericMultipleBarcodeReader reader;
    
	public int getPageNumber() {
		return pageNumber;
	}

	public PDPage getPdPage() {
		return pdPage;
	}

	public int getMaximumBlankPixelDelimiterCount() {
		return maximumBlankPixelDelimiterCount;
	}

	public void setMaximumBlankPixelDelimiterCount(
			int maximumBlankPixelDelimiterCount) {
		this.maximumBlankPixelDelimiterCount = maximumBlankPixelDelimiterCount;
	}

	public List<Result> getResultList() {
		return resultList;
	}

	public PdPageBarcodeScanner( PDPage pdPage, int pageNumber, int maximumBlankPixelDelimiterCount) {
		this.pdPage = pdPage;
		this.pageNumber = pageNumber;
		this.maximumBlankPixelDelimiterCount = maximumBlankPixelDelimiterCount;
		this.resultList = new ArrayList<Result>();

		/*
		 * build default bar code reader
		 */
        this.dataMatrixReader = new DataMatrixReader();
        this.reader = new GenericMultipleBarcodeReader(dataMatrixReader);

        this.decodeHintTypes = new Hashtable<DecodeHintType, Object>();
        this.decodeHintTypes.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
	}

	public void scan() throws IOException {
		PDResources pdResources = pdPage.getResources();

		for (COSName name : pdResources.getXObjectNames())
		{
			PDXObject xobject = pdResources.getXObject(name);
			if (xobject instanceof PDImageXObject)
			{
				PDImageXObject imageObject = (PDImageXObject) xobject;
				String suffix = imageObject.getSuffix();
				if (suffix != null)
				{
//                  if ("jpx".equals(suffix))
//                  {
//                      suffix = "JPEG2000";
//                  }
					BufferedImage image = imageObject.getImage();
					extractBarcodeArrayByAreas(image, this.maximumBlankPixelDelimiterCount);
				}
			}
        }
	}

	public void displayResults() {
		for (Result result : resultList) {
			System.out.println("page=" + pageNumber + ", barcodeFormat=" + result.getBarcodeFormat() + ", value=" + result.getText());
		}
	}

	/**
	 * get area list by color
	 * 
	 * @param in
	 * @param out
	 * @param redColor
	 * @param greenColor
	 * @param blueColor
	 * @param maximumBlankPixelDelimiterCount define the same area until number of blank pixel 
	 *        is not greater than maximumBlankPixelDelimiterCount
	 * @return 
	 * @throws IOException
	 */
	public static List<Rectangle> getAllAreaByColor(
			BufferedImage in,
			BufferedImage out, /* if not null draw rectangle for debug purpose */
			int redColor,
			int greenColor,
			int blueColor,
			int maximumBlankPixelDelimiterCount,
			boolean debug) 
	throws IOException 
	{
		int w = in.getWidth();
		int h = in.getHeight();
		int pixel;
		List<Rectangle> areaList = new ArrayList<>();

		Graphics2D gc = null;

		if (out != null){
			gc = out.createGraphics();
			gc.setColor(new Color(1f, 0f, 0f));
		}

		int maximumBlankPixelDelimiterCountDouble = maximumBlankPixelDelimiterCount * 2;

		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				pixel = in.getRGB(x, y);
				int alpha = ((pixel >>24 ) & 0xFF);
				int red = ((pixel >>16 ) & 0xFF);
				int green = ((pixel >>8 ) & 0xFF);
				int blue = (pixel & 0xFF);

				if (red == redColor
				&& green == greenColor
				&& blue == blueColor)
				{
					Rectangle rect 
						= new Rectangle(
							x - maximumBlankPixelDelimiterCount, 
							y - maximumBlankPixelDelimiterCount, 
							maximumBlankPixelDelimiterCountDouble,
							maximumBlankPixelDelimiterCountDouble);

					boolean isInArea = false; 
					for(Rectangle rectangle : areaList){
						if(rectangle.contains(x, y))
						{
							rectangle.add(rect);
							isInArea = true;
							break;
						}
					}

					if (isInArea)
					{
						continue;
					}

					/*
					 * get pixel colors
					 */
					pixel = pixel & 0x00000000;
					pixel = pixel | (alpha << 24);
					pixel = pixel | (0 <<16);
					pixel = pixel | (255 <<8);
					pixel = pixel | (0);

					isInArea = false;
					for (Rectangle rectangle : areaList) {
						Rectangle intersection = rectangle.intersection(rect);
						if(intersection.width > 0 && intersection.height > 0)
						{
							isInArea = true;
							rectangle.add(rect);

							break;
						}
					}

					if (!isInArea) {
						areaList.add(rect);
					}

					while (isInArea)
					{
						Rectangle rectToRemove = null;
						isInArea = false;
						for (Rectangle rectangle : areaList){
							for(Rectangle rec2 : areaList)
							{
								if(rec2 == rectangle) continue;

								Rectangle intersection = rectangle.intersection(rec2);
								if (intersection.width > 0 && intersection.height > 0)
								{
									if (debug) {
										System.out.println(rectangle + " intersect " + rec2);
									}
									isInArea = true;
									rectangle.add(rec2);
									rectToRemove = rec2;
									break;
								}

								if (isInArea) {
									break;
								}
							}
						}

						if (rectToRemove != null) {
							areaList.remove(rectToRemove);
						}
					}

					if (out != null) {
						out.setRGB(x, y, pixel);
						gc.draw(rect);
					}
				}
			}
		}
		return areaList;
	}

	public static List<Rectangle> getAreaList( BufferedImage image, int maximumBlankPixelDelimiterCount)
	throws IOException 
	{
//		BufferedImage out = copy(image, BufferedImage.TYPE_INT_ARGB);
		List<Rectangle> areaList 
			= getAllAreaByColor(
				image, 
				null, // only for debug use 'out' image, 
				0, 
				0, 
				0, 
				maximumBlankPixelDelimiterCount, 
				false);

		return areaList;
	}

	public void extractBarcodeArrayByAreas( BufferedImage image, int maximumBlankPixelDelimiterCount) 
	throws IOException
	{
		BufferedImage blackAndWhiteImage = getThresholdImage(image);
		List<Rectangle> areaList = getAreaList(blackAndWhiteImage, maximumBlankPixelDelimiterCount);

		for (Rectangle rectangle : areaList) {

			/*
			 * verify bounds before crop image
			 */
			if (rectangle.x < 0) {
				rectangle.x = 0;
			}

			if (rectangle.y < 0) {
				rectangle.y = 0;
			}

			if (rectangle.y + rectangle.height > image.getHeight()) {
				rectangle.height = image.getHeight() - rectangle.y;
			}

			if (rectangle.x + rectangle.width > image.getWidth()) {
				rectangle.width = image.getWidth() - rectangle.x;
			}

			/*
			 * crop image to extract barcodes
			 */
			BufferedImage croppedImage = image.getSubimage( rectangle.x, rectangle.y, rectangle.width, rectangle.height);

			/*
			 * zxing library can not deals with DataMatrix in all orientations, so
			 * we have to rotate the image and ask zxing four times to find DataMatrix 
			 */
			addResults(extractBarcodeArray(croppedImage));
			addResults(extractBarcodeArray(rotate90ToLeftImage(croppedImage, BufferedImage.TYPE_INT_ARGB)));
			addResults(extractBarcodeArray(rotate90ToRightImage(croppedImage, BufferedImage.TYPE_INT_ARGB)));
			addResults(extractBarcodeArray(rotate180Image(croppedImage, BufferedImage.TYPE_INT_ARGB)));
		}
	}

	public void addResults(Result[] results) {
		if (results == null) {
			return;
		}

		for (Result result : results) {
			this.resultList.add(result);
		}
	}

	public Result[] extractBarcodeArray2(BufferedImage bufferedImage) 
	{
        Result[] results = null;
        
        try {
    		LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        	results = reader.decodeMultiple(bitmap, decodeHintTypes);
        } catch (NotFoundException e) {}

        return results;
    }


	public static Result[] extractBarcodeArray( BufferedImage bufferedImage) 
	{
        Result[] results = null;

        try {
            LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);  
    		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));  
    		Reader reader = new MultiFormatReader();  
    		Result result = reader.decode(bitmap);  
    		results = new Result[1];
    		results[0] = result;
            return results;
        } catch (Exception e) {}
        
        return results;
	}

	/**
	 * @see http://forum.codecall.net/topic/69182-java-image-rotation/
	 */
	public static BufferedImage rotate180Image(BufferedImage inputImage, int imageType) {
		int width = inputImage.getWidth(); 
		int height = inputImage.getHeight();
		BufferedImage returnImage = new BufferedImage(width, height, imageType);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				returnImage.setRGB(
						width - x - 1, 
						height - y - 1, 
						inputImage.getRGB(x, y));
			}
		}

		return returnImage;
	}

	/**
	 * @see http://forum.codecall.net/topic/69182-java-image-rotation/
	 */
	public static BufferedImage rotate90ToRightImage(BufferedImage inputImage, int imageType) {
		int width = inputImage.getWidth();
		int height = inputImage.getHeight();
		BufferedImage returnImage = new BufferedImage(height, width, imageType);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				returnImage.setRGB(
						height - y - 1,
						x, 
						inputImage.getRGB(x, y));
			}
		}
		return returnImage;
	}

	/**
	 * @see http://forum.codecall.net/topic/69182-java-image-rotation/
	 */
	public static BufferedImage rotate90ToLeftImage(BufferedImage inputImage, int imageType) {
		int width = inputImage.getWidth();
		int height = inputImage.getHeight();
		BufferedImage returnImage = new BufferedImage(height, width, imageType);

		for (int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				returnImage.setRGB( y, width - x - 1, inputImage.getRGB(x, y));
			}
		}
		return returnImage;
	}

	public static BufferedImage getBlackAndWhiteImage( BufferedImage image) 
	throws IOException
	{
		int width = image.getWidth();
		int height = image.getHeight();
		BufferedImage imageBW = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
		Graphics2D g = imageBW.createGraphics();
		g.drawRenderedImage(image, null);
		g.dispose();

		return  imageBW;
	}

	public static BufferedImage getThresholdImage( BufferedImage image)
	throws IOException 
	{
		float saturationMin = 0.10f;
		float brightnessMin = 0.80f;
		BufferedImage thresholdImage = copyImage(image, BufferedImage.TYPE_INT_ARGB);

		computeBlackAndWhite(image, thresholdImage, saturationMin, brightnessMin);

		return thresholdImage;
	}

	public static void computeBlackAndWhite( BufferedImage in, BufferedImage out, float saturationMin, float brightnessMin) 
	throws IOException 
	{
		int w = in.getWidth();
		int h = in.getHeight();
		int pixel;
		float[] hsb = new float[3];

		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				pixel = in.getRGB(x, y);

				int alpha = ((pixel >>24 ) & 0xFF);
				int red = ((pixel >>16 ) & 0xFF);
				int green = ((pixel >>8 ) & 0xFF);
				int blue = (pixel & 0xFF);

				Color.RGBtoHSB(red, green, blue, hsb);

				if( hsb[2] < brightnessMin
				|| (hsb[2] >= brightnessMin &&  hsb[1] >= saturationMin)
				// (red +  green + blue > iThreshold *3 )
				)
				{
					red = 0;
					green = 0;
					blue = 0;
				} else {
					red = 255;
					green = 255;
					blue = 255;
				}
				pixel = pixel & 0x00000000;
				pixel = pixel | (alpha <<24 );
				pixel = pixel | (red <<16 );
				pixel = pixel | (green <<8 );
				pixel = pixel | (blue  );

				out.setRGB(x, y, pixel);
			}
		}
	}

	public static final BufferedImage copyImage(BufferedImage bi, int type) {
		BufferedImage result = new BufferedImage(bi.getWidth(), bi.getHeight(), type);
		Graphics2D g = result.createGraphics();
		g.drawRenderedImage(bi, null);
		g.dispose();
		return result;
	}
}