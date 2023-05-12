package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;

import businessLogic.BLFacade;
import domain.Event;
import domain.Forecast;
import domain.Question;
import domain.User;
import exceptions.BetAlreadyExist;
import exceptions.QuestionDoesntExist;
import theme.Bets21Theme;

import javax.swing.ImageIcon;


public class EventInfoUserGUI extends JPanel {
	private static final long serialVersionUID = 1L;
	private final JLabel jLabelQueries = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Queries")); 
	private JScrollPane scrollPaneQueries = new JScrollPane();
	private JScrollPane scrollPaneForecast = new JScrollPane();
	private JTable tableQueries = new JTable() {
		public TableCellRenderer getCellRenderer(int row, int column) {
	        return new EventTableCellRender();
	    }
	};
	private JTable tableForecast = new JTable();

	private DefaultTableModel tableModelQueries;
	private DefaultTableModel tableModelForecast;
	
	private String[] columnNamesQueries = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("QueryN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Query"),
			"Apuesta Mínima"

	};
	private String[] columnNamesForecast = new String[] {
			"Pronostico#","Pronostico","Ganancia"
	};
	private JPanel EventInfo;
	private JLabel lblDineroAApostar;
	private JTextField dineroApostar;
	private JButton btnApostar;
	private JLabel lblError;

	public EventInfoUserGUI(Event ev)
	{
		try
		{
			jbInit(ev);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	
	public void jbInit(Event ev) throws Exception
	{
		Bets21Theme.setup();
		this.setLayout(null);
		this.setSize(new Dimension(886, 541));
		this.setBackground(new Color(238, 238, 238));
		jLabelQueries.setFont(new Font("Roboto", Font.PLAIN, 14));
		jLabelQueries.setBounds(40, 188, 598, 20);
		this.add(jLabelQueries);

		scrollPaneQueries.setBounds(new Rectangle(40, 214, 385, 170));
		tableQueries.setFont(new Font("Roboto", Font.PLAIN, 14));


		scrollPaneQueries.setViewportView(tableQueries);
		tableModelQueries = new DefaultTableModel(null, columnNamesQueries);

		scrollPaneQueries.setFont(tableQueries.getFont());
		tableQueries.setModel(tableModelQueries);
		tableQueries.setRowHeight(20);
		tableQueries.getColumnModel().getColumn(0).setPreferredWidth(40);
		tableQueries.getColumnModel().getColumn(1).setPreferredWidth(190);
		tableQueries.getColumnModel().getColumn(2).setPreferredWidth(55);
		this.add(scrollPaneQueries, null);
		for(Question q: ev.getQuestions()) {
			Vector<Object> row = new Vector<Object>();
			row.add(q.getQuestionNumber());
			row.add(q.getQuestion());
			row.add(q.getBetMinimum());
			tableModelQueries.addRow(row);
		}
		
		
		scrollPaneForecast.setBounds(new Rectangle(40, 274, 406, 116));
		scrollPaneForecast.setBounds(437, 214, 410, 170);
		add(scrollPaneForecast);
		tableForecast.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnApostar.setEnabled(true);
			}
		});
		tableForecast.setFont(new Font("Roboto", Font.PLAIN, 14));
		

		scrollPaneForecast.setViewportView(tableForecast);
		tableModelForecast = new DefaultTableModel(null, columnNamesForecast);
		tableForecast.setModel(tableModelForecast);
		scrollPaneForecast.setViewportView(tableForecast);
		scrollPaneForecast.setFont(tableForecast.getFont());
		
		tableForecast.setRowHeight(20);
		tableForecast.getColumnModel().getColumn(0).setPreferredWidth(40);
		tableForecast.getColumnModel().getColumn(1).setPreferredWidth(150);
		tableForecast.getColumnModel().getColumn(2).setPreferredWidth(60);
		
		
		/*
		tableForecast = new JTable() {

			public TableCellRenderer getCellRenderer(int row, int column) {
		        return new BetsTableCellRender();
		    }
		};
		*/
		
		
		tableQueries.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnApostar.setEnabled(false);
				int i=tableQueries.getSelectedRow();
				BLFacade facade=MainGUI.getBusinessLogic();
				int qNumber = (int) tableModelQueries.getValueAt(i,0);
				Question q;
				try {
					q = facade.getQuestion(qNumber);
					Vector<Forecast> forecasts = q.getForecasts();

					tableModelForecast.setDataVector(null, columnNamesForecast);
	
					if (forecasts.isEmpty())
						jLabelQueries.setText("No hay pronosticos para la pregunta " + q.getQuestion());
					else 
						jLabelQueries.setText("Pronosticos para la pregunta " + q.getQuestion());
	
					for (domain.Forecast f:forecasts){
						Vector<Object> row = new Vector<Object>();
						row.add(f.getForecastNumber());
						row.add(f.getDescription());
						row.add(String.format("%.2f", f.getGain()));
						tableModelForecast.addRow(row);	
					}
				} catch (QuestionDoesntExist e1) {
					e1.printStackTrace();
				}
				tableForecast.getColumnModel().getColumn(0).setPreferredWidth(40);
				tableForecast.getColumnModel().getColumn(1).setPreferredWidth(150);
				tableForecast.getColumnModel().getColumn(2).setPreferredWidth(60);
			}
		});

		
		EventInfo = new EventInfoPanel(ev, 806, 100);
		EventInfo.setBounds(40, 37, 807, 165);
		add(EventInfo);
		
		lblDineroAApostar = new JLabel("DINERO A APOSTAR");
		lblDineroAApostar.setFont(new Font("Roboto", Font.PLAIN, 14));
		lblDineroAApostar.setBounds(502, 396, 153, 25);
		add(lblDineroAApostar);
		
		dineroApostar = new JTextField();
		dineroApostar.setFont(new Font("Roboto", Font.PLAIN, 14));
		dineroApostar.setText("");
		dineroApostar.setBounds(673, 396, 114, 25);
		add(dineroApostar);
		dineroApostar.setColumns(10);
		
		btnApostar = new JButton("Apostar"); 
		ImageIcon icon = new ImageIcon(EventInfoPanel.class.getResource("/icons/bet.png"));
		Image scaledIcon = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		btnApostar.setIcon(new ImageIcon(scaledIcon));
		btnApostar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblError.setVisible(false);
				BLFacade facade = MainGUI.getBusinessLogic();
				User u = MainGUI.getUserRegistered();
				int i = tableQueries.getSelectedRow();
				int qNumber = (int) tableModelQueries.getValueAt(i,0);
				Question q;
				try {
					q = facade.getQuestion(qNumber);
					float minBet = q.getBetMinimum();
					float dineroBet = Float.parseFloat(dineroApostar.getText());
					if(u.getSaldo() < dineroBet) {
						lblError.setText("Saldo insuficiente");
						lblError.setVisible(true);
					}else if(minBet > dineroBet) {
						lblError.setText("El dinero de la apuesta no supera el dinero mínimo");
						lblError.setVisible(true);
					}else {
						i = tableForecast.getSelectedRow();
						int fNumber = (int) tableModelForecast.getValueAt(i,0);
						Forecast f = facade.getForecast(fNumber);
						facade.createBet(u.getDni(), dineroBet, f);
						facade.modifySaldo(-dineroBet, u.getDni());
						MainGUI.setUserRegistered(facade.getUser(u.getDni()));//Actualizamos los datos del usuario
						UserGUI.updateSaldo();
						lblError.setText("Apuesta realizada");
						lblError.setVisible(true);
					}
				}catch(BetAlreadyExist e2) {
					lblError.setText("Ya has apostado a este pronostico");
					lblError.setVisible(true);
				}catch(Exception ea) {
					lblError.setText("Asegurate que estes introduciendo un número");
					lblError.setVisible(true);
				}
			}
		});
		btnApostar.setBackground(Color.WHITE);
		btnApostar.setFont(new Font("Roboto", Font.BOLD, 14));
		btnApostar.setBounds(599, 433, 118, 27);
		btnApostar.setEnabled(false);
		add(btnApostar);
		
		lblError = new JLabel("");
		lblError.setForeground(new Color(220, 20, 60));
		lblError.setFont(new Font("Roboto", Font.PLAIN, 14));
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setBounds(437, 465, 410, 25);
		add(lblError);
		
		//Si la fecha actual es posterior a la del evento, no se puede añadir preguntas y pronosticos
		Date today = Calendar.getInstance().getTime();
		if(today.after(ev.getEventDate())) {
			lblDineroAApostar.setEnabled(false);
			dineroApostar.setEnabled(false);
			btnApostar.setEnabled(false);
			lblError.setEnabled(false);
		}
	}
}