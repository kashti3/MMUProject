package com.hit.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MMUView extends Observable implements View {
	
	private InfoPanel infoPanel;
	private TablePanel tablePanel;
	private BottunsPanel buttonsPanel;
	JFrame frame;
	List<String> log;
	int pageFaultcount;
	int pageReplacementcount;
	String algo = null;
	List<String> pageListInRam;
	List<String> processList;
	Boolean firstStart = true;
	Component cell;
	
	
	public MMUView() {
		firstStart = true;
		pageFaultcount = 0;
		pageReplacementcount = 0;
	}
	
	public void setLog(List<String> log) {
		this.log = log;
	}
	public void setAlgo(String algo) {
		this.algo = algo;
	}
	
	
	public void buttonWasClicked(String command) {

		String[] val;
		infoPanel.currentLogCommandAns.setText(command);
		if(command.startsWith("RC")) {
			val = command.split(" ");
			infoPanel.ramCapacityAns.setText(val[2]);
			pageListInRam = new ArrayList<String>(Integer.parseInt(val[2]));
		}else if(command.startsWith("PN")) {
			val = command.split(" ");
			processList = new ArrayList<>(Integer.parseInt(val[2]));
		}
		else if(command.startsWith("PF")) {
			val = command.split(" ");
			pageListInRam.add(val[2]);
			infoPanel.pagesInRamAns.setText(pageListInRam.toString());
			++pageFaultcount;
			infoPanel.pageFaultAns.setText(Integer.toString(pageFaultcount));
		}
		else if(command.startsWith("PR")) {
			val = command.split(" ");
			pageListInRam.remove(val[3]);
			for (int i = 1; i <= 5; i++) {
				tablePanel.table.setValueAt(0, i, Integer.parseInt(val[3]) - 1);
			}
			++pageReplacementcount;
			infoPanel.pageReplacementAns.setText(Integer.toString(pageReplacementcount));
			pageListInRam.add(val[5]);
			infoPanel.pagesInRamAns.setText(pageListInRam.toString());
		}
		else if(command.startsWith("GP")) {
			val = command.split(" ");
			processList.add(val[2].substring(1));
			if(infoPanel.list.isSelectedIndex(Integer.parseInt(val[2].substring(1)) -1)) {
				for (int i = 5,j=1; i < val.length; j++,i+=2) {
					tablePanel.table.setValueAt(Integer.parseInt(val[i]),j , Integer.parseInt(val[3]) - 1);
				}
			}
		}
	}
	
	public void createFrame(){
		infoPanel = new InfoPanel(this);
		tablePanel = new TablePanel();
		buttonsPanel = new BottunsPanel(this);
		
		frame = new JFrame("MMU Simulator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(700, 400));
		try {
			frame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("src\\main\\resources\\com\\hit\\image\\image.jpg")))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		infoPanel.currentAlgoAns.setText(algo);
		infoPanel.pageFaultAns.setText(Integer.toString(pageFaultcount));
		infoPanel.pageReplacementAns.setText(Integer.toString(pageReplacementcount));
		
		frame.setLayout(new FlowLayout());
		frame.add(tablePanel);
		frame.add(infoPanel);
		frame.add(buttonsPanel);
		frame.pack();
		frame.setVisible(true);
		
	}
	@Override
	public void start(){//start the view operation
		if(firstStart) {
			firstStart = false;
		}else {
			frame.setVisible(false);
			frame.dispose();
		}
		createFrame();
	}

	

}
