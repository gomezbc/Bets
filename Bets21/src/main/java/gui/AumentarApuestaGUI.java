package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.Bet;
import domain.User;
import exceptions.BetDoesntExist;
import exceptions.UserDoesntExist;
import theme.Bets21Theme;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;

public class AumentarApuestaGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JButton btnNewButton_1;
	private JLabel lblNewLabel_1 = new JLabel("Dinero Añadido");
	private JLabel lblNewLabel_2;


	public AumentarApuestaGUI() {
		JBinit();
	}

	/**
	 * Create the frame.
	 */
	public void JBinit() {
		Bets21Theme.setup();
		lblNewLabel_1.setVisible(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
		btnNewButton.setBounds(134, 181, 112, 38);
		contentPane.add(btnNewButton);
		
		
		
		btnNewButton_1 = new JButton("AUMENTAR");
		btnNewButton_1.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				int BetNumber = ListUserBetsGUI.getBetNumber2();
				int fila = ListUserBetsGUI.getFila();
				User u = MainGUI.getUserRegistered();
				lblNewLabel_1.setVisible(false);
				
			
				try {
					if(Float.parseFloat(textField.getText()) > 0) {
					Bet b = facade.modifyBet(Float.parseFloat(textField.getText()), BetNumber, u.getDni() );
					DefaultTableModel tableModelBets2 = ListUserBetsGUI.getTableModelBets();
					tableModelBets2.setValueAt(b.getBetMoney(), fila, 5);
					tableModelBets2.setValueAt(Double.toString(b.getBetMoney()), fila, 6);
					lblNewLabel_1.setVisible(true);
					
					
					MainGUI.setUserRegistered(facade.getUser(u.getDni()));
					UserGUI.updateSaldo();
					
					jButtonClose_actionPerformed(e);
					
					} else {
						
						lblNewLabel_2.setVisible(true);
						
					}
					
				} catch (UserDoesntExist e1) {
					System.out.println("error");
				} catch  (NumberFormatException e2) {
					System.out.println("error");
				} catch (BetDoesntExist e3) {
					
				}
	        	System.out.println(u.getSaldo());
				
	            
	        }
	    });
		btnNewButton_1.setBounds(55, 132, 123, 38);
		contentPane.add(btnNewButton_1);
		
		
		lblNewLabel_1.setForeground(new Color(0, 0, 255));
		lblNewLabel_1.setBounds(240, 92, 102, 22);
		contentPane.add(lblNewLabel_1);
		
		JButton btnNewButton_2 = new JButton("DISMINUIR");
		btnNewButton_2.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				int BetNumber = ListUserBetsGUI.getBetNumber2();
				int fila = ListUserBetsGUI.getFila();
				User u = MainGUI.getUserRegistered();
				
				
			
				try {
					if(Float.parseFloat(textField.getText()) > 0) {
					Bet b = facade.modifyBet(-(Float.parseFloat(textField.getText())), BetNumber, u.getDni() );
					lblNewLabel_1.setVisible(true);
					DefaultTableModel tableModelBets2 = ListUserBetsGUI.getTableModelBets();
					tableModelBets2.setValueAt(b.getBetMoney(), fila, 5);
					tableModelBets2.setValueAt(Double.toString(b.getBetMoney()), fila, 6);
					
					
					MainGUI.setUserRegistered(facade.getUser(u.getDni()));
					UserGUI.updateSaldo();
					
					
					jButtonClose_actionPerformed(e);
					
					} else {
						lblNewLabel_2.setVisible(true);
					}
					
				} catch (UserDoesntExist e1) {
					System.out.println("error");
				} catch  (NumberFormatException e2) {
					System.out.println("error");
				} catch (BetDoesntExist e3) {
					
				}
	        	System.out.println(u.getSaldo());
				
			
	            
	        }
	    });
		btnNewButton_2.setBounds(188, 132, 123, 38);
		contentPane.add(btnNewButton_2);
		
		
		lblNewLabel_2 = new JLabel("No se pudo modificar la apuesta.");
		lblNewLabel_2.setForeground(new Color(25, 25, 112));
		lblNewLabel_2.setBounds(44, 96, 244, 14);
		lblNewLabel_2.setVisible(false);
		contentPane.add(lblNewLabel_2);
		
		
		
		
	}
	
	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
		
	}
}
