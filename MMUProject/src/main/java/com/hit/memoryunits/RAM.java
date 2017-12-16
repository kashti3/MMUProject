package com.hit.memoryunits;

import java.util.HashMap;
import java.util.Map;

public class RAM {
	
	private int initialCapacity;
	private Map<Long,Page<byte[]>> ram;
	
	public RAM(int initialCapacity){
		this.setInitialCapacity(initialCapacity);
		ram = new HashMap<>(initialCapacity);
		
	}
	
	public int getMapSize(){
		return ram.size();
	}
	
	public int getInitialCapacity() {
		return initialCapacity;
	}

	public void setInitialCapacity(int initialCapacity) {
		this.initialCapacity = initialCapacity;
	}
	
	public void addPage(Page<byte[]> addPage){ // add single page
		ram.put((Long)addPage.getPageId(),addPage);
	}
	
	public void addPages(Page<byte[]>[] addPages){ // add many pages
		for(Page<byte[]> page : addPages)
			ram.put((Long)page.getPageId(),page);
	}
	
	public Page<byte[]> getPage(Long page){ //return single page
		return ram.get(page);
	}
	
	public Map<Long,Page<byte[]>> getPages(){ // get all the pages in ram
		return ram;
	}
	
	public Page<byte[]>[] getPages(Long[] pageIds){ // get all pages in ram to Array
		@SuppressWarnings("unchecked")
		Page<byte[]>[] arr = new Page[pageIds.length];
		for (int i = 0; i < arr.length; i++) {
			if(ram.containsKey(pageIds[i]))
				arr[i] = ram.get(pageIds[i]);
			else{
				arr[i] = null;	
			}
		}
		return arr;
	}
	public void removePage(Page<byte[]> removePage){
		ram.remove(removePage.getPageId());
	}
	public void removePages(Page<byte[]>[] removePages){
		for(Page<byte[]> page : removePages)
			ram.remove(page.getPageId());
	}
	public void setPages(Map<Long,Page<byte[]>> pages){
		for(Map.Entry<Long,Page<byte[]>> p : pages.entrySet()){
			ram.put(p.getKey(),p.getValue());
		}
	}
	
}
