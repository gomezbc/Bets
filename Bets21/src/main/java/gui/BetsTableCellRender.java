package gui;
import java.awt.Component;
import java.awt.Font;
import java.awt.Color;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table. DefaultTableCellRenderer;

import configuration.UtilDate;
import domain.Event;

public class BetsTableCellRender extends DefaultTableCellRenderer{

	private boolean hasWin = false;
	private boolean hasFinished = false;
	private boolean isClosed = false;
	
	private Color finished = new Color(255,204,128);
	private Color win = new Color(139,195,74);
	private Color lose = new Color(255,205,210);
	
	public BetsTableCellRender() {	
	}

	@Override
	public Component getTableCellRendererComponent (JTable jtable, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent (jtable, value, isSelected, hasFocus, row, column);
		this.setBorder(new EmptyBorder(1,1,1,1));
		this.setFont(new Font("Roboto", Font.PLAIN, 14));
		this.setForeground(Color.black);

		Date today = Calendar.getInstance().getTime();
		List<String> dateList = Arrays.asList(today.toLocaleString().split(","));
		String todayDate = dateList.get(0)+dateList.get(1);
		String betDate = ((String) jtable.getModel().getValueAt(row, 0));
		if(todayDate.compareTo(betDate)>0){
			hasFinished = true;
		}
		
		String balance = ((String) jtable.getModel().getValueAt(row, 6));
		if(balance.contains("+")){
			hasWin = true;
			isClosed = true;
		}else if(balance.contains("-")){
			hasWin = false;
            isClosed = true;
		}

		if(hasFinished && !isClosed){
			this.setBackground(finished);
		}else if(hasWin && isClosed){
			this.setBackground(win);
		}else if(!hasWin && isClosed){
			this.setBackground(lose);
		}
		
		return this;
	}
}


