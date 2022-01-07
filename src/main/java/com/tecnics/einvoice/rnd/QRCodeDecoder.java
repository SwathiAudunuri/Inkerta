package com.tecnics.einvoice.rnd;

import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.RasterFormatException;
import java.util.HashMap;
import java.util.Map;
 
import org.springframework.stereotype.Service;
 
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
 
@Service
public class QRCodeDecoder {
 
    public String getCode(BufferedImage image) {
        String code = null;
        try {
            // vertical bottom scan
            BufferedImage procImage = image.getSubimage(0, image.getHeight() / 2, image.getWidth(), image.getHeight() / 2 - 20);
            code = decode(procImage);
 
        } catch (RasterFormatException e) {
            // continue
        }
        return code;
    }
 
    public String getCompressedQRCode(BufferedImage image) {
        String code = null;
        try {
            // vertical bottom scan
            BufferedImage procImage = image.getSubimage(150, image.getHeight() / 2, image.getWidth() - 150, image.getHeight() / 2 - 200);
            code = decode(procImage);
        } catch (RasterFormatException e) {
            // continue
        }
        return code;
    }
 
    public String decode(BufferedImage image) {
        RenderingHints hints = getRenderingHints();
        float[] kernelData = null;
        BufferedImageOp op = null;
        BufferedImage procImage = null;
        LuminanceSource source = null;
        BinaryBitmap bitmap = null;
        QRCodeReader reader = new QRCodeReader();
        Result result = null;
        String code = null;
 
        for (int denom = 10; denom <= 50; denom += 10) {
            try {
                procImage = image;
                float factor = (1.0f / ((float) denom));
                kernelData = getKernelData(factor);
                op = new ConvolveOp(new Kernel(4, 4, kernelData), ConvolveOp.EDGE_NO_OP, hints);
                source = new BufferedImageLuminanceSource(op.filter(procImage, null));
                bitmap = new BinaryBitmap(new HybridBinarizer(source));
                result = reader.decode(bitmap);
                code = result.getText();
                break;
            } catch (NotFoundException e) {
                // continue
            } catch (ChecksumException e) {
                // continue
            } catch (FormatException e) {
                // continue
            } finally {
                hints = null;
                kernelData = null;
                op = null;
                source = null;
                bitmap = null;
                procImage = null;
                result = null;
            }
        }
        return code;
    }
 
    protected RenderingHints getRenderingHints() {
        Map<Key, Object> map = new HashMap<Key, Object>();
        map.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        map.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        RenderingHints hints = new RenderingHints(map);
        return hints;
    }
 
    protected float[] getKernelData(float data) {
        return new float[] { data, data, data, data, data, data, data, data, data, data, data, data, data, data, data, data };
    }
}