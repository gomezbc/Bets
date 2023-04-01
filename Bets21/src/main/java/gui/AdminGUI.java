package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domain.Event;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.awt.event.ActionEvent;

public class AdminGUI extends JFrame {

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
	public AdminGUI() {
		setTitle("Administrador");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		JButton jButtonQueryQueries = new JButton();
		jButtonQueryQueries.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new FindQuestionsGUI();
				a.setVisible(true);
			}
		});
		jButtonQueryQueries.setText("Consultar Preguntas");
		jButtonQueryQueries.setBounds(228, 100, 194, 52);
		contentPane.add(jButtonQueryQueries);
		
		
		
		
		
		JButton jButtonCreateQuery = new JButton();
		jButtonCreateQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new CreateQuestionGUI(new Vector<Event>());
				a.setVisible(true);
			}
		});
		jButtonCreateQuery.setText("Crear Pregunta");
		jButtonCreateQuery.setBounds(12, 100, 194, 52);
		contentPane.add(jButtonCreateQuery);
		
		
		
		JLabel jLabelSelectOption = new JLabel("Selecciona una opcion");
		jLabelSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelSelectOption.setForeground(Color.BLACK);
		jLabelSelectOption.setFont(new Font("Dialog", Font.BOLD, 13));
		jLabelSelectOption.setBounds(-35, 7, 495, 30);
		contentPane.add(jLabelSelectOption);
		
		
		
		JButton btnClose = new JButton("Cerrar");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnClose_actionPerformed(e);
			}
		});
		
		
		btnClose.setBounds(173, 230, 105, 27);
		contentPane.add(btnClose);
		
		
		
		
		
		JButton btnNewButton = new JButton("Crear Evento");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new CreateEvent();
				a.setVisible(true);
			}
			
		});
		btnNewButton.setBounds(12, 40, 194, 52);
		contentPane.add(btnNewButton);
		
		
		
		JButton btnNewButton_1 = new JButton("Crear Pronostico");
		btnNewButton_1.setBounds(12, 160, 194, 52);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new CreateForecast();
				a.setVisible(true);
			}
		});
		contentPane.add(btnNewButton_1);
		
		JButton btnCerrarEvento = new JButton("Cerrar Evento");
		btnCerrarEvento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame a = new CloseEventGUI();
				a.setVisible(true);
			}
		});
		btnCerrarEvento.setBounds(228, 40, 194, 52);
		contentPane.add(btnCerrarEvento);
		
	}
	
	
	private void btnClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
