package com.hit.memoryunits;

import java.io.IOException;
import java.util.logging.Level;

import com.hit.algorithm.IAlgoCache;
import com.hit.util.MMULogger;

public class MemoryManagementUnit{

	private RAM ram;
	private IAlgoCache<Long,Long> algo;

	public MemoryManagementUnit(int ramCapacity, IAlgoCache<Long,Long> algo){
		this.algo = algo;
		ram = new RAM(ramCapacity);
	}

	public Page<byte[]>[] getPages(Long[] pagesIds) 
			throws IOException{
		MMULogger logger = MMULogger.getInstance();
		Page<byte[]> page = null;

		for(Long p: pagesIds){
			if(ram.getPage(p) == null){ //page isn't in map
				if(ram.getMapSize() < ram.getInitialCapacity()){ // ram isn't full - page_fault
					try{
						page = HardDisk.getInstance().pageFault(p);
						logger.write("PF: " +page.getPageId(), Level.INFO);
						ram.addPage(page);
						algo.putElement(p, p);
					}catch(Exception e){
						e.printStackTrace();
						logger.write("[Error] | [ MemoryManagementUnit: getPages method] -  pageFault segment  " , Level.SEVERE);
					}
				}
				else if(ram.getMapSize() == ram.getInitialCapacity()){ //ram is full - page_replacement
					try{
							//get the page that remove from the algo cache
						Page<byte[]> removePageFromRam = ram.getPage(algo.putElement(p, p));
							//remove from the ram
						ram.removePage(removePageFromRam); 
							//insert the page into ram and put the remove page in hard disk
						page = HardDisk.getInstance().pageReplacement(removePageFromRam,p);
						ram.addPage(page);
						logger.write("PR: MTH " +removePageFromRam.getPageId()+ " MTR " + page.getPageId() , Level.INFO);
					}catch(Exception e){
						e.printStackTrace();
						logger.write("[Error] | [ MemoryManagementUnit: getPages method ] - pageReplacement segment  " , Level.SEVERE);
					}
				}
			}// page already in ram
			else { 
				algo.putElement(p, p);
			}
		}
		return ram.getPages(pagesIds);
	}
	public void shutDown(){
		MMULogger logger = MMULogger.getInstance();
		try {
			HardDisk.getInstance().shutDownRam(ram.getPages());
			ram.getPages().clear();
		}catch(Exception e) {
			e.printStackTrace();
			logger.write("[Error] | [ MemoryManagementUnit: shutDown method ] - shutDownRam from HasdDisk/clear ram pages  " , Level.SEVERE);	
		}
	}
}
