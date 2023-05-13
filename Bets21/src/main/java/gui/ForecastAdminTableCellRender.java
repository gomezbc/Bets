package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

import businessLogic.BLFacade;
import domain.Forecast;
import exceptions.ForecastDoesntExist;

import java.util.Calendar;
import java.util.Date;



public class ForecastAdminTableCellRender extends DefaultTableCellRenderer{
	
	private static final long serialVersionUID = 1L;

	private Color onGoing = new Color(224,224,224);
	private Color onGoingS = new Color(204,204,204);
	private Color win = new Color(153, 217, 89);
	private Color winS = new Color(153,204,102);
	private Color lose = new Color(255,205,210);
	private Color loseS = new Color(255,153,153);
	private Color pending = new Color(255, 191, 0);
	private Color pendingS = new Color(255,204,51);
	
	public Component getTableCellRendererComponent(JTable jtable, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent (jtable, value, isSelected, hasFocus, row, column);
		setBorder(new EmptyBorder(1,1,1,1));
		setFont(new Font("Roboto", Font.PLAIN, 14));
		setForeground(Color.black);
		
		int numForecast = ((int) jtable.getModel().getValueAt(row, 0));
		BLFacade facade = MainGUI.getBusinessLogic();
		Forecast f = null;
		try {
			f = facade.getForecast(numForecast);
			Boolean isResult = (f == f.getQuestion().getResult()); 
			Boolean hasResult = (f.getQuestion().getResult() != null);
			Date todayDate = Calendar.getInstance().getTime();
			Date eventDate = f.getQuestion().getEvent().getEventDate();
			Boolean readyToClose = (todayDate.compareTo(eventDate)>=1);
			if(readyToClose) {
				if(hasResult) {
					if(isResult) {
						if(isSelected) setBackground(winS);
						else setBackground(win);
					}else {
						if(isSelected) setBackground(loseS);
						else setBackground(lose);
					}
				}else {
					if(isSelected) setBackground(pendingS);
					else setBackground(pending);
				}
			}else {
				if(isSelected) setBackground(onGoingS);
				else setBackground(onGoing);
			}
		} catch (ForecastDoesntExist e) {
			return this;
		}
		
		
		return this;
	}
}
