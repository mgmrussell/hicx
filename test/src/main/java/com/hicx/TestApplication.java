package com.hicx;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.hicx.processors.Listener;
import com.hicx.processors.Processor;

//@SpringBootApplication
public class TestApplication {

	public static void main(String[] args) throws IOException {
		String directory = args[0];
		Processor processor = new Processor(directory);
		Path pathProcessed = Paths.get(directory + "/processed");
		Path pathResults = Paths.get(directory + "/results");
		Files.createDirectories(pathProcessed);
		Files.createDirectories(pathResults);

		Path directoryPath = FileSystems.getDefault().getPath(directory);
		Thread thread = new Thread(new Listener(directoryPath, processor));
        thread.start();

	}	
}
