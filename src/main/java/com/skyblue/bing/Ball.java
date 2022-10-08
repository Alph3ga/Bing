
package com.skyblue.bing;

import java.awt.Image;
import java.awt.Polygon;

public class Ball {
    float xpos, ypos, xv, yv, xa, ya, tsx=0,tsy=0;
    final float t= 1/60F;
    Image sprite;
    int lasthit;
    boolean motion, rolling;
    Polygon hitbox;
    int[] xlist, ylist;
    float[] xlisf, ylisf;
    Ball(float x, float y, float xvel, float yvel, float xacc, float yacc, Image sp){
        sprite=sp;
        xpos=x;
        ypos=y;
        xv=xvel;
        yv=yvel;
        xa=xacc;
        ya=yacc;
        inithitbox();
        Screen.prvy=70;
    }
    
    private void inithitbox(){
        xlist=new int[]{(int)(xpos+10), (int)(xpos+7.07), (int)xpos, (int)(xpos-7.07), (int)(xpos-10), (int)(xpos-7.07), (int)xpos, (int)(xpos+7.07)};
        ylist=new int[]{(int)ypos, (int)(ypos+7.07), (int)(ypos+10), (int)(ypos+7.07), (int)ypos, (int)(ypos-7.07), (int)(ypos-10), (int)(ypos-7.07)};
        hitbox=new Polygon(xlist, ylist, 8);
    }
    
    public void setpos(float x, float y){
        xpos=x;
        ypos=y;
    }
    
    public void setsprite(Image sp){
        sprite=sp;
    }
    
    public void move(){
        xv=xv+xa*t;
        yv=yv+ya*t;
        xpos=xpos+xv*t;
        ypos=ypos+yv*t;
        inithitbox();
    }
}
