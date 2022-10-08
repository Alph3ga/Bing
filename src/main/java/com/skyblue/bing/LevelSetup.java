package com.skyblue.bing;

import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class LevelSetup {
    static Ball ball;
    static int currentLvl;
    static Finish finish;
    static ArrayList<ToolTip> texts=new ArrayList<>();
    static Image ballmg=new ImageIcon(ClassLoader.getSystemResource("ball.png")).getImage();
    static Rectangle deployArea;
    static final Font TIP_FONT= new Font("Consolas", Font.BOLD, 18);
    
    public static void setup(int l){
        ball=new Ball(-20, -20, 0, 0, 0, 0, ballmg);
        texts.clear();
        Screen.elements.clear();
        switch(l){
            case 1:
                deployArea=new Rectangle(30, 55, 100, 100);
                finish=new Finish(590, 300);
                texts.add(new ToolTip("Click and Drag within the dark box", 405, 210));
                texts.add(new ToolTip("to throw the ball", 480, 230));
                texts.add(new ToolTip("Touch the Finish to win", 480, 350));
                return;
            case 2:
                deployArea=new Rectangle(130, 80, 100, 100);
                finish=new Finish(530, 80);
                Screen.elements.add(new Slab(310, 300, 0.0F, 0, 80, 5, false));
                texts.add(new ToolTip("The ball can bounce", 290, 330));
                break;
            case 3:
                deployArea=new Rectangle(300, 70, 50, 50);
                finish=new Finish(290, 500);
                Screen.elements.add(new Slab(250, 300, 0.0F, 2, 100, 5, false));
                texts.add(new ToolTip("this slab completely stops your ball", 230, 330));
                Screen.elements.add(new Slab(465, 405, 2.2F, 0, 80, 5, false));
                return;
            case 4:
                deployArea=new Rectangle(50, 70, 50, 50);
                finish=new Finish(615, 480);
                Screen.elements.add(new Slab(450, 350, 0.52F, 2, 150, 5, false));
                texts.add(new ToolTip("the ball can also roll on these", 220, 460));
                return;
            case 5:
                deployArea=new Rectangle(90, 450, 50, 50);
                finish=new Finish(620, 240);
                Screen.elements.add(new Slab(250, 400, 0.0F, 3, 100, 5, false));
                texts.add(new ToolTip("this is a super bouncy slab", 170, 430));
                return;
            case 6:
                deployArea=new Rectangle(50, 70, 50, 50);
                finish=new Finish(520, 220);
                Screen.elements.add(new Slab(130, 60, 1.57F, 2, 100, 5, false));
                Screen.elements.add(new Slab(70, 450, 0.76F, 0, 100, 5, false));
                Screen.elements.add(new Slab(370, 490, 2.8F, 0, 100, 5, false));
                return;
            case 7:
                deployArea=new Rectangle(120, 70, 50, 50);
                finish=new Finish(670, 550);
                Screen.elements.add(new Slab(190, 200, 0.0F, 1, 100, 5, false));
                Screen.elements.add(new Slab(370, 370, 0.0F, 1, 100, 5, false));
                Screen.elements.add(new Slab(480, 480, 0.0F, 1, 100, 5, false));
                texts.add(new ToolTip("less bouncy :( ", 180, 230));
                return;
            case 8:
                Screen.finish=true;
                return;
        }
    }

    static void release(Point pt, Point endpt) {
        ball.setpos(pt.x, pt.y);
        ball.xv=pt.x-endpt.x;
        ball.yv=pt.y-endpt.y;
        ball.ya=70;
        Screen.ballbeingset=false;
        BallSetup.setupnow=false;
    }
    
    
}
