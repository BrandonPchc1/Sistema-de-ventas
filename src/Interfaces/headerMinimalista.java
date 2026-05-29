/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author decab
 */
//para usar este header se debe importar en el UI y modificar unas propiedades de ese UI
public class headerMinimalista extends JPanel{
    
    private JButton exitButton;
    private JButton minimizeButton;
    private Point initialClick;
    private JFrame parentFrame;
    public headerMinimalista(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(900, 30)); //Tamaño de la barra
        
        setVisible(true);
        
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,5,2)); //agregara elementos 
        rightPanel.setOpaque(false);
        
        exitButton = createMinimalButton("X"); //boton para quitar pantalla
        exitButton.addActionListener(e -> { 
            System.exit(0);
        });
        minimizeButton = createMinimalButton("—"); //boton para minimizar pantalla
        minimizeButton.addActionListener(e -> {
            parentFrame.setState(JFrame.ICONIFIED);
        });
        cambioColor(exitButton); 
        cambioColor(minimizeButton);
        
        rightPanel.add(minimizeButton);
        rightPanel.add(exitButton);
        
        add(rightPanel, BorderLayout.EAST); //crea el panel
        arrastre(); //Para poder mover la pantalla 
    }
    private JButton createMinimalButton(String text) { //Configuracion de los botones
        JButton btn = new JButton(text);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setForeground(Color.WHITE);
        btn.setOpaque(false);
        btn.setFont(new Font("Arial", Font.PLAIN, 15));
        return btn;
    }
    private void arrastre(){ //para poder arrastrar la ventana
        //detectar
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                initialClick = e.getPoint();
                
            }
            
        });
        //mover ventana
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseDragged(java.awt.event.MouseEvent e) {
                if (initialClick != null && parentFrame != null) {
                    int thisX = parentFrame.getLocation().x;
                    int thisY = parentFrame.getLocation().y;
                    
                    int xMoved = e.getX() - initialClick.x;
                    int yMoved = e.getY() - initialClick.y;
                    
                    int X = thisX + xMoved;
                    int Y = thisY + yMoved;
                    
                    parentFrame.setLocation(X, Y);
                }
            }
        });
    }
    private void cambioColor(JButton boton){ //cambia el color al poner el mouse encima
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e){
                boton.setForeground(Color.GRAY);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e){
                boton.setForeground(Color.WHITE);
            }
        });
    }
}
