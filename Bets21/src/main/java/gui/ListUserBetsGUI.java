package gui;

import java.util.Vector;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import domain.*;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListUserBetsGUI extends JPanel {

	private static final long serialVersionUID = 1L;
	private JScrollPane scrollPane;
	private JTable tableBets;
	private DefaultTableModel tableModelBets;
	
	private Vector<String> columnNames = new Vector<String>(Arrays.asList("Fecha","Evento","Pregunta","Pronostico","Ganancia","Apostado","Balance"));

	/**
	 * Create the panel.
	 */
	public ListUserBetsGUI() {
		try
		{
			jbInit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void jbInit() {
		setSize(new Dimension(886, 541));
		setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setFont(new Font("Roboto", Font.PLAIN, 14));
		scrollPane.setBorder(null);
		scrollPane.setBounds(12, 12, 862, 481);
		add(scrollPane);
		
		tableBets = new JTable() {
			public TableCellRenderer getCellRenderer(int row, int column) {
		        return new BetsTableCellRender();
		    }
		};
		tableBets.setFont(new Font("Roboto", Font.PLAIN, 14));
		tableBets.setBorder(null);
		tableBets.setBounds(0, 0, 1, 1);
		
		scrollPane.setViewportView(tableBets);

		tableModelBets = new DefaultTableModel(null, columnNames);
		tableBets.setModel(tableModelBets);
		
		updateTable();

	}
	
	public void updateTable() {
		tableModelBets.setDataVector(null, columnNames);
		
		User u = MainGUI.getUserRegistered();
		Vector<Bet> bets = u.getBets();
		Vector<Vector<Object>> tableData = new Vector<Vector<Object>>();
		for(Bet b: bets) {
			Vector<Object> row = new Vector<Object>();
			Date d = b.getForecast().getQuestion().getEvent().getEventDate();
			List<String> dateList = Arrays.asList(d.toLocaleString().split(","));
			row.add(dateList.get(0)+dateList.get(1));
			row.add(b.getForecast().getQuestion().getEvent().getDescription());
			row.add(b.getForecast().getQuestion().getQuestion());
			row.add(b.getForecast().getDescription());
			row.add(String.format("%.2f", b.getForecast().getGain()));
			row.add(String.format("%.2f", b.getBetMoney()));
			if(b.getForecast().getQuestion().getResult()==null) row.add(String.format("%.2f", b.getBetMoney()));
			else {
				if(b.getForecast().getQuestion().getResult() == b.getForecast()) {
					row.add("+ "+String.format("%.2f", b.getBetMoney() * b.getForecast().getGain()));
				}else {
					row.add("- "+String.format("%.2f", b.getBetMoney()));
				}
			}
			tableData.add(row);
		}
		tableData.sort(new Comparator<Object>() {
			@Override
            public int compare(Object o1, Object o2) {
                Vector<Object> v1 = (Vector<Object>) o1;
                Vector<Object> v2 = (Vector<Object>) o2;
                return ((String) v1.get(0)).compareTo((String) v2.get(0));
            }
		});
		tableModelBets.setDataVector(tableData, columnNames);
		tableBets.getColumnModel().getColumn(0).setPreferredWidth(15);
		tableBets.getColumnModel().getColumn(2).setPreferredWidth(110);
		tableBets.getColumnModel().getColumn(4).setPreferredWidth(20);
		tableBets.getColumnModel().getColumn(5).setPreferredWidth(20);
		tableBets.getColumnModel().getColumn(6).setPreferredWidth(20);
	}
}
