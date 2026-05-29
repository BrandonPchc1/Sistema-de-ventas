/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Clases;

import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author decab
 */
/*clase que funcionara para escalar las imagenes sin tener errores,la hice por que me estaban fallando las 
predeterminadas de netbeans */
public class imageLabel {
    public imageLabel(JLabel labelName,String root){ //solo funciona con labels y pide la ruta de la imagen
        ImageIcon image = new ImageIcon(root);
        Icon icon = new ImageIcon(image.getImage().getScaledInstance(labelName.getWidth(),
                labelName.getHeight(),
                Image.SCALE_DEFAULT));
        labelName.setIcon(icon); //llamar repaint despues de crear
    }
}
