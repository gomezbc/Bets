package gui.renderers;
import java.awt.Component;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table. DefaultTableCellRenderer;

import domain.Event;
import gui.EventInfoPanel;

public class EventInfoCellRender extends DefaultTableCellRenderer{
	private static final long serialVersionUID = 1L;
	private Vector<Event> ev;
	private static int index=0;
	
	public EventInfoCellRender(Vector<Event> ev) {	
		this.ev = ev;
	}

	@Override
	public Component getTableCellRendererComponent (JTable jtable, Object o, boolean bln, boolean bin, int i, int i1) {
		Component com = super.getTableCellRendererComponent (jtable, o, bln, bln, i, i);
		if(index<ev.size()) {
			EventInfoPanel ep = new EventInfoPanel(ev.get(index), jtable.getWidth(), jtable.getRowHeight());
			index++;
			return ep;
		}
		else return com;
	}

	public static int getIndex() {
		return index;
	}

	public static void setIndex(int index) {
		EventInfoCellRender.index = index;
	}


}


