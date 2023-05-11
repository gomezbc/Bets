package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.User;


public class EventTableCellRender extends DefaultTableCellRenderer{
	
	private static final long serialVersionUID = 1L;

	private Color onGoing = new Color(224,224,224);
	
	
	public Component getTableCellRendererComponent(JTable tabla, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		DefaultTableCellRenderer f = new DefaultTableCellRenderer();
		this.setBorder(new EmptyBorder(1,1,1,1));
		this.setFont(new Font("Roboto", Font.PLAIN, 14));
		this.setForeground(Color.black);
		
		
		String numeroPronostico = ((String) tabla.getModel().getValueAt(row, 0));
		User u = MainGUI.getUserRegistered();
		BLFacade facade = MainGUI.getBusinessLogic();
		
	    boolean Apostado = facade.bApostado(u, Integer.parseInt(numeroPronostico));
	    System.out.println(Apostado);
        if (Apostado & isSelected) {
            setBackground(onGoing);
        } else {
            setBackground(tabla.getBackground());
        }
		
		
		return f;
	}
}
