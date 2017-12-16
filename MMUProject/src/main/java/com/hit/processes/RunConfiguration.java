package com.hit.processes;

import java.util.List;

public class RunConfiguration {
	
	private List<ProcessCycles> processesCycles;

	public RunConfiguration(List<ProcessCycles> processesCycles) {
		super();
		this.processesCycles = processesCycles;
	}

	@Override
	public String toString() {
		return "RunConfiguration [processesCycles=" + processesCycles + "]";
	}

	public List<ProcessCycles> getProcessesCycles() {
		return processesCycles;
	}

	public void setProcessesCycles(List<ProcessCycles> processesCycles) {
		this.processesCycles = processesCycles;
	}

}
