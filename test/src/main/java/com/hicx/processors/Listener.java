package com.hicx.processors;

import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class Listener implements Runnable {
	
	
	private Path directoryPath; 
	private Processor processor;
	
	public Listener(Path directoryPath, Processor processor ) {
		this.directoryPath = directoryPath;
		this.processor = processor;
	}
	
	@Override
	public void run() {
		try {
            WatchService watchService = directoryPath.getFileSystem().newWatchService();
            directoryPath.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);

            //Start infinite loop to watch changes on the directory
            while (true) {
                
            	WatchKey watchKey = watchService.take();
                for (final WatchEvent<?> event : watchKey.pollEvents()) {
                    takeActionOnChangeEvent(event);
                }
                //Break out of the loop if watch directory got deleted
                if (!watchKey.reset()) {
                    watchKey.cancel();
                    watchService.close();
                    System.out.println("Deleted. Stop watching it.");
                    break;
                }
                Thread.sleep(1000); //Check every s.
            }

        } catch (InterruptedException interruptedException) {
            System.out.println("Interrupted:"+interruptedException);
            return;
        } catch (Exception exception) {
        	exception.printStackTrace();
            return;
        }
		
	}
	
	private void takeActionOnChangeEvent(WatchEvent<?> event) {
        
		Kind<?> kind = event.kind();
        
		if (kind.equals(StandardWatchEventKinds.ENTRY_CREATE)) {
            Path entryCreated = (Path) event.context();
            System.out.println("Created:" + entryCreated);

            processor.process(entryCreated);
            
        } else if (kind.equals(StandardWatchEventKinds.ENTRY_DELETE)) {
            Path entryDeleted = (Path) event.context();
            System.out.println("Deleted:" + entryDeleted);
        } else if (kind.equals(StandardWatchEventKinds.ENTRY_MODIFY)) {
            Path entryModified = (Path) event.context();
            System.out.println("Modified:"+ entryModified);
        }
    }

}