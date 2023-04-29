package gui;

import javax.swing.JPanel;

import domain.Event;
import java.io.File;
import java.awt.Image;
import java.awt.Rectangle;
import org.apache.commons.lang3.StringUtils;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.Dimension;

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
	public EventPanel(Event ev) {
		jbInit(ev);
	}
	private void jbInit(Event ev) {
		setMinimumSize(new Dimension(740, 60));
		setLayout(null);
		
		if(ev.getDescription().contains("-")) {
			local = ev.getDescription().substring(0, ev.getDescription().indexOf("-"));
            visitante = ev.getDescription().substring(ev.getDescription().indexOf("-")+1);
            local = local.toLowerCase().trim();
            local = StringUtils.stripAccents(local);
            visitante = visitante.toLowerCase().trim();
            visitante = StringUtils.stripAccents(visitante);
		}
		
		lblDescription = new JLabel("");
		lblDescription.setBounds(300, 5, 377, 17);
		lblDescription.setText(ev.getDescription());
		add(lblDescription);
		
		lblDate = new JLabel("");
		lblDate.setBounds(271, 31, 209, 17);
		lblDate.setText(ev.getEventDate().toString());
		add(lblDate);
		
		logoLocal = new JLabel("");
		logoLocal.setBounds(62, 5, 50, 50);
		File localF = new File("icons/laliga/"+local+".png");
		ImageIcon icon = new ImageIcon("icons/laliga/"+local+".png");
		Image scaledIcon = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		logoLocal = new JLabel(new ImageIcon(scaledIcon));
		logoLocal.setBounds(62, 5, 50, 50);
		add(logoLocal);
		
		logoVisitante = new JLabel("");
		logoVisitante.setBounds(622, 5, 50, 50);
		File visitanteF = new File("icons/laliga/"+visitante+".png");
		icon = new ImageIcon("icons/laliga/"+visitante+".png");
		scaledIcon = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		logoVisitante = new JLabel(new ImageIcon(scaledIcon));
		logoVisitante.setBounds(638, 5, 50, 50);
		add(logoVisitante);
		
		lbllogoLiga = new JLabel("");
		lbllogoLiga.setBounds(450, 5, 81, 50);
		
		icon = new ImageIcon("icons/laliga/laliga.png");
		scaledIcon = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		logoVisitante = new JLabel(new ImageIcon(scaledIcon));
		add(lbllogoLiga);
		
		if(localF.isFile() && visitanteF.isFile()) {
//			icon = new ImageIcon("icons/laliga/betis.png");
//			scaledIcon = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
//			System.out.println("Encontrado");
//			logoVisitante = new JLabel(new ImageIcon(scaledIcon));
		}
		
	}
	
	public void updateBounds(int width, int height) {
//		System.out.println(width+","+height);
		this.setSize(width, height);
		System.out.println(this.getWidth()+","+this.getHeight());
		ImageIcon icon = new ImageIcon("icons/laliga/"+local+".png");
		Image scaledIcon = icon.getImage().getScaledInstance((int) (height*0.7), (int) (height*0.7), Image.SCALE_SMOOTH);
		logoLocal.setIcon(new ImageIcon(scaledIcon));
		logoLocal.setSize((int) (height*0.7), (int) (height*0.7));
		logoLocal.setBounds(new Rectangle(62, (int) (height*0.1), logoLocal.getWidth(), logoLocal.getHeight()));
		icon = new ImageIcon("icons/laliga/"+visitante+".png");
		scaledIcon = icon.getImage().getScaledInstance((int) (height*0.7), (int) (height*0.7), Image.SCALE_SMOOTH);
		logoVisitante.setIcon(new ImageIcon(scaledIcon));
		logoVisitante.setSize((int) (height*0.7), (int) (height*0.7));
		logoVisitante.setBounds(new Rectangle((int) (this.getWidth()*0.85), (int) (height*0.1), logoLocal.getWidth(), logoLocal.getHeight()));
	}
}
