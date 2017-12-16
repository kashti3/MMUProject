package com.hit.memoryunits;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

import com.hit.util.MMULogger;

public class HardDisk {

	private static HardDisk instance = null;
	private static final String DEFAULT_FILE_NAME = "Hard_Disk.txt";
	private static final int _SIZE = 100;
	private Map<Long,Page<byte[]>> hardDiskMap;
	private ObjectOutputStream write;
	private ObjectInputStream read;

	private HardDisk(){// Constructor for singleton pattern
		hardDiskMap = new HashMap<Long,Page<byte[]>>(_SIZE);
		MMULogger logger = MMULogger.getInstance();
		byte[] content = new byte[5];
		for (int i = 0; i < _SIZE; ++i) { //set pages in hard disk
			hardDiskMap.put(new Long(i), new Page<byte[]>(new Long(i),content));	
		}
		try{
			write = new ObjectOutputStream(new FileOutputStream(DEFAULT_FILE_NAME));
			write.writeObject(hardDiskMap);
			write.flush();
		}catch(IOException e){
			e.getMessage();
			logger.write("[Error] | [ HardDisk: Constructor ] - ObjectOutputStream couldn't open/write" , Level.SEVERE);
		}finally{
			try{
			write.close();
			}catch(Exception e){
				e.printStackTrace();
				logger.write("[Error] | [ HardDisk: Constructor ]  - ObjectOutputStream couldn't close" , Level.SEVERE);
			}
		}
	}

	public static HardDisk getInstance(){ //create single instance of HardDisk 
		if(instance == null)
			instance = new HardDisk();
		return instance;
	}

	@SuppressWarnings("unchecked")
	public Page<byte[]> pageFault(Long pageId)
			throws FileNotFoundException,IOException{
		MMULogger logger = MMULogger.getInstance();
		Map<Long,Page<byte[]>> hardDiskMap = null;
		try{ //read map from hard disk file
			read = new ObjectInputStream(new FileInputStream(DEFAULT_FILE_NAME));
			hardDiskMap = (Map<Long, Page<byte[]>>)read.readObject();
		}catch(FileNotFoundException | ClassNotFoundException e){
			e.getMessage();
			logger.write("[Error] | [ HardDisk: pageFault method ]  - ObjectInputStream couldn't open/read" , Level.SEVERE);
		}finally{
			try {
				read.close();
			} catch (Exception e2) {
				logger.write("[Error] | [ HardDisk: pageFault method ]  - ObjectInputStream couldn't close" , Level.SEVERE);
			}
		}
		return hardDiskMap.get(pageId);
	}

	@SuppressWarnings("unchecked")
	public Page<byte[]> pageReplacement(Page<byte[]> moveToHdPage, Long moveToRamId) 
			throws FileNotFoundException,IOException{
		MMULogger logger = MMULogger.getInstance();
		Map<Long,Page<byte[]>> hardDiskMap = null;
		try{
			//Try to read from Disk 
			read = new ObjectInputStream(new FileInputStream(DEFAULT_FILE_NAME));
			hardDiskMap = (Map<Long, Page<byte[]>>)read.readObject();
		}catch(Exception e){
			e.getMessage();
			logger.write("[Error] | [ HardDisk: pageReplacement method ]  - ObjectInputStream couldn't open/read" , Level.SEVERE);
		}finally{
			try {
				read.close();
			} catch (Exception e2) {
				logger.write("[Error] | [ HardDisk: pageReplacement method ]  - ObjectInputStream couldn't close" , Level.SEVERE);
			}
			
		}
		// Insert the page into the Hard Disk
		hardDiskMap.put((Long)moveToHdPage.getPageId(), moveToHdPage);
		//Writing into the Hard Disk
		try {
			write = new ObjectOutputStream(new FileOutputStream(DEFAULT_FILE_NAME));
			write.writeObject(hardDiskMap);
			write.flush();
			write.close();
		} catch (Exception e) {
			logger.write("[Error] | [ HardDisk: pageReplacement method ]  - ObjectOutputStream couldn't open/write/close" , Level.SEVERE);
		}
		
		return hardDiskMap.get(moveToRamId);
	}
	public void shutDownRam(Map<Long,Page<byte[]>> pagesFromRam){
		MMULogger logger = MMULogger.getInstance();
		//update the pages from ram to Hard Disk hash map 
		for (Entry<Long, Page<byte[]>> entry : pagesFromRam.entrySet()) {
			hardDiskMap.put(entry.getKey(),entry.getValue());
		}
		try{
		//write it into the file
		write = new ObjectOutputStream(new FileOutputStream(DEFAULT_FILE_NAME));
		write.writeObject(hardDiskMap);
		write.flush();
		write.close();
		} catch(Exception e){
			e.printStackTrace();
			logger.write("[Error] | [ HardDisk: shutDownRam method ]  - ObjectOutputStream couldn't open/write/close" , Level.SEVERE);
		}
	}
}
