package com.hit.processes;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.logging.Level;

import com.hit.memoryunits.MemoryManagementUnit;
import com.hit.memoryunits.Page;
import com.hit.util.MMULogger;

public class Process implements Callable<Boolean> {

	private int processId;
	private MemoryManagementUnit mmu;
	private ProcessCycles processCycles;

	public Process(int processId, MemoryManagementUnit mmu, ProcessCycles processCycles) {
		super();
		this.setId(processId);
		this.mmu = mmu;
		this.processCycles = processCycles;
	}

	@Override
	public Boolean call() throws Exception { 
		MMULogger logger = MMULogger.getInstance();
		try{
			//start to run on each cycle
			for(ProcessCycle c : processCycles.getProcessCycles()){
				synchronized (mmu) {
					//get the pages on each cycle
					Long[] cyclePages= c.getPages().toArray(new Long[0]);
					Page<byte[]>[] pages = mmu.getPages(cyclePages);


					//writing to the log and set content for each page
					for (int j = 0; j < pages.length; j++) {
						if(pages[j].getContent().length == 5) {
							pages[j].setContent(c.getData().get(j));	
							logger.write("GP: "+"P"+this.processId+" "+pages[j].getPageId()+" "+ Arrays.toString(pages[j].getContent()),Level.INFO);
						}else {
							logger.write("GP: "+"P"+this.processId+" "+pages[j].getPageId()+" [ ]",Level.INFO);
						}
					}
				}
				Thread.sleep(c.getSleepMs());
			}
			return true;
		}catch(IOException | InterruptedException e){
			if( e instanceof IOException)
				logger.write("[Error] | [ Process: call mathod] - getPages method ", Level.SEVERE);
			else 
				logger.write("[Error] | [ Process: call mathod] - sleep was interrupt ", Level.SEVERE);
			return false;
		}
	}

	public int getId() {
		return processId;
	}

	public void setId(int processId) {
		this.processId = processId;
	}



}
