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
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;

import theme.Bets21Theme;
import javax.swing.JScrollBar;

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
		Bets21Theme.setup();
		
		setBounds(100, 100, 784, 675);
		getContentPane().setLayout(null);
		contentPanel.setBounds(10, 61, 758, 566);
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
		lblNewLabel_1.setBounds(192, 45, 530, 14);
		contentPanel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("2. Escribe los equipos que van a realizar el evento.");
		lblNewLabel_2.setBounds(192, 70, 490, 14);
		contentPanel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("3. Para finalizar, pulsa crear.");
		lblNewLabel_3.setBounds(192, 95, 574, 14);
		contentPanel.add(lblNewLabel_3);
		{
			JLabel lblNewLabel_4 = new JLabel("Añadir preguntas y pronósticos.");
			lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblNewLabel_4.setBounds(46, 132, 259, 14);
			contentPanel.add(lblNewLabel_4);
		}
		{
			JLabel lblNewLabel_5 = new JLabel("1. Selecciona el día, y haz doble click en el evento al que quieres");
			lblNewLabel_5.setBounds(192, 157, 574, 30);
			contentPanel.add(lblNewLabel_5);
		}
		{
			JLabel lblNewLabel_6 = new JLabel("añadir una pregunta o un pronóstico");
			lblNewLabel_6.setBounds(210, 184, 522, 14);
			contentPanel.add(lblNewLabel_6);
		}
		{
			JLabel lblNewLabel_7 = new JLabel("2. Si quieres añadir una pregunta, primero escribe la descripción");
			lblNewLabel_7.setBounds(192, 211, 530, 14);
			contentPanel.add(lblNewLabel_7);
		}
		{
			JLabel lblNewLabel_8 = new JLabel("de la pregunta, y luego, el dinero mínimo de la apuesta");
			lblNewLabel_8.setBounds(210, 223, 574, 25);
			contentPanel.add(lblNewLabel_8);
		}
		{
			JLabel lblNewLabel_9 = new JLabel("Si crees que todo es correcto. Presiona el botón de añadir de abajo.");
			lblNewLabel_9.setBounds(210, 247, 574, 14);
			contentPanel.add(lblNewLabel_9);
		}
		{
			JLabel lblNewLabel_10 = new JLabel("3. Si quieres añadir un pronóstico, primero tienes que seleccionar una pregunta.");
			lblNewLabel_10.setBounds(192, 265, 561, 14);
			contentPanel.add(lblNewLabel_10);
		}
		{
			JLabel lblNewLabel_11 = new JLabel("Luego, escribes la descripción de la apuesta, y la ganancia.");
			lblNewLabel_11.setBounds(210, 282, 574, 14);
			contentPanel.add(lblNewLabel_11);
		}
		{
			JLabel lblNewLabel_12 = new JLabel("Cuando estés seguro de crear el pronóstico, presiona el botón de añadir .");
			lblNewLabel_12.setBounds(210, 297, 604, 14);
			contentPanel.add(lblNewLabel_12);
		}
		{
			JLabel lblNewLabel_13 = new JLabel("Cerrar eventos:");
			lblNewLabel_13.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblNewLabel_13.setBounds(46, 344, 107, 14);
			contentPanel.add(lblNewLabel_13);
		}
		{
			JLabel lblNewLabel_14 = new JLabel("1. Selecciona el día del evento que quieres cerrar.");
			lblNewLabel_14.setBounds(192, 370, 561, 14);
			contentPanel.add(lblNewLabel_14);
		}
		{
			JLabel lblNewLabel_15 = new JLabel("2. Haz doble click sobre el evento que quieres cerrar.");
			lblNewLabel_15.setBounds(192, 385, 530, 25);
			contentPanel.add(lblNewLabel_15);
		}
		{
			JLabel lblNewLabel_16 = new JLabel("3. Selecciona la pregunta.");
			lblNewLabel_16.setBounds(192, 407, 588, 25);
			contentPanel.add(lblNewLabel_16);
		}
		{
			JLabel lblNewLabel_17 = new JLabel("4. Selecciona el pronóstico correcto. Después, haz click en asignar.");
			lblNewLabel_17.setBounds(192, 436, 629, 14);
			contentPanel.add(lblNewLabel_17);
		}
		{
			JLabel lblNewLabel_18 = new JLabel("Lista de Usuarios: ");
			lblNewLabel_18.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblNewLabel_18.setBounds(46, 490, 138, 14);
			contentPanel.add(lblNewLabel_18);
		}
		{
			JLabel lblNewLabel_20 = new JLabel("1. Puedes ver todos los usuarios registrados, con todos sus datos.");
			lblNewLabel_20.setBounds(192, 491, 604, 14);
			contentPanel.add(lblNewLabel_20);
		}
		{
			JLabel lblNewLabel_21 = new JLabel("2. Si quieres eliminar un usuario, lo seleccionas y haces click en eliminar usuario.");
			lblNewLabel_21.setBounds(192, 516, 629, 14);
			contentPanel.add(lblNewLabel_21);
		}
		{
			JLabel lblNewLabel_22 = new JLabel("3. Si en las pestañadas haces click en admin, puedes ver todos los administradores.");
			lblNewLabel_22.setBounds(192, 541, 629, 14);
			contentPanel.add(lblNewLabel_22);
		}
		{
			JLabel lblNewLabel_23 = new JLabel("* Los eventos que están en naranja, ya están cerrados.");
			lblNewLabel_23.setBounds(158, 345, 503, 14);
			contentPanel.add(lblNewLabel_23);
		}
		{
			JLabel lblNewLabel_19 = new JLabel("AYUDA");
			lblNewLabel_19.setBounds(52, 8, 103, 39);
			getContentPane().add(lblNewLabel_19);
			lblNewLabel_19.setForeground(new Color(0, 0, 205));
			lblNewLabel_19.setFont(new Font("Tahoma", Font.BOLD, 20));
		}
		{
		    JButton okButton = new JButton("OK");
		    okButton.setBounds(651, 11, 79, 40);
		    getContentPane().add(okButton);
		    okButton.setActionCommand("OK");
		    okButton.addActionListener(new ActionListener() {
		        public void actionPerformed(ActionEvent e) {
		            jButtonClose_actionPerformed(e);
		        }
		    });
		    getRootPane().setDefaultButton(okButton);
		}
	}
	
	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
		
	}
}
