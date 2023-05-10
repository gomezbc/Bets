package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Bet;
import domain.User;
import exceptions.UserDoesntExist;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;

public class AumentarApuestaGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JButton btnNewButton_1;
	private JLabel lblNewLabel_1 = new JLabel("Dinero Añadido");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AumentarApuestaGUI frame = new AumentarApuestaGUI();
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
	public AumentarApuestaGUI() {
		lblNewLabel_1.setVisible(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 386, 276);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Dinero ha añadir : ");
		lblNewLabel.setFont(new Font("Dialog", Font.PLAIN, 13));
		lblNewLabel.setBounds(44, 26, 123, 38);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(161, 27, 112, 38);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("CANCELAR");
		btnNewButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            jButtonClose_actionPerformed(e);
	        }
	    });
		btnNewButton.setBounds(70, 145, 102, 38);
		contentPane.add(btnNewButton);
		
		
		
		btnNewButton_1 = new JButton("ACEPTAR");
		btnNewButton_1.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				int BetNumber = ListUserBetsGUI.getBetNumber2();
				int fila = ListUserBetsGUI.getFila();
				User u = MainGUI.getUserRegistered();
				
				
			
				try {
					if(Float.parseFloat(textField.getText()) > 0) {
					Bet b = facade.modifyBet(Float.parseFloat(textField.getText()), BetNumber, u );
					lblNewLabel_1.setVisible(true);
					DefaultTableModel tableModelBets2 = ListUserBetsGUI.getTableModelBets();
					tableModelBets2.setValueAt(b.getBetMoney(), fila, 5);
					tableModelBets2.setValueAt(Double.toString(b.getBetMoney()), fila, 6);
					
					
					u.setSaldo(u.getSaldo() - Float.parseFloat(textField.getText())); 
					UserGUI.updateSaldo();
					
					} else {
						System.out.println("error");
					}
					
				} catch (UserDoesntExist e1) {
					System.out.println("error");
				} catch  (NumberFormatException e2) {
					System.out.println("error");
					
				}
	        	System.out.println(u.getSaldo());
				
				
				try {
					Thread.sleep(5*100);
				} catch (InterruptedException e1) {
				}
	            jButtonClose_actionPerformed(e);
	        }
	    });
		btnNewButton_1.setBounds(182, 145, 112, 38);
		contentPane.add(btnNewButton_1);
		
		
		lblNewLabel_1.setForeground(new Color(0, 0, 255));
		lblNewLabel_1.setBounds(44, 99, 102, 22);
		contentPane.add(lblNewLabel_1);
		
		
		
		
	}
	
	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
		
	}
}
