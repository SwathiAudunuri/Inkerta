package com.tecnics.einvoice.rnd;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

public class PdDocumentBarcodeScanner {

    private static int maximumBlankPixelDelimiterCount = 20;

    public static void main (String[] args) throws Exception {
        String filename = "mail_with_barcode.pdf";
        String filePath = "D:\\eInvoicing\\references\\ExcelFormTemplates\\V2\\2000784533.pdf";
        if (args.length > 0)
            filename = args[0];

        FileInputStream pdfInputStream = new FileInputStream(new File(filePath));
        PDDocument doc = PDDocument.load(pdfInputStream);
        pdfInputStream.close();

        List<PdPageBarcodeScanner> pageScanners = new ArrayList<PdPageBarcodeScanner>();
        System.out.println("Displaying");
        scan(doc, pageScanners);
        displayResults(pageScanners);
    }

    private static void scan (PDDocument doc, List<PdPageBarcodeScanner> pageScanners) throws IOException {
        int pageNumber = 0;
       /* for (PDPage pdPage : doc.getDocumentCatalog().getPages()) {
            PdPageBarcodeScanner pageScanner =
                    new PdPageBarcodeScanner(pdPage, pageNumber, maximumBlankPixelDelimiterCount);
            pageScanners.add(pageScanner);
            pageScanner.scan();
            pageNumber++;
        } */
        PDPage pdPage= doc.getDocumentCatalog().getPages().get(0);
        PdPageBarcodeScanner pageScanner =
                new PdPageBarcodeScanner(pdPage, pageNumber, maximumBlankPixelDelimiterCount);
        pageScanners.add(pageScanner);
        pageScanner.scan();
        
        
    }

    private static void displayResults (List<PdPageBarcodeScanner> pageScanners) {
        for (PdPageBarcodeScanner pageScanner : pageScanners) {
            pageScanner.displayResults();
        }
    }
}