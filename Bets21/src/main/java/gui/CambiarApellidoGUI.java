package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.User;
import exceptions.UserDoesntExist;

public class CambiarApellidoGUI extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			CambiarApellidoGUI dialog = new CambiarApellidoGUI();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public CambiarApellidoGUI() {
		User u = MainGUI.getUserRegistered();
		BLFacade facade = MainGUI.getBusinessLogic();
		
		setBounds(100, 100, 364, 223);
		getContentPane().setLayout(null);
		contentPanel.setBounds(10, 0, 350, 175);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("APELLIDO :  ");
			lblNewLabel.setBounds(43, 34, 87, 31);
			contentPanel.add(lblNewLabel);
		}
		
		textField = new JTextField();
		textField.setBounds(140, 34, 96, 31);
		contentPanel.add(textField);
		textField.setColumns(10);
		{
			JButton okButton = new JButton("OK");
			okButton.setBounds(76, 109, 79, 31);
			contentPanel.add(okButton);
			okButton.setActionCommand("OK");
			getRootPane().setDefaultButton(okButton);
			okButton.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			    	facade.modifyUserApellido(u, textField.getText());
			    	
			    	try {
						MainGUI.setUserRegistered(facade.getUser(u.getDni()));
					} catch (UserDoesntExist e1) {
						e1.printStackTrace();
					}
			    	
			    	CambiarDatosGUI.UpdateApellido();
			    	
			    	jButtonClose_actionPerformed(e);
			    	
			    	
			    	
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
