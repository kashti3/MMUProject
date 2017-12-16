package com.hit.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class InfoPanel extends JPanel {
	private static final long serialVersionUID = -2445191710235416230L;

	MMUView view;
	
	
	
	JList<String> list;
	private String[] proceesName;
	private JTextField processSelectation;
	
	private JTextField pageFault;
	private JTextField pageReplacement;
	private JTextField ramCapacity;
	private JTextField currentAlgo;
	private JTextField currentLogCommand;
	private JTextField pagesInRam;

	JTextField pageFaultAns;
	JTextField pageReplacementAns;
	JTextField ramCapacityAns;
	JTextField currentAlgoAns;
	JTextField currentLogCommandAns;
	JTextField pagesInRamAns;

	public InfoPanel(MMUView view) {
		super();
		this.view = view;
		
		setOpaque(false);
		
		setProcessList(7);
		
		setData();
		
		
	}

	public void setListVal(int size) {
		proceesName = new String[size];
		for (int i = 0; i < proceesName.length; i++) {
			proceesName[i] = "Process " + (i+1);
		}
	}

	

	public void setProcessList(int size) {
		JPanel mainPanel = new JPanel();
		this.add(mainPanel);
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setOpaque(false);
		
		processSelectation = new JTextField("Process Selection:");
		setListVal(size);
		list = new JList<String>(proceesName);
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		mainPanel.add(processSelectation, BorderLayout.PAGE_START);
		
		mainPanel.add(list, BorderLayout.CENTER);
	}
	
	public void setData() {
		JPanel mainPanel = new JPanel();
		this.add(mainPanel);
		mainPanel.setLayout(new GridLayout(0, 2));
		mainPanel.setPreferredSize(new Dimension(350, 150));
		
		pageFault =  new JTextField("Page Faults:");
		pageFaultAns =  new JTextField("     ");

		pageReplacement =  new JTextField("Page Replacement:");
		pageReplacementAns =  new JTextField("   ");

		ramCapacity =  new JTextField("Ram Capacity:");
		ramCapacityAns =  new JTextField("     ");

		currentAlgo =  new JTextField("Algo Cache In Use:");
		currentAlgoAns =  new JTextField("     ");

		currentLogCommand =  new JTextField("Current Log Command:");
		currentLogCommandAns =  new JTextField("     ");
		
		pagesInRam = new JTextField("Pages In Ram:");
		pagesInRamAns = new JTextField("     ");
		
		mainPanel.add(currentLogCommand);
		mainPanel.add(currentLogCommandAns);
		
		mainPanel.add(currentAlgo);
		mainPanel.add(currentAlgoAns);
		
		mainPanel.add(ramCapacity);
		mainPanel.add(ramCapacityAns);
		
		mainPanel.add(pagesInRam);
		mainPanel.add(pagesInRamAns);
		
		mainPanel.add(pageFault);
		mainPanel.add(pageFaultAns);
		
		mainPanel.add(pageReplacement);
		mainPanel.add(pageReplacementAns);
		


	}

}
