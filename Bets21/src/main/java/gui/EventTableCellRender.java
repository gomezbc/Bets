package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Bet;
import domain.Forecast;
import domain.Question;
import domain.User;
import exceptions.QuestionDoesntExist;


public class EventTableCellRender extends DefaultTableCellRenderer{
	
	private static final long serialVersionUID = 1L;

	private Color onGoing = new Color(224,224,224);
	
	
	public Component getTableCellRendererComponent(JTable jtable, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent (jtable, value, isSelected, hasFocus, row, column);
		setBorder(new EmptyBorder(1,1,1,1));
		setFont(new Font("Roboto", Font.PLAIN, 14));
		setForeground(Color.black);
		setBackground(onGoing);
		
		int numQuestion = ((int) jtable.getModel().getValueAt(row, 0));
		User u = MainGUI.getUserRegistered();
		BLFacade facade = MainGUI.getBusinessLogic();
		Question q = null;
		try {
			q = facade.getQuestion(numQuestion);
			Vector<Forecast> fList=  q.getForecasts();
			for (Forecast f:fList) {
				Bet b = u.DoesBetExists(f.getForecastNumber());
				if(b != null) {
					if(isSelected) setBackground(new Color(255, 191, 0));
					else setBackground(new Color(255,204,51));
					break;
				}
			}
		} catch (QuestionDoesntExist e) {
			return this;
		}
		
		
		return this;
	}
}
