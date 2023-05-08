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
import javax.swing.JDialog;

import java.awt.Color;
import java.awt.Dimension;

public class AddSaldoGUI extends JPanel {

	
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	
	private JLabel lblNewLabel = new JLabel("Dinero a añadir : ");
	private JLabel lblInfoSaldo = new JLabel("Saldo actual:");
	private JButton btnNewButton = new JButton("Añadir");
	private final JLabel lblSaldo = new JLabel(" ");
	private final JLabel lblNewLabel_1 = new JLabel("Sacar dinero:");
	private final JTextField textField_1 = new JTextField();
	private JLabel lblNewLabel_2;
	private JLabel lblDineroAñadido = new JLabel("Dinero añadido");
	private JLabel lblError = new JLabel("Tiene que ser positivo");

	
	
	/**
	 * Create the panel.
	 */
	public AddSaldoGUI() {
		textField_1.setBounds(201, 215, 72, 30);
		textField_1.setColumns(10);
			
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
		JButton btnNewButton2 = new JButton("AYUDA");
		btnNewButton2.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton2.setBounds(726, 46, 89, 23);
		btnNewButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog a = new AyudaUserGUI();
				a.setVisible(true);
				
			}
		});
		this.add(btnNewButton2);
		
		setSize(new Dimension(886, 541));
		
		
		User userRegistered = MainGUI.getUserRegistered();
		lblSaldo.setFont(new Font("Roboto", Font.BOLD, 16));
		lblSaldo.setText( Float.toString(userRegistered.getSaldo()));
		
		setLayout(null);

		lblNewLabel.setFont(new Font("Roboto", Font.BOLD, 16));
		lblNewLabel.setBounds(64, 119, 154, 51);
		add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBorder(null);
		textField.setFont(new Font("Roboto Black", Font.PLAIN, 16));
		textField.setBounds(223, 130, 72, 30);
		add(textField);
		textField.setColumns(10);
		

		lblInfoSaldo.setFont(new Font("Roboto", Font.BOLD, 16));
		lblInfoSaldo.setBounds(64, 46, 139, 37);
		add(lblInfoSaldo);
		
		lblSaldo.setText( Float.toString(userRegistered.getSaldo()));
		btnNewButton.setFont(new Font("Roboto", Font.BOLD, 16));
		btnNewButton.setBackground(Color.WHITE);
		btnNewButton.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		btnNewButton.setBounds(394, 126, 112, 37);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User userRegistered = MainGUI.getUserRegistered();
				BLFacade facade = MainGUI.getBusinessLogic();
				if (Float.parseFloat(textField.getText()) > 0){
					lblNewLabel_2.setVisible(true);
					try {
						facade.modifySaldo(Float.parseFloat(textField.getText()), userRegistered.getDni());
						MainGUI.setUserRegistered(facade.getUser(userRegistered.getDni()));
						lblDineroAñadido.setVisible(true);
						lblError.setVisible(false);
						textField.setText("");
					} catch (NumberFormatException e3) {
						lblError.setVisible(true);
						lblDineroAñadido.setVisible(false);
						
					} catch (Exception e2) {
						lblDineroAñadido.setVisible(false);
						lblError.setVisible(false);
					
				}  
				} else {

					
				}
				UserGUI.updateFrame(new AddSaldoGUI());
			} });
		
		add(btnNewButton);
		lblSaldo.setBounds(222, 52, 72, 24);
		add(lblSaldo);
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNewLabel_1.setBounds(64, 212, 142, 30);
		
		add(lblNewLabel_1);
		
		add(textField_1);
		
		JButton btnNewButton_1 = new JButton("Sacar");
		btnNewButton_1.setFont(new Font("Dialog", Font.BOLD, 16));
		btnNewButton_1.setBounds(339, 212, 112, 37);
		btnNewButton_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblNewLabel_2.setVisible(false);
				User userRegistered = MainGUI.getUserRegistered();
				BLFacade facade = MainGUI.getBusinessLogic();
				if (Float.parseFloat(textField_1.getText()) > 0){
					try {
						lblNewLabel_2.setText("Dinero añadido");
						lblNewLabel_2.setVisible(true);
						
						facade.modifySaldo(-(Float.parseFloat(textField_1.getText())), userRegistered.getDni());
						MainGUI.setUserRegistered(facade.getUser(userRegistered.getDni()));
						textField.setText("");
					} catch (NumberFormatException e3) {

						
					} catch (Exception e2) {

						//e2.printStackTrace();
			    	
					}
				} else {

					
				}
				UserGUI.updateFrame(new AddSaldoGUI());
			}
		});
		
		
		add(btnNewButton_1);
		lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_2.setForeground(new Color(46, 139, 87));
		lblNewLabel_2.setBounds(79, 299, 134, 23);
		add(lblNewLabel_2);


		
	}
}
