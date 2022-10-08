package com.skyblue.bing;

import java.awt.Font;

public class ToolTip {
    String tip;
    int xpos, ypos;
    
    ToolTip(String text, int x, int y){
        xpos=x;
        ypos=y;
        tip=text;
    }
}
