package gui;

import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import com.toedter.calendar.JCalendar;

import configuration.UtilDate;
import domain.Forecast;
import domain.Question;
import exceptions.QuestionDoesntExist;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ScrollPaneConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class EventsGUI extends JPanel {
	private static final long serialVersionUID = 1L;

	private final JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Events")); 

	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarAnt = null;
	private Calendar calendarAct = null;
	private JScrollPane scrollPaneEvents = new JScrollPane();

	
	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();

	private JTable tableEvents= new JTable();


	private DefaultTableModel tableModelEvents;



	
	private String[] columnNamesEvents = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("EventN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Event"), 

	};
	private JButton betButton;

	public EventsGUI()
	{
		try
		{
			jbInit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	
	public void jbInit() throws Exception
	{
		this.setLayout(null);
		this.setSize(new Dimension(700, 500));
		this.setBackground(new Color(238, 238, 238));
		setBorder(new LineBorder(new Color(146, 154, 171), 2, true));
		jLabelEventDate.setFont(new Font("Roboto", Font.BOLD, 12));

		jLabelEventDate.setBounds(new Rectangle(40, 23, 140, 25));
		jLabelEvents.setFont(new Font("Roboto", Font.BOLD, 12));
		jLabelEvents.setBounds(40, 212, 259, 16);

		this.add(jLabelEventDate, null);
		this.add(jLabelEvents);


		jCalendar1.setBounds(new Rectangle(40, 50, 225, 150));

		BLFacade facade = MainGUI.getBusinessLogic();
		datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar1.getDate());
		CreateQuestionGUI.paintDaysWithEvents(jCalendar1,datesWithEventsCurrentMonth);

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
     				jCalendar1.setCalendar(calendarAct);
					Date firstDay = UtilDate.trim(new Date(jCalendar1.getCalendar().getTime().getTime()));
					

					 
					
					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);
					
					if (monthAct!=monthAnt) {
						if (monthAct==monthAnt+2) {
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
						//Limpia el contenido de la tabla de pronosticos, para evitar que aparezcan resultados previos
						
						
						tableModelEvents.setColumnCount(3); // another column added to allocate ev objects


						BLFacade facade=MainGUI.getBusinessLogic();

						Vector<domain.Event> events = facade.getEvents(firstDay);

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

//						jLabelQueries.setText(e1.getMessage());
					}

				}
			} 
		});

		this.add(jCalendar1, null);
		scrollPaneEvents.setFont(new Font("Roboto", Font.PLAIN, 12));
		
		scrollPaneEvents.setBounds(new Rectangle(40, 233, 610, 212));
		tableEvents.setFont(new Font("Roboto", Font.PLAIN, 12));
		

		tableEvents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});

		scrollPaneEvents.setViewportView(tableEvents);
		tableModelEvents = new DefaultTableModel(null, columnNamesEvents);

		tableEvents.setModel(tableModelEvents);
		tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);

		this.add(scrollPaneEvents, null);
		
		betButton = new JButton(""); //$NON-NLS-1$ //$NON-NLS-2$
		betButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ImageIcon icon = new ImageIcon("icons/chips-bet.png");
				Image scaledIcon = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
				UserGUI.updateTab(2, "Apostar", new ImageIcon(scaledIcon), new CreateBetGUI());
			}
		});
		betButton.setBounds(274, 457, 103, 27);
		ImageIcon icon = new ImageIcon("icons/chips-bet.png");
		Image scaledIcon = icon.getImage().getScaledInstance(20, betButton.getHeight(), Image.SCALE_SMOOTH);
		betButton.setIcon(new ImageIcon(scaledIcon));
		this.add(betButton);
		
		//Actualiza el tamaño de los componentes respecto al frame
		ComponentListener componentListener = new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				// Actualizar el tamaño del JTabbedPane
				int nuevoAncho = e.getComponent().getWidth();
				int nuevoAlto = e.getComponent().getHeight();
				System.out.println(nuevoAncho+", "+nuevoAlto);     
			}
		};
		this.addComponentListener(componentListener);
		
	}
}