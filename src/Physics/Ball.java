package Physics;

import java.awt.Color;

public class Ball
{
    private double x;
    private double y;
    private double xSpeed;
    private double ySpeed;

    Color color;
    Color color2;               // inner
    private int ringSize;     // inner
    private int diameter;
    private float mass;
    float angle;

    public Ball(float x, float y, float xSpeed, float ySpeed, int diameter, int ringSize, Color color, Color color2, float mass)
    {
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        this.ringSize = ringSize;
        this.color = color;
        this.color2 = color2;
        this.mass = mass;
    }

    public double getxSpeed()
    {
        return xSpeed;
    }

    public void setxSpeed(double xSpeed)
    {
        this.xSpeed = xSpeed;
    }

    public double getySpeed()
    {
        return ySpeed;
    }

    public void setySpeed(double ySpeed)
    {
        this.ySpeed = ySpeed;
    }

    public double getX()
    {
        return x;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public double getY()
    {
        return y;
    }

    public void setY(double y)
    {
        this.y = y;
    }

    public int getDiameter()
    {
        return diameter;
    }

    public void setDiameter(int diameter)
    {
        this.diameter = diameter;
    }

    public int getRingSize()
    {
        return ringSize;
    }

    public void setRingSize(int ringSize)
    {
        this.ringSize = ringSize;
    }

    public double getSpeedVector()
    {
        return Math.atan2(getySpeed(), getxSpeed());
    }
}

