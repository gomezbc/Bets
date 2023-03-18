package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.toedter.calendar.JCalendar;

import businessLogic.BLFacade;
import exceptions.EventAlreadyExist;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;

public class CreateEvent extends JFrame {


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField_EscribirEquipos;
	
	private JLabel lblNewLabel = new JLabel("Evento Creado");
	private JLabel lblNewLabel_1 = new JLabel("Evento ya creado para este día");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateEvent frame = new CreateEvent();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	
	/**
	 * Create the frame.
	 */
	public CreateEvent() {
		setTitle("Crear Evento");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 559, 372);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JCalendar jCalendar = new JCalendar();
		jCalendar.setBounds(30, 20, 225, 150);
		contentPane.add(jCalendar);
		
		JLabel lblNewLabelEquipos = new JLabel("Evento:");
		lblNewLabelEquipos.setBounds(125, 211, 119, 14);
		lblNewLabelEquipos.setFont(new Font("Tahoma", Font.PLAIN, 13));
		contentPane.add(lblNewLabelEquipos);
		
		textField_EscribirEquipos = new JTextField();
		textField_EscribirEquipos.setBounds(211, 204, 234, 30);
		contentPane.add(textField_EscribirEquipos);
		textField_EscribirEquipos.setColumns(10);
		
		
		JButton btnButton_Save = new JButton("Guardar");
		btnButton_Save.setBounds(77, 246, 148, 37);
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
		contentPane.add(btnButton_Save);
		
		
		JButton btnButton_Close = new JButton("Cerrar");
		btnButton_Close.setBounds(328, 246, 148, 37);
		btnButton_Close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
			
			
		});
		contentPane.add(btnButton_Close);
		lblNewLabel.setBounds(350, 142, 119, 14);
		
		
		lblNewLabel.setEnabled(true);
		lblNewLabel.setVisible(false);
		lblNewLabel.setForeground(new Color(148, 0, 211));
		contentPane.add(lblNewLabel);
		lblNewLabel_1.setBounds(319, 94, 189, 37);
		
	
		lblNewLabel_1.setForeground(new Color(165, 42, 42));
		lblNewLabel_1.setVisible(false);
		contentPane.add(lblNewLabel_1);
	}
	
	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
