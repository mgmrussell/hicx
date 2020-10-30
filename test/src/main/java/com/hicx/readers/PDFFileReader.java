package com.hicx.readers;

import java.io.File;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PDFFileReader implements FileReader {

	@Override
	public String readfile(File file, String directory) {
		String text = "";
		  try {
			  PDDocument document = PDDocument.load(file);
				if (!document.isEncrypted()) {
				    PDFTextStripper stripper = new PDFTextStripper();
				    text = stripper.getText(document);
				    file.renameTo(new File(directory + "/processed/" + file.getName()));
				}
				document.close();
			  
		  } catch (Exception e) {
			  
		  }
		return text;
	}
	
}