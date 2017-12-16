package com.hit.controller;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.hit.driver.CLI;
import com.hit.model.MMUModel;
import com.hit.view.MMUView;

public class MMUController implements Controller,Observer {

	private Boolean gotLog = false;
	private MMUModel model;
	private MMUView view;
	
	public MMUController(MMUModel model, MMUView view) {
		super();
		this.model = model;
		this.view = view;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg0 instanceof CLI) {
			model.setConfiguration((List<String>) arg1);
			model.start();
		}
		else if (arg0 instanceof MMUModel) {
			if(!gotLog) {
				view.setLog((List<String>) arg1);
				gotLog =true;
			}else {
				view.setAlgo((String) arg1);
				view.start();
				gotLog =false;
			}
		}
		else if (arg0 instanceof MMUView) {
			
		}
		
	}

}
