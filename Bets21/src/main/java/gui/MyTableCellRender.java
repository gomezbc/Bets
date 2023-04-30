package gui;
import java.awt.Component;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table. DefaultTableCellRenderer;

import domain.Event;

public class MyTableCellRender extends DefaultTableCellRenderer{
	private Vector<Event> ev;
	private static int index=0;
	
	public MyTableCellRender(Vector<Event> ev) {	
		this.ev = ev;
	}

	@Override
	public Component getTableCellRendererComponent (JTable jtable, Object o, boolean bln, boolean bin, int i, int i1) {
		Component com = super.getTableCellRendererComponent (jtable, o, bln, bln, i, i);
		if(index<ev.size()) {
			EventPanel ep = new EventPanel(ev.get(index), jtable.getWidth(), jtable.getRowHeight());
			index++;
			return ep;
		}
		else return com;
	}

	public static int getIndex() {
		return index;
	}

	public static void setIndex(int index) {
		MyTableCellRender.index = index;
	}


}


