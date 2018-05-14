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
}
