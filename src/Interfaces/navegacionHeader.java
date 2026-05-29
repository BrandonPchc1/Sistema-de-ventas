/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces;

import Clases.imageLabel;
import Clases.sesionUsuario;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author decab
 */
public class navegacionHeader extends JPanel{
    private final int altura = 26; //altura de estos iconos
    private JButton usuario,realVentas,tabla,login;
    private JLabel usImg,rVImg,tabImg,logImg;

    public navegacionHeader(JFrame parentFrame) {
        this.usuario = usuario;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(900, 40));
        setVisible(true);
        crearBotones(parentFrame);
        cambioColor(usuario);
        cambioColor(realVentas);
        cambioColor(tabla);
        cambioColor(login);
        
    }
    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setForeground(Color.black);
        
        btn.setFont(new Font("Arial", Font.PLAIN, 15));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
    private JLabel createImage(String root){
        JLabel imagen = new JLabel();
        imagen.setSize(30, altura);
        new imageLabel(imagen, root);
        return imagen;
    }
    private void crearBotones(JFrame parentFrame){
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,25,4)); //agregara elementos 
        rightPanel.setOpaque(false);
        usImg = createImage("src\\res\\user.png");
        usuario = createButton("USUARIO"); 
        usuario.addActionListener(e -> { 
            usuarioUI us = new usuarioUI();
            us.setVisible(true);
            parentFrame.dispose();
        });
        
        rVImg = createImage("src\\res\\parcel.png");
        realVentas = createButton("REALIZAR VENTAS"); 
        realVentas.addActionListener(e -> {
            realVentas rV = new realVentas();
            rV.setVisible(true);
            parentFrame.dispose();
        });
        
        tabImg = createImage("src\\res\\increase.png");
        tabla = createButton("VENTAS"); 
        tabla.addActionListener(e -> {
            tablaUI tui = new tablaUI();
            tui.setVisible(true);
            parentFrame.dispose();
        });
        
        logImg = createImage("src\\res\\exit.png");
        login = createButton("CERRAR SESION");
        login.addActionListener(e -> {
            Login lo = new Login();
            lo.setVisible(true);
            parentFrame.dispose();
            sesionUsuario.getInstance().cerrarSesion();
        });
        
        rightPanel.add(usImg);
        rightPanel.add(usuario);
        rightPanel.add(rVImg);
        rightPanel.add(realVentas);
        rightPanel.add(tabImg);
        rightPanel.add(tabla);
        rightPanel.add(logImg);
        rightPanel.add(login);
        add(rightPanel, BorderLayout.SOUTH); //crea el panel
    }
    private void cambioColor(JButton boton){
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e){
                boton.setForeground(Color.GRAY);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e){
                boton.setForeground(Color.BLACK);
            }
        });
    }
}
