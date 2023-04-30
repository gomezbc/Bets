package gui;

import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import domain.*;
import java.awt.Dimension;
import java.awt.Font;

public class ListUserBetsGUI extends JPanel {

	private static final long serialVersionUID = 1L;
	private JScrollPane scrollPane;
	private JTable tableBets;
	private DefaultTableModel tableModelBets;
	
	private String[] columnNames = new String[] {
			"#Apuesta","Evento","Pregunta","Pronostico","Ganancia","Apostado","Balance"
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
		setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setFont(new Font("Roboto", Font.PLAIN, 14));
		scrollPane.setBorder(null);
		scrollPane.setBounds(12, 12, 862, 481);
		add(scrollPane);
		
		tableBets = new JTable();
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
		tableBets.getColumnModel().getColumn(0).setPreferredWidth(20);
		tableBets.getColumnModel().getColumn(4).setPreferredWidth(20);
		tableBets.getColumnModel().getColumn(5).setPreferredWidth(20);
		tableBets.getColumnModel().getColumn(6).setPreferredWidth(20);
		User u = MainGUI.getUserRegistered();
		Vector<Bet> bets = new Vector<Bet>();
		bets = u.getBets();
		for(Bet b: bets) {
			Vector<Object> row = new Vector<Object>();
			row.add(b.getBetNumber());
			row.add(b.getForecast().getQuestion().getEvent().getDescription());
			row.add(b.getForecast().getQuestion().getQuestion());
			row.add(b.getForecast().getDescription());
			row.add(String.format("%.2f", b.getForecast().getGain()));
			row.add(b.getBetMoney());
			if(b.getForecast().getQuestion().getResult()==null) row.add(String.format("%.2f", b.getBetMoney()));
			else {
				if(b.getForecast().getQuestion().getResult() == b.getForecast()) {
					row.add("+ "+String.format("%.2f", b.getBetMoney() * b.getForecast().getGain()));
				}else {
					row.add("- "+String.format("%.2f", b.getBetMoney()));
				}
			}
			tableModelBets.addRow(row);
		}
	}
}
