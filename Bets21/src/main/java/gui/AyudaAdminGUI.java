package gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class AyudaAdminGUI extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AyudaAdminGUI dialog = new AyudaAdminGUI();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AyudaAdminGUI() {
		
		setBounds(100, 100, 813, 871);
		getContentPane().setLayout(null);
		contentPanel.setBounds(10, 11, 795, 813);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Crear Eventos: ");
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblNewLabel.setBounds(46, 39, 107, 25);
			contentPanel.add(lblNewLabel);
		}
		
		JLabel lblNewLabel_1 = new JLabel("1. Selecciona el día en el que quieres realizar el evento. ");
		lblNewLabel_1.setBounds(148, 44, 530, 14);
		contentPanel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("2. Escribe los equipos que van a realizar el evento.");
		lblNewLabel_2.setBounds(148, 63, 490, 14);
		contentPanel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("3. Para finalizar, pulsa crear.");
		lblNewLabel_3.setBounds(148, 83, 574, 14);
		contentPanel.add(lblNewLabel_3);
		{
			JLabel lblNewLabel_4 = new JLabel("Añadir preguntas y pronósticos.");
			lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblNewLabel_4.setBounds(46, 154, 259, 14);
			contentPanel.add(lblNewLabel_4);
		}
		{
			JLabel lblNewLabel_5 = new JLabel("1. Selecciona el día, y haz doble click en el evento al que quieres");
			lblNewLabel_5.setBounds(139, 179, 574, 30);
			contentPanel.add(lblNewLabel_5);
		}
		{
			JLabel lblNewLabel_6 = new JLabel("añadir una pregunta o un pronóstico");
			lblNewLabel_6.setBounds(141, 208, 522, 14);
			contentPanel.add(lblNewLabel_6);
		}
		{
			JLabel lblNewLabel_7 = new JLabel("2. Si quieres añadir una pregunta, primero escribe la descripción");
			lblNewLabel_7.setBounds(148, 256, 530, 14);
			contentPanel.add(lblNewLabel_7);
		}
		{
			JLabel lblNewLabel_8 = new JLabel("de la pregunta, y luego, el dinero mínimo de la apuesta");
			lblNewLabel_8.setBounds(148, 278, 574, 14);
			contentPanel.add(lblNewLabel_8);
		}
		{
			JLabel lblNewLabel_9 = new JLabel("Si crees que todo es correcto. Presiona el botón de añadir de abajo.");
			lblNewLabel_9.setBounds(148, 303, 574, 14);
			contentPanel.add(lblNewLabel_9);
		}
		{
			JLabel lblNewLabel_10 = new JLabel("3. Si quieres añadir un pronóstico, primero tienes que seleccionar una pregunta.");
			lblNewLabel_10.setBounds(148, 357, 561, 14);
			contentPanel.add(lblNewLabel_10);
		}
		{
			JLabel lblNewLabel_11 = new JLabel("Luego, escribes la descripción de la apuesta, y la ganancia.");
			lblNewLabel_11.setBounds(148, 382, 574, 14);
			contentPanel.add(lblNewLabel_11);
		}
		{
			JLabel lblNewLabel_12 = new JLabel("Cuando estés seguro de crear el pronóstico, presiona el botón de añadir .");
			lblNewLabel_12.setBounds(148, 407, 604, 14);
			contentPanel.add(lblNewLabel_12);
		}
		{
			JLabel lblNewLabel_13 = new JLabel("Cerrar eventos:");
			lblNewLabel_13.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblNewLabel_13.setBounds(64, 463, 107, 14);
			contentPanel.add(lblNewLabel_13);
		}
		{
			JLabel lblNewLabel_14 = new JLabel("1. Selecciona el día del evento que quieres cerrar.");
			lblNewLabel_14.setBounds(148, 488, 561, 14);
			contentPanel.add(lblNewLabel_14);
		}
		{
			JLabel lblNewLabel_15 = new JLabel("2. Haz doble click sobre el evento que quieres cerrar.");
			lblNewLabel_15.setBounds(148, 506, 530, 14);
			contentPanel.add(lblNewLabel_15);
		}
		{
			JLabel lblNewLabel_16 = new JLabel("3. Selecciona la pregunta.");
			lblNewLabel_16.setBounds(148, 526, 588, 14);
			contentPanel.add(lblNewLabel_16);
		}
		{
			JLabel lblNewLabel_17 = new JLabel("4. Selecciona el pronóstico correcto. Después, haz click en asignar.");
			lblNewLabel_17.setBounds(148, 546, 629, 14);
			contentPanel.add(lblNewLabel_17);
		}
		{
			JLabel lblNewLabel_18 = new JLabel("Lista de Usuarios: ");
			lblNewLabel_18.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblNewLabel_18.setBounds(64, 598, 138, 14);
			contentPanel.add(lblNewLabel_18);
		}
		{
			JLabel lblNewLabel_19 = new JLabel("AYUDA");
			lblNewLabel_19.setForeground(new Color(0, 0, 205));
			lblNewLabel_19.setFont(new Font("Tahoma", Font.BOLD, 20));
			lblNewLabel_19.setBounds(10, 0, 79, 28);
			contentPanel.add(lblNewLabel_19);
		}
		{
			JLabel lblNewLabel_20 = new JLabel("1. Puedes ver todos los usuarios registrados, con todos sus datos.");
			lblNewLabel_20.setBounds(148, 623, 604, 14);
			contentPanel.add(lblNewLabel_20);
		}
		{
			JLabel lblNewLabel_21 = new JLabel("2. Si quieres eliminar un usuario, lo seleccionas y haces click en eliminar usuario.");
			lblNewLabel_21.setBounds(148, 651, 629, 14);
			contentPanel.add(lblNewLabel_21);
		}
		{
			JLabel lblNewLabel_22 = new JLabel("3. Si en las pestañadas haces click en admin, puedes ver todos los administradores.");
			lblNewLabel_22.setBounds(148, 680, 629, 14);
			contentPanel.add(lblNewLabel_22);
		}
		
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(143, 740, 508, 33);
			contentPanel.add(buttonPane);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			{
			    JButton okButton = new JButton("OK");
			    okButton.setActionCommand("OK");
			    buttonPane.add(okButton);
			    okButton.addActionListener(new ActionListener() {
			        public void actionPerformed(ActionEvent e) {
			            jButtonClose_actionPerformed(e);
			        }
			    });
			    getRootPane().setDefaultButton(okButton);
			}
			


		}
	}
	
	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
		
	}
}
