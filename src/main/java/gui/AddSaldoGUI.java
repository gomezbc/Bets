package gui;

import businessLogic.BLFacade;
import domain.User;
import theme.Bets21Theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddSaldoGUI extends JPanel {

	
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	
	private JLabel lblAñadirInfo = new JLabel("Dinero a añadir : ");
	private JLabel lblInfoSaldo = new JLabel("Saldo actual:");
	private JButton btnAñadir = new JButton("Añadir");
	private final JLabel lblSaldo = new JLabel(" ");
	private final JLabel lblSacarInfo = new JLabel("Sacar dinero:");
	private final JTextField textField_1 = new JTextField();
	private JLabel lblAñadir;
	private JLabel lblError = new JLabel("Tiene que ser positivo");
	private JLabel lblSacar;
	private final JLabel lblAñadeUnaTarjeta = new JLabel("AÑADE UNA TARJETA DE CREDITO EN EL APARTADO DATOS PERSONALES");

	
	
	/**
	 * Create the panel.
	 */
	public AddSaldoGUI() {
		textField_1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		textField_1.setBorder(new EmptyBorder(0, 0, 0, 0));
		textField_1.setBounds(223, 214, 110, 30);
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
		Bets21Theme.setup();
		
		setSize(new Dimension(886, 541));
		
		Font fuenteSegoe = new Font("Segoe UI Semibold", Font.PLAIN, 16);

		User userRegistered = MainGUI.getUserRegistered();
		lblSaldo.setFont(fuenteSegoe);
		lblSaldo.setText(String.format("%.2f", userRegistered.getSaldo()));
		
		setLayout(null);

		lblAñadirInfo.setFont(fuenteSegoe);
		lblAñadirInfo.setBounds(64, 119, 154, 51);
		add(lblAñadirInfo);
		
		textField = new JTextField();
		textField.setBorder(null);
		textField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		textField.setBounds(223, 130, 110, 30);
		add(textField);
		textField.setColumns(10);
		

		lblInfoSaldo.setFont(fuenteSegoe);
		lblInfoSaldo.setBounds(64, 46, 139, 37);
		add(lblInfoSaldo);
		
		ImageIcon icon = new ImageIcon(EventInfoPanel.class.getResource("/icons/addmoney.png"));
		Image scaledIcon = icon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
		btnAñadir.setIcon(new ImageIcon(scaledIcon));
		
		btnAñadir.setBounds(394, 126, 112, 37);
		btnAñadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblAñadir.setVisible(false);
				User userRegistered = MainGUI.getUserRegistered();
				BLFacade facade = MainGUI.getBusinessLogic();
				try {
					if (Float.parseFloat(textField.getText()) > 0){
						
							facade.modifySaldo(Float.parseFloat(textField.getText()), userRegistered.getDni());
							MainGUI.setUserRegistered(facade.getUser(userRegistered.getDni()));
							userRegistered = MainGUI.getUserRegistered();
							lblSaldo.setText(String.format("%.2f", userRegistered.getSaldo()));
							lblAñadir.setVisible(true);
							lblSacar.setVisible(false);
							lblError.setVisible(false);
							textField.setText("");
					}
				} catch (NumberFormatException e3) {
						lblError.setVisible(true);
						lblAñadir.setVisible(false);
						lblSacar.setVisible(false);
				} catch (Exception e2) {
						lblError.setVisible(true);
						lblAñadir.setVisible(false);
						lblSacar.setVisible(false);
				}  
				UserGUI.updateSaldo();
			} });
		
		add(btnAñadir);
		lblSaldo.setBounds(222, 52, 72, 24);
		add(lblSaldo);
		lblSacarInfo.setFont(fuenteSegoe);
		lblSacarInfo.setBounds(64, 212, 142, 30);
		
		add(lblSacarInfo);
		
		add(textField_1);
		
		JButton btnSacar = new JButton("Sacar");
		icon = new ImageIcon(EventInfoPanel.class.getResource("/icons/withdraw.png"));
		scaledIcon = icon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
		btnSacar.setIcon(new ImageIcon(scaledIcon));
		btnSacar.setBounds(394, 210, 112, 37);
		btnSacar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblSacar.setVisible(false);
				User userRegistered = MainGUI.getUserRegistered();
				BLFacade facade = MainGUI.getBusinessLogic();
				try {
					if (Float.parseFloat(textField_1.getText()) > 0){
						facade.modifySaldo(-(Float.parseFloat(textField_1.getText())), userRegistered.getDni());
						MainGUI.setUserRegistered(facade.getUser(userRegistered.getDni()));
						userRegistered = MainGUI.getUserRegistered();
						lblSaldo.setText(String.format("%.2f", userRegistered.getSaldo()));
						textField_1.setText("");
						lblSacar.setVisible(true);
						lblAñadir.setVisible(false);
					}
				} catch (NumberFormatException e3) {
					lblError.setVisible(true);
					lblSacar.setVisible(false);
					lblAñadir.setVisible(false);
				} catch (Exception e2) {
					lblError.setVisible(true);
					lblSacar.setVisible(false);
					lblAñadir.setVisible(false);
				}
				UserGUI.updateSaldo();
			}
		});
		add(btnSacar);
		
		lblAñadir = new JLabel("Dinero añadido");
		lblAñadir.setVisible(false);
		lblAñadir.setFont(fuenteSegoe);
		lblAñadir.setForeground(new Color(46, 139, 87));
		lblAñadir.setBounds(523, 130, 134, 23);
		add(lblAñadir);


		
		
		lblSacar = new JLabel("Dinero sacado");
		lblSacar.setVisible(false);
		lblSacar.setForeground(new Color(46, 139, 87));
		lblSacar.setFont(fuenteSegoe);
		lblSacar.setBounds(523, 214, 139, 23);
		add(lblSacar);
		lblAñadeUnaTarjeta.setForeground(new Color(246, 97, 81));
		
		
		lblAñadeUnaTarjeta.setBounds(145, 312, 577, 30);
		lblAñadeUnaTarjeta.setFont(fuenteSegoe);
		lblAñadeUnaTarjeta.setVisible(false);
		add(lblAñadeUnaTarjeta);
		
		User u = MainGUI.getUserRegistered();
		if (u.getCreditCard() == null) {
			btnSacar.setEnabled(false);
			btnAñadir.setEnabled(false);
			lblAñadeUnaTarjeta.setVisible(true);
		}
		
	}
}
