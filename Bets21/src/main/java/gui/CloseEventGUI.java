package gui;

import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ResourceBundle;

import javax.swing.JLabel;
import java.text.DateFormat;
import java.util.Locale;
import java.util.Calendar;
import java.util.Date;

import domain.Event;
import domain.Forecast;
import domain.Question;
import domain.User;
import exceptions.BetAlreadyExist;
import exceptions.EventFinished;
import exceptions.EventHasntFinished;
import exceptions.ForecastAlreadyExist;
import exceptions.ForecastDoesntExist;
import exceptions.QuestionAlreadyExist;
import exceptions.QuestionDoesntExist;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;


public class CloseEventGUI extends JPanel {
	private static final long serialVersionUID = 1L;
	private final JLabel jLabelQueries = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Queries")); 
	private JScrollPane scrollPaneQueries = new JScrollPane();
	private JScrollPane scrollPaneForecast = new JScrollPane();
	
	private JTable tableQueries = new JTable();
	private JTable tableForecast = new JTable();;

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
	private JButton btnAñadirPronostico;
	private JLabel lblInfo;

	public CloseEventGUI(Event ev)
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
		
		tableQueries.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
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

		
		EventInfo = new EventPanel(ev, 806, 100);
		EventInfo.setBounds(40, 37, 807, 165);
		add(EventInfo);
		
		btnAñadirPronostico = new JButton(ResourceBundle.getBundle("Etiquetas").getString("EventInfoGUI.btnApostar.text")); //$NON-NLS-1$ //$NON-NLS-2$
		btnAñadirPronostico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblInfo.setVisible(false);
				BLFacade facade=MainGUI.getBusinessLogic();
				int i=tableQueries.getSelectedRow();
				int questionNumber = (int) tableModelQueries.getValueAt(i,0);
				
				i=tableForecast.getSelectedRow();
				int forecastNumber = (int) tableModelForecast.getValueAt(i,0);
		
				try {
					if(facade.getQuestion(questionNumber).getResult()!=null) {
						lblInfo.setText("Ya tiene un resultado");
						lblInfo.setVisible(true);
					}else {
						facade.assignResult(questionNumber, forecastNumber);
						facade.updateCloseEvent(forecastNumber);
					}
				}catch(EventHasntFinished e1){
					lblInfo.setText("El evento no ha finalizado");
					lblInfo.setVisible(true);
				} catch (QuestionDoesntExist e1) {
					e1.printStackTrace();
				} catch (ForecastDoesntExist e1) {
					e1.printStackTrace();
				}
			}	
		});
		btnAñadirPronostico.setBackground(Color.WHITE);
		btnAñadirPronostico.setFont(new Font("Roboto", Font.BOLD, 14));
		btnAñadirPronostico.setBounds(584, 411, 103, 27);
		btnAñadirPronostico.setEnabled(false);
		add(btnAñadirPronostico);
		
		lblInfo = new JLabel("");
		lblInfo.setForeground(new Color(220, 20, 60));
		lblInfo.setFont(new Font("Roboto", Font.PLAIN, 14));
		lblInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfo.setBounds(437, 450, 410, 25);
		add(lblInfo);
		
		//Si la fecha actual es posterior a la del evento, no se puede añadir preguntas y pronosticos
		Date today = Calendar.getInstance().getTime();
		if(today.before(ev.getEventDate())) {
			btnAñadirPronostico.setEnabled(false);
			lblInfo.setEnabled(false);
		}else {
			btnAñadirPronostico.setEnabled(true);
			lblInfo.setEnabled(true);
		}
		
	}
}