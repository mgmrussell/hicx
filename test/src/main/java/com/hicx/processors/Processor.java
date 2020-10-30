package com.hicx.processors;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Arrays;

import com.hicx.readers.FileReader;
import com.hicx.readers.PDFFileReader;
import com.hicx.readers.TxtFileReader;
import com.hicx.readers.WordFileReader;

public class Processor {

	private String dir;

	public Processor(String dir) {
		this.dir = dir;
	}

	public void process(Path entryCreated) {

		File file = new File(this.dir + "/" + entryCreated);
		FileReader reader = null;
		String text = "";
		if (file.isFile() && file.getName().endsWith(".pdf")) {
			reader = new PDFFileReader();
			text = reader.readfile(file, this.dir);
			printAndSaveStaistics(text, file.getName());
		} else if (file.isFile() && file.getName().endsWith(".txt")) {
			reader = new TxtFileReader();
			text = reader.readfile(file, this.dir);
			printAndSaveStaistics(text, file.getName());
		} else if (file.isFile() && file.getName().endsWith(".doc") || file.getName().endsWith(".docx")) {
			reader = new WordFileReader();
			text = reader.readfile(file, this.dir);
			printAndSaveStaistics(text, file.getName());
		}

	}

	public void printAndSaveStaistics(String string, String fileName) {
		String spaces = "Total spaces      : " + countSpaces(string) + "\n";
		String dots = "Total dots        : " + countDots(string) + "\n";
		String freqword = "Most frequent word: " + mostFrequestWord(string) + "\n";

		String result = "Statistics for " + fileName + ":\n" + spaces + dots + freqword;

		System.out.println(result);

		OutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(new File(this.dir + "/results/" + fileName + "-statistics.txt"));
			outputStream.write(result.getBytes(), 0, result.length());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public long countSpaces(String string) {
		return string.length() - string.replaceAll(" ", "").length();
	}

	public long countDots(String string) {
		return string.length() - string.replaceAll(".", "").length();
	}

	public String mostFrequestWord(String text) {

		String[] splited = text.split(" ");
		Arrays.sort(splited);
		int max = 0;
		int count = 1;
		String word = splited[0];
		String current = splited[0];
		for (int i = 1; i < splited.length; i++) {
			if (splited[i].equals(current)) {
				count++;
			} else {
				count = 1;
				current = splited[i];
			}
			if (max < count) {
				max = count;
				word = splited[i];
			}
		}
		return word;

	}
}
