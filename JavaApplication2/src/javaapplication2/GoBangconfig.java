/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication2;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author Fool。
 */
public interface GoBangconfig {
        int x=20,y=20,size=40,row=15,column=15;
        
        Image blackchess= new ImageIcon("black.png").getImage();//这里不能用ImageIcon
	Image whitechess= new ImageIcon("white.png").getImage();//这里不能用ImageIcon
}
