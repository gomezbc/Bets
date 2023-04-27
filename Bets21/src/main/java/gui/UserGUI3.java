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
		setResizable(true);
		setMinimumSize(new Dimension(886, 632));
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
		        displayFrame.setBounds(new Rectangle(0, 50, nuevoAncho, contentPane.getHeight()-50));
		        if(displayFrame instanceof EventsListGUI) ((EventsListGUI) displayFrame).renderEventsTable();
		    }
		};
		this.addComponentListener(componentListener);
		
		displayFrame = new EventsListGUI();
		displayFrame.setBounds(0, 50, 886, 541);
		contentPane.add(displayFrame);
		
		lblUser = new JLabel("");
		lblUser.setForeground(Color.WHITE);
		lblUser.setFont(new Font("Dialog", Font.BOLD, 13));
		lblUser.setBounds(29, 0, 485, 17);
		User u = MainGUI.getUserRegistered();
		if(u!=null) lblUser.setText("User: "+u.getUsername()+"     Saldo: "+u.getSaldo());
		contentPane.add(lblUser);
		
		menuBar = new JMenuBar();
		menuBar.setBackground(new Color(0, 145, 202));
		menuBar.setBounds(30, 22, 243, 30);
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
		btnHome.setBorder(new EmptyBorder(3,3,3,3));
		btnHome.setBackground(Color.WHITE);
		
		btnApuestasRealizadas = new JButton("Apuestas Realizadas");
		btnApuestasRealizadas.setBorder(null);
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
	}
	
	private void jButtonClose_actionPerformed(ActionEvent e) {
			this.setVisible(false);
	}
	
	public static void updateFrame(JPanel panel) {
		contentPane.removeAll();
		contentPane.add(btnLogOut);
		contentPane.add(menuBar);
		displayFrame = panel;
		displayFrame.setBounds(29, 49, 826, 541);
		contentPane.add(displayFrame);
		contentPane.revalidate();
		contentPane.repaint();
		User u = MainGUI.getUserRegistered();
		if(u!=null) lblUser.setText("User: "+u.getUsername()+"     Saldo: "+u.getSaldo());
		contentPane.add(lblUser);
	}
}
