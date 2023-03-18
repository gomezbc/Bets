package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;

public class UserGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserGUI frame = new UserGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UserGUI() {
		setTitle("Usuario");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnClose = new JButton("Cerrar");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnClose_actionPerformed(e);
			}
		});
		btnClose.setBounds(165, 191, 105, 27);
		contentPane.add(btnClose);
		
		JButton jButtonQueryQueries = new JButton();
		jButtonQueryQueries.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new FindQuestionsGUI();
				a.setVisible(true);
			}
		});
		jButtonQueryQueries.setText("Consultar Preguntas");
		jButtonQueryQueries.setBounds(113, 64, 213, 52);
		contentPane.add(jButtonQueryQueries);
		
		JLabel jLabelSelectOption = new JLabel("Selecciona una opcion");
		jLabelSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelSelectOption.setForeground(Color.BLACK);
		jLabelSelectOption.setFont(new Font("Dialog", Font.BOLD, 13));
		jLabelSelectOption.setBounds(-30, 25, 495, 30);
		contentPane.add(jLabelSelectOption);
	}
	private void btnClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
