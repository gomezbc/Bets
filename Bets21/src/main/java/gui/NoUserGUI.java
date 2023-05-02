package gui;

import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JMenuBar;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NoUserGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private static JPanel contentPane;
	private static JButton btnLogOut;
	private static JPanel displayFrame;
	private JButton btnHome;
	private static JMenuBar menuBar;
	private static JLabel lblLogoUser;
	/**
	 * Launch the application.
	 */
	public NoUserGUI() {
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
		setBounds(100, 100, 914, 680);
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
		ImageIcon icon = new ImageIcon(EventPanel.class.getResource("/icons/exit.png"));
		Image scaledIcon = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		
		btnLogOut.setIcon(new ImageIcon(scaledIcon));
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});
		btnLogOut.setBounds(810, 8, 35, 35);
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

		menuBar = new JMenuBar();
		menuBar.setBackground(new Color(0, 145, 202));
		menuBar.setBounds(20, 42, 340, 30);
		contentPane.add(menuBar);
		
		btnHome = new JButton("     Eventos    ");
		btnHome.setFont(new Font("Roboto", Font.BOLD, 14));
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
				NoUserGUI.updateFrame(new EventsListGUI());
			}
		});
		btnHome.setBorder(new EmptyBorder(3, 3, 3, 3));
		btnHome.setBackground(Color.WHITE);
		btnHome.setBorder(new EmptyBorder(3,3,3,3));
		
		icon = new ImageIcon(EventPanel.class.getResource("/icons/user.png"));
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
		contentPane.revalidate();
		contentPane.repaint();
	}
}
