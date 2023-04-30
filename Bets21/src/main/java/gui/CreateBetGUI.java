package gui;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Forecast;
import domain.Question;
import domain.User;
import exceptions.BetAlreadyExist;
import exceptions.UserDoesntExist;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.JComboBox;


public class CreateBetGUI extends JPanel {

	
	private static final long serialVersionUID = 1L;
	
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarAnt = null;
	private Calendar calendarAct = null;
	
	//private final JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private final JLabel jLabelQueries = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Queries")); 
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Events")); 

	
	private JScrollPane scrollPaneEvents = new JScrollPane();
	private JTable tableEvents= new JTable();
	private JComboBox<Question> jComboBoxQuestions =  new JComboBox<Question>();
	private DefaultComboBoxModel<Question> modelQuestions = new DefaultComboBoxModel<Question>();
	
	
	private JComboBox<Forecast> JComboBoxForecast = new JComboBox<Forecast>();
	private DefaultComboBoxModel<Forecast> modelForecast = new DefaultComboBoxModel<Forecast>();
	
	private DefaultTableModel tableModelEvents;
	private DefaultTableModel tableModelQueries;
		
	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();
	
	private String[] columnNamesEvents = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("EventN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Event"), 

	};
	private String[] columnNamesQueries = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("QueryN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Query")

	};
	private final JLabel lblPronostico = new JLabel("PRONOSTICO: ");
	private final JLabel lblNewLabel_Finalizada = new JLabel("Dinero Apuesta : ");
	private final JTextField textFieldDinero = new JTextField();
	
	private JLabel lblErrorDinero = new JLabel("El dinero de la apuesta no supera el dinero mínimo ");
	
	private JLabel lblErrorApuesta = new JLabel("Apuesta creada");
	private JLabel lblErrorExiste = new JLabel("Ya has apostado a este pronóstico");
	private final JLabel lblNewLabel = new JLabel("No tienes suficiente saldo.");
	private JLabel lblNewLabel_2 = new JLabel("Este evento ya ha sucedido");
	private JLabel lblNewLabel_1 = new JLabel("Introduce un número positivo.");
	
	private User userRegistered = LoginUserGUI.getUserRegistered();



	
	/**
	 * Launch the application.
	 */
	public CreateBetGUI() {
		try
		{
			jbInit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	

	

	/**
	 * Create the frame.
	 */
	public void jbInit() {
		setBorder(new LineBorder(new Color(17, 110, 80), 2, true));

		textFieldDinero.setBounds(298, 358, 114, 21);
		textFieldDinero.setColumns(10);
		

		BLFacade facade = MainGUI.getBusinessLogic();
		datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar1.getDate());
		CreateQuestionGUI.paintDaysWithEvents(jCalendar1,datesWithEventsCurrentMonth);
		jCalendar1.setBounds(new Rectangle(40, 50, 225, 150));

		// Code for JCalendar
		this.jCalendar1.addPropertyChangeListener(new PropertyChangeListener()
		{
			public void propertyChange(PropertyChangeEvent propertychangeevent)
			{

				if (propertychangeevent.getPropertyName().equals("locale"))
				{
					jCalendar1.setLocale((Locale) propertychangeevent.getNewValue());
				}
				else if (propertychangeevent.getPropertyName().equals("calendar"))
				{
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar1.getLocale());
//					jCalendar1.setCalendar(calendarAct);
					Date firstDay=UtilDate.trim(new Date(jCalendar1.getCalendar().getTime().getTime()));

					 
					
					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);
					
					if (monthAct!=monthAnt) {
						if (monthAct==monthAnt+2) {
							// Si en JCalendar está 30 de enero y se avanza al mes siguiente, devolvería 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este código se dejará como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt+1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}						
						
						jCalendar1.setCalendar(calendarAct);

						BLFacade facade = MainGUI.getBusinessLogic();

						datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar1.getDate());
					}



					CreateQuestionGUI.paintDaysWithEvents(jCalendar1,datesWithEventsCurrentMonth);
													
					

					try {
						modelQuestions.removeAllElements();
						modelForecast.removeAllElements();
						tableModelEvents.setDataVector(null, columnNamesEvents);
						tableModelEvents.setColumnCount(3); // another column added to allocate ev objects

						BLFacade facade=MainGUI.getBusinessLogic();

						Vector<domain.Event> events=facade.getEvents(firstDay);

						if (events.isEmpty() ) jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents")+ ": "+dateformat1.format(calendarAct.getTime()));
						else jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events")+ ": "+dateformat1.format(calendarAct.getTime()));
						for (domain.Event ev:events){
							Vector<Object> row = new Vector<Object>();

							System.out.println("Events "+ev);

							row.add(ev.getEventNumber());
							row.add(ev.getDescription());
							row.add(ev); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,2)
							tableModelEvents.addRow(row);		
						}
						tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
						tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);
						tableEvents.getColumnModel().removeColumn(tableEvents.getColumnModel().getColumn(2)); // not shown in JTable
					} catch (Exception e1) {

						jLabelQueries.setText(e1.getMessage());
					}

				}
			} 
		});
		setLayout(null);


		this.add(jCalendar1);

		tableEvents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				modelQuestions.removeAllElements();
				int i=tableEvents.getSelectedRow();
				domain.Event ev=(domain.Event)tableModelEvents.getValueAt(i,2); // obtain ev object
				Vector<Question> queries=ev.getQuestions();

				tableModelQueries.setDataVector(null, columnNamesQueries);

				if (queries.isEmpty())
					jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("NoQueries")+": "+ev.getDescription());
				else 
					jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectedEvent")+" "+ev.getDescription());

				for (domain.Question q:queries){
					modelQuestions.addElement(q);		
				}
			}
		});
		scrollPaneEvents.setBounds(280, 44, 453, 156);

		scrollPaneEvents.setViewportView(tableEvents);
		tableModelEvents = new DefaultTableModel(null, columnNamesEvents);

		tableEvents.setModel(tableModelEvents);
		tableEvents.getColumnModel().getColumn(0).setPreferredWidth(45);
		tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);
		tableModelQueries = new DefaultTableModel(null, columnNamesQueries);

		this.add(scrollPaneEvents);
		
		
		JButton btnSaveForecast = new JButton("Guardar Apuesta");
		btnSaveForecast.setBounds(480, 358, 138, 27);
		btnSaveForecast.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				userRegistered = LoginUserGUI.getUserRegistered();
				try {
					JCalendar jCalendar = new JCalendar();
					java.util.Date fechaSeleccionada = jCalendar.getDate();
					Calendar calendario = Calendar.getInstance();
					calendario.setTime(fechaSeleccionada);
					calendario.set(Calendar.HOUR_OF_DAY, 0);
					calendario.set(Calendar.MINUTE, 0);
					calendario.set(Calendar.SECOND, 0);
					calendario.set(Calendar.MILLISECOND, 0);
					
					
					Question q1 = (Question) jComboBoxQuestions.getSelectedItem();
					float a = q1.getBetMinimum();
					if ( fechaSeleccionada.after(q1.getEvent().getEventDate())) {
						lblErrorDinero.setVisible(false);
						lblErrorApuesta.setVisible(false);
						lblErrorExiste.setVisible(false);
						lblNewLabel.setVisible(false);
						lblNewLabel_Finalizada.setVisible(true);
						lblNewLabel_2.setVisible(true);
						lblNewLabel_1.setVisible(false);
						
					} else if( Float.parseFloat(textFieldDinero.getText()) < a){
							lblErrorDinero.setVisible(true);
							lblErrorApuesta.setVisible(false);
							lblErrorExiste.setVisible(false);
							lblNewLabel_Finalizada.setVisible(false);
							lblNewLabel.setVisible(false);
							lblNewLabel_2.setVisible(false);
							lblNewLabel_1.setVisible(false);
							
					}else if( userRegistered.getSaldo() < Float.parseFloat(textFieldDinero.getText())){
						lblErrorDinero.setVisible(false);
						lblErrorApuesta.setVisible(false);
						lblErrorExiste.setVisible(false);
						lblNewLabel_Finalizada.setVisible(false);
						lblNewLabel.setVisible(true);
						lblNewLabel_2.setVisible(false);
						lblNewLabel_1.setVisible(false);
						
					} else {
						facade.createBet(userRegistered.getDni(), Float.parseFloat(textFieldDinero.getText()), (Forecast) JComboBoxForecast.getSelectedItem());
						facade.modifySaldo(- Float.parseFloat(textFieldDinero.getText()), userRegistered.getDni());
						lblErrorDinero.setVisible(false);
						lblErrorApuesta.setVisible(true);
						lblErrorExiste.setVisible(false);
						lblNewLabel_Finalizada.setVisible(false);
						lblNewLabel.setVisible(false);
						lblNewLabel_2.setVisible(false);
						lblNewLabel_1.setVisible(false);
					}
				} catch (BetAlreadyExist ea){
					lblErrorDinero.setVisible(false);
					lblErrorApuesta.setVisible(false);
					lblErrorExiste.setVisible(true);
					lblNewLabel_Finalizada.setVisible(false);
					lblNewLabel.setVisible(false);
					lblNewLabel_2.setVisible(false);
					lblNewLabel_1.setVisible(false);
					
				} catch (NumberFormatException e1) {
					lblNewLabel_1.setVisible(true);
					lblErrorDinero.setVisible(false);
					lblErrorApuesta.setVisible(false);
					lblErrorExiste.setVisible(false);
					lblNewLabel_Finalizada.setVisible(false);
					lblNewLabel.setVisible(false);
					lblNewLabel_2.setVisible(false);
					
					
				} catch (UserDoesntExist e1) {
					e1.printStackTrace();
					
				}
				userRegistered = LoginUserGUI.getUserRegistered();
		   }
		});
		this.add(btnSaveForecast);
		
		jComboBoxQuestions.setModel(modelQuestions);
		jComboBoxQuestions.setBounds(34, 310, 31, 26);
		jComboBoxQuestions.addActionListener(new ActionListener () {
			public void actionPerformed (ActionEvent e) {
				modelForecast.removeAllElements();
			    Question question = (Question) jComboBoxQuestions.getSelectedItem();
			    Vector<Forecast> queries2 = question.getForecasts();
			    
			    if(! queries2.isEmpty())
			    	jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectedEvent")+" "+ question.getForecasts());
			    	
			    
			    for (domain.Forecast f:queries2) {
			    	modelForecast.addElement(f);
			    }
			}
			
		} );
				
		jComboBoxQuestions.setModel(modelQuestions);
		jComboBoxQuestions.setBounds(30, 310, 305, 26);
		this.add(jComboBoxQuestions);
		
		
		JComboBoxForecast.setModel(modelForecast);
		JComboBoxForecast.setBounds(398, 310, 305, 26);
		this.add(JComboBoxForecast);
		
		JLabel lblPregunta = new JLabel("PREGUNTA");
		lblPregunta.setFont(new Font("Dialog", Font.BOLD, 14));
		lblPregunta.setBounds(38, 284, 119, 20);
		this.add(lblPregunta);
		lblPronostico.setFont(new Font("Dialog", Font.BOLD, 14));
		lblPronostico.setBounds(399, 282, 105, 20);
		
		this.add(lblPronostico);
		lblNewLabel_Finalizada.setFont(new Font("Dialog", Font.BOLD, 14));
		lblNewLabel_Finalizada.setVisible(false);
		lblNewLabel_Finalizada.setBounds(174, 358, 119, 20);
		
		this.add(lblNewLabel_Finalizada);
		
		this.add(textFieldDinero);
		
		
		lblErrorDinero.setVisible(false);
		lblErrorDinero.setForeground(Color.RED);
		lblErrorDinero.setBackground(Color.BLACK);
		lblErrorDinero.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblErrorDinero.setBounds(152, 397, 322, 17);
		this.add(lblErrorDinero);
		
		
		lblErrorApuesta.setVisible(false);
		lblErrorApuesta.setForeground(Color.BLUE);
		lblErrorApuesta.setBounds(497, 397, 96, 17);
		this.add(lblErrorApuesta);
		
		
		lblErrorExiste.setVisible(false);
		lblErrorExiste.setForeground(new Color(199, 21, 133));
		lblErrorExiste.setBounds(289, 419, 214, 17);
		this.add(lblErrorExiste);
		
		lblNewLabel.setVisible(false);
		lblNewLabel.setForeground(new Color(255, 0, 0));
		lblNewLabel.setBounds(199, 446, 193, 20);
		
		add(lblNewLabel);
		

		lblNewLabel_2.setVisible(false);
		lblNewLabel_2.setForeground(new Color(0, 0, 205));
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_2.setBounds(398, 225, 185, 32);
		add(lblNewLabel_2);
		
	
		lblNewLabel_1.setVisible(false);
		lblNewLabel_1.setBounds(455, 449, 193, 17);
		add(lblNewLabel_1);
		


	}
}