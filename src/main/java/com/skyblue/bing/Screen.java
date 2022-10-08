
package com.skyblue.bing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Screen { 
    static Point ballsetpoint, stretch;
    static String prv, nxt;
    public static boolean started, transitioning, umoving, downtrans, ballbeingset, finish=false;
    static int yinc=0, lvlcnt=1;
    
    static final Font LevelFont=new Font("Consolas",Font.BOLD, 100);
    static final Color PEACH=new Color(0xFCCE9E);
    static final Color PURPLE=new Color(0xA561FF);
    static final Color PINK=new Color(0xFD72AD);
    static final Color BLUE=new Color(0xCFE3FF);
    static final Color DARK_PEACH=new Color(0xEDC39F);
    static final Color NORMAL_SLAB=new Color(0xDE6718);
    static final Color DAMP_COLOR=new Color(0x877055);
    static final Color BOUNCY=new Color(0xEAFF4A);
    static final Color STOPPER=new Color(0xD94734);
    
    Graphics2D g;
    int width, height;
    int xr=0, yr=0;
    static float prvy;
    static ArrayList<Slab> elements=new ArrayList<>();
    Screen(BufferedImage image){
        g= image.createGraphics();
        width=image.getWidth();
        height=image.getHeight();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
    }
    
    static void ballset(Point point) {
        ballbeingset=true;
        ballsetpoint=point;
    }

    static void startgame() {
        level_transition(0);
        downtrans=true;
        started=true;
        yinc=0;
    }
    

    
    public void update(){
        if(started){
            if(!transitioning && !finish){update_elem();}
            update_screen();
        }
        else{
            title_screen();
        }
    }
    
    private void update_elem(){
        LevelSetup.ball.move();
        for(int i=0; i<8; i++){
            int xin=LevelSetup.ball.hitbox.xpoints[i];
            int yin=LevelSetup.ball.hitbox.ypoints[i];
            if(LevelSetup.finish.hitbox.contains(xin, yin)){
                level_transition(lvlcnt++);
                transitioning=true;
                downtrans=true;
                yinc=0;
            }
            else if(!LevelSetup.ball.rolling){
                for(Slab sp: elements){
                    if(sp.hitbox.contains(xin, yin)){
                        if(LevelSetup.ball.xv*LevelSetup.ball.xv>=2F || LevelSetup.ball.yv*LevelSetup.ball.yv>=2F){
                            bounce(sp);
                        }
                        else{
                            
                            roll(sp, prvy);
                            LevelSetup.ball.rolling=true;
                            xr=i;
                            yr=i;
                            
                        }
                        LevelSetup.ball.lasthit=elements.indexOf(sp);
                    }
                }
            }
            else if(!(elements.get(LevelSetup.ball.lasthit).hitbox.contains(LevelSetup.ball.hitbox.xpoints[xr], LevelSetup.ball.hitbox.ypoints[yr]+1))){
                LevelSetup.ball.rolling=false;
                LevelSetup.ball.ya=prvy;
                LevelSetup.ball.xa=0;
            }
        }
    }
    
    public void bounce(Slab sp){
        float angb=1.571F;
        if(LevelSetup.ball.xv!=0){
            angb=(float)Math.atan2(LevelSetup.ball.yv, LevelSetup.ball.xv);
        }
        float betw= angb>sp.ang? angb-sp.ang : sp.ang-angb;
        float rotai=2*(1.571F-betw);
        float rota= sp.ang>1.571F ? 6.284F-rotai : rotai;
        float prevx=LevelSetup.ball.xv;
        float ey=sp.e;
        if(LevelSetup.ball.yv<=0){ey=-ey;}
        LevelSetup.ball.xv= -(float)(sp.e*(LevelSetup.ball.xv*Math.cos(rota) - LevelSetup.ball.yv*Math.sin(rota)));
        LevelSetup.ball.yv= -(float)(ey*(prevx*Math.sin(rota) + LevelSetup.ball.yv*Math.cos(rota)));
        Sound.playSFX();
    }
    
    public void roll(Slab sp, float prv){
        LevelSetup.ball.xv=0;
        LevelSetup.ball.yv=0;
        LevelSetup.ball.xa=(float)(prv*Math.sin(2*sp.ang)/2);
        LevelSetup.ball.ya=(float)(prv*Math.pow(Math.sin(sp.ang),2));
    }
    
    private void update_screen(){
        if(finish){
            g.setColor(PEACH);
            g.fillRect(0, 0, 800, 600);
            g.setColor(PURPLE);
            g.setFont(LevelFont);
            g.drawString("GAME OVER", 150, 300);
            g.setFont(new Font("Consolas",Font.BOLD, 50));
            g.drawString("Thanks for playing :)", 130, 370);
            g.setFont(new Font("Consolas",Font.BOLD, 10));
            g.drawString("-Akashneel", 725, 580);
        }
        else{
            if(transitioning){
                g.setFont(LevelFont);
                if(downtrans){
                    g.setColor(PEACH);
                    g.fillRect(0, 0, 800, 600);
                    g.setColor(PURPLE);
                    g.drawString(prv, 210, 300+yinc);
                    yinc+=4;
                    if(yinc>=400){
                        yinc=0;
                        downtrans=false;
                    }
                }
                else{
                    g.setColor(PEACH);
                    g.fillRect(0, 0, 800, 600);
                    g.setColor(PURPLE);
                    g.drawString(nxt, 210, 700-yinc);
                    yinc+=4;
                    if(yinc>=400){
                        transitioning=false;
                        BallSetup.setupnow=true;
                        LevelSetup.setup(lvlcnt);
                    }
                } 
            }
            else{
                g.setColor(PEACH);
                g.fillRect(0, 0, width, height);
                g.setColor(Color.red);
                for(Slab sp: elements){
                    if(sp.type==0){g.setColor(NORMAL_SLAB);}
                    else if(sp.type==1){g.setColor(DAMP_COLOR);}
                    else if(sp.type==2){g.setColor(STOPPER);}
                    else if(sp.type==3){g.setColor(BOUNCY);}
                    g.fill(sp.hitbox);
                }
                g.setColor(DARK_PEACH);
                g.fill(LevelSetup.deployArea);
                g.setColor(PINK);
                g.setFont(LevelSetup.TIP_FONT);
                for(ToolTip s: LevelSetup.texts){
                    g.drawString(s.tip, s.xpos, s.ypos);
                }
                g.setColor(Color.RED);
                g.drawImage(LevelSetup.ball.sprite, Math.round(LevelSetup.ball.xpos)-10, Math.round(LevelSetup.ball.ypos)-10, null);
                g.drawImage(Finish.finish, LevelSetup.finish.hitbox.x, LevelSetup.finish.hitbox.y, null);
                g.setStroke(new BasicStroke(2.0F, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0.0F, new float[]{30, 10}, 0.0F));
                if(ballbeingset){
                    stretch=MouseInfo.getPointerInfo().getLocation();
                    stretch.x-=Frame.frame.getX();
                    stretch.y-=Frame.frame.getY();
                    g.drawLine(ballsetpoint.x, ballsetpoint.y, stretch.x, stretch.y);
                    g.drawImage(LevelSetup.ball.sprite, ballsetpoint.x-10, ballsetpoint.y-10, null);
                }
            }   
        }
    }
    
    private void title_screen(){
        g.setColor(PEACH);
        g.fillRect(0, 0, 800, 600);
        g.setColor(PINK);
        g.fill(BallSetup.startbt);
        g.setColor(PURPLE);
        g.draw(BallSetup.startbt);
        g.setFont(LevelFont);
        g.drawString("BiNG", 275, 300);
        g.setFont(new Font("Consolas",Font.BOLD, 10));
        g.drawString("-Akashneel", 725, 580);
    }
    
    private static void level_transition(int l){
        prv= "LEVEL "+ String.valueOf(l);
        nxt= "LEVEL "+ String.valueOf(l+1);
        transitioning=true;
    }
    
}
