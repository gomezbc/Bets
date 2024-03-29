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

public class AyudaUserGUI extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AyudaUserGUI dialog = new AyudaUserGUI();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AyudaUserGUI() {
		Bets21Theme.setup();
		setBounds(100, 100, 658, 584);
		getContentPane().setLayout(null);
		contentPanel.setBounds(10, 0, 607, 536);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("AYUDA");
			lblNewLabel.setForeground(new Color(0, 0, 255));
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
			lblNewLabel.setBounds(20, 11, 100, 34);
			contentPanel.add(lblNewLabel);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Hacer una apuesta: ");
			lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblNewLabel_1.setBounds(35, 67, 169, 16);
			contentPanel.add(lblNewLabel_1);
		}
		{
			JLabel lblNewLabel_2 = new JLabel("1. Seleccionar el día del partido al que se quiere apostar.");
			lblNewLabel_2.setBounds(81, 94, 526, 14);
			contentPanel.add(lblNewLabel_2);
		}
		{
			JLabel lblNewLabel_3 = new JLabel("2. Hacer doble click sobre el evento al que se quiere apostar");
			lblNewLabel_3.setBounds(81, 119, 478, 14);
			contentPanel.add(lblNewLabel_3);
		}
		{
			JLabel lblNewLabel_4 = new JLabel("3. Seleccionar la pregunta y el pronóstico sobre el que se quiere apostar.");
			lblNewLabel_4.setBounds(81, 143, 543, 14);
			contentPanel.add(lblNewLabel_4);
		}
		{
			JLabel lblNewLabel_5 = new JLabel("4. Introducir el dinero que se quiere apostar.");
			lblNewLabel_5.setBounds(81, 170, 543, 14);
			contentPanel.add(lblNewLabel_5);
		}
		{
			JLabel lblNewLabel_6 = new JLabel("5. Cuando estés seguro, haz click sobre el botón ASIGNAR. Se creará la apuesta.");
			lblNewLabel_6.setBounds(81, 195, 543, 14);
			contentPanel.add(lblNewLabel_6);
		}
		{
			JLabel lblNewLabel_7 = new JLabel("Apuestas realizadas:");
			lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblNewLabel_7.setBounds(34, 245, 149, 14);
			contentPanel.add(lblNewLabel_7);
		}
		{
			JLabel lblNewLabel_8 = new JLabel("1. Puedes visualizar todas las apuestas que has creado.");
			lblNewLabel_8.setBounds(81, 270, 526, 14);
			contentPanel.add(lblNewLabel_8);
		}
		{
			JLabel lblNewLabel_9 = new JLabel("Añadir Saldo: ");
			lblNewLabel_9.setFont(new Font("Tahoma", Font.BOLD, 13));
			lblNewLabel_9.setBounds(35, 366, 100, 14);
			contentPanel.add(lblNewLabel_9);
		}
		{
			JLabel lblNewLabel_10 = new JLabel("1. Puedes ver el saldo que tienes. ");
			lblNewLabel_10.setBounds(81, 391, 478, 14);
			contentPanel.add(lblNewLabel_10);
		}
		{
			JLabel lblNewLabel_11 = new JLabel("2. Si quieres añadir saldo, primero, introduce la cantidad a ingresar.");
			lblNewLabel_11.setBounds(81, 405, 478, 29);
			contentPanel.add(lblNewLabel_11);
		}
		{
			JLabel lblNewLabel_12 = new JLabel("Luego, pulsa el botón de ACEPTAR.");
			lblNewLabel_12.setBounds(81, 431, 478, 26);
			contentPanel.add(lblNewLabel_12);
		}
		{
			JLabel lblNewLabel_13 = new JLabel("2. Las apuestas que se encuentran en rojo son las que has perdido");
			lblNewLabel_13.setBounds(81, 287, 526, 14);
			contentPanel.add(lblNewLabel_13);
		}
		{
			JLabel lblNewLabel_14 = new JLabel("3. Las que se encuentran en verde, son las que has ganado.");
			lblNewLabel_14.setBounds(81, 307, 383, 14);
			contentPanel.add(lblNewLabel_14);
		}
		{
			JLabel lblNewLabel_15 = new JLabel("4. Las que están en gris todavía no han sucedido, las puedes eliminar o modificar. ");
			lblNewLabel_15.setBounds(81, 324, 553, 14);
			contentPanel.add(lblNewLabel_15);
		}
		{
			JButton okButton = new JButton("OK");
			okButton.setBounds(494, 456, 83, 57);
			contentPanel.add(okButton);
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
