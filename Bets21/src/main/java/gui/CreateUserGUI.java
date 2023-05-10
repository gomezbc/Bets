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
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;

import businessLogic.BLFacade;
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
	private JLabel lblRegistro;
	private JSeparator separator;
	private JSeparator separator_1;
	private JSeparator separator_2;
	private JSeparator separator_3;
	private JSeparator separator_4;

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
		try {
			FlatLaf.registerCustomDefaultsSource( "main.resources");
			UIManager.setLookAndFeel( new FlatLightLaf() );
		} catch( Exception ex ) {
		    System.err.println( "Failed to initialize LaF" );
		}
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
		username.setBorder(null);
		username.setBounds(37, 208, 220, 20);
		username.setColumns(10);
		contentPane.add(username);
		
		passwd = new JPasswordField();
		passwd.setBorder(null);
		passwd.setBounds(37, 253, 220, 20);
		contentPane.add(passwd);
		
		dni = new JTextField();
		dni.setBorder(null);
		dni.setBounds(37, 63, 220, 21);
		contentPane.add(dni);
		dni.setColumns(10);
		
		name = new JTextField();
		name.setBorder(null);
		name.setBounds(37, 110, 220, 21);
		contentPane.add(name);
		name.setColumns(10);
		
		surname = new JTextField();
		surname.setBorder(null);
		surname.setBounds(37, 160, 220, 21);
		contentPane.add(surname);
		surname.setColumns(10);
		
		JLabel lblDni = new JLabel("DNI");
		lblDni.setFont(new Font("Roboto", Font.PLAIN, 15));
		lblDni.setForeground(Color.DARK_GRAY);
		lblDni.setBounds(37, 43, 60, 17);
		contentPane.add(lblDni);
		
		JLabel lblName = new JLabel("NOMBRE");
		lblName.setFont(new Font("Roboto", Font.PLAIN, 15));
		lblName.setForeground(Color.DARK_GRAY);
		lblName.setBounds(37, 93, 96, 17);
		contentPane.add(lblName);
		
		JLabel lblSurname = new JLabel("APELLIDO");
		lblSurname.setFont(new Font("Roboto", Font.PLAIN, 15));
		lblSurname.setForeground(Color.DARK_GRAY);
		lblSurname.setBounds(37, 141, 96, 17);
		contentPane.add(lblSurname);
		
		JLabel lblUsername = new JLabel("NOMBRE DE USUARIO");
		lblUsername.setFont(new Font("Roboto", Font.PLAIN, 15));
		lblUsername.setForeground(Color.DARK_GRAY);
		lblUsername.setBounds(37, 190, 170, 17);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("CONTRASEÑA");
		lblPassword.setFont(new Font("Roboto", Font.PLAIN, 15));
		lblPassword.setForeground(Color.DARK_GRAY);
		lblPassword.setBounds(37, 234, 133, 20);
		contentPane.add(lblPassword);
		
		JButton btnCreateUser = new JButton("Crear Usuario");
		btnCreateUser.setBackground(Color.WHITE);
		btnCreateUser.setFont(new Font("Roboto", Font.BOLD, 15));
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
		btnCreateUser.setBounds(69, 313, 138, 27);
		contentPane.add(btnCreateUser);
		
		lblUserAlreadyExists = new JLabel("Este usuario ya existe!");
		lblUserAlreadyExists.setFont(new Font("Roboto", Font.PLAIN, 14));
		lblUserAlreadyExists.setForeground(new Color(220, 20, 60));
		lblUserAlreadyExists.setBounds(70, 345, 191, 15);
		contentPane.add(lblUserAlreadyExists);
		
		checkbxisAdmin = new JCheckBox("ADMIN");
		checkbxisAdmin.setFont(new Font("Roboto", Font.PLAIN, 15));
		checkbxisAdmin.setForeground(Color.DARK_GRAY);
		checkbxisAdmin.setBackground(Color.WHITE);
		checkbxisAdmin.setBounds(37, 280, 114, 25);
		contentPane.add(checkbxisAdmin);
		lblUserAlreadyExists.setVisible(false);
		
		lblRegistro = new JLabel("REGISTRO");
		lblRegistro.setFont(new Font("Roboto", Font.BOLD, 18));
		lblRegistro.setBounds(37, 12, 111, 17);
		contentPane.add(lblRegistro);
		
		separator_1 = new JSeparator();
		separator_1.setForeground(Color.DARK_GRAY);
		separator_1.setBounds(37, 131, 220, 1);
		contentPane.add(separator_1);
		
		separator = new JSeparator();
		separator.setForeground(Color.DARK_GRAY);
		separator.setBounds(37, 84, 220, 1);
		contentPane.add(separator);
		
		separator_2 = new JSeparator();
		separator_2.setForeground(Color.DARK_GRAY);
		separator_2.setBounds(37, 181, 220, 1);
		contentPane.add(separator_2);
		
		separator_3 = new JSeparator();
		separator_3.setForeground(Color.DARK_GRAY);
		separator_3.setBounds(37, 228, 220, 1);
		contentPane.add(separator_3);
		
		separator_4 = new JSeparator();
		separator_4.setForeground(Color.DARK_GRAY);
		separator_4.setBounds(37, 273, 220, 1);
		contentPane.add(separator_4);
	}
	private void btnClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}