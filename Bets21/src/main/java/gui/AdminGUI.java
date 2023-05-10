package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;

import domain.User;

public class AdminGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private static JPanel contentPane;
	private static JButton btnLogOut;
	private static JPanel displayFrame;
	private JButton btnAñadirPreguntasPronosticos;
	private static JLabel lblUser;
	private static JMenuBar menuBar;
	private JButton btnCerrarEventos;
	private static JLabel lblLogoUser;
	private JButton btnListUser;
	private JButton btnCrearEvento;

	
	private static String currentTab = null;
	private static JButton btnHelp;

	/**
	 * Launch the application.
	 */
	public AdminGUI() {
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
	public void jbInit() {
		try {
			FlatLaf.registerCustomDefaultsSource( "main.resources");
			UIManager.setLookAndFeel( new FlatLightLaf() );
		} catch( Exception ex ) {
		    System.err.println( "Failed to initialize LaF" );
		}
		setTitle("Admin");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 966, 704);
		setResizable(false);
		setMinimumSize(new Dimension(886, 652));
		setBounds(100, 100, 886, 632);
		contentPane = new JPanel();
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(0, 146, 202));
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		
		btnLogOut = new JButton();
		btnLogOut.setBorderPainted(false);
		btnLogOut.setOpaque(false);
		btnLogOut.setBorder(null);
		btnLogOut.setBackground(new Color(17, 110, 80));
		btnLogOut.setToolTipText("Log Out");
		ImageIcon icon = new ImageIcon(EventInfoPanel.class.getResource("/icons/exit.png"));
		Image scaledIcon = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		
		btnLogOut.setIcon(new ImageIcon(scaledIcon));
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});
		btnLogOut.setBounds(839, 5, 35, 35);
		contentPane.add(btnLogOut);
		
		//Actualiza el tamaño de los componentes respecto al frame
		ComponentListener componentListener = new ComponentAdapter() {
		    @Override
		    public void componentResized(ComponentEvent e) {
		        // Actualizar el tamaño del JTabbedPane
		        int nuevoAncho = e.getComponent().getWidth();
		        int nuevoAlto = e.getComponent().getHeight();
		        displayFrame.setBounds(new Rectangle(0, 70, nuevoAncho, contentPane.getHeight()-70));
		        if(displayFrame instanceof EventsListGUI) ((EventsListGUI) displayFrame).renderEventsTable();
		    }
		};
		this.addComponentListener(componentListener);
		
		displayFrame = new CreateEventGUI();
		displayFrame.setBounds(0, 70, 886, 541);
		contentPane.add(displayFrame);
		
		lblUser = new JLabel("");
		lblUser.setForeground(Color.WHITE);
		lblUser.setFont(new Font("Dialog", Font.BOLD, 15));
		lblUser.setBounds(60, 10, 485, 25);
		User u = MainGUI.getUserRegistered();
		if(u!=null) lblUser.setText("User: "+u.getUsername());
		contentPane.add(lblUser);
		
		
		
		btnHelp = new JButton("");
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JDialog a = new AyudaAdminGUI();
				a.setVisible(true);
			}
		});
		icon = new ImageIcon(EventInfoPanel.class.getResource("/icons/help.png"));
		scaledIcon = icon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
		btnHelp.setIcon(new ImageIcon(scaledIcon));
		btnHelp.setFont(new Font("Dialog", Font.BOLD, 11));
		btnHelp.setBorder(new EmptyBorder(0, 0, 0, 0));
		btnHelp.setBackground(new Color(0, 145, 202));
		btnHelp.setBounds(794, 5, 35, 35);
		contentPane.add(btnHelp);
		
		
		
		
		
		menuBar = new JMenuBar();
		menuBar.setBackground(new Color(0, 145, 202));
		menuBar.setBounds(40, 42, 640, 30);
		contentPane.add(menuBar);
		
		btnAñadirPreguntasPronosticos = new JButton("Añadir Preguntas y Pronosticos");
		btnAñadirPreguntasPronosticos.setFont(new Font("Roboto", Font.BOLD, 14));
		btnAñadirPreguntasPronosticos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAñadirPreguntasPronosticos.setBorder(new LineBorder(new Color(26, 95, 180), 2));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnAñadirPreguntasPronosticos.setBorder(new EmptyBorder(3,3,3,3));
			}
		});
		
		btnCrearEvento = new JButton("   Crear Eventos    ");
		btnCrearEvento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminGUI.setCurrentTab("CreateEventGUI");
				AdminGUI.updateFrame(new CreateEventGUI());
			}
		});
		btnCrearEvento.setFont(new Font("Roboto", Font.BOLD, 14));
		btnCrearEvento.setBorder(new EmptyBorder(3, 3, 3, 3));
		btnCrearEvento.setBackground(Color.WHITE);
		menuBar.add(btnCrearEvento);
		menuBar.add(btnAñadirPreguntasPronosticos);
		btnAñadirPreguntasPronosticos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminGUI.setCurrentTab("EventInfoAdminGUI");
				AdminGUI.updateFrame(new EventsListGUI());
			}
		});
		btnAñadirPreguntasPronosticos.setBorder(new EmptyBorder(3, 3, 3, 3));
		btnAñadirPreguntasPronosticos.setBackground(Color.WHITE);
		
		btnCerrarEventos = new JButton("   Cerrar Eventos   ");
		btnCerrarEventos.setFont(new Font("Roboto", Font.BOLD, 14));
		btnCerrarEventos.setBorder(new EmptyBorder(3, 3, 3, 3));
		btnCerrarEventos.setBackground(Color.WHITE);
		btnAñadirPreguntasPronosticos.setBorder(new EmptyBorder(3,3,3,3));
		btnCerrarEventos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminGUI.setCurrentTab("CloseEventGUI");
				AdminGUI.updateFrame(new EventsListGUI());
			}
		});
		btnCerrarEventos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnCerrarEventos.setBorder(new LineBorder(new Color(26, 95, 180), 2));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnCerrarEventos.setBorder(new EmptyBorder(3,3,3,3));
			}
		});
		menuBar.add(btnCerrarEventos);
		
		btnListUser = new JButton("   Lista de Usuarios   ");
		btnListUser.setFont(new Font("Roboto", Font.BOLD, 14));
		btnListUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnListUser.setBorder(new LineBorder(new Color(26, 95, 180), 2));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnListUser.setBorder(new EmptyBorder(3,3,3,3));
			}
		});
		btnListUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminGUI.setCurrentTab("ListUsersGUI");
				AdminGUI.updateFrame(new ListUsersGUI());
			}
		});
		btnListUser.setBorder(new EmptyBorder(3, 3, 3, 3));
		btnListUser.setBackground(Color.WHITE);
		menuBar.add(btnListUser);
		
		icon = new ImageIcon(EventInfoPanel.class.getResource("/icons/user.png"));
		scaledIcon = icon.getImage().getScaledInstance(30, 25, Image.SCALE_SMOOTH);
		lblLogoUser = new JLabel(new ImageIcon(scaledIcon));
		lblLogoUser.setBounds(20, 8, 30, 25);
		contentPane.add(lblLogoUser);
	}
	
	
	private void jButtonClose_actionPerformed(ActionEvent e) {
			this.setVisible(false);
			MainGUI.setUserRegistered(null);
	}
	
	public static void updateFrame(JPanel panel) {
		contentPane.removeAll();
		contentPane.add(btnLogOut);
		contentPane.add(menuBar);
		displayFrame = panel;
        displayFrame.setBounds(new Rectangle(0, 70, contentPane.getWidth(), contentPane.getHeight()-70));
		contentPane.add(displayFrame);
		contentPane.add(lblLogoUser);
		User u = MainGUI.getUserRegistered();
		if(u!=null) lblUser.setText("User: "+u.getUsername());
		contentPane.add(lblUser);
		contentPane.add(btnHelp);
		contentPane.revalidate();
		contentPane.repaint();
	}

	public static String getCurrentTab() {
		return currentTab;
	}

	public static void setCurrentTab(String currentTab) {
		AdminGUI.currentTab = currentTab;
	}
}
