package com.hicx.readers;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class WordFileReader implements FileReader {

	/**
	 * TODO: Exception Handling
	 */
	@Override
	public String readfile(File file, String directory) {

		String text = "";

		try {
			FileInputStream fis = new FileInputStream(file);
			XWPFDocument xdoc = new XWPFDocument(OPCPackage.open(fis));
			@SuppressWarnings("resource")
			XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
			text = extractor.getText();
			file.renameTo(new File(directory + "/processed/" + file.getName()));
		} catch (Exception e) {

		}

		return text;
	}

}
