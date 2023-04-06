package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.toedter.calendar.JCalendar;

import businessLogic.BLFacade;
import exceptions.EventAlreadyExist;
import javax.swing.border.LineBorder;

public class CreateEventGUI extends JPanel {


	private static final long serialVersionUID = 1L;
	private JTextField textField_EscribirEquipos;
	
	private JLabel lblNewLabel = new JLabel("Evento Creado");
	private JLabel lblNewLabel_1 = new JLabel("Evento ya creado para este día");

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
		setBorder(new LineBorder(new Color(17, 110, 80), 2, true));
		this.setBounds(100, 100, 559, 372);

		this.setLayout(null);
		
		JCalendar jCalendar = new JCalendar();
		jCalendar.setBounds(30, 20, 225, 150);
		this.add(jCalendar);
		
		JLabel lblNewLabelEquipos = new JLabel("Evento:");
		lblNewLabelEquipos.setBounds(125, 211, 119, 14);
		lblNewLabelEquipos.setFont(new Font("Tahoma", Font.PLAIN, 13));
		this.add(lblNewLabelEquipos);
		
		textField_EscribirEquipos = new JTextField();
		textField_EscribirEquipos.setBounds(211, 204, 234, 30);
		this.add(textField_EscribirEquipos);
		textField_EscribirEquipos.setColumns(10);
		
		
		JButton btnButton_Save = new JButton("Guardar");
		btnButton_Save.setBounds(194, 284, 148, 37);
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
					
					
					facade.createEvent( textField_EscribirEquipos.getText(), fechaConHoraCero);
					lblNewLabel_1.setVisible(false);
					lblNewLabel.setVisible(true);
					
					
					
				} catch (EventAlreadyExist e1) {
					lblNewLabel_1.setVisible(true);
					lblNewLabel.setVisible(false);
				}
			}
			
		});
		this.add(btnButton_Save);
		lblNewLabel.setBounds(350, 142, 119, 14);
		
		
		lblNewLabel.setEnabled(true);
		lblNewLabel.setVisible(false);
		lblNewLabel.setForeground(new Color(148, 0, 211));
		this.add(lblNewLabel);
		lblNewLabel_1.setBounds(319, 94, 189, 37);
		
	
		lblNewLabel_1.setForeground(new Color(165, 42, 42));
		lblNewLabel_1.setVisible(false);
		this.add(lblNewLabel_1);
	}
}
