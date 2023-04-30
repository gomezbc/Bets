package gui;

import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JLabel;

import com.toedter.calendar.JCalendar;

import configuration.UtilDate;
import domain.Event;
import domain.Forecast;
import domain.Question;
import exceptions.QuestionDoesntExist;


public class FindQuestionsGUI extends JPanel {
	private static final long serialVersionUID = 1L;
	private final JLabel jLabelQueries = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Queries")); 
	private JScrollPane scrollPaneQueries = new JScrollPane();
	private JScrollPane scrollPaneForecast = new JScrollPane();
	
	private JTable tableQueries = new JTable();
	private JTable tableForecast = new JTable();;

	private DefaultTableModel tableModelQueries;
	private DefaultTableModel tableModelForecast;


	
	private String[] columnNamesEvents = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("EventN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Event"), 

	};
	private String[] columnNamesQueries = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("QueryN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Query")

	};
	private String[] columnNamesForecast = new String[] {
			"Pronostico#","Pronostico","Ganancia"
	};
	private JPanel EventInfo;

	public FindQuestionsGUI(Event ev)
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
		jLabelQueries.setBounds(40, 188, 598, 14);
		this.add(jLabelQueries);

		BLFacade facade = MainGUI.getBusinessLogic();
		scrollPaneQueries.setBounds(new Rectangle(40, 214, 368, 170));


		scrollPaneQueries.setViewportView(tableQueries);
		tableModelQueries = new DefaultTableModel(null, columnNamesQueries);

		tableQueries.setModel(tableModelQueries);
		tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);
		this.add(scrollPaneQueries, null);
		for(Question q: ev.getQuestions()) {
			Vector<Object> row = new Vector<Object>();
			row.add(q.getQuestionNumber());
			row.add(q.getQuestion());
			tableModelQueries.addRow(row);
		}
		
		
		scrollPaneForecast.setBounds(new Rectangle(40, 274, 406, 116));
		scrollPaneForecast.setBounds(437, 214, 410, 170);
		add(scrollPaneForecast);
		

		scrollPaneForecast.setViewportView(tableForecast);
		tableModelForecast = new DefaultTableModel(null, columnNamesForecast);
		tableForecast.setModel(tableModelForecast);
		scrollPaneForecast.setViewportView(tableForecast);
		
		tableForecast.getColumnModel().getColumn(0).setPreferredWidth(35);
		tableForecast.getColumnModel().getColumn(1).setPreferredWidth(150);
		tableForecast.getColumnModel().getColumn(2).setPreferredWidth(70);
		
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
					tableForecast.getColumnModel().getColumn(0).setPreferredWidth(35);
					tableForecast.getColumnModel().getColumn(1).setPreferredWidth(150);
					tableForecast.getColumnModel().getColumn(2).setPreferredWidth(70);
				} catch (QuestionDoesntExist e1) {
					e1.printStackTrace();
				}
				
			}
		});

		
		EventInfo = new EventPanel(ev);
		EventInfo.setBounds(40, 37, 807, 165);
		add(EventInfo);
	}
}