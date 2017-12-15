/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package squarewins;

/**
 *
 * @author kbwschuler
 */
public class Point {
   /**
    * color: b = blue r = red c = blank
    * pointTaken: check if point is occupied
    */
   private int x;
   private int y;
   private boolean color;
   
    public Point(int x, int y, boolean color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        color = true;
    }
    
    public int checkLength()
    {
        int length = 0;
        
        return length;
    }
   
    public void setColor(int x, int y, boolean color)
    {
       this.color = color;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean getColor() {
        return color;
    }

    public void setColor(boolean color) {
        this.color = color;
    }
}
