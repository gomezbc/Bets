package gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import businessLogic.BLFacade;
import domain.User;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import java.awt.Color;

public class añadirSaldoGUI extends JPanel {

	
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	
	private JLabel lblNewLabel = new JLabel("Dinero a añadir : ");
	private JLabel lblNewLabel_1 = new JLabel("Saldo actual:");
	private JButton btnNewButton = new JButton("Aceptar");
	private final JLabel lblNewLabel_2 = new JLabel(" ");
	private JLabel lblNewLabel_3 = new JLabel("Dinero añadido");
	private final JLabel lblNewLabel_4 = new JLabel("Tiene que ser positivo");
	
	
	/**
	 * Create the panel.
	 */
	public añadirSaldoGUI() {
			
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
		
		User userRegistered = LoginUserGUI.getUserRegistered();
		BLFacade facade = MainGUI.getBusinessLogic();
		
		lblNewLabel_2.setText( Float.toString(facade.saldoActual(userRegistered.getDni())));
		
		setLayout(null);

		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(70, 134, 154, 69);
		add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(229, 152, 96, 37);
		add(textField);
		textField.setColumns(10);
		

		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(70, 94, 139, 37);
		add(lblNewLabel_1);
		
		lblNewLabel_2.setText( Float.toString(facade.saldoActual(userRegistered.getDni())));
		
		btnNewButton.setBounds(150, 252, 112, 37);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				if (Float.parseFloat(textField.getText()) > 0){
					lblNewLabel_4.setVisible(false);
					try {
						
						facade.modifySaldo(Float.parseFloat(textField.getText()), userRegistered.getDni());
						lblNewLabel_2.setText( Float.toString(facade.saldoActual(userRegistered.getDni())));
						lblNewLabel_3.setVisible(true);
						textField.setText("");
						
					} catch (Exception e2) {
						lblNewLabel_3.setVisible(false);
						e2.printStackTrace();
			    	
					}
				} else {
					lblNewLabel_4.setVisible(true);
					
				}
				
				
				
				
			} });
		
		add(btnNewButton);
		lblNewLabel_2.setBounds(253, 102, 72, 24);
		//lblNewLabel_2.setText( Float.toString(facade.saldoActual(userRegistered.getDni())));
		add(lblNewLabel_2);
		
	
		
		lblNewLabel_3.setVisible(false);
		lblNewLabel_3.setForeground(new Color(199, 21, 133));
		lblNewLabel_3.setBackground(Color.BLACK);
		lblNewLabel_3.setBounds(70, 201, 129, 27);
		add(lblNewLabel_3);
		
		
		lblNewLabel_4.setVisible(false);
		lblNewLabel_4.setForeground(new Color(255, 0, 0));
		lblNewLabel_4.setBounds(211, 181, 169, 60);
		
		add(lblNewLabel_4);
		
	}
}
