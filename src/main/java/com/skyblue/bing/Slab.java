package com.skyblue.bing;

import java.awt.Polygon;


public class Slab {
    
    float xpos, ypos, ang, e;
    int type;
    boolean moveable;
    Polygon hitbox;
    Slab(float x, float y, float rot, int typeof, int len, int high, boolean move){
        xpos=x;
        ypos=y;
        ang=rot;
        type=typeof;
        e=gete(typeof);
        moveable=move;
        int xlist[]=new int[]{(int)x, (int)(x+len*Math.cos(rot)), (int)(x+len*Math.cos(rot)-high*Math.sin(rot)), (int)(x-high*Math.sin(rot))};
        int ylist[]=new int[]{(int)y, (int)(y+len*Math.sin(rot)), (int)(y+len*Math.sin(rot)+high*Math.cos(rot)), (int)(y+high*Math.cos(rot))};
        hitbox=new Polygon(xlist, ylist, 4);
    }
    
    private static float gete(int typeof){
        switch(typeof){
            case 0: //perfectly elastic
                return 1.0F; 
            case 1: //damped
                return 0.7F;
            case 2: //perfetly inelastic
                return 0.0F;
            case 3: //bouncy
                return 2.0F;
            default:
                return 1.0F;
        }
    }
}
