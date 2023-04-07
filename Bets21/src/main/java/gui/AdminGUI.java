package gui;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

import domain.Event;


public class AdminGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnLogOut;


	/**
	 * Create the frame.
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
	
	private void jbInit() {
		setBackground(new Color(17, 110, 80));
		setTitle("Admin");
		setResizable(false);
		setMaximumSize(new Dimension(886, 612));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 886, 612);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(17, 110, 80));
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.setLayout(null);
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setForeground(new Color(222, 221, 218));
		tabbedPane.setBackground(new Color(56, 56, 56));
		tabbedPane.setBorder(null);
		tabbedPane.setBounds(12, 35, 850, 553);
		tabbedPane.setUI(new CustomTabbedPaneUI());
		
		contentPane.add(tabbedPane);
		
		JPanel createEvent = new CreateEventGUI();
		ImageIcon icon = new ImageIcon(AdminGUI.class.getResource("/event-creat.png"));
		Image scaledIcon = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		tabbedPane.addTab("Crear Evento", new ImageIcon(scaledIcon), createEvent);
		
		JPanel closeEvent = new CloseEventGUI();
		icon = new ImageIcon(AdminGUI.class.getResource("/close-event.png"));
		scaledIcon = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		tabbedPane.addTab("Cerrar Evento", new ImageIcon(scaledIcon), closeEvent, null);
		
		JPanel createQuestion = new CreateQuestionGUI(new Vector<Event>());
		icon = new ImageIcon(AdminGUI.class.getResource("/event-creat.png"));
		scaledIcon = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		tabbedPane.addTab(ResourceBundle.getBundle("Etiquetas").getString("CreateQuery"), new ImageIcon(scaledIcon), createQuestion, null);
		
		JPanel findQuestion = new FindQuestionsGUI();
		icon = new ImageIcon(AdminGUI.class.getResource("/event-list.png"));
		scaledIcon = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		tabbedPane.addTab(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"), new ImageIcon(scaledIcon), findQuestion, null);
		
		JPanel createForecas = new CreateForecastGUI();
		icon = new ImageIcon(AdminGUI.class.getResource("/close-event.png"));
		scaledIcon = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		tabbedPane.addTab("Crear Pronostico", new ImageIcon(scaledIcon), createForecas, null);
		
		JPanel listUsers = new ListUsersGUI();
		icon = new ImageIcon(AdminGUI.class.getResource("/users-list.png"));
		scaledIcon = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		tabbedPane.addTab("Listar Usuarios", new ImageIcon(scaledIcon), listUsers, null);
		
		
		btnLogOut = new JButton();
		btnLogOut.setBorderPainted(false);
		btnLogOut.setOpaque(false);
		btnLogOut.setBorder(null);
		btnLogOut.setBackground(new Color(17, 110, 80));
		btnLogOut.setToolTipText("Log Out");
		icon = new ImageIcon(AdminGUI.class.getResource("/exit.png"));
		scaledIcon = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		btnLogOut.setIcon(new ImageIcon(scaledIcon));
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});
		btnLogOut.setBounds(820, 0, 35, 35);
		contentPane.add(btnLogOut);
	}
	
	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
	
	protected class CustomTabbedPaneUI extends BasicTabbedPaneUI {
	    
	    @Override
	    protected void installDefaults() {
	        super.installDefaults();
	        tabPane.setBackground(new Color(17, 110, 80)); // establecer el color de fondo de la pestaña
	        tabPane.setFocusable(false); // quitar el focus de la pestaña
	        tabPane.setForeground(lightHighlight);
	        tabPane.setOpaque(false); // establecer la opacidad de la pestaña a verdadero
	    }
	}
}