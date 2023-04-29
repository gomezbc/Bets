package gui;

import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
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

import javax.swing.JLabel;

import com.toedter.calendar.JCalendar;

import configuration.UtilDate;
import domain.Event;

import java.awt.Font;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseMotionAdapter;


public class EventsListGUI extends JPanel {
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

	private Vector<Event> todayEvents;
	
	private String[] columnNamesEvents = new String[] {
			//ResourceBundle.getBundle("Etiquetas").getString("EventN"), 
			ResourceBundle.getBundle("Etiquetas").getString("Event"), 

	};

	public EventsListGUI()
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
		setMinimumSize(new Dimension(826, 541));
		this.setLayout(null);
		this.setSize(new Dimension(826, 541));
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
						
						tableModelEvents.setColumnCount(2); // another column added to allocate ev objects

						BLFacade facade=MainGUI.getBusinessLogic();

						Vector<domain.Event> events = facade.getEvents(firstDay);

						if (events.isEmpty() ) jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents")+ ": "+dateformat1.format(calendarAct.getTime()));
						else jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events")+ ": "+dateformat1.format(calendarAct.getTime()));
						todayEvents = events;
						tableEvents.getColumnModel().getColumn(0).setCellRenderer(new MyTableCellRender(events));
						MyTableCellRender.setIndex(0);
						tableEvents.setDefaultRenderer(Object.class, new DefaultTableCellRenderer());
						for (domain.Event ev:events){
							Vector<Object> row = new Vector<Object>();
							System.out.println("Events "+ev);
							row.add(new EventPanel(ev));
							row.add(ev); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,1)
							tableModelEvents.addRow(row);
							tableModelEvents.fireTableDataChanged();
						}
						tableEvents.getColumnModel().getColumn(0).setPreferredWidth(300);
						tableEvents.getColumnModel().removeColumn(tableEvents.getColumnModel().getColumn(1)); // not shown in JTable
					} catch (Exception e1) {

					}

				}
			} 
		});

		this.add(jCalendar1, null);
		scrollPaneEvents.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				renderEventsTable();
			}
		});
		scrollPaneEvents.setFont(new Font("Roboto", Font.PLAIN, 12));
		
		scrollPaneEvents.setBounds(new Rectangle(40, 233, 740, 300));
		
//		tableEvents.setModel(tableModelEvents);
		tableModelEvents = new DefaultTableModel(null, columnNamesEvents);
		tableEvents = new JTable(tableModelEvents);
		tableEvents.setFont(new Font("Roboto", Font.PLAIN, 12));
		tableEvents.setRowHeight(60);
		tableEvents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i=tableEvents.getSelectedRow();
				domain.Event ev=(domain.Event)tableModelEvents.getValueAt(i,1); // obtain ev object
				if(ev!=null) UserGUI3.updateFrame(new FindQuestionsGUI(ev));
			}
		});

		scrollPaneEvents.setViewportView(tableEvents);

		tableEvents.getColumnModel().getColumn(0).setPreferredWidth(300);

		this.add(scrollPaneEvents, null);
		
		//Actualiza el tamaño de los componentes respecto al frame
		ComponentListener componentListener = new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				// Actualizar el tamaño del JTabbedPane
				int nuevoAncho = e.getComponent().getWidth();
				int nuevoAlto = e.getComponent().getHeight();
				scrollPaneEvents.setSize(nuevoAncho-80, (int) (nuevoAlto*0.5));
				tableEvents.getColumnModel().getColumn(0).setPreferredWidth(nuevoAncho-80);
				tableEvents.setRowHeight((int) (nuevoAlto*0.12));
				tableEvents.repaint();
				renderEventsTable();
			}
		};
		this.addComponentListener(componentListener);
		
	}
	
	public void renderEventsTable() {
		Vector<Event> events = todayEvents;
		MyTableCellRender.setIndex(0);
		tableEvents.getColumnModel().getColumn(0).setCellRenderer(new MyTableCellRender(events));
		tableModelEvents.fireTableDataChanged();
		tableEvents.repaint();
	}
}