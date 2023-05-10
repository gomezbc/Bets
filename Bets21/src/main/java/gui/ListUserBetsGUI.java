package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import businessLogic.BLFacade;
import domain.Bet;
import domain.User;
import exceptions.BetDoesntExist;
import exceptions.UserDoesntExist;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ListUserBetsGUI extends JPanel {

	private static final long serialVersionUID = 1L;
	private JScrollPane scrollPane;
	private JTable tableBets;
	private static DefaultTableModel tableModelBets;
	
	public static int getFila() {
		return fila;
	}



	public static void setFila(int fila) {
		ListUserBetsGUI.fila = fila;
	}



	public static DefaultTableModel getTableModelBets() {
		return tableModelBets;
	}



	public static void setTableModelBets(DefaultTableModel tableModelBets) {
		ListUserBetsGUI.tableModelBets = tableModelBets;
	}

	private Vector<String> columnNames = new Vector<String>(Arrays.asList("Fecha","Evento","Pregunta","Pronostico","Ganancia","Apostado","Balance"));
	private JButton btnAnularApuesta;
	private JButton btnAumentarApuesta;

	private static int betNumber2;
	private static int fila;
	
	

	
	
	public static int getBetNumber2() {
		return betNumber2;
	}

	

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
		tableBets.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tableBets.getSelectedRow();
				Date todayDate = Calendar.getInstance().getTime();
				long todayTime = todayDate.getTime();
				String eventDate = ((String) tableBets.getModel().getValueAt(row, 0));
				long eventTime = Date.parse(eventDate);
				if(todayTime < eventTime) {
					btnAnularApuesta.setEnabled(true);
					btnAumentarApuesta.setEnabled(true);
					
				}else {
					btnAnularApuesta.setEnabled(false);
					btnAumentarApuesta.setEnabled(false);
				}
			}
		});
		tableBets.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableBets.setFont(new Font("Roboto", Font.PLAIN, 14));
		tableBets.setBorder(null);
		tableBets.setBounds(0, 0, 1, 1);
		
		scrollPane.setViewportView(tableBets);

		tableModelBets = new DefaultTableModel(null, columnNames);
		tableBets.setModel(tableModelBets);
		
		updateTable();

	
		btnAnularApuesta = new JButton("Anular Apuesta");
		btnAnularApuesta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = tableBets.getSelectedRow();
				int betNumber = ((int) tableBets.getModel().getValueAt(row, 7));
				User u = MainGUI.getUserRegistered();
				BLFacade facade = MainGUI.getBusinessLogic();
				try {
					facade.removeBet(betNumber);
					try {
						//Actualizamos el saldo del usuario
						MainGUI.setUserRegistered(facade.getUser(u.getDni()));
						UserGUI.updateSaldo();
					} catch (UserDoesntExist e1) {
						e1.printStackTrace();
					}
				} catch (BetDoesntExist e1) {
					e1.printStackTrace();
				}
				tableModelBets.removeRow(row);
			}
		});
		btnAnularApuesta.setEnabled(false);
		btnAnularApuesta.setBackground(Color.WHITE);
		btnAnularApuesta.setFont(new Font("Roboto", Font.BOLD, 14));
		btnAnularApuesta.setBounds(220, 504, 186, 26);
		add(btnAnularApuesta);
		
		
		
		
		btnAumentarApuesta = new JButton("Aumentar Apuesta");
		btnAumentarApuesta.setBackground(Color.WHITE);
		btnAumentarApuesta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fila = tableBets.getSelectedRow();
				
				betNumber2 = ((int) tableBets.getModel().getValueAt(fila, 7));
				
				JFrame a = new AumentarApuestaGUI();
				a.setVisible(true);
			
			}
			
		});
		updateTable();
		
		
		
		
		btnAumentarApuesta.setFont(new Font("Dialog", Font.BOLD, 14));
		btnAumentarApuesta.setEnabled(false);
		btnAumentarApuesta.setBounds(444, 504, 209, 27);
		add(btnAumentarApuesta);
		
		
		
		
	}
	
	public void updateTable() {
		tableModelBets.setDataVector(null, columnNames);
		tableModelBets.addColumn("#B"); //Bet number, no aparece en la tabla, se usa para identificar la apuesta
		User u = MainGUI.getUserRegistered();
		Vector<Bet> bets = u.getBets();
		Vector<Vector<Object>> tableData = new Vector<Vector<Object>>();
		for(Bet b: bets) {
			Vector<Object> row = new Vector<Object>();
			Date d = b.getForecast().getQuestion().getEvent().getEventDate();
			if(d == null) continue;
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
			row.add(b.getBetNumber());
			tableData.add(row);
		}
		tableData.sort(new Comparator<Object>() {
			@Override
            public int compare(Object o1, Object o2) {
                Vector<Object> v1 = (Vector<Object>) o1;
                Vector<Object> v2 = (Vector<Object>) o2;
                long t1 = Date.parse(v1.get(0).toString());
                long t2 = Date.parse(v2.get(0).toString());
                if(t1 < t2) return -1;
                else if(t1 > t2) return 1;
                else return 0;
            }
		});
		tableModelBets.setDataVector(tableData, columnNames);
		tableBets.getColumnModel().getColumn(0).setPreferredWidth(15);
		tableBets.getColumnModel().getColumn(2).setPreferredWidth(110);
		tableBets.getColumnModel().getColumn(4).setPreferredWidth(20);
		tableBets.getColumnModel().getColumn(5).setPreferredWidth(20);
		tableBets.getColumnModel().getColumn(6).setPreferredWidth(20);
		tableBets.getColumnModel().removeColumn(tableBets.getColumnModel().getColumn(7));
	}
}
