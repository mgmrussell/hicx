package com.hicx.readers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class TxtFileReader implements com.hicx.readers.FileReader {

	@Override
	public String readfile(File file, String directory) {

		String text = "";
		try {

			try (BufferedReader br = new BufferedReader(new FileReader(file))) {
				StringBuilder sb = new StringBuilder();
				String line = br.readLine();

				while (line != null) {
					sb.append(line);
					sb.append(System.lineSeparator());
					line = br.readLine();
				}
				text = sb.toString();
				file.renameTo(new File(directory + "/processed/" + file.getName()));
			}

		} catch (Exception e) {

		}

		return text;
	}

}