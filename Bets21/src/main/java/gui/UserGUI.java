package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
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
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

public class UserGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnLogOut;
	private JPanel findQuestion = new FindQuestionsGUI();
	private JPanel createBet = new CreateBetGUI();
	private JPanel AddSaldoGUI = new AddSaldoGUI();
	private JPanel listUserBets = new ListUserBetsGUI();
	private JFrame frame;

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
		contentPane.setBackground(new Color(223, 234, 232));
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.setLayout(null);

		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setForeground(new Color(222, 221, 218));
		tabbedPane.setBackground(new Color(56, 56, 56));
		tabbedPane.setBorder(null);
		tabbedPane.setBounds(12, 35, this.getWidth()-20, this.getHeight()-60); 
		tabbedPane.setUI(new CustomTabbedPaneUI());
		
		contentPane.add(tabbedPane);
		
		ImageIcon icon = new ImageIcon(UserGUI.class.getResource("/event-list.png"));
		Image scaledIcon = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		tabbedPane.addTab(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"), new ImageIcon(scaledIcon), findQuestion, null);
		
		icon = new ImageIcon(UserGUI.class.getResource("/chips-bet.png"));
		scaledIcon = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		tabbedPane.addTab("Apostar", new ImageIcon(scaledIcon), createBet);
		
		icon = new ImageIcon(UserGUI.class.getResource("/chip-exchange.png"));
		scaledIcon = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		tabbedPane.addTab("Añadir Saldo", new ImageIcon(scaledIcon), AddSaldoGUI);
		
		icon = new ImageIcon(UserGUI.class.getResource("/bets-saldo.png"));
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
		icon = new ImageIcon(UserGUI.class.getResource("/exit.png"));
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
		        tabbedPane.setSize(nuevoAncho-20, nuevoAlto-30);
		        btnLogOut.setBounds(new Rectangle(nuevoAncho-40,0,btnLogOut.getWidth(),btnLogOut.getHeight()));
		        btnLogOut.setAlignmentY(nuevoAncho-40);
		    }
		};
		this.addComponentListener(componentListener);
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