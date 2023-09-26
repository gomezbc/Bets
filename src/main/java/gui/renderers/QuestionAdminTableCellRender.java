package gui.renderers;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

import businessLogic.BLFacade;
import domain.Question;
import exceptions.QuestionDoesntExist;
import gui.MainGUI;


public class QuestionAdminTableCellRender extends DefaultTableCellRenderer{
	
	private static final long serialVersionUID = 1L;

	private Color onGoing = new Color(224,224,224);
	private Color onGoingS = new Color(204,204,204);
	private Color closed = new Color(232, 195,158);
	private Color closedS = new Color(222, 184, 135);
	private Color pending = new Color(255, 191, 0);
	private Color pendingS = new Color(255,204,51);
	
	
	public Component getTableCellRendererComponent(JTable jtable, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent (jtable, value, isSelected, hasFocus, row, column);
		setBorder(new EmptyBorder(1,1,1,1));
		setFont(new Font("Roboto", Font.PLAIN, 14));
		setForeground(Color.black);
		setBackground(onGoing);
		if (isSelected) setBackground(onGoingS);
		
		int numQuestion = ((int) jtable.getModel().getValueAt(row, 0));
		BLFacade facade = MainGUI.getBusinessLogic();
		Question q = null;
		try {
			q = facade.getQuestion(numQuestion);
			Date todayDate = Calendar.getInstance().getTime();
			Date eventDate = q.getEvent().getEventDate();
			Boolean readyToClose = (todayDate.compareTo(eventDate)>=1);
			Boolean isClosed = (q.getResult() != null);
			if (readyToClose) {
				if(isClosed) {
					if(isSelected) setBackground(closedS);
					else setBackground(closed);
				}else {
					if(isSelected) setBackground(pendingS);
					else setBackground(pending);
				}
			}else {
				if(isSelected) setBackground(onGoingS);
				else setBackground(onGoing);
			}
			
		} catch (QuestionDoesntExist e) {
			return this;
		}
		
		
		return this;
	}
}
