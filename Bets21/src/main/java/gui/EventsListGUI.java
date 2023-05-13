package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.toedter.calendar.JCalendar;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Event;
import domain.User;
import theme.Bets21Theme;


public class EventsListGUI extends JPanel {
	private static final long serialVersionUID = 1L;

	private final JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Events")); 

	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarAnt = null;
	private Calendar calendarAct = null;

	
	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();

	private JTable tableEvents= new JTable();


	private DefaultTableModel tableModelEvents;

	private static Vector<Event> todayEvents;
	private static Vector<Event> currentEvents = new Vector<Event>(4);
	private static int currentIndex;
	private static int currentMax;
	
	private String[] columnNamesEvents = new String[] {
			ResourceBundle.getBundle("Etiquetas").getString("Event")
	};
	private JButton next;
	private JButton previous;

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
		Bets21Theme.setup();
		setMinimumSize(new Dimension(886, 541));
		this.setLayout(null);
		this.setSize(new Dimension(886, 541));
		this.setBackground(new Color(238, 238, 238));
		jLabelEventDate.setFont(new Font("Roboto", Font.BOLD, 15));

		jLabelEventDate.setBounds(new Rectangle(40, 23, 140, 25));
		jLabelEvents.setFont(new Font("Roboto", Font.BOLD, 14));
		jLabelEvents.setBounds(40, 205, 443, 25);

		this.add(jLabelEventDate, null);
		this.add(jLabelEvents);


		jCalendar1.setBounds(new Rectangle(40, 50, 225, 150));

		BLFacade facade = MainGUI.getBusinessLogic();
		datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar1.getDate());
		paintDaysWithEvents(jCalendar1,datesWithEventsCurrentMonth);

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

						datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar1.getDate());
					}

					paintDaysWithEvents(jCalendar1,datesWithEventsCurrentMonth);
													
					

					try {
						tableModelEvents.setDataVector(null, columnNamesEvents);
						//Limpia el contenido de la tabla de pronosticos, para evitar que aparezcan resultados previos
						next.setVisible(false);
						previous.setVisible(false);
						tableModelEvents.setColumnCount(2); // another column added to allocate ev objects
						
						Vector<domain.Event> events = facade.getEvents(firstDay);

						if (events.isEmpty() ) jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents")+ ": "+dateformat1.format(calendarAct.getTime()));
						else jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events")+ ": "+dateformat1.format(calendarAct.getTime()));
						
						todayEvents = events;
						currentIndex=0;
						
						if(events.size()/4>0) currentMax=4;
						else currentMax=events.size();
						
						currentEvents.removeAllElements();
						
						for(int i=0;i<currentMax;i++){
							currentEvents.add(i, todayEvents.get(i));
						}
						tableEvents.getColumnModel().getColumn(0).setCellRenderer(new EventInfoCellRender(currentEvents));
						EventInfoCellRender.setIndex(0);
						tableEvents.setDefaultRenderer(Object.class, new DefaultTableCellRenderer());
						for (domain.Event ev:currentEvents){
							Vector<Object> row = new Vector<Object>();
							System.out.println("Events "+ev);
							row.add(new EventInfoPanel(ev, tableEvents.getColumnModel().getColumn(0).getWidth(), tableEvents.getRowHeight()));
							row.add(ev); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,1)
							tableModelEvents.addRow(row);
							tableModelEvents.fireTableDataChanged();
						}
						tableEvents.getColumnModel().getColumn(0).setPreferredWidth(300);
						tableEvents.getColumnModel().removeColumn(tableEvents.getColumnModel().getColumn(1)); // not shown in JTable
						if(currentIndex+4<events.size()) next.setVisible(true);
						else next.setVisible(false);
						if(currentIndex>0) previous.setVisible(true);
						else previous.setVisible(false);
					} catch (Exception e1) {

					}

				}
			} 
		});

		this.add(jCalendar1, null);
		
		tableModelEvents = new DefaultTableModel(null, columnNamesEvents);
		
		tableEvents = new JTable(tableModelEvents);
		tableEvents.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableEvents.setBackground(new Color(238, 238, 238));
		tableEvents.setBounds(40, 233, 803, 280);
		add(tableEvents);
		tableEvents.setFont(new Font("Roboto", Font.PLAIN, 12));
		tableEvents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i=tableEvents.getSelectedRow();
				domain.Event ev=(domain.Event)tableModelEvents.getValueAt(i,1); // obtain ev object
				if(ev!=null) {
					User u = MainGUI.getUserRegistered();
					if(u==null) NoUserGUI.updateFrame(new EventInfoNoUserGUI(ev));
					else if(!u.isAdmin()) UserGUI.updateFrame(new EventInfoUserGUI(ev));
					else if(AdminGUI.getCurrentTab().equals("EventInfoAdminGUI")) {
						AdminGUI.updateFrame(new EventInfoAdminGUI(ev));
					}
					else if(AdminGUI.getCurrentTab().equals("CloseEventGUI"))AdminGUI.updateFrame(new CloseEventGUI(ev));
				}
			}
		});
		
		tableEvents.getColumnModel().getColumn(0).setPreferredWidth(300);
		tableEvents.setRowHeight(tableEvents.getHeight()/4);
		
		//Actualiza el tamaño de los componentes respecto al frame
		/*ComponentListener componentListener = new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				System.out.println("componentResized");
				// Actualizar el tamaño del JTabbedPane
				int nuevoAncho = e.getComponent().getWidth();
				int nuevoAlto = e.getComponent().getHeight();
				tableEvents.setSize(nuevoAncho-80, (int) (nuevoAlto*0.5));
				tableEvents.getColumnModel().getColumn(0).setPreferredWidth(nuevoAncho-80);
				tableEvents.setRowHeight((int) (nuevoAlto*0.12));
				tableEvents.repaint();
				renderEventsTable();
			}
		};
		this.addComponentListener(componentListener);*/
		
		ImageIcon icon = new ImageIcon(EventInfoPanel.class.getResource("/icons/right.png"));
		Image scaledIcon = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		next = new JButton(new ImageIcon(scaledIcon));
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(todayEvents.size()-(currentIndex+4)>=4) currentMax += 4;
				else currentMax += todayEvents.size()-(currentIndex+4);
				currentIndex += 4;
				currentEvents.removeAllElements();
				for(int i=0;i<4-(currentMax%4);i++){
					currentEvents.add(i, todayEvents.get(currentIndex+i));
				}
				tableModelEvents.setDataVector(null, columnNamesEvents);
				tableModelEvents.setColumnCount(2);
				tableEvents.setDefaultRenderer(Object.class, new DefaultTableCellRenderer());
				for (domain.Event ev:currentEvents){
					Vector<Object> row = new Vector<Object>();
					System.out.println("Events "+ev);
					row.add(new EventInfoPanel(ev, tableEvents.getColumnModel().getColumn(0).getWidth(), tableEvents.getRowHeight()));
					row.add(ev); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,1)
					tableModelEvents.addRow(row);
				}
				tableEvents.getColumnModel().getColumn(0).setPreferredWidth(300);
				tableEvents.getColumnModel().removeColumn(tableEvents.getColumnModel().getColumn(1)); // not shown in JTable
				renderEventsTable();
			}
		});
		next.setBorder(null);
		next.setBounds(813, 200, 30, 30);
		next.setVisible(false);
		add(next);
		
		icon = new ImageIcon(EventInfoPanel.class.getResource("/icons/left.png"));
		scaledIcon = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		previous = new JButton(new ImageIcon(scaledIcon));
		previous.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentIndex -= 4;
				currentMax -= 4-(currentMax%4);
				currentEvents.removeAllElements();
				for(int i=0;i<4-(currentMax%4);i++){
					currentEvents.add(i, todayEvents.get(currentIndex+i));
				}
				tableModelEvents.setDataVector(null, columnNamesEvents);
				tableModelEvents.setColumnCount(2);
				tableEvents.setDefaultRenderer(Object.class, new DefaultTableCellRenderer());
				for (domain.Event ev:currentEvents){
					Vector<Object> row = new Vector<Object>();
					System.out.println("Events "+ev);
					row.add(new EventInfoPanel(ev, tableEvents.getColumnModel().getColumn(0).getWidth(), tableEvents.getRowHeight()));
					row.add(ev); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,1)
					tableModelEvents.addRow(row);
				}
				tableEvents.getColumnModel().getColumn(0).setPreferredWidth(300);
				tableEvents.getColumnModel().removeColumn(tableEvents.getColumnModel().getColumn(1)); // not shown in JTable
				renderEventsTable();
			}
		});
		previous.setBorder(null);
		previous.setBounds(771, 200, 30, 30);
		previous.setVisible(false);
		add(previous);
		
		//Para que si el usuario vuelve a la pestaña de evntos se le cargue los anteriores
		if(currentEvents!=null) {
			tableModelEvents.setDataVector(null, columnNamesEvents);
			tableModelEvents.setColumnCount(2);
			tableEvents.setDefaultRenderer(Object.class, new DefaultTableCellRenderer());
			for (domain.Event ev:currentEvents){
				Vector<Object> row = new Vector<Object>();
				System.out.println("Events "+ev);
				row.add(new EventInfoPanel(ev, tableEvents.getColumnModel().getColumn(0).getWidth(), tableEvents.getRowHeight()));
				row.add(ev); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,1)
				tableModelEvents.addRow(row);
			}
			tableEvents.getColumnModel().getColumn(0).setPreferredWidth(300);
			tableEvents.getColumnModel().removeColumn(tableEvents.getColumnModel().getColumn(1)); // not shown in JTable
			renderEventsTable();
		}
		
	}
	
	public void renderEventsTable() {
		EventInfoCellRender.setIndex(0);
		tableEvents.getColumnModel().getColumn(0).setCellRenderer(new EventInfoCellRender(currentEvents));
		tableModelEvents.fireTableDataChanged();
		tableEvents.repaint();
		if(todayEvents!=null && currentIndex+4<todayEvents.size()) next.setVisible(true);
		else next.setVisible(false);
		if(currentIndex>0) previous.setVisible(true);
		else previous.setVisible(false);
	}
	
	public static void paintDaysWithEvents(JCalendar jCalendar,Vector<Date> datesWithEventsCurrentMonth) {
		// For each day with events in current month, the background color for that day is changed to cyan.

		Calendar calendar = jCalendar.getCalendar();
		
		int month = calendar.get(Calendar.MONTH);
		int today=calendar.get(Calendar.DAY_OF_MONTH);
		int year=calendar.get(Calendar.YEAR);
		
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int offset = calendar.get(Calendar.DAY_OF_WEEK);

		if (Locale.getDefault().equals(new Locale("es")))
			offset += 4;
		else
			offset += 5;

	 	for (Date d:datesWithEventsCurrentMonth){

	 		calendar.setTime(d);
	 		System.out.println(d);
			// Obtain the component of the day in the panel of the DayChooser of the
			// JCalendar.
			// The component is located after the decorator buttons of "Sun", "Mon",... or
			// "Lun", "Mar"...,
			// the empty days before day 1 of month, and all the days previous to each day.
			// That number of components is calculated with "offset" and is different in
			// English and Spanish
//			    		  Component o=(Component) jCalendar.getDayChooser().getDayPanel().getComponent(i+offset);; 
			Component o = (Component) jCalendar.getDayChooser().getDayPanel()
					.getComponent(calendar.get(Calendar.DAY_OF_MONTH) + offset);
			o.setBackground(Color.CYAN);
	 	}
 			calendar.set(Calendar.DAY_OF_MONTH, today);
	 		calendar.set(Calendar.MONTH, month);
	 		calendar.set(Calendar.YEAR, year);

	 	
	}
}