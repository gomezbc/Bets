package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Software Engineering teachers
 */
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import businessLogic.BLFacade;
import domain.User;
import exceptions.UserDoesntExist;

import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.border.LineBorder;


public class MainGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

    private static BLFacade appFacadeInterface;
	private static User userRegistered;
    
	public static User getUserRegistered() {
		BLFacade facade = MainGUI.getBusinessLogic();
		try {
			return facade.getUser(userRegistered.getDni());
		} catch (UserDoesntExist e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static void setUserRegistered(User userRegistered) {
		MainGUI.userRegistered = userRegistered;
	}
	
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}
	 
	public static void setBussinessLogic (BLFacade afi){
		appFacadeInterface=afi;
	}
	protected JLabel jLabelSelectOption;
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnNewRadioButton_1;
	private JRadioButton rdbtnNewRadioButton_2;
	private JPanel panel;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton jButtonCreateUser;
	private JButton jButtonLogin;
	private JButton jButtonEvents;
	private JPanel login_panel;
	private JTextField textField;
	private JPasswordField passwordField;
	private JLabel lblDni;
	private JLabel lblPassword;
	private JLabel lblRegistrateAhora;
	private JLabel userError;
	
	/**
	 * This is the default constructor
	 */
	public MainGUI() {
		super();
		setBackground(new Color(255, 255, 255));
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					//if (ConfigXML.getInstance().isBusinessLogicLocal()) facade.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					System.out.println("Error: "+e1.toString()+" , probably problems with Business Logic or Database");
				}
				System.exit(1);
			}
		});

		initialize();
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		// this.setSize(271, 295);
		this.setSize(593, 336);
		this.setContentPane(getJContentPane());
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainTitle"));
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setBackground(new Color(238, 238, 238));
			jContentPane.setLayout(null);
			
			login_panel = new JPanel();
			login_panel.setBackground(new Color(0, 145, 202));
			login_panel.setBounds(262, 0, 333, 306);
			jContentPane.add(login_panel);
			login_panel.setLayout(null);
			login_panel.add(getJButtonCreateUser());
			login_panel.add(getJButtonLogin());
			login_panel.add(getPanel());
			
			textField = new JTextField();
			textField.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					textField.setBorder(new LineBorder(new Color(26, 95, 180), 2, false));
				}
				@Override
				public void focusLost(FocusEvent e) {
					textField.setBorder(null);
				}
			});
			textField.setColumns(10);
			textField.setBounds(128, 45, 180, 23);
			login_panel.add(textField);
			
			passwordField = new JPasswordField();
			passwordField.setBounds(128, 89, 180, 23);
			passwordField.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					passwordField.setBorder(new LineBorder(new Color(26, 95, 180), 2, false));
				}
				@Override
				public void focusLost(FocusEvent e) {
					passwordField.setBorder(null);
				}
			});
			login_panel.add(passwordField);
			
			lblDni = new JLabel("Dni:");
			lblDni.setFont(new Font("Dialog", Font.BOLD, 14));
			lblDni.setForeground(Color.WHITE);
			lblDni.setBounds(42, 48, 102, 17);
			login_panel.add(lblDni);
			
			lblPassword = new JLabel("Contraseña: ");
			lblPassword.setFont(new Font("Dialog", Font.BOLD, 14));
			lblPassword.setForeground(Color.WHITE);
			lblPassword.setBounds(42, 93, 102, 17);
			login_panel.add(lblPassword);
			login_panel.add(getLblRegistrateAhora());
			login_panel.add(getUserError());
			jContentPane.add(getJButtonEvents());
		}
		return jContentPane;
	}
	private JLabel getjLabelSelectOption() {
		if (jLabelSelectOption == null) {
			jLabelSelectOption = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("SelectOption"));
			jLabelSelectOption.setBounds(0, 0, 495, 30);
			jLabelSelectOption.setFont(new Font("Tahoma", Font.BOLD, 13));
			jLabelSelectOption.setForeground(Color.BLACK);
			jLabelSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return jLabelSelectOption;
	}
	private JRadioButton getRdbtnNewRadioButton() {
		if (rdbtnNewRadioButton == null) {
			rdbtnNewRadioButton = new JRadioButton("English");
			rdbtnNewRadioButton.setBorder(null);
			rdbtnNewRadioButton.setForeground(Color.WHITE);
			rdbtnNewRadioButton.setBackground(new Color(0, 145, 202));
			rdbtnNewRadioButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Locale.setDefault(new Locale("en"));
					System.out.println("Locale: "+Locale.getDefault());
					redibujar();				}
			});
			buttonGroup.add(rdbtnNewRadioButton);
		}
		return rdbtnNewRadioButton;
	}
	private JRadioButton getRdbtnNewRadioButton_1() {
		if (rdbtnNewRadioButton_1 == null) {
			rdbtnNewRadioButton_1 = new JRadioButton("Euskara");
			rdbtnNewRadioButton_1.setBorder(null);
			rdbtnNewRadioButton_1.setForeground(Color.WHITE);
			rdbtnNewRadioButton_1.setBackground(new Color(0, 145, 202));
			rdbtnNewRadioButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Locale.setDefault(new Locale("eus"));
					System.out.println("Locale: "+Locale.getDefault());
					redibujar();				}
			});
			buttonGroup.add(rdbtnNewRadioButton_1);
		}
		return rdbtnNewRadioButton_1;
	}
	
	
	private JRadioButton getRdbtnNewRadioButton_2() {
		if (rdbtnNewRadioButton_2 == null) {
			rdbtnNewRadioButton_2 = new JRadioButton("Castellano");
			rdbtnNewRadioButton_2.setBorder(null);
			rdbtnNewRadioButton_2.setForeground(Color.WHITE);
			rdbtnNewRadioButton_2.setBackground(new Color(0, 145, 202));
			rdbtnNewRadioButton_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Locale.setDefault(new Locale("es"));
					System.out.println("Locale: "+Locale.getDefault());
					redibujar();
				}
			});
			buttonGroup.add(rdbtnNewRadioButton_2);
		}
		return rdbtnNewRadioButton_2;
	}
	
	
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setBounds(12, 253, 285, 52);
			panel.setForeground(new Color(255, 255, 255));
			panel.setBorder(null);
			panel.setBackground(new Color(0, 145, 202));
			panel.add(getRdbtnNewRadioButton_1());
			panel.add(getRdbtnNewRadioButton_2());
			panel.add(getRdbtnNewRadioButton());
		}
		return panel;
	}
	
	private void redibujar() {
		jLabelSelectOption.setText(ResourceBundle.getBundle("Etiquetas").getString("Seleccionar opción:"));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainTitle"));
	}
	
	private JButton getJButtonCreateUser() {
		if (jButtonCreateUser == null) {
			jButtonCreateUser = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.btnCreateUser.text")); //$NON-NLS-1$ //$NON-NLS-2$
			jButtonCreateUser.setBorder(null);
			jButtonCreateUser.setForeground(new Color(10, 51, 100));
			jButtonCreateUser.setBackground(new Color(0, 145, 202));
			jButtonCreateUser.setBounds(155, 209, 142, 32);
			ImageIcon icon = new ImageIcon("icons/user-create.png");
			Image scaledIcon = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			jButtonCreateUser.setIcon(new ImageIcon(scaledIcon));
			jButtonCreateUser.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFrame a = new CreateUserGUI();
					a.setVisible(true);
				}
			});
		}
		return jButtonCreateUser;
	}
	
	
	
	
	private JButton getJButtonLogin() {
		if (jButtonLogin == null) {
			jButtonLogin = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.jButtonLogin.text")); //$NON-NLS-1$ //$NON-NLS-2$
			jButtonLogin.setBounds(86, 145, 190, 32);
			jButtonLogin.setBackground(new Color(255, 255, 255));
			ImageIcon icon = new ImageIcon("icons/user-login.png");
			Image scaledIcon = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			jButtonLogin.setIcon(new ImageIcon(scaledIcon));
			jButtonLogin.addActionListener(new ActionListener() {
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
		}
		return jButtonLogin;
	}
	
	private JButton getJButtonEvents() {
		if (jButtonEvents == null) {
			jButtonEvents = new JButton("Consultar Eventos");
			jButtonEvents.setBackground(new Color(238, 238, 238));
			jButtonEvents.setBounds(34, 258, 200, 35);
			ImageIcon icon = new ImageIcon("icons/event-list-black.png");
			Image scaledIcon = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			jButtonEvents.setIcon(new ImageIcon(scaledIcon));
			jButtonEvents.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFrame a = new NoUserGUI();
					a.setVisible(true);
				}
			});
		}
		return jButtonEvents;
	}
	private JLabel getLblRegistrateAhora() {
		if (lblRegistrateAhora == null) {
			lblRegistrateAhora = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.lblRegistrateAhora.text")); //$NON-NLS-1$ //$NON-NLS-2$
			lblRegistrateAhora.setFont(new Font("Dialog", Font.BOLD, 13));
			lblRegistrateAhora.setForeground(Color.WHITE);
			lblRegistrateAhora.setBounds(42, 217, 111, 17);
		}
		return lblRegistrateAhora;
	}
	private JLabel getUserError() {
		if (userError == null) {
			userError = new JLabel("");
			userError.setForeground(Color.WHITE);
			userError.setHorizontalAlignment(SwingConstants.CENTER);
			userError.setBounds(28, 180, 293, 17);
		}
		return userError;
	}
} // @jve:decl-index=0:visual-constraint="0,0"