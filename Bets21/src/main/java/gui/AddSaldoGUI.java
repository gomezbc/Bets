package gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import businessLogic.BLFacade;
import domain.User;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Dimension;

public class AddSaldoGUI extends JPanel {

	
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	
	private JLabel lblNewLabel = new JLabel("Dinero a añadir : ");
	private JLabel lblInfoSaldo = new JLabel("Saldo actual:");
	private JButton btnNewButton = new JButton("Aceptar");
	private final JLabel lblSaldo = new JLabel(" ");
	private JLabel lblDineroAñadido = new JLabel("Dinero añadido");
	private final JLabel lblError = new JLabel("Tiene que ser positivo");
	
	
	/**
	 * Create the panel.
	 */
	public AddSaldoGUI() {
			
		try
		{
			
			jbInit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}
	
	
	public void jbInit() {
		setSize(new Dimension(886, 541));
		
		User userRegistered = MainGUI.getUserRegistered();
		lblSaldo.setFont(new Font("Roboto", Font.BOLD, 16));
		lblSaldo.setText( Float.toString(userRegistered.getSaldo()));
		
		setLayout(null);

		lblNewLabel.setFont(new Font("Roboto", Font.BOLD, 16));
		lblNewLabel.setBounds(62, 94, 154, 51);
		add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBorder(null);
		textField.setFont(new Font("Roboto Black", Font.PLAIN, 16));
		textField.setBounds(203, 102, 72, 30);
		add(textField);
		textField.setColumns(10);
		

		lblInfoSaldo.setFont(new Font("Roboto", Font.BOLD, 16));
		lblInfoSaldo.setBounds(64, 46, 139, 37);
		add(lblInfoSaldo);
		
		lblSaldo.setText( Float.toString(userRegistered.getSaldo()));
		btnNewButton.setFont(new Font("Roboto", Font.BOLD, 16));
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		btnNewButton.setBounds(127, 158, 112, 37);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User userRegistered = MainGUI.getUserRegistered();
				BLFacade facade = MainGUI.getBusinessLogic();
				if (Float.parseFloat(textField.getText()) > 0){
					lblError.setVisible(false);
					try {
						facade.modifySaldo(Float.parseFloat(textField.getText()), userRegistered.getDni());
						MainGUI.setUserRegistered(facade.getUser(userRegistered.getDni()));//Actualizamos los datos del usuario
						lblDineroAñadido.setVisible(true);
						textField.setText("");
						
					} catch (Exception e2) {
						lblDineroAñadido.setVisible(false);
						e2.printStackTrace();
			    	
					}
				} else {
					lblError.setVisible(true);
					
				}
				UserGUI3.updateFrame(new AddSaldoGUI());
			} });
		
		add(btnNewButton);
		lblSaldo.setBounds(222, 52, 72, 24);
		add(lblSaldo);
		lblDineroAñadido.setFont(new Font("Roboto", Font.BOLD, 14));
		
	
		
		lblDineroAñadido.setVisible(false);
		lblDineroAñadido.setForeground(new Color(199, 21, 133));
		lblDineroAñadido.setBackground(Color.BLACK);
		lblDineroAñadido.setBounds(70, 214, 129, 30);
		add(lblDineroAñadido);
		lblError.setFont(new Font("Roboto", Font.BOLD, 14));
		
		
		lblError.setVisible(false);
		lblError.setForeground(new Color(255, 0, 0));
		lblError.setBounds(199, 214, 169, 30);
		
		add(lblError);
		
	}
}
