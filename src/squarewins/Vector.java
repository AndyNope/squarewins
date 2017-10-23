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
public class Vector 
{
    
    private Point a,b;
    private int xVecDistance, yVecDistance;

    public Vector(Point a, Point b) 
    {
        this.xVecDistance = b.getX() - a.getX();
        this.yVecDistance = b.getY() - a.getY();
        this.a = a;
        this.b = b;
    }

    public Point getPointA() 
    {
        return a;
    }

    public Point getPointB() 
    {
        return b;
    }

    public int getxVec() 
    {
        return xVecDistance;
    }

    public int getyVec() 
    {
        return yVecDistance;
    }
    
    
}
