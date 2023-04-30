package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.toedter.calendar.JCalendar;

import businessLogic.BLFacade;
import configuration.UtilDate;
import exceptions.EventAlreadyExist;
import domain.Event;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;

import java.awt.Dimension;

public class CreateEventGUI extends JPanel {


	private static final long serialVersionUID = 1L;
	private JTextField textField;
	
	private JLabel lblCreado = new JLabel("Evento Creado");
	private JLabel lblYaExiste = new JLabel("Ese evento ya existe para ese día");
	private JPanel preview = new JPanel();

	/**
	 * Launch the application.
	 */
	public CreateEventGUI() {
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
		setMinimumSize(new Dimension(886, 541));
		setSize(new Dimension(886, 541));

		this.setLayout(null);
		
		JCalendar jCalendar = new JCalendar();
		jCalendar.setBounds(new Rectangle(40, 50, 225, 150));
		this.add(jCalendar);
		
		JLabel lblEvento = new JLabel("EVENTO:");
		lblEvento.setBounds(176, 267, 119, 14);
		lblEvento.setFont(new Font("Roboto", Font.PLAIN, 16));
		this.add(lblEvento);
		
		textField = new JTextField();
		textField.setBounds(266, 264, 353, 22);
		this.add(textField);
		textField.setColumns(10);
		
		
		JButton btnButton_Save = new JButton("CREAR");
		btnButton_Save.setFont(new Font("Roboto", Font.BOLD, 14));
		btnButton_Save.setBackground(Color.WHITE);
		btnButton_Save.setBounds(312, 298, 148, 37);
		btnButton_Save.setEnabled(false);
		btnButton_Save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				try {
					 
					// Obtén la fecha seleccionada del JCalendar
					java.util.Date fechaSeleccionada = jCalendar.getDate();

					// Crea un objeto Calendar y establece la fecha seleccionada
					Calendar calendario = Calendar.getInstance();
					calendario.setTime(fechaSeleccionada);

					// Establece la hora, los minutos y los segundos en cero
					calendario.set(Calendar.HOUR_OF_DAY, 0);
					calendario.set(Calendar.MINUTE, 0);
					calendario.set(Calendar.SECOND, 0);
					calendario.set(Calendar.MILLISECOND, 0);

					// Obtiene la fecha actualizada como un objeto Date
					java.util.Date fechaConHoraCero = calendario.getTime();
					
					
					facade.createEvent( textField.getText(), fechaConHoraCero);
					lblYaExiste.setVisible(false);
					lblCreado.setVisible(true);
					
					
					
				} catch (EventAlreadyExist e1) {
					lblYaExiste.setVisible(true);
					lblCreado.setVisible(false);
				}
			}
			
		});
		this.add(btnButton_Save);
		lblCreado.setFont(new Font("Roboto", Font.BOLD, 14));
		lblCreado.setBounds(341, 347, 119, 14);
		
		
		lblCreado.setEnabled(true);
		lblCreado.setVisible(false);
		lblCreado.setForeground(new Color(148, 0, 211));
		this.add(lblCreado);
		lblYaExiste.setFont(new Font("Roboto", Font.BOLD, 14));
		lblYaExiste.setBounds(478, 303, 243, 37);
		
	
		lblYaExiste.setForeground(new Color(165, 42, 42));
		lblYaExiste.setVisible(false);
		this.add(lblYaExiste);
		
		preview.setBounds(40, 379, 804, 150);
		add(preview);
		
		textField.getDocument().addDocumentListener(new DocumentListener() {
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
				  
				  if(!textField.getText().isBlank()) {
					  try {
							btnButton_Save.setEnabled(true);
							// Obtén la fecha seleccionada del JCalendar
							java.util.Date fechaSeleccionada = jCalendar.getDate();

							// Crea un objeto Calendar y establece la fecha seleccionada
							Calendar calendario = Calendar.getInstance();
							calendario.setTime(fechaSeleccionada);

							// Establece la hora, los minutos y los segundos en cero
							calendario.set(Calendar.HOUR_OF_DAY, 0);
							calendario.set(Calendar.MINUTE, 0);
							calendario.set(Calendar.SECOND, 0);
							calendario.set(Calendar.MILLISECOND, 0);

							// Obtiene la fecha actualizada como un objeto Date
							java.util.Date fechaConHoraCero = calendario.getTime();
							
							Event ev = new Event(textField.getText(), fechaConHoraCero);
							preview = new EventPanel(ev);
							preview.setBounds(40, 379, 804, 150);
							add(preview);
							preview.repaint();
					  }catch (Exception e1) {
						  e1.printStackTrace();
					  }
				  }else {
						btnButton_Save.setEnabled(false);
				  }
				  Date today = Calendar.getInstance().getTime();
				  Date calendarDate = UtilDate.trim(new Date(jCalendar.getCalendar().getTime().getTime()));
				  if(today.after(calendarDate) && !today.equals(calendarDate)) {
						btnButton_Save.setEnabled(false);
				  }else btnButton_Save.setEnabled(true);
				  
			  }
			});
		
		JLabel jLabelEventDate = new JLabel("Event Date");
		jLabelEventDate.setFont(new Font("Roboto", Font.BOLD, 15));
		jLabelEventDate.setBounds(new Rectangle(40, 23, 140, 25));
		jLabelEventDate.setBounds(39, 22, 140, 25);
		add(jLabelEventDate);
		
		JLabel lblPreview = new JLabel("Preview");
		lblPreview.setFont(new Font("Roboto", Font.BOLD, 15));
		lblPreview.setBounds(new Rectangle(40, 23, 140, 25));
		lblPreview.setBounds(40, 342, 140, 25);
		add(lblPreview);
		
		JLabel lblParaMostrarEl = new JLabel("Para mostrar el logo de los equipos, separe los nombres por un guion (-)");
		lblParaMostrarEl.setForeground(Color.DARK_GRAY);
		lblParaMostrarEl.setFont(new Font("Roboto", Font.PLAIN, 14));
		lblParaMostrarEl.setBounds(217, 225, 458, 17);
		add(lblParaMostrarEl);
		
		jCalendar.addPropertyChangeListener(new PropertyChangeListener()
		{
			public void propertyChange(PropertyChangeEvent propertychangeevent)
			{
				if (propertychangeevent.getPropertyName().equals("locale"))
				{
					jCalendar.setLocale((Locale) propertychangeevent.getNewValue());
				}
				Date today = Calendar.getInstance().getTime();
				Date calendarDate = UtilDate.trim(new Date(jCalendar.getCalendar().getTime().getTime()));
				if(today.after(calendarDate) && !today.equals(calendarDate)) {
					btnButton_Save.setEnabled(false);
				}else btnButton_Save.setEnabled(true);
			}
		});
		
		JLabel lblSeleccionaUnaFecha = new JLabel("(Selecciona una fecha posterio a hoy)");
		lblSeleccionaUnaFecha.setFont(new Font("Roboto", Font.PLAIN, 14));
		lblSeleccionaUnaFecha.setBounds(133, 26, 264, 17);
		add(lblSeleccionaUnaFecha);
	}
}