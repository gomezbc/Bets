package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Event;
import domain.Forecast;
import domain.Question;
import exceptions.EventFinished;
import exceptions.ForecastAlreadyExist;
import exceptions.QuestionAlreadyExist;
import exceptions.QuestionDoesntExist;
import theme.Bets21Theme;


public class EventInfoAdminGUI extends JPanel {
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
	private JLabel lblPronostico;
	private JTextField pronostico;
	private JButton btnAñadirPronostico;
	private JLabel lblErrorPronostico;
	private JLabel lblPregunta;
	private JTextField pregunta;
	private JButton btnAñadirPregunta;
	private JLabel lblErrorPregunta;
	private JTextField preguntaMinimo;
	private JTextField pronosticoMinimo;
	private JLabel lblDescripcinMnimo;
	private JLabel lblDescripcinMnimo_2;

	public EventInfoAdminGUI(Event ev)
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
		tableQueries.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
		tableForecast.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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

		
		EventInfo = new EventInfoPanel(ev, 806, 100);
		EventInfo.setBounds(40, 37, 807, 165);
		add(EventInfo);
		
		lblPronostico = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventInfoGUI.lblDineroAApostar.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblPronostico.setFont(new Font("Roboto", Font.PLAIN, 14));
		lblPronostico.setBounds(437, 385, 201, 25);
		add(lblPronostico);
		
		pronostico = new JTextField();
		pronostico.setFont(new Font("Roboto", Font.PLAIN, 14));
		pronostico.setText(ResourceBundle.getBundle("Etiquetas").getString("EventInfoGUI.textField.text")); //$NON-NLS-1$ //$NON-NLS-2$
		pronostico.setBounds(437, 428, 298, 25);
		add(pronostico);
		pronostico.setColumns(10);
		
		btnAñadirPronostico = new JButton("AÑADIR"); //$NON-NLS-1$ //$NON-NLS-2$
		btnAñadirPronostico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblErrorPronostico.setVisible(false);
				lblErrorPregunta.setVisible(false);
				try {
					if(Float.parseFloat(pronosticoMinimo.getText()) <= 1.00f || Float.parseFloat(pronosticoMinimo.getText()) <= 0.00f) {
						lblErrorPronostico.setText("La ganancia debe ser mayor a 1");
						lblErrorPronostico.setVisible(true);
						return;
					}
					BLFacade facade = MainGUI.getBusinessLogic();
					int i = tableQueries.getSelectedRow();
					int qNumber = (int) tableModelQueries.getValueAt(i,0);
					Forecast f = facade.createForecast(pronostico.getText().trim(), Float.parseFloat(pronosticoMinimo.getText()), qNumber);
					Vector<Object> row = new Vector<Object>();
					row.add(f.getForecastNumber());
					row.add(f.getDescription());
					row.add(String.format("%.2f", f.getGain()));
					tableModelForecast.addRow(row);	
					lblErrorPronostico.setText("Pronostico añadido correctamente");
					lblErrorPronostico.setVisible(true);
					pronostico.setText("");
					pronosticoMinimo.setText("");
				}catch(ForecastAlreadyExist ef) {
					lblErrorPronostico.setText("El pronostico ya existe");
					lblErrorPronostico.setVisible(true);
				}catch(QuestionDoesntExist eq) {
					eq.printStackTrace();
				}catch(NumberFormatException nf) {
					lblErrorPronostico.setText("Asegurate de introducir un número");
					lblErrorPronostico.setVisible(true);
				}
				catch(Exception e1) {
					lblErrorPronostico.setText("Selecciona una pregunta");
					lblErrorPronostico.setVisible(true);
				}
			}
		});
		btnAñadirPronostico.setBackground(Color.WHITE);
		btnAñadirPronostico.setFont(new Font("Roboto", Font.BOLD, 14));
		btnAñadirPronostico.setBounds(595, 463, 103, 27);
		btnAñadirPronostico.setEnabled(false);
		add(btnAñadirPronostico);
		
		lblErrorPronostico = new JLabel("");
		lblErrorPronostico.setForeground(new Color(220, 20, 60));
		lblErrorPronostico.setFont(new Font("Roboto", Font.PLAIN, 14));
		lblErrorPronostico.setHorizontalAlignment(SwingConstants.CENTER);
		lblErrorPronostico.setBounds(437, 504, 410, 25);
		add(lblErrorPronostico);
		
		lblPregunta = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventInfoAdminGUI.lblAadeUnaPregunta.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblPregunta.setFont(new Font("Roboto", Font.PLAIN, 14));
		lblPregunta.setBounds(40, 385, 191, 25);
		add(lblPregunta);
		
		pregunta = new JTextField();
		pregunta.setText(" ");
		pregunta.setFont(new Font("Roboto", Font.PLAIN, 14));
		pregunta.setColumns(10);
		pregunta.setBounds(40, 428, 275, 25);
		add(pregunta);
		
		btnAñadirPregunta = new JButton("AÑADIR");
		btnAñadirPregunta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblErrorPregunta.setVisible(false);
				lblErrorPronostico.setVisible(false);
				try {
					if(Float.parseFloat(preguntaMinimo.getText()) < 1.00f || Float.parseFloat(preguntaMinimo.getText()) <= 0.00f) {
						lblErrorPregunta.setText("La apuesta minima debe ser mayor a 1");
						lblErrorPregunta.setVisible(true);
						return;
					}
					BLFacade facade = MainGUI.getBusinessLogic();
					Question q = facade.createQuestion(ev, pregunta.getText().trim(), Float.parseFloat(preguntaMinimo.getText()));
					Vector<Object> row = new Vector<Object>();
					row.add(q.getQuestionNumber());
					row.add(q.getQuestion());
					row.add(String.format("%.2f", q.getBetMinimum()));
					tableModelQueries.addRow(row);	
					lblErrorPregunta.setText("Pregunta añadida correctamente");
					lblErrorPregunta.setVisible(true);
					preguntaMinimo.setText("");
					pregunta.setText("");
				}catch(QuestionAlreadyExist ef) {
					lblErrorPregunta.setText("La pregunta ya existe");
					lblErrorPregunta.setVisible(true);
				}catch(EventFinished eq) {
					lblErrorPregunta.setText("El evento ha finalizado");
					lblErrorPregunta.setVisible(true);
				}catch(NumberFormatException nf) {
					lblErrorPregunta.setText("Asegurate de introducir un número");
					lblErrorPregunta.setVisible(true);
				}catch(Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnAñadirPregunta.setFont(new Font("Roboto", Font.BOLD, 14));
		btnAñadirPregunta.setBackground(Color.WHITE);
		btnAñadirPregunta.setBounds(177, 463, 103, 27);
   	 	btnAñadirPregunta.setEnabled(false);
		add(btnAñadirPregunta);
		
		lblErrorPregunta = new JLabel("");
		lblErrorPregunta.setHorizontalAlignment(SwingConstants.CENTER);
		lblErrorPregunta.setForeground(new Color(220, 20, 60));
		lblErrorPregunta.setFont(new Font("Roboto", Font.PLAIN, 14));
		lblErrorPregunta.setBounds(40, 504, 385, 25);
		add(lblErrorPregunta);
		
		preguntaMinimo = new JTextField();
		preguntaMinimo.setText(" ");
		preguntaMinimo.setFont(new Font("Roboto", Font.PLAIN, 14));
		preguntaMinimo.setColumns(10);
		preguntaMinimo.setBounds(323, 428, 102, 25);
		add(preguntaMinimo);
		
		pronosticoMinimo = new JTextField();
		pronosticoMinimo.setText(" ");
		pronosticoMinimo.setFont(new Font("Roboto", Font.PLAIN, 14));
		pronosticoMinimo.setColumns(10);
		pronosticoMinimo.setBounds(747, 428, 100, 25);
		add(pronosticoMinimo);
		
		
		lblDescripcinMnimo = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventInfoAdminGUI.lblDescripcinMnimo.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblDescripcinMnimo.setFont(new Font("Roboto", Font.PLAIN, 14));
		lblDescripcinMnimo.setBounds(40, 407, 385, 25);
		add(lblDescripcinMnimo);
		
		lblDescripcinMnimo_2 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventInfoAdminGUI.lblDescripcinMnimo_2.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblDescripcinMnimo_2.setFont(new Font("Roboto", Font.PLAIN, 14));
		lblDescripcinMnimo_2.setBounds(437, 407, 410, 25);
		add(lblDescripcinMnimo_2);
		
		//Si la fecha actual es posterior a la del evento, no se puede añadir preguntas y pronosticos
		Date today = Calendar.getInstance().getTime();
		if(today.after(ev.getEventDate())) {
			lblPronostico.setEnabled(false);
			pronostico.setEnabled(false);
			btnAñadirPronostico.setEnabled(false);
			lblErrorPronostico.setEnabled(false);
			lblPregunta.setEnabled(false);
			pregunta.setEnabled(false);
			btnAñadirPregunta.setEnabled(false);
			lblErrorPregunta.setEnabled(false);
			preguntaMinimo.setEnabled(false);
			pronosticoMinimo.setEnabled(false);
			lblDescripcinMnimo.setEnabled(false);
			lblDescripcinMnimo_2.setEnabled(false);
		}
		//Comprobaciones para que el boton no este activo en caso de que no se haya rellenado todos los campos
		
		
		
		preguntaMinimo.getDocument().addDocumentListener(new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) {
				  check();
			  }
			  public void removeUpdate(DocumentEvent e) {
				  check();
			  }
			  public void insertUpdate(DocumentEvent e) {
				  check();
			  }
			  public void check() {
			     if (preguntaMinimo.getText().trim().isEmpty() || pregunta.getText().trim().isEmpty()){
			    	 btnAñadirPregunta.setEnabled(false);
			     }else {
			    	 btnAñadirPregunta.setEnabled(true);
			     }
			  }
			});
		
		
		
		pregunta.getDocument().addDocumentListener(new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) {
				  check();
			  }
			  public void removeUpdate(DocumentEvent e) {
				  check();
			  }
			  public void insertUpdate(DocumentEvent e) {
				  check();
			  }
			  public void check() {
			     if (preguntaMinimo.getText().trim().isEmpty() || pregunta.getText().trim().isEmpty()){
			    	 btnAñadirPregunta.setEnabled(false);
			     }else {
			    	 btnAñadirPregunta.setEnabled(true);
			     }
			  }
			});
		
		
		
		pronosticoMinimo.getDocument().addDocumentListener(new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) {
				  check();
			  }
			  public void removeUpdate(DocumentEvent e) {
				  check();
			  }
			  public void insertUpdate(DocumentEvent e) {
				  check();
			  }
			  public void check() {
			     if (pronosticoMinimo.getText().trim().isEmpty() || pronostico.getText().trim().isEmpty()){
			    	 btnAñadirPronostico.setEnabled(false);
			     }else {
			    	 btnAñadirPronostico.setEnabled(true);
			     }
			  }
			});
		
		
		
		pronostico.getDocument().addDocumentListener(new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) {
				  check();
			  }
			  public void removeUpdate(DocumentEvent e) {
				  check();
			  }
			  public void insertUpdate(DocumentEvent e) {
				  check();
			  }
			  public void check() {
			     if (pronosticoMinimo.getText().trim().isEmpty() || pronostico.getText().trim().isEmpty()){
			    	 btnAñadirPronostico.setEnabled(false);
			     }else {
			    	 btnAñadirPronostico.setEnabled(true);
			     }
			  }
			});
		
	}
}