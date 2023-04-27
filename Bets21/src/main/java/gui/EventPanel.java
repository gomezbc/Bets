package gui;

import javax.swing.JPanel;

import domain.Event;
import java.io.File;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

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
		setLayout(null);
		
		if(ev.getDescription().contains("-")) {
			local = ev.getDescription().substring(0, ev.getDescription().indexOf("-"));
            visitante = ev.getDescription().substring(ev.getDescription().indexOf("-")+1);
            local = local.toLowerCase().trim();
            visitante = visitante.toLowerCase().trim();
		}
		
		lblDescription = new JLabel("");
		lblDescription.setBounds(277, 5, 377, 17);
		lblDescription.setText(ev.getDescription());
		add(lblDescription);
		
		lblDate = new JLabel("");
		lblDate.setBounds(240, 22, 209, 17);
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
		logoVisitante.setBounds(622, 5, 50, 50);
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
}
