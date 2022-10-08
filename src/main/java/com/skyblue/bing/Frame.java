package com.skyblue.bing;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Frame extends JFrame implements Runnable{
    public static final long serialversionUID=1L;
    private Thread thread;
    private boolean running;
    public BufferedImage simage;
    static boolean rotate;
    Screen screen;
    static Frame frame;
    
    Frame(){
        thread= new Thread(this);
        simage=new BufferedImage(800,600,BufferedImage.TYPE_INT_RGB);
        screen=new Screen(simage);
        this.setResizable(false);
        this.setSize(800,600);
        this.setBackground(Color.BLACK);
        this.setTitle("Bing");
        Toolkit tk= Toolkit.getDefaultToolkit();
        Cursor cr=tk.createCustomCursor(new ImageIcon(ClassLoader.getSystemResource("paintcursor.png")).getImage(), new Point(8,3), "Paint Cursor");
        this.setCursor(cr);
        this.setIconImage(new ImageIcon(ClassLoader.getSystemResource("bing.png")).getImage());
        this.setLocationRelativeTo(null);
        this.addMouseListener(new BallSetup());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        start();
    }
    
    private synchronized void start(){
        running=true;
        thread.start();
    }
    private synchronized void stop(){
        running=false;
        try {
            thread.join();
        } catch (InterruptedException ex) { //REMEMBER TO HANDLE THE EXCEPTION LATER!!!!!!!
            
        }
    }
    public void render(){
        BufferStrategy bs= getBufferStrategy();
        if(bs == null) {
		createBufferStrategy(3);
		return;
	}
        Graphics g= bs.getDrawGraphics();
        g.drawImage(simage, 0, 0, simage.getWidth(), simage.getHeight(), null);
        bs.show();
    }

    @Override
    public void run() {
        LevelSetup.setup(0);
        long lastTime= System.nanoTime();
        final double ns= 1000000000.0/60.0;//60Hz refresh rate
        double delta=0;
        requestFocus();
        while(running){
            long now= System.nanoTime();
            delta= delta+((now-lastTime)/ns);
            lastTime=now;
            while (delta >= 1)
		{
                    screen.update();
		    delta--;
		}
            render();
        }
    }
    
    public static void main(String[] args){
        try{
        frame=new Frame();
        Sound.playBGMusic();}
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, "idk what happened plz restart :/", "SOMETHING HAPPENED NOOOO", JOptionPane.ERROR_MESSAGE);
        }
    }
}
