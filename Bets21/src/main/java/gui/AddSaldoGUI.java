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
		
		User userRegistered = MainGUI.getUserRegistered();
		setBorder(new LineBorder(new Color(17, 110, 80), 2, true));
		lblSaldo.setText( Float.toString(userRegistered.getSaldo()));
		
		setLayout(null);

		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(70, 134, 154, 69);
		add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(229, 152, 96, 37);
		add(textField);
		textField.setColumns(10);
		

		lblInfoSaldo.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblInfoSaldo.setBounds(70, 94, 139, 37);
		add(lblInfoSaldo);
		
		lblSaldo.setText( Float.toString(userRegistered.getSaldo()));
		
		btnNewButton.setBounds(150, 252, 112, 37);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User userRegistered = MainGUI.getUserRegistered();
				BLFacade facade = MainGUI.getBusinessLogic();
				if (Float.parseFloat(textField.getText()) > 0){
					lblError.setVisible(false);
					try {
						facade.modifySaldo(Float.parseFloat(textField.getText()), userRegistered.getDni());
						userRegistered = MainGUI.getUserRegistered();//Actualizamos los datos del usuario
						lblSaldo.setText( Float.toString(userRegistered.getSaldo()));
						lblDineroAñadido.setVisible(true);
						textField.setText("");
						
					} catch (Exception e2) {
						lblDineroAñadido.setVisible(false);
						e2.printStackTrace();
			    	
					}
				} else {
					lblError.setVisible(true);
					
				}
				updateFrame();
			} });
		
		add(btnNewButton);
		lblSaldo.setBounds(253, 102, 72, 24);
		add(lblSaldo);
		
	
		
		lblDineroAñadido.setVisible(false);
		lblDineroAñadido.setForeground(new Color(199, 21, 133));
		lblDineroAñadido.setBackground(Color.BLACK);
		lblDineroAñadido.setBounds(70, 201, 129, 27);
		add(lblDineroAñadido);
		
		
		lblError.setVisible(false);
		lblError.setForeground(new Color(255, 0, 0));
		lblError.setBounds(211, 181, 169, 60);
		
		add(lblError);
		
	}
	
	public void updateFrame() {
		User userRegistered = MainGUI.getUserRegistered();
		lblSaldo.setText( Float.toString(userRegistered.getSaldo()));
	}
}
