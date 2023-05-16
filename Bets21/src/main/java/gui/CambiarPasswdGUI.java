package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.User;
import exceptions.UserDoesntExist;
import javax.swing.JPasswordField;

public class CambiarPasswdGUI extends JDialog {

	
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JPasswordField passwordField;
	private JLabel lblNewLabel_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			CambiarPasswdGUI dialog = new CambiarPasswdGUI();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public CambiarPasswdGUI() {
		User u = MainGUI.getUserRegistered();
		BLFacade facade = MainGUI.getBusinessLogic();
		
	    lblNewLabel_1 = new JLabel("Lo introducido no es correcto");
	    lblNewLabel_1.setForeground(Color.RED);
	    lblNewLabel_1.setBounds(10, 11, 214, 14);
	    lblNewLabel_1.setVisible(false);
	    contentPanel.add(lblNewLabel_1);
		
		setBounds(100, 100, 364, 223);
		getContentPane().setLayout(null);
		contentPanel.setBounds(10, 0, 350, 175);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("CONTRASEÑA :  ");
			lblNewLabel.setBounds(20, 34, 110, 31);
			contentPanel.add(lblNewLabel);
		}
		
		passwordField = new JPasswordField();
		passwordField.setBounds(140, 34, 96, 31);
		contentPanel.add(passwordField);
		passwordField.setColumns(10);
		{
			JButton okButton = new JButton("OK");
			okButton.setBounds(76, 109, 79, 31);
			contentPanel.add(okButton);
			okButton.setActionCommand("OK");
			getRootPane().setDefaultButton(okButton);
			okButton.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			    	
			    	if(String.valueOf(passwordField.getPassword()).trim().length()>0) {
			    		facade.modifyUserPasswd(u, String.valueOf(passwordField.getPassword()).trim());
			    		lblNewLabel_1.setVisible(false);
			    		passwordField.setText("");
			    		
			    	try {
						MainGUI.setUserRegistered(facade.getUser(u.getDni()));
					} catch (UserDoesntExist e1) {
						e1.printStackTrace();
					}
			    	
			    	
			    	 jButtonClose_actionPerformed(e);
			    	
			    	}else {
			    		lblNewLabel_1.setVisible(true);
			    	}
			    	
			    }
			});
			
		}
		{
			JButton cancelButton = new JButton("Cancel");
			cancelButton.setBounds(165, 109, 96, 31);
			contentPanel.add(cancelButton);
			cancelButton.setActionCommand("Cancel");
			cancelButton.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			        jButtonClose_actionPerformed(e);
			    }
			});
		}
	}

	
	
	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
		
	}
	
}
