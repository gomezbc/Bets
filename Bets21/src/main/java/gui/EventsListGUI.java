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
import java.awt.Image;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


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

						datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar1.getDate());
					}



					CreateQuestionGUI.paintDaysWithEvents(jCalendar1,datesWithEventsCurrentMonth);
													
					

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
						tableEvents.getColumnModel().getColumn(0).setCellRenderer(new MyTableCellRender(currentEvents));
						MyTableCellRender.setIndex(0);
						tableEvents.setDefaultRenderer(Object.class, new DefaultTableCellRenderer());
						for (domain.Event ev:currentEvents){
							Vector<Object> row = new Vector<Object>();
							System.out.println("Events "+ev);
							row.add(new EventPanel(ev));
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
		tableEvents.setBounds(40, 233, 803, 280);
		add(tableEvents);
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
		
		ImageIcon icon = new ImageIcon("icons/right.png");
		Image scaledIcon = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		next = new JButton(new ImageIcon(scaledIcon));
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("next");
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
					row.add(new EventPanel(ev));
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
		
		icon = new ImageIcon("icons/left.png");
		scaledIcon = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		previous = new JButton(new ImageIcon(scaledIcon));
		previous.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("previous");
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
					row.add(new EventPanel(ev));
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
				row.add(new EventPanel(ev));
				row.add(ev); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,1)
				tableModelEvents.addRow(row);
			}
			tableEvents.getColumnModel().getColumn(0).setPreferredWidth(300);
			tableEvents.getColumnModel().removeColumn(tableEvents.getColumnModel().getColumn(1)); // not shown in JTable
			renderEventsTable();
		}
		
	}
	
	public void renderEventsTable() {
		MyTableCellRender.setIndex(0);
		tableEvents.getColumnModel().getColumn(0).setCellRenderer(new MyTableCellRender(currentEvents));
		tableModelEvents.fireTableDataChanged();
		tableEvents.repaint();
		if(todayEvents!=null && currentIndex+4<todayEvents.size()) next.setVisible(true);
		else next.setVisible(false);
		if(currentIndex>0) previous.setVisible(true);
		else previous.setVisible(false);
	}
}