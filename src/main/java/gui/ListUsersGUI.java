package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.User;
import theme.Bets21Theme;

public class ListUsersGUI extends JPanel {

	private static final long serialVersionUID = 1L;
	public static final String tabbedPaneFont = "Roboto";
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
		Bets21Theme.setup();
		
		lblResBtn.setVisible(false);
		this.setLayout(null);
		this.setSize(new Dimension(886, 541));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font(tabbedPaneFont, Font.PLAIN, 14));
		tabbedPane.setBounds(11, 0, 860, 480);
		this.add(tabbedPane);
		scrollPaneUser.setFont(new Font(tabbedPaneFont, Font.PLAIN, 14));
		
		scrollPaneUser.setViewportView(tableUsers);
		scrollPaneAdmin.setFont(new Font(tabbedPaneFont, Font.PLAIN, 14));
		scrollPaneAdmin.setViewportView(tableAdmins);
		tabbedPane.addTab("Users", null, scrollPaneUser, null);
		tabbedPane.addTab("Admins", null, scrollPaneAdmin, null);
		
		tableModelUser = new DefaultTableModel(null, columnNames);
		tableUsers.setModel(tableModelUser);
		tableUsers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		tableModelAdmin = new DefaultTableModel(null, columnNames);
		tableAdmins.setModel(tableModelAdmin);
		tableAdmins.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		updateTable();
		
		btnEliminarUsuario = new JButton("Eliminar Usuario");
		btnEliminarUsuario.setFont(new Font(tabbedPaneFont, Font.BOLD, 14));
		btnEliminarUsuario.setBackground(Color.WHITE);
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
				boolean ret = facade.deleteUserByDni(dni);
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
		ImageIcon icon = new ImageIcon(EventInfoPanel.class.getResource("/icons/users-remove.png"));
		Image scaledIcon = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		btnEliminarUsuario.setIcon(new ImageIcon(scaledIcon));
		btnEliminarUsuario.setBounds(333, 487, 180, 30);
		add(btnEliminarUsuario);
		
		lblResBtn.setForeground(new Color(246, 97, 81));
		lblResBtn.setBounds(531, 492, 186, 17);
		add(lblResBtn);
	}
	
	public void updateTable() {
		tableModelUser.setDataVector(null, columnNames);
		tableModelAdmin.setDataVector(null, columnNames);
		BLFacade facade=MainGUI.getBusinessLogic();
		List<User> users = new Vector<User>();
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