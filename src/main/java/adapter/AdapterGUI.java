package adapter;

import domain.User;
import domain.UserAdapter;

import javax.swing.*;
import java.awt.*;

public class AdapterGUI  extends JFrame {
    private User user;
    private JTable	tabla;
    public AdapterGUI(User	user){
        super("Apuesta realizadas por "+user.getName()+":");
        this.setBounds(100,	100,	700,	200);
        this.user =	user;
        UserAdapter adapt =	new UserAdapter(user);
        tabla =	new JTable(adapt);
        tabla.setPreferredScrollableViewportSize(new Dimension(500,	70));
        //Creamos un JscrollPane	y	le agregamos la JTable
        JScrollPane	scrollPane =	new JScrollPane(tabla);
        //Agregamos el	JScrollPane	al contenedor
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }
}

