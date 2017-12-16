package com.hit.processes;

import java.util.List;

public class ProcessCycle {
	
	private List<Long> pages;
	private int sleepMs;
	private List<byte[]> data;
	
	
	public ProcessCycle(List<byte[]> data, int sleepMs, List<Long> pages) {
		super();
		this.data = data;
		this.sleepMs = sleepMs;
		this.pages = pages;
	}
	
	@Override
	public String toString() {
		return "ProcessCycle [data=" + data + ", sleepMs=" + sleepMs + ", pages=" + pages + "]";
	}
	public List<byte[]> getData() {
		return data;
	}
	public void setData(List<byte[]> data) {
		this.data = data;
	}
	public int getSleepMs() {
		return sleepMs;
	}
	public void setSleepMs(int sleepMs) {
		this.sleepMs = sleepMs;
	}
	public List<Long> getPages() {
		return pages;
	}
	public void setPages(List<Long> pages) {
		this.pages = pages;
	}

}
