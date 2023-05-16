package gui;

import javax.swing.JPanel;

import domain.User;
import exceptions.UserDoesntExist;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import businessLogic.BLFacade;

public class CambiarDatosGUI extends JPanel {
	
	private static JLabel lbl_CDNI = new JLabel("");
	private static JLabel lbl_CNombre = new JLabel("");
	private static JLabel lbl_CApellido = new JLabel(" ");
	private static JLabel lbl_CUsuario = new JLabel(" ");
	private JLabel logo;
	
	private static final long serialVersionUID = 1L;
	private JLabel lblTarjeta;
	private JTextField textFieldTarjeta;
	private JButton btnTarjeta;
	private JLabel lblWarning;
	

	/**
	 * Create the panel.
	 */
	public CambiarDatosGUI() {

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
		setLayout(null);
		User u = MainGUI.getUserRegistered();
		BLFacade facade = MainGUI.getBusinessLogic();
		JLabel lbl_DNI = new JLabel("DNI : ");
		lbl_DNI.setFont(new Font("Dialog", Font.PLAIN, 13));
		lbl_DNI.setBounds(237, 50, 41, 14);
		add(lbl_DNI);
		
		JLabel lblName = new JLabel("NOMBRE : ");
		lblName.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblName.setBounds(237, 91, 109, 24);
		add(lblName);
		
		JLabel lblSurname = new JLabel("APELLIDO : ");
		lblSurname.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblSurname.setBounds(233, 147, 88, 14);
		add(lblSurname);
		
		JLabel lblPasswd = new JLabel("CONTRASEÑA : ");
		lblPasswd.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblPasswd.setBounds(80, 263, 109, 14);
		add(lblPasswd);
		
		JLabel lblUser = new JLabel("NOMBRE DE USUARIO:");
		lblUser.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblUser.setBounds(80, 211, 156, 14);
		add(lblUser);
		
		User userRegistered = MainGUI.getUserRegistered();
		lbl_CDNI.setFont(new Font("Dialog", Font.BOLD, 13));
		
		
		
		lbl_CDNI.setText(userRegistered.getDni());
		lbl_CDNI.setBounds(291, 50, 129, 14);
		add(lbl_CDNI);
		
		JLabel lblNewLabel_5 = new JLabel("********");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_5.setBounds(253, 264, 147, 14);
		add(lblNewLabel_5);
		lbl_CNombre.setFont(new Font("Dialog", Font.BOLD, 13));
		
		lbl_CNombre.setText(userRegistered.getName());
		lbl_CNombre.setBounds(319, 96, 190, 14);
		add(lbl_CNombre);
		lbl_CApellido.setFont(new Font("Dialog", Font.BOLD, 13));
		
		lbl_CApellido.setText(userRegistered.getApellido());
		lbl_CApellido.setBounds(319, 147, 136, 14);
		add(lbl_CApellido);
		lbl_CUsuario.setFont(new Font("Dialog", Font.BOLD, 13));
		
		lbl_CUsuario.setText(userRegistered.getUsername());
		lbl_CUsuario.setBounds(248, 211, 165, 14);
		add(lbl_CUsuario);
		
		
		JButton btnNewButton = new JButton("Cambiar Nombre");
		btnNewButton.setBounds(487, 84, 175, 40);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JDialog a = new CambiarNombreGUI();
				a.setVisible(true);
			
			}
			
		});
	
		
		add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Cambiar Apellido");
		btnNewButton_1.setBounds(487, 135, 175, 40);
		add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JDialog a = new CambiarApellidoGUI();
				a.setVisible(true);
			
			}
			
		});
		
		JButton btnNewButton_2 = new JButton("Cambiar Usuario");
		btnNewButton_2.setBounds(487, 198, 175, 40);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JDialog a = new CambiarUsuarioGUI();
				a.setVisible(true);
			
			}
			
		});
		add(btnNewButton_2);
		
		
		
		JButton btnNewButton_3 = new JButton("Cambiar Contraseña");
		btnNewButton_3.setBounds(487, 250, 175, 40);
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JDialog a = new CambiarPasswdGUI();
				a.setVisible(true);
			
			}
			
		});
		add(btnNewButton_3);
		
		
		
		logo = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.label.text")); //$NON-NLS-1$ //$NON-NLS-2$
		logo.setForeground(new Color(0, 0, 0));
		ImageIcon icon = new ImageIcon(EventInfoPanel.class.getResource("/icons/user-icon.png"));
		Image scaledIcon = icon.getImage().getScaledInstance(98, 120, Image.SCALE_SMOOTH);
		logo.setIcon(new ImageIcon(scaledIcon));
		logo.setBounds(100, 35, 98, 136);
		add(logo);
		
		
		
		lblTarjeta = new JLabel("TARJETA DE CREDITO:");
		lblTarjeta.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblTarjeta.setBounds(80, 321, 156, 14);
		add(lblTarjeta);
		
		textFieldTarjeta = new JTextField();
		if(u.getCreditCard() != null)textFieldTarjeta.setText(u.getCreditCard().toString());
		textFieldTarjeta.setBounds(237, 318, 257, 21);
		add(textFieldTarjeta);
		textFieldTarjeta.setColumns(10);
		
		btnTarjeta = new JButton("Cambiar Tarjeta");
		btnTarjeta.setEnabled(false);
		btnTarjeta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					lblWarning.setVisible(false);
					Long newCard = Long.parseLong(textFieldTarjeta.getText().toString().trim());
					facade.modifyUserCreditCard(u.getDni(), newCard);
					MainGUI.setUserRegistered(facade.getUser(u.getDni()));
				}catch (NumberFormatException eN) {
					lblWarning.setVisible(true);
				} catch (UserDoesntExist e1) {

				}
				
				}
		});
		btnTarjeta.setBounds(253, 354, 175, 40);
		add(btnTarjeta);
		
		textFieldTarjeta.getDocument().addDocumentListener(new DocumentListener() {
			  public void changedUpdate(DocumentEvent e) {
				  check();
			  }
			  public void removeUpdate(DocumentEvent e) {
				  check();
			  }
			  public void insertUpdate(DocumentEvent e) {
				  check();
			  }
			  public void check() {
				if(textFieldTarjeta.getText().length()!=16) {
					textFieldTarjeta.setBorder(new LineBorder(new Color(255,153,153), 1));
					btnTarjeta.setEnabled(false);
				}else {
					textFieldTarjeta.setBorder(new LineBorder(new Color(153,204,102), 1));
					btnTarjeta.setEnabled(true);
				}
			  }
			});
		
		
		lblWarning = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CambiarDatosGUI.lblAsegurateQueSolamente.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblWarning.setForeground(new Color(220, 20, 60));
		lblWarning.setBounds(201, 406, 294, 17);
		add(lblWarning);
		lblWarning.setVisible(false);
	}
	
	
	public static void UpdateName() {
		User userRegistered = MainGUI.getUserRegistered();
		lbl_CNombre.setText(userRegistered.getName());
	}
	
	
	public static void UpdateApellido() {
		User userRegistered = MainGUI.getUserRegistered();
		lbl_CApellido.setText(userRegistered.getApellido());
	}
	
	
	public static void UpdateUsuario() {
		User userRegistered = MainGUI.getUserRegistered();
		lbl_CUsuario.setText(userRegistered.getUsername());
	}
}

