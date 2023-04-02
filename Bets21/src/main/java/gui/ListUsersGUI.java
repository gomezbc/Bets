package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.*;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JButton;

public class ListUsersGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tableUsers = new JTable();
	private JTable tableAdmins = new JTable();
	private JScrollPane scrollPaneUser = new JScrollPane();
	private JScrollPane scrollPaneAdmin = new JScrollPane();
	private DefaultTableModel tableModelUser;
	private DefaultTableModel tableModelAdmin;

	private String[] columnNames = new String[] {
			"Dni","Nombre","Apellidos","Usuario","Saldo"
	};

	/**
	 * Launch the application.
	 */
	public  ListUsersGUI(){
		setTitle("Lista de Usuarios");
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		this.setSize(new Dimension(700, 500));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(12, 0, 673, 400);
		contentPane.add(tabbedPane);
		
		scrollPaneUser.setViewportView(tableUsers);
		scrollPaneAdmin.setViewportView(tableAdmins);
		tabbedPane.addTab("Users", null, scrollPaneUser, null);
		tabbedPane.addTab("Admins", null, scrollPaneAdmin, null);
		
		tableModelUser = new DefaultTableModel(null, columnNames);
		tableUsers.setModel(tableModelUser);
		
		tableModelAdmin = new DefaultTableModel(null, columnNames);
		tableAdmins.setModel(tableModelAdmin);
		
		JButton btnClose = new JButton("Cerrar");
		btnClose.setBounds(292, 416, 105, 27);
		contentPane.add(btnClose);
		
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
		
		btnClose.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				btnClose_actionPerformed(e);
			}
		});
		
	}
	
	private void btnClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}
