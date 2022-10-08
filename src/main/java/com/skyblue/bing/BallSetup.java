package com.skyblue.bing;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BallSetup implements MouseListener{
    public static boolean setupnow=true;
    Point lastpt;
    boolean shooting=false;
    static Rectangle startbt=new Rectangle(276,320, 220, 50);
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if(!Screen.started){
            if(startbt.contains(e.getPoint())){
                Screen.startgame();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(LevelSetup.deployArea != null && LevelSetup.deployArea.contains(e.getPoint())){
        Screen.ballset(e.getPoint());
        lastpt=e.getPoint();
        shooting=true;}
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(shooting){
        LevelSetup.release(lastpt, e.getPoint());
        shooting=false;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
