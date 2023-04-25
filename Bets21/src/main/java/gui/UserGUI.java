package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

public class UserGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnLogOut;
	private JPanel findQuestion = new EventsGUI();
	private JPanel createBet = new CreateBetGUI();
	private JPanel AddSaldoGUI = new AddSaldoGUI();
	private JPanel listUserBets = new ListUserBetsGUI();
	private static JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

	/**
	 * Launch the application.
	 */
	public UserGUI() {
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
	public void jbInit() throws Exception {
		setTitle("User");
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(true);
		setMinimumSize(new Dimension(886, 632));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 886, 632);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 146, 202));
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.setLayout(null);

		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setForeground(new Color(222, 221, 218));
		tabbedPane.setBackground(new Color(56, 56, 56));
		tabbedPane.setBorder(null);
		tabbedPane.setBounds(15, 35, this.getWidth()-40, this.getHeight()-60); 
	    tabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
		tabbedPane.setUI(new CustomTabbedPaneUI());
		UIManager.put("TabbedPane.selected", new Color(146, 154, 171));	
		UIManager.put("TabbedPane.borderHightlightColor", Color.TRANSLUCENT);
//		UIManager.put("TabbedPane.highlight", Color.RED);//Se actualiza cunado hago log out y log in
		UIManager.put("TabbedPane.contentBorderInsets", new Insets(0, 0, 0, 0));
		SwingUtilities.updateComponentTreeUI(tabbedPane);
		
		contentPane.add(tabbedPane);
		
		ImageIcon icon = new ImageIcon("icons/event-list.png");
		Image scaledIcon = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		tabbedPane.addTab(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"), new ImageIcon(scaledIcon), findQuestion, null);
		
		icon = new ImageIcon("icons/chips-bet.png");
		scaledIcon = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		tabbedPane.addTab("Apostar", new ImageIcon(scaledIcon), createBet);
		
		icon = new ImageIcon("icons/chip-exchange.png");
		scaledIcon = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		tabbedPane.addTab("Añadir Saldo", new ImageIcon(scaledIcon), AddSaldoGUI);
		
		icon = new ImageIcon("icons/bets-saldo.png");
		scaledIcon = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		tabbedPane.addTab("Apuestas", new ImageIcon(scaledIcon), listUserBets);
		
		tabbedPane.addChangeListener(new ChangeListener() {
		    public void stateChanged(ChangeEvent e) {
		    	Integer i = tabbedPane.getSelectedIndex();
		    	switch(i) {
		    	case 2:
		    		((AddSaldoGUI) tabbedPane.getSelectedComponent()).updateFrame();;
		    		break;
		    	case 3:
		    		((ListUserBetsGUI) tabbedPane.getSelectedComponent()).updateTable();
		    		System.out.print("upadte");
		    		break;
		    	}
		    }
		});
		
		btnLogOut = new JButton();
		btnLogOut.setBorderPainted(false);
		btnLogOut.setOpaque(false);
		btnLogOut.setBorder(null);
		btnLogOut.setBackground(new Color(17, 110, 80));
		btnLogOut.setToolTipText("Log Out");
		icon = new ImageIcon("icons/exit.png");
		scaledIcon = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		
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
		        tabbedPane.setSize(nuevoAncho-40, nuevoAlto-75);
		        btnLogOut.setBounds(new Rectangle(nuevoAncho-40,0,btnLogOut.getWidth(),btnLogOut.getHeight()));
		        btnLogOut.setAlignmentY(nuevoAncho-40);
		    }
		};
		this.addComponentListener(componentListener);
	}
	
	public static void updateTab(int index, String title, ImageIcon icon, JPanel panel) {
		tabbedPane.removeTabAt(tabbedPane.getSelectedIndex());
		tabbedPane.addTab(title, icon, panel);
	}
	
	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
	
	protected class CustomTabbedPaneUI extends BasicTabbedPaneUI {
	    
	    @Override
	    protected void installDefaults() {
	        super.installDefaults();
	        tabPane.setBackground(new Color(57, 62, 70));; // establecer el color de fondo de la pestaña
	        tabPane.setFocusable(true);
	        tabPane.setForeground(new Color(238, 238, 238));
	        tabPane.setOpaque(false); // establecer la opacidad de la pestaña a verdadero
	        UIManager.put("TabbedPane.selected", Color.red);
	    }
	}
}