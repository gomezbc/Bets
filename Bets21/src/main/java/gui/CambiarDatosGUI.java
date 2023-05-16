package gui;

import javax.swing.JPanel;

import domain.User;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import java.awt.Color;

public class CambiarDatosGUI extends JPanel {
	
	private static JLabel lbl_CDNI = new JLabel("");
	private static JLabel lbl_CNombre = new JLabel("");
	private static JLabel lbl_CApellido = new JLabel(" ");
	private static JLabel lbl_CUsuario = new JLabel(" ");
	private JLabel logo;
	
	private static final long serialVersionUID = 1L;
	

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
		
		JLabel lbl_DNI = new JLabel("DNI : ");
		lbl_DNI.setFont(new Font("Dialog", Font.PLAIN, 13));
		lbl_DNI.setBounds(237, 50, 41, 14);
		add(lbl_DNI);
		
		JLabel lblNewLabel = new JLabel("NOMBRE : ");
		lblNewLabel.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblNewLabel.setBounds(237, 91, 109, 24);
		add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("APELLIDO : ");
		lblNewLabel_1.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(233, 147, 88, 14);
		add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("CONTRASEÑA : ");
		lblNewLabel_2.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblNewLabel_2.setBounds(69, 293, 109, 14);
		add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("NOMBRE DE USUARIO:");
		lblNewLabel_3.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblNewLabel_3.setBounds(69, 237, 156, 14);
		add(lblNewLabel_3);
		
		User userRegistered = MainGUI.getUserRegistered();
		lbl_CDNI.setFont(new Font("Dialog", Font.BOLD, 13));
		
		
		
		lbl_CDNI.setText(userRegistered.getDni());
		lbl_CDNI.setBounds(291, 50, 129, 14);
		add(lbl_CDNI);
		
		JLabel lblNewLabel_5 = new JLabel("********");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_5.setBounds(199, 294, 147, 14);
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
		lbl_CUsuario.setBounds(233, 237, 165, 14);
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
		btnNewButton_2.setBounds(487, 225, 175, 40);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JDialog a = new CambiarUsuarioGUI();
				a.setVisible(true);
			
			}
			
		});
		add(btnNewButton_2);
		
		
		
		JButton btnNewButton_3 = new JButton("Cambiar Contraseña");
		btnNewButton_3.setBounds(487, 281, 175, 40);
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JDialog a = new CambiarPasswdGUI();
				a.setVisible(true);
			
			}
			
		});
		add(btnNewButton_3);
		
		
		
		logo = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.label.text")); //$NON-NLS-1$ //$NON-NLS-2$
		logo.setForeground(new Color(0, 0, 0));
		ImageIcon icon = new ImageIcon(EventInfoPanel.class.getResource("/icons/user-create.png"));
		Image scaledIcon = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		logo.setIcon(new ImageIcon(scaledIcon));
		logo.setBounds(53, 50, 98, 136);
		add(logo);
		
		
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

