package gui;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table. DefaultTableCellRenderer;

public class BetsTableCellRender extends DefaultTableCellRenderer{

	private static final long serialVersionUID = 1L;
	private boolean hasWin = false;
	private boolean hasFinished = false;
	private boolean isClosed = false;
	
	private Color finished = new Color(255,204,128);
	private Color onGoing = new Color(224,224,224);
	private Color onGoingS = new Color(204,204,204);
	private Color win = new Color(153, 217, 89);
	private Color winS = new Color(153,204,102);
	private Color lose = new Color(255,205,210);
	private Color loseS = new Color(255,153,153);
	
	public BetsTableCellRender() {	
	}

	
	@Override
	public Component getTableCellRendererComponent (JTable jtable, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent (jtable, value, isSelected, hasFocus, row, column);
		this.setBorder(new EmptyBorder(1,1,1,1));
		this.setFont(new Font("Roboto", Font.PLAIN, 14));
		this.setForeground(Color.black);

		Date todayDate = Calendar.getInstance().getTime();
		long todayTime = todayDate.getTime();
		String eventDate = ((String) jtable.getModel().getValueAt(row, 0));
		long eventTime = Date.parse(eventDate);
		if(todayTime>eventTime){
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
			if(isSelected) this.setBackground(new Color(232,186,118));
			else this.setBackground(finished);
		}else if(hasWin && isClosed){
			if(isSelected) this.setBackground(winS);
			else this.setBackground(win);
		}else if(!hasWin && isClosed){
			if(isSelected) this.setBackground(loseS);
			else this.setBackground(lose);
		}else {
			if(isSelected) this.setBackground(onGoingS);
			else this.setBackground(onGoing);
		}

		
		return this;
	}
}


