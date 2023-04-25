package gui;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table. DefaultTableCellRenderer;

import domain.Event;

public class TableActionCellRender extends DefaultTableCellRenderer{
	private Event ev;
	
	public TableActionCellRender(Event ev) {
		this.ev = ev;
	}

	@Override
	public Component getTableCellRendererComponent (JTable jtable, Object o, boolean bln, boolean bin, int i, int i1) {
		Component com = super.getTableCellRendererComponent (jtable, o, bln, bln, i, i);
		EventPanel action = new EventPanel(ev) ;
		return action;
	}


}


