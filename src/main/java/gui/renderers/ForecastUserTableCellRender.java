package gui.renderers;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;


import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

import businessLogic.BLFacade;
import domain.Bet;
import domain.Forecast;
import domain.User;
import exceptions.ForecastDoesntExist;
import gui.MainGUI;


public class ForecastUserTableCellRender extends DefaultTableCellRenderer{
	
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
		setBackground(onGoing);
		
		int numForecast = ((int) jtable.getModel().getValueAt(row, 0));
		BLFacade facade = MainGUI.getBusinessLogic();
		User u = MainGUI.getUserRegistered();
		Forecast f = null;
		try {
			f = facade.getForecastByForecastNumber(numForecast);
			u = facade.getUserByDni(u.getDni());
			Boolean isResult = (f == f.getQuestion().getResult()); 
			Bet b = u.DoesBetExists(numForecast);
			Boolean hasBets = (b != null);
			Boolean hasFinished = (f.getQuestion().getResult() != null);
			if(hasBets) {
				if(isResult && hasFinished) {
					if(isSelected) setBackground(winS);
					else setBackground(win);
				}else if(hasFinished){
					if(isSelected) setBackground(loseS);
					else setBackground(lose);
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
		}catch (Exception e1) {
			return this;
		}
		
		
		return this;
	}
}
