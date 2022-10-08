
package com.skyblue.bing;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Finish {
    Rectangle hitbox;
    static Image finish=new ImageIcon(ClassLoader.getSystemResource("finish.png")).getImage();
    Finish(int x, int y){
        hitbox=new Rectangle(x-30, y-30,30,30);
    }
}
