package com.hit.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JPanel;

public class BottunsPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	JButton play;
	JButton playAll;
	private int i;
	MMUView view;
	
	public BottunsPanel(MMUView view) {
		super();
		this.view = view;
		i = 0;
		setOpaque(false);
		setButtons();
		
	}

	public void setButtons() {
		JPanel mainPanel = new JPanel();
		this.add(mainPanel);
		
		play = new JButton("Play");
		playAll= new JButton("Play All");
		
		play.setEnabled(true);
		play.setVerticalTextPosition(AbstractButton.CENTER);
		play.setHorizontalTextPosition(AbstractButton.LEADING);
		play.addActionListener(this);
		
		playAll.setEnabled(true);
		playAll.setVerticalTextPosition(AbstractButton.BOTTOM);
		playAll.setHorizontalTextPosition(AbstractButton.CENTER);
		playAll.addActionListener(this);
		
	
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(play, BorderLayout.PAGE_START);
		mainPanel.add(playAll, BorderLayout.CENTER);
		
		
	}
	
	public void endOfLog() {
		play.setEnabled(false);
		playAll.setEnabled(false);
		view.pageFaultcount = 0;
		view.pageReplacementcount = 0;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == play) {
			if(i < view.log.size())
				view.buttonWasClicked(view.log.get(i++));
			else { //end of log
				endOfLog();
			}
			
		}else {//playAll was clicked
			for ( ; i < view.log.size(); i++) {
				view.buttonWasClicked(view.log.get(i));
			}
			endOfLog();
		}
		
		
	}

}
