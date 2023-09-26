package gui;

import java.awt.Color;
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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;

import businessLogic.BLFacade;
import domain.User;
import exceptions.UserDoesntExist;
import theme.Bets21Theme;


public class MainGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

    private static BLFacade appFacadeInterface;
	private static User userRegistered = null;
    
	public static User getUserRegistered() {
		if(userRegistered!=null)return userRegistered;
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
	private JPasswordField pwdIngreseSuContrasea;
	private JLabel lblDni;
	private JLabel lblPassword;
	private JLabel userError;
	private JLabel lblIniciarSesin;
	private JLabel logo;
	
	/**
	 * This is the default constructor
	 */
	public MainGUI() {
		super();
		setResizable(false);
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
		Bets21Theme.setup();
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
			jContentPane.setBackground(new Color(0, 145, 202));
			jContentPane.setLayout(null);
			
			login_panel = new JPanel();
			login_panel.setBackground(Color.WHITE);
			login_panel.setBounds(262, 0, 333, 306);
			jContentPane.add(login_panel);
			login_panel.setLayout(null);
			login_panel.add(getJButtonCreateUser());
			login_panel.add(getJButtonLogin());
			login_panel.add(getPanel());
			
			textField = new JTextField();
			textField.setColumns(10);
			textField.setBounds(20, 60, 200, 23);
			login_panel.add(textField);
			
			pwdIngreseSuContrasea = new JPasswordField();
			pwdIngreseSuContrasea.setBounds(20, 125, 200, 23);
			login_panel.add(pwdIngreseSuContrasea);
			
			lblDni = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.lblDni.text")); //$NON-NLS-1$ //$NON-NLS-2$
			lblDni.setBounds(20, 40, 102, 20);
			login_panel.add(lblDni);
			
			lblPassword = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.lblPassword.text")); //$NON-NLS-1$ //$NON-NLS-2$
			lblPassword.setBounds(20, 100, 123, 20);
			login_panel.add(lblPassword);
			login_panel.add(getUserError());
			login_panel.add(getLblIniciarSesin());
			jContentPane.add(getJButtonEvents());
			
			logo = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.label.text")); //$NON-NLS-1$ //$NON-NLS-2$
			ImageIcon icon = new ImageIcon(EventInfoPanel.class.getResource("/icons/bets21.png"));
			Image scaledIcon = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
			logo.setIcon(new ImageIcon(scaledIcon));
			logo.setBounds(34, 26, 200, 200);
			jContentPane.add(logo);
		}
		return jContentPane;
	}
	private JLabel getjLabelSelectOption() {
		if (jLabelSelectOption == null) {
			jLabelSelectOption = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("SelectOption"));
			jLabelSelectOption.setBounds(0, 0, 495, 30);
			jLabelSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return jLabelSelectOption;
	}
	private JRadioButton getRdbtnNewRadioButton() {
		if (rdbtnNewRadioButton == null) {
			rdbtnNewRadioButton = new JRadioButton("English");
			rdbtnNewRadioButton.setBorder(null);
			rdbtnNewRadioButton.setForeground(Color.DARK_GRAY);
			rdbtnNewRadioButton.setBackground(Color.WHITE);
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
			rdbtnNewRadioButton_1.setForeground(Color.DARK_GRAY);
			rdbtnNewRadioButton_1.setBackground(Color.WHITE);
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
			rdbtnNewRadioButton_2.setForeground(Color.DARK_GRAY);
			rdbtnNewRadioButton_2.setBackground(Color.WHITE);
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
			panel.setBackground(Color.WHITE);
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
			jButtonCreateUser.setForeground(Color.DARK_GRAY);
			jButtonCreateUser.setBackground(Color.WHITE);
			jButtonCreateUser.setBounds(170, 170, 140, 32);
			ImageIcon icon = new ImageIcon(EventInfoPanel.class.getResource("/icons/user-create.png"));
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
			jButtonLogin.setBounds(20, 170, 140, 32);
			jButtonLogin.setBackground(new Color(255, 255, 255));
			ImageIcon icon = new ImageIcon(EventInfoPanel.class.getResource("/icons/user-login.png"));
			Image scaledIcon = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			jButtonLogin.setIcon(new ImageIcon(scaledIcon));
			jButtonLogin.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e1) {
					BLFacade facade = MainGUI.getBusinessLogic();
					if(userRegistered!= null) {
						userError.setText("<html>Cierra sesión antes de iniciar <br>sesión con otro usuario</html>");
						userError.setVisible(true);
					}else {
						User user = null;
						try {
							user = facade.getUser(textField.getText());
							if(!user.checkCredentials(new String(pwdIngreseSuContrasea.getPassword()))) userError.setText("La contraseña es incorrecta");
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
					
				}
			});
		}
		return jButtonLogin;
	}
	
	private JButton getJButtonEvents() {
		if (jButtonEvents == null) {
			jButtonEvents = new JButton("Consultar Eventos");
			jButtonEvents.setBounds(34, 258, 200, 35);
			ImageIcon icon = new ImageIcon(EventInfoPanel.class.getResource("/icons/event-list-black.png"));
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
	private JLabel getUserError() {
		if (userError == null) {
			userError = new JLabel("");
			userError.setForeground(Color.DARK_GRAY);
			userError.setHorizontalAlignment(SwingConstants.CENTER);
			userError.setBounds(20, 211, 293, 30);
		}
		return userError;
	}
	private JLabel getLblIniciarSesin() {
		if (lblIniciarSesin == null) {
			lblIniciarSesin = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.lblIniciarSesin.text")); //$NON-NLS-1$ //$NON-NLS-2$
			lblIniciarSesin.setForeground(Color.DARK_GRAY);
		}
		return lblIniciarSesin;
	}
} // @jve:decl-index=0:visual-constraint="0,0"