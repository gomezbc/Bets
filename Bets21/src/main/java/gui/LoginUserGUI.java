package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.User;
import exceptions.UserDoesntExist;

public class LoginUserGUI extends JFrame {

	private static final long serialVersionUID = 1L;
//	private static User userRegistered;
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private JLabel userError;

//	public static User getUserRegistered() {
//		BLFacade facade = MainGUI.getBusinessLogic();
//		try {
//			return facade.getUser(userRegistered.getDni());
//		} catch (UserDoesntExist e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}

//	public static void setUserRegistered(User userRegistered) {
//		LoginUserGUI.userRegistered = userRegistered;
//	}
	
	/**
	 * Launch the application.
	 */
	public LoginUserGUI() {
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
	public void jbInit() throws Exception {
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(208, 39, 180, 20);
		contentPane.add(textField);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(208, 73, 180, 20);
		contentPane.add(passwordField);
		
		JLabel lblDni = new JLabel("Dni:");
		lblDni.setBounds(69, 41, 102, 17);
		contentPane.add(lblDni);
		
		JLabel lblPassword = new JLabel("Contraseña: ");
		lblPassword.setBounds(69, 75, 102, 17);
		contentPane.add(lblPassword);
		
		JButton btnClose = new JButton("Cerrar");
		btnClose.setBounds(284, 131, 105, 27);
		contentPane.add(btnClose);
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnClose_actionPerformed(e);
			}
		});
		
		
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e1) {
				BLFacade facade = MainGUI.getBusinessLogic();
				User user = null;
				try {
					user = facade.getUser(textField.getText());
					if(!user.checkCredentials(new String(passwordField.getPassword()))) userError.setText("La contraseña es incorrecta");
					else {
							setUserRegistered(user);
							userError.setVisible(false);
						if(user.isAdmin()) {
							JFrame a = new AdminGUI();
							a.setVisible(true);
						}else {
							JFrame a = new UserGUI();
							a.setVisible(true);
						}
					}
				}catch(UserDoesntExist e2) {
					userError.setVisible(true);
					userError.setText(e2.getMessage());
				}
			}
		});
		
		
		btnLogin.setBounds(69, 131, 105, 27);
		contentPane.add(btnLogin);
		
		userError = new JLabel("");
		userError.setHorizontalAlignment(SwingConstants.CENTER);
		userError.setBounds(55, 178, 349, 17);
		contentPane.add(userError);
	}
	private void btnClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}