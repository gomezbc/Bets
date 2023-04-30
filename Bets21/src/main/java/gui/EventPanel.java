package gui;

import javax.swing.JPanel;

import domain.Event;
import java.net.URL;
import java.awt.Image;
import java.awt.Rectangle;
import org.apache.commons.lang3.StringUtils;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.Font;

public class EventPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JLabel lblDescription;
	private JLabel lblDate;
	private JLabel logoLocal;
	private JLabel logoVisitante;
	private String local;
	private String visitante;
	private JLabel lbllogoLiga;

	/**
	 * Create the panel.
	 */
	public EventPanel(Event ev, int width, int height) {
		jbInit(ev, width, height);
	}
	
	public EventPanel(Event ev) {
		jbInit(ev, 806, 66);
	}
	
	private void jbInit(Event ev, int width, int height) {
		setMinimumSize(new Dimension(740, 60));
		setPreferredSize(new Dimension(806, 66));
		setLayout(null);
		setSize(width, height);
		
		if(ev.getDescription().contains("-")) {
			local = ev.getDescription().substring(0, ev.getDescription().indexOf("-"));
            visitante = ev.getDescription().substring(ev.getDescription().indexOf("-")+1);
            local = local.toLowerCase().trim();
            local = StringUtils.stripAccents(local);
            visitante = visitante.toLowerCase().trim();
            visitante = StringUtils.stripAccents(visitante);
		}
		
		lblDescription = new JLabel("");
		lblDescription.setFont(new Font("Roboto", Font.BOLD, 14));
		lblDescription.setBounds(365, 5, 377, 17);
		lblDescription.setText(ev.getDescription());
		add(lblDescription);
		
		lblDate = new JLabel("");
		lblDate.setFont(new Font("Roboto", Font.BOLD, 14));
		lblDate.setBounds(302, 38, 209, 17);
		lblDate.setText(ev.getEventDate().toString());
		add(lblDate);
		
		logoLocal = new JLabel("");
		logoLocal.setBounds(62, 5, (int) (height*0.7), (int) (height*0.7));
		URL localF = null;
		try {
			localF = EventPanel.class.getResource("/icons/laliga/"+local+".png");
			ImageIcon icon = new ImageIcon(localF);
			Image scaledIcon = icon.getImage().getScaledInstance((int) (height*0.7), (int) (height*0.7), Image.SCALE_SMOOTH);
			logoLocal.setIcon(new ImageIcon(scaledIcon));
			logoLocal.setSize((int) (height*0.7), (int) (height*0.7));
			logoLocal.setBounds(new Rectangle(62, (int) (height*0.1), logoLocal.getWidth(), logoLocal.getHeight()));
		}catch (Exception eL) {

		}
		
		add(logoLocal);
		
		logoVisitante = new JLabel("");
		logoVisitante.setBounds(622, 5, (int) (height*0.7), (int) (height*0.7));
		URL visitanteF = null;
		try {
			visitanteF = EventPanel.class.getResource("/icons/laliga/"+visitante+".png");
			ImageIcon icon = new ImageIcon(visitanteF);
			Image scaledIcon = icon.getImage().getScaledInstance((int) (height*0.7), (int) (height*0.7), Image.SCALE_SMOOTH);
			logoVisitante.setIcon(new ImageIcon(scaledIcon));
			logoVisitante.setSize((int) (height*0.7), (int) (height*0.7));
			logoVisitante.setBounds(new Rectangle((int) (this.getWidth()*0.85), (int) (height*0.1), logoVisitante.getWidth(), logoVisitante.getHeight()));
		}catch (Exception eV) {

		}
		add(logoVisitante);
		
		if(localF!=null && visitanteF!=null) {
			lbllogoLiga = new JLabel("");
			ImageIcon icon = new ImageIcon(EventPanel.class.getResource("/icons/laliga/laliga.png"));
			Image scaledIcon = icon.getImage().getScaledInstance(50, 55, Image.SCALE_SMOOTH);
			lbllogoLiga.setIcon(new ImageIcon(scaledIcon));
			lbllogoLiga.setBounds(302, 5, 55, 17);
			add(lbllogoLiga);
		}
	}
	
	public void updateBounds(int width, int height) {
		this.setSize(width, height);
		ImageIcon icon = new ImageIcon(EventPanel.class.getResource("/icons/laliga/"+local+".png"));
		Image scaledIcon = icon.getImage().getScaledInstance((int) (height*0.7), (int) (height*0.7), Image.SCALE_SMOOTH);
		logoLocal.setIcon(new ImageIcon(scaledIcon));
		logoLocal.setSize((int) (height*0.7), (int) (height*0.7));
		logoLocal.setBounds(new Rectangle(62, (int) (height*0.1), logoLocal.getWidth(), logoLocal.getHeight()));
		
		icon = new ImageIcon(EventPanel.class.getResource("/icons/laliga/"+visitante+".png"));
		scaledIcon = icon.getImage().getScaledInstance((int) (height*0.7), (int) (height*0.7), Image.SCALE_SMOOTH);
		logoVisitante.setIcon(new ImageIcon(scaledIcon));
		logoVisitante.setSize((int) (height*0.7), (int) (height*0.7));
		logoVisitante.setBounds(new Rectangle((int) (this.getWidth()*0.85), (int) (height*0.1), logoLocal.getWidth(), logoLocal.getHeight()));
	}
}
