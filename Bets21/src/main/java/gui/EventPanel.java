package gui;

import javax.swing.JPanel;

import domain.Event;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class EventPanel extends JPanel {
	private JLabel lblDescription;
	private JLabel lblDate;
	private JLabel logoLocal;
	private JLabel logoVisitante;

	/**
	 * Create the panel.
	 */
	public EventPanel(Event ev) {
		jbInit(ev);
	}
	private void jbInit(Event ev) {
		setLayout(null);
		
		lblDescription = new JLabel("");
		lblDescription.setBounds(199, 12, 377, 17);
		lblDescription.setText(ev.getDescription());
		add(lblDescription);
		
		lblDate = new JLabel("");
		lblDate.setBounds(289, 31, 209, 17);
		lblDate.setText(ev.getEventDate().toString());
		add(lblDate);
		
		logoLocal = new JLabel("");
		logoLocal.setBounds(62, 5, 50, 50);
		ImageIcon icon = new ImageIcon("icons/laliga/osasuna.png");
		Image scaledIcon = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		logoLocal = new JLabel(new ImageIcon(scaledIcon));
		logoLocal.setBounds(62, 5, 50, 50);
		add(logoLocal);
		
		logoVisitante = new JLabel("");
		logoVisitante.setBounds(622, 5, 50, 50);
		icon = new ImageIcon("icons/laliga/barcelona.png");
		scaledIcon = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		logoVisitante = new JLabel(new ImageIcon(scaledIcon));
		logoVisitante.setBounds(622, 5, 50, 50);
		add(logoVisitante);
	}
}
