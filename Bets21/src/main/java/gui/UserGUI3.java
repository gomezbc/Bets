package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.border.LineBorder;

import domain.User;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JMenuBar;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UserGUI3 extends JFrame {

	private static JPanel contentPane;
	private static JButton btnLogOut;
	private static JPanel displayFrame;
	private JButton btnHome;
	private static JLabel lblUser;
	private static JMenuBar menuBar;
	private JButton btnApuestasRealizadas;
	private static JLabel lblLogoUser;
	/**
	 * Launch the application.
	 */
	public UserGUI3() {
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
		setTitle("User");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
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
		ImageIcon icon = new ImageIcon("icons/exit.png");
		Image scaledIcon = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		
		btnLogOut.setIcon(new ImageIcon(scaledIcon));
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});
		btnLogOut.setBounds(820, 0, 35, 35);
		contentPane.add(btnLogOut);
		
		//Actualiza el tamaño de los componentes respecto al frame
		ComponentListener componentListener = new ComponentAdapter() {
		    @Override
		    public void componentResized(ComponentEvent e) {
		        // Actualizar el tamaño del JTabbedPane
		        int nuevoAncho = e.getComponent().getWidth();
		        int nuevoAlto = e.getComponent().getHeight();
		        btnLogOut.setBounds(new Rectangle(nuevoAncho-40,0,btnLogOut.getWidth(),btnLogOut.getHeight()));
		        btnLogOut.setAlignmentY(nuevoAncho-40);
		        displayFrame.setBounds(new Rectangle(0, 70, nuevoAncho, contentPane.getHeight()-70));
		        if(displayFrame instanceof EventsListGUI) ((EventsListGUI) displayFrame).renderEventsTable();
		    }
		};
		this.addComponentListener(componentListener);
		
		displayFrame = new EventsListGUI();
		displayFrame.setBounds(0, 70, 886, 541);
		contentPane.add(displayFrame);
		
		lblUser = new JLabel("");
		lblUser.setForeground(Color.WHITE);
		lblUser.setFont(new Font("Dialog", Font.BOLD, 15));
		lblUser.setBounds(60, 10, 485, 25);
		User u = MainGUI.getUserRegistered();
		if(u!=null) lblUser.setText("User: "+u.getUsername()+"     Saldo: "+u.getSaldo());
		contentPane.add(lblUser);
		
		menuBar = new JMenuBar();
		menuBar.setBackground(new Color(0, 145, 202));
		menuBar.setBounds(20, 42, 243, 30);
		contentPane.add(menuBar);
		
		btnHome = new JButton("     Eventos    ");
		btnHome.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnHome.setBorder(new LineBorder(new Color(26, 95, 180), 2));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnHome.setBorder(new EmptyBorder(3,3,3,3));
			}
		});
		menuBar.add(btnHome);
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserGUI3.updateFrame(new EventsListGUI());
			}
		});
		btnHome.setBorder(new EmptyBorder(3, 3, 3, 3));
		btnHome.setBackground(Color.WHITE);
		
		btnApuestasRealizadas = new JButton("Apuestas Realizadas");
		btnApuestasRealizadas.setBorder(new EmptyBorder(3, 3, 3, 3));
		btnApuestasRealizadas.setBackground(Color.WHITE);
		btnHome.setBorder(new EmptyBorder(3,3,3,3));
		btnApuestasRealizadas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserGUI3.updateFrame(new ListUserBetsGUI());
			}
		});
		btnApuestasRealizadas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnApuestasRealizadas.setBorder(new LineBorder(new Color(26, 95, 180), 2));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnApuestasRealizadas.setBorder(new EmptyBorder(3,3,3,3));
			}
		});
		menuBar.add(btnApuestasRealizadas);
		
		icon = new ImageIcon("icons/user.png");
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
		displayFrame.setBounds(0, 70, 886, 541);
        displayFrame.setBounds(new Rectangle(0, 70, displayFrame.getWidth(), contentPane.getHeight()-70));
		contentPane.add(displayFrame);
		contentPane.add(lblLogoUser);
		contentPane.revalidate();
		contentPane.repaint();
		User u = MainGUI.getUserRegistered();
		if(u!=null) lblUser.setText("User: "+u.getUsername()+"     Saldo: "+u.getSaldo());
		contentPane.add(lblUser);
	}
}
