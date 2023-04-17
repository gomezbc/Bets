package gui;

import java.awt.Color;
import java.awt.Font;
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

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Question;
import exceptions.ForecastAlreadyExist;

public class CreateForecastGUI extends JPanel {

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
	private DefaultTableModel tableModelEvents;
	
	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();
	
	private String[] columnNamesEvents = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("EventN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Event"), 

	};
	private final JLabel lblNewLabel = new JLabel("Pronostico :");
	private JTextField textPronostico;
	private JTextField textGanancia;
	
	
	private JLabel lblForecastAlreadyExists = new JLabel("Este pronostico ya existe!");
	private JLabel lblPronosticoCreado = new JLabel("Pronóstico creado");

	/**
	 * Launch the application.
	 */
	public CreateForecastGUI() {
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
	public void jbInit() throws Exception{
		setBorder(new LineBorder(new Color(17, 110, 80), 2, true));
		setBounds(100, 100, 802, 500);		

		BLFacade facade = MainGUI.getBusinessLogic();
		datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar1.getDate());
		CreateQuestionGUI.paintDaysWithEvents(jCalendar1,datesWithEventsCurrentMonth);
		jCalendar1.setBounds(30, 10, 213, 164);

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
						tableModelEvents.setDataVector(null, columnNamesEvents);
						tableModelEvents.setColumnCount(3); // another column added to allocate ev objects
						modelQuestions.removeAllElements();

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
		this.setLayout(null);

		this.add(jCalendar1);

		tableEvents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				modelQuestions.removeAllElements();
				int i=tableEvents.getSelectedRow();
				domain.Event ev=(domain.Event)tableModelEvents.getValueAt(i,2); // obtain ev object
				//evento = ev; 
				Vector<Question> queries=ev.getQuestions();


				if (queries.isEmpty())
					jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("NoQueries")+": "+ev.getDescription());
				else 
					jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectedEvent")+" "+ev.getDescription());

				for (domain.Question q:queries){
					modelQuestions.addElement(q);		
				}
			}
		});
		scrollPaneEvents.setBounds(330, 14, 346, 150);

		scrollPaneEvents.setViewportView(tableEvents);
		tableModelEvents = new DefaultTableModel(null, columnNamesEvents);

		tableEvents.setModel(tableModelEvents);
		tableEvents.getColumnModel().getColumn(0).setPreferredWidth(45);
		tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);

		this.add(scrollPaneEvents, null);
		lblNewLabel.setBounds(394, 204, 102, 22);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		this.add(lblNewLabel);
		
		textPronostico = new JTextField();
		textPronostico.setBounds(517, 197, 164, 38);
		this.add(textPronostico);
		textPronostico.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Ganancia:");
		lblNewLabel_1.setBounds(394, 282, 102, 22);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		this.add(lblNewLabel_1);
		
		textGanancia = new JTextField();
		textGanancia.setBounds(517, 275, 164, 38);
		this.add(textGanancia);
		textGanancia.setColumns(10);
		
		
		
		JButton btnSaveForecast = new JButton("Guardar Pronostico");
		btnSaveForecast.setBounds(193, 374, 164, 50);
		btnSaveForecast.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				try {
					
					facade.createForecast(textPronostico.getText(), Float.parseFloat(textGanancia.getText()), (Question) jComboBoxQuestions.getSelectedItem());
					lblForecastAlreadyExists.setVisible(false);
					lblPronosticoCreado.setVisible(true); 
					
				} catch (ForecastAlreadyExist e1) {
					lblForecastAlreadyExists.setVisible(true);
					lblPronosticoCreado.setVisible(false); 
					//e1.printStackTrace();
				}
						
		   }
		});
		this.add(btnSaveForecast);
		
		lblForecastAlreadyExists.setVisible(false);
		lblForecastAlreadyExists.setBounds(193, 340, 184, 43);
		lblForecastAlreadyExists.setBackground(Color.RED);
		lblForecastAlreadyExists.setForeground(new Color(255, 0, 0));
		this.add(lblForecastAlreadyExists);
		
		jComboBoxQuestions.setModel(modelQuestions);
		jComboBoxQuestions.setBounds(30, 233, 305, 26);
		this.add(jComboBoxQuestions);
		
		JLabel lblQuestions = new JLabel("Preguntas");
		lblQuestions.setFont(new Font("Dialog", Font.BOLD, 14));
		lblQuestions.setBounds(30, 207, 102, 17);
		this.add(lblQuestions);
		
		lblPronosticoCreado.setVisible(false); 
		lblPronosticoCreado.setForeground(new Color(0, 0, 255));
		lblPronosticoCreado.setBounds(113, 299, 130, 29);
		this.add(lblPronosticoCreado);

	}
}