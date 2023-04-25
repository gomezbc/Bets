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

public class UserGUI3 extends JFrame {

	private static JPanel contentPane;
	private static JButton btnLogOut;
	private static JPanel displayFrame;
	private static JButton btnHome;
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
		    }
		};
		this.addComponentListener(componentListener);
		
		displayFrame = new EventsListGUI();
		displayFrame.setBounds(29, 49, 826, 541);
		contentPane.add(displayFrame);
		
		btnHome = new JButton("Home");
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserGUI3.updateFrame(new EventsListGUI());
			}
		});
		btnHome.setBorder(new EmptyBorder(3,3,3,3));
		btnHome.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				btnHome.setBorder(new LineBorder(new Color(26, 95, 180), 2));
			}
			@Override
			public void focusLost(FocusEvent e) {
				btnHome.setBorder(new EmptyBorder(3,3,3,3));
			}
		});
		btnHome.setBackground(Color.WHITE);
		btnHome.setBounds(29, 8, 103, 27);
		contentPane.add(btnHome);
	}
	
	private void jButtonClose_actionPerformed(ActionEvent e) {
			this.setVisible(false);
	}
	
	public static void updateFrame(JPanel panel) {
		contentPane.removeAll();
		contentPane.add(btnLogOut);
		contentPane.add(btnHome);
		displayFrame = panel;
		displayFrame.setBounds(29, 49, 826, 541);
		contentPane.add(displayFrame);
		contentPane.revalidate();
		contentPane.repaint();
	}
}
