package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.JInternalFrame;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.awt.event.ActionEvent;

public class AdmGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JInternalFrame internalFrame = new ListUsersGUI();

	
	BasicInternalFrameUI ui = new BasicInternalFrameUI(internalFrame) {
        protected JComponent createNorthPane() {
            return null;
        }
    };
    
    private void paintInternalFrame() {
    	internalFrame.setUI(ui);//Quitamos la barra superior
		internalFrame.setBounds(118, 16, 703, 516);
		contentPane.add(internalFrame);
		internalFrame.setBorder(null);
		internalFrame.setVisible(true);
    }
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdmGUI frame = new AdmGUI();
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
	public AdmGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 833, 574);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setBounds(5, 16, 101, 466);
		toolBar.setOrientation(SwingConstants.VERTICAL);
		contentPane.add(toolBar);
		
		JButton btnListaUsuarios = new JButton("Lista de \nUsuarios");
		btnListaUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					internalFrame.setClosed(true);
				} catch (PropertyVetoException e1) {
					e1.printStackTrace();
				}
				internalFrame = new ListUsersGUI();
				paintInternalFrame();
			}
		});
		btnListaUsuarios.setAlignmentY(Component.TOP_ALIGNMENT);
		toolBar.add(btnListaUsuarios);
		
		paintInternalFrame();
	}
}
