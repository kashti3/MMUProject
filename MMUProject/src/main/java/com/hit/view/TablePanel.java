package com.hit.view;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.JTable;

public class TablePanel extends JPanel{
	
	private static final long serialVersionUID = -6129488007171548172L;
	 JTable table;
	Rectangle cell;
	

	public TablePanel() {
		super();
		setPreferredSize(new Dimension(600, 150));
		
		table = new JTable(6, 21);
		table.setPreferredScrollableViewportSize(new Dimension(1300, 200));
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 21; j++) {
				if (i == 0)
					table.setValueAt(j+1, i, j);
				else {
					table.setValueAt(0, i, j);
				}
			}
		}
			//set size of every column in table
		for (int i = 0 ; i < 21 ; i++) {
			table.getColumnModel().getColumn(i).setMaxWidth(25);
			table.getColumnModel().getColumn(i).setMinWidth(25);
		}
		setLayout(null);
		Insets insets = getInsets();
		Dimension size = table.getPreferredSize();
		table.setBounds(30 + insets.left, 20 + insets.top,
	             size.width, size.height);
		setOpaque(false);
		add(table);
	}



}
