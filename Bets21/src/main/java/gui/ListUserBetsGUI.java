package gui;

import java.awt.Color;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import domain.*;
import java.awt.Dimension;

public class ListUserBetsGUI extends JPanel {

	private static final long serialVersionUID = 1L;
	private JScrollPane scrollPane;
	private JTable tableBets;
	private DefaultTableModel tableModelBets;
	
	private String[] columnNames = new String[] {
			"#Apuesta","Evento","Pregunta","Pronostico","Ganancia","Dinero Apostado","Balance"
	};

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
		setBounds(100, 100, 863, 629);
		setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 12, 839, 455);
		add(scrollPane);
		
		tableBets = new JTable();
		tableBets.setBounds(0, 0, 1, 1);
		
		scrollPane.setViewportView(tableBets);

		tableModelBets = new DefaultTableModel(null, columnNames);
		tableBets.setModel(tableModelBets);
		
		updateTable();

	}
	
	public void updateTable() {
		tableModelBets.setRowCount(0);
		User u = MainGUI.getUserRegistered();
		Vector<Bet> bets = new Vector<Bet>();
		bets = u.getBets();
		for(Bet b: bets) {
			Vector<Object> row = new Vector<Object>();
			row.add(b.getBetNumber());
			row.add(b.getForecast().getQuestion().getEvent().getDescription());
			row.add(b.getForecast().getQuestion().getQuestion());
			row.add(b.getForecast().getDescription());
			row.add(b.getForecast().getGain());
			row.add(b.getBetMoney());
			if(b.getForecast().getQuestion().getResult()==null) row.add(b.getBetMoney());
			else {
				if(b.getForecast().getQuestion().getResult() == b.getForecast()) {
					row.add("+ "+b.getBetMoney() * b.getForecast().getGain());
				}else {
					row.add("- "+b.getBetMoney());
				}
			}
			tableModelBets.addRow(row);
		}
	}
}
