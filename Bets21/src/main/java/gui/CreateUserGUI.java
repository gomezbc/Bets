package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import exceptions.UserAlreadyExist;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JCheckBox;
import java.awt.Font;

public class CreateUserGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField username;
	private JPasswordField passwd;
	private JTextField dni;
	private JTextField name;
	private JTextField surname;
	private JLabel lblUserAlreadyExists;
	private JCheckBox checkbxisAdmin;

	/**
	 * Launch the application.
	 */
	public CreateUserGUI() {
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
	public void jbInit() throws Exception{
		setTitle("Crear Usuario");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		username = new JTextField();
		username.setBounds(198, 120, 180, 20);
		username.setColumns(10);
		contentPane.add(username);
		
		passwd = new JPasswordField();
		passwd.setBounds(198, 154, 180, 20);
		contentPane.add(passwd);
		
		dni = new JTextField();
		dni.setBounds(198, 23, 180, 21);
		contentPane.add(dni);
		dni.setColumns(10);
		
		name = new JTextField();
		name.setBounds(198, 56, 180, 21);
		contentPane.add(name);
		name.setColumns(10);
		
		surname = new JTextField();
		surname.setBounds(198, 87, 180, 21);
		contentPane.add(surname);
		surname.setColumns(10);
		
		JLabel lblDni = new JLabel("Dni:");
		lblDni.setBounds(59, 23, 60, 17);
		contentPane.add(lblDni);
		
		JLabel lblName = new JLabel("Nombre:");
		lblName.setBounds(59, 58, 60, 17);
		contentPane.add(lblName);
		
		JLabel lblSurname = new JLabel("Apellido: ");
		lblSurname.setBounds(59, 89, 60, 17);
		contentPane.add(lblSurname);
		
		JLabel lblUsername = new JLabel("Nombre de usuario: ");
		lblUsername.setBounds(59, 122, 150, 17);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Contraseña: ");
		lblPassword.setBounds(59, 156, 133, 17);
		contentPane.add(lblPassword);
		
		JButton btnCreateUser = new JButton("Crear Usuario");
		btnCreateUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				lblUserAlreadyExists.setVisible(false);
				try {
					facade.createUser(username.getText(), new String(passwd.getPassword()), dni.getText(), name.getText(), surname.getText(), checkbxisAdmin.isSelected());
				} catch (UserAlreadyExist e1) {
					lblUserAlreadyExists.setVisible(true);
				}
				
			}
		});
		btnCreateUser.setBounds(71, 205, 138, 27);
		contentPane.add(btnCreateUser);
		
		JButton btnClose = new JButton("Cerrar");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnClose_actionPerformed(e);
			}
		});
		btnClose.setBounds(259, 205, 105, 27);
		contentPane.add(btnClose);
		
		lblUserAlreadyExists = new JLabel("Este usuario ya existe!");
		lblUserAlreadyExists.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblUserAlreadyExists.setForeground(new Color(220, 20, 60));
		lblUserAlreadyExists.setBounds(255, 182, 191, 15);
		contentPane.add(lblUserAlreadyExists);
		
		checkbxisAdmin = new JCheckBox("Admin");
		checkbxisAdmin.setBounds(170, 177, 114, 25);
		contentPane.add(checkbxisAdmin);
		lblUserAlreadyExists.setVisible(false);
	}
	private void btnClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
