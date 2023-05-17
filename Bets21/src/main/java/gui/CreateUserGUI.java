package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
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
import exceptions.UserAlreadyExist;
import theme.Bets21Theme;
import javax.swing.ImageIcon;

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
	private JLabel lblRegistro;

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
		Bets21Theme.setup();
		setResizable(false);
		setBackground(Color.WHITE);
		setTitle("Registrarse");
		setBounds(100, 100, 522, 430);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		username = new JTextField();
		username.setBounds(37, 208, 220, 20);
		username.setColumns(10);
		contentPane.add(username);
		
		passwd = new JPasswordField();
		passwd.setBounds(37, 253, 220, 20);
		contentPane.add(passwd);
		
		dni = new JTextField();
		dni.setBounds(37, 63, 220, 21);
		contentPane.add(dni);
		dni.setColumns(10);
		
		name = new JTextField();
		name.setBounds(37, 110, 220, 21);
		contentPane.add(name);
		name.setColumns(10);
		
		surname = new JTextField();
		surname.setBounds(37, 160, 220, 21);
		contentPane.add(surname);
		surname.setColumns(10);
		
		JLabel lblDni = new JLabel("DNI");
		lblDni.setBounds(37, 43, 60, 17);
		contentPane.add(lblDni);
		
		JLabel lblName = new JLabel("NOMBRE");
		lblName.setBounds(37, 93, 96, 17);
		contentPane.add(lblName);
		
		JLabel lblSurname = new JLabel("APELLIDO");
		lblSurname.setBounds(37, 141, 96, 17);
		contentPane.add(lblSurname);
		
		JLabel lblUsername = new JLabel("NOMBRE DE USUARIO");
		lblUsername.setBounds(37, 190, 170, 17);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("CONTRASEÑA");
		lblPassword.setBounds(37, 234, 133, 20);
		contentPane.add(lblPassword);
		
		JButton btnCreateUser = new JButton("Crear Usuario");
		ImageIcon icon = new ImageIcon(CreateUserGUI.class.getResource("/icons/create-user.png"));
		Image scaledIcon = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		btnCreateUser.setIcon(new ImageIcon(scaledIcon));
		btnCreateUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(username.getText().contentEquals("") || new String(passwd.getPassword()).contentEquals("") || dni.getText().contentEquals("") || name.getText().contentEquals("") || surname.getText().contentEquals("")) {
					lblUserAlreadyExists.setText("Rellena todos los campos");
					lblUserAlreadyExists.setVisible(true);
				}else {
					BLFacade facade = MainGUI.getBusinessLogic();
					lblUserAlreadyExists.setText("Este usuario ya existe!");
					lblUserAlreadyExists.setVisible(false);
					try {
						facade.createUser(username.getText(), new String(passwd.getPassword()), dni.getText(), name.getText(), surname.getText(), checkbxisAdmin.isSelected());
					} catch (UserAlreadyExist e1) {
						lblUserAlreadyExists.setVisible(true);
					}
					btnClose_actionPerformed(e);
				}
				
			}
		});
		btnCreateUser.setBounds(69, 313, 164, 27);
		contentPane.add(btnCreateUser);
		
		lblUserAlreadyExists = new JLabel("Este usuario ya existe!");
		lblUserAlreadyExists.setForeground(new Color(220, 20, 60));
		lblUserAlreadyExists.setBounds(70, 345, 191, 15);
		contentPane.add(lblUserAlreadyExists);
		
		checkbxisAdmin = new JCheckBox("ADMIN");
		checkbxisAdmin.setBounds(37, 280, 114, 25);
		contentPane.add(checkbxisAdmin);
		lblUserAlreadyExists.setVisible(false);
		
		lblRegistro = new JLabel("REGISTRO");
		lblRegistro.setFont(new Font("Segoe UI Semibold", Font.BOLD, 15));
		lblRegistro.setBounds(37, 12, 111, 17);
		contentPane.add(lblRegistro);
	}
	private void btnClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}