package gui;

import java.awt.Image;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.User;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Color;

public class ListUsersGUI extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable tableUsers = new JTable();
	private JTable tableAdmins = new JTable();
	private JScrollPane scrollPaneUser = new JScrollPane();
	private JScrollPane scrollPaneAdmin = new JScrollPane();
	private DefaultTableModel tableModelUser;
	private DefaultTableModel tableModelAdmin;
	private JTable selectedTable;
	private DefaultTableModel selectedModel;

	private String[] columnNames = new String[] {
			"Dni","Nombre","Apellidos","Usuario","Saldo"
	};
	private JButton btnEliminarUsuario;
	private JLabel lblResBtn = new JLabel("");

	/**
	 * Launch the application.
	 */
	public  ListUsersGUI(){
		try
		{
			jbInit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public void jbInit() {
		lblResBtn.setVisible(false);
		setBounds(100, 100, 863, 609);
		this.setBorder(new EmptyBorder(5, 5, 5, 5));

		this.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(12, 0, 826, 480);
		this.add(tabbedPane);
		
		scrollPaneUser.setViewportView(tableUsers);
		scrollPaneAdmin.setViewportView(tableAdmins);
		tabbedPane.addTab("Users", null, scrollPaneUser, null);
		tabbedPane.addTab("Admins", null, scrollPaneAdmin, null);
		
		tableModelUser = new DefaultTableModel(null, columnNames);
		tableUsers.setModel(tableModelUser);
		
		tableModelAdmin = new DefaultTableModel(null, columnNames);
		tableAdmins.setModel(tableModelAdmin);
		
		updateTable();
		
		btnEliminarUsuario = new JButton("Eliminar Usuario");
		btnEliminarUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblResBtn.setText("");
				int index = tabbedPane.getSelectedIndex();
				switch(index) {
					case(0): {
						selectedTable = tableUsers;
						selectedModel = tableModelUser;
						break;
					}
					case(1): {
						 selectedTable = tableAdmins;
				         selectedModel = tableModelAdmin;
				         break;
					}
				}
				int i = selectedTable.getSelectedRow();
				String dni = (String) selectedModel.getValueAt(i, 0);
				BLFacade facade = MainGUI.getBusinessLogic();
				boolean ret = facade.removeUser(dni);
				lblResBtn.setVisible(true);
				if(ret) {
					lblResBtn.setForeground(new Color(51, 51, 51));
					lblResBtn.setText("Usuario eliminado");
				}else {
					lblResBtn.setForeground(new Color(246, 97, 81));
					lblResBtn.setText("No se ha podido eliminar");
				}
				updateTable();
			}
		});
		ImageIcon icon = new ImageIcon(AdminGUI.class.getResource("/users-remove.png"));
		Image scaledIcon = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		btnEliminarUsuario.setIcon(new ImageIcon(scaledIcon));
		btnEliminarUsuario.setBounds(333, 487, 180, 27);
		add(btnEliminarUsuario);
		
		lblResBtn.setForeground(new Color(246, 97, 81));
		lblResBtn.setBounds(531, 492, 186, 17);
		add(lblResBtn);
	}
	
	public void updateTable() {
		setBorder(new LineBorder(new Color(17, 110, 80), 2, true));
		tableModelUser.setRowCount(0);
		tableModelAdmin.setRowCount(0);
		BLFacade facade=MainGUI.getBusinessLogic();
		Vector<User> users = new Vector<User>();
		users = facade.getAllUsers();
		
		for(User u: users) {
			Vector<Object> row = new Vector<Object>();
			row.add(u.getDni());
			row.add(u.getName());
			row.add(u.getApellido());
			row.add(u.getUsername());
			row.add(u.getSaldo());
			if(u.isAdmin()) tableModelAdmin.addRow(row);
			else tableModelUser.addRow(row);
		}
	}
}