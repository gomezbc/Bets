package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.User;
import exceptions.UserAlreadyExist;

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
		setResizable(false);
		setBackground(new Color(0, 145, 202));
		setTitle("Crear Usuario");
		setBounds(100, 100, 370, 327);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 145, 202));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		username = new JTextField();
		username.setBounds(164, 116, 180, 20);
		username.setColumns(10);
		contentPane.add(username);
		
		passwd = new JPasswordField();
		passwd.setBounds(164, 150, 180, 20);
		contentPane.add(passwd);
		
		dni = new JTextField();
		dni.setBounds(164, 19, 180, 21);
		contentPane.add(dni);
		dni.setColumns(10);
		
		name = new JTextField();
		name.setBounds(164, 52, 180, 21);
		contentPane.add(name);
		name.setColumns(10);
		
		surname = new JTextField();
		surname.setBounds(164, 83, 180, 21);
		contentPane.add(surname);
		surname.setColumns(10);
		
		JLabel lblDni = new JLabel("Dni:");
		lblDni.setFont(new Font("Dialog", Font.BOLD, 14));
		lblDni.setForeground(Color.WHITE);
		lblDni.setBounds(25, 19, 60, 17);
		contentPane.add(lblDni);
		
		JLabel lblName = new JLabel("Nombre:");
		lblName.setFont(new Font("Dialog", Font.BOLD, 14));
		lblName.setForeground(Color.WHITE);
		lblName.setBounds(25, 54, 60, 17);
		contentPane.add(lblName);
		
		JLabel lblSurname = new JLabel("Apellido: ");
		lblSurname.setFont(new Font("Dialog", Font.BOLD, 14));
		lblSurname.setForeground(Color.WHITE);
		lblSurname.setBounds(25, 85, 60, 17);
		contentPane.add(lblSurname);
		
		JLabel lblUsername = new JLabel("Nombre de usuario: ");
		lblUsername.setFont(new Font("Dialog", Font.BOLD, 14));
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setBounds(25, 118, 150, 17);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Contraseña: ");
		lblPassword.setFont(new Font("Dialog", Font.BOLD, 14));
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setBounds(25, 152, 133, 17);
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
				btnClose_actionPerformed(e);
			}
		});
		btnCreateUser.setBounds(35, 210, 138, 27);
		contentPane.add(btnCreateUser);
		
		lblUserAlreadyExists = new JLabel("Este usuario ya existe!");
		lblUserAlreadyExists.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblUserAlreadyExists.setForeground(new Color(220, 20, 60));
		lblUserAlreadyExists.setBounds(35, 256, 191, 15);
		contentPane.add(lblUserAlreadyExists);
		
		checkbxisAdmin = new JCheckBox("Admin");
		checkbxisAdmin.setFont(new Font("Dialog", Font.BOLD, 14));
		checkbxisAdmin.setForeground(new Color(255, 255, 255));
		checkbxisAdmin.setBackground(new Color(0, 145, 202));
		checkbxisAdmin.setBounds(25, 177, 114, 25);
		contentPane.add(checkbxisAdmin);
		lblUserAlreadyExists.setVisible(false);
	}
	private void btnClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}