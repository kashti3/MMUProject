package com.hit.memoryunits;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.hit.algorithm.IAlgoCache;
import com.hit.algorithm.LRUAlgoCacheImpl;

public class MemoryManagmentUnitTest {
	
	private IAlgoCache<Long,Long> algo = new LRUAlgoCacheImpl<>(30);
	private MemoryManagementUnit mmu = new MemoryManagementUnit(30, algo);
	
	@Test
	public void testMemoryManagementUnit() throws IOException{
			//ram is empty - page_fulat
		Long[] pages = new Long[30];
		for (int i = 0; i < pages.length; i++) {
			pages[i] = new Long(i);
		}
		Page<byte[]>[] actual = mmu.getPages(pages);
		for (int i = 0; i < actual.length; i++) {
			Assert.assertEquals(pages[i], actual[i].getPageId());
		}
			//insert an existing pages
		Long[] existingPages = new Long[5];
		for (int i = 0; i < existingPages.length; i++) {
			existingPages[i] = new Long(i+1);
		}
		mmu.getPages(existingPages);
			//ram is full - page_remplacement
		Long[] newPages = new Long[5];
		for (int i = 0; i < newPages.length; i++) {
			newPages[i] = new Long(30+i);
		}
		Page<byte[]>[] actuals = mmu.getPages(newPages);
		for (int i = 0; i < actuals.length; i++) {
			Assert.assertEquals(newPages[i], actuals[i].getPageId());
		}	
	}

/*	@Test
	public void testMemoryManagementUnitTest(){
		
		IAlgoCache<Long, Long> algo = new LRUAlgoCacheImpl<>(5);
		MemoryManagementUnit mmu = new MemoryManagementUnit(5, algo);
		Page<byte[]>[] pages = null;
		
		
		//Get 1-5 pages (RAM capacity equals to 5, only pageFault method is tested)
		Long[] pageIds = new Long[] {(long) 1,(long) 2,(long) 3,(long) 4,(long) 5};
		Long[] expected = new Long[] {(long) 1,(long) 2,(long) 3,(long) 4,(long) 5};

		try {
			pages = mmu.getPages(pageIds);
		} catch (IOException e) {
			System.out.println("HardDisk file is missing");
			e.printStackTrace();
		}
		
		if(pages != null) {
			for(int i = 0 ; i < pages.length ; i++) {
				Assert.assertEquals(expected[i], pages[i].getPageId());
			}
		}


		//Get 9-20 pages (RAM capacity is full, pageReplacement method is tested)
		pageIds = new Long[13];
		expected = new Long[13];
		
		for(int i = 9 ; i < 21 ; i++) {
			pageIds[i-9] = (long) i;
		}
		pageIds[12] = (long) 50;
		
		for(int i = 9 ; i < 21 ; i++) {
			expected[i-9] = (long) i;
		}
		expected[12] = (long) 50;

		try {
			pages = mmu.getPages(pageIds);
		} catch (IOException e) {
			System.out.println("HardDisk file is missing");
			e.printStackTrace();
		}

		if(pages != null) {
			for(int i = 0 ; i < pages.length ; i++) {
				Assert.assertEquals(expected[i], pages[i].getPageId());
			}
		}
	}
	*/
}
