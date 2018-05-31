package Physics;

import java.awt.*;
import java.awt.geom.Point2D;

public class PhysicsObject
{
    protected double x;
    protected double y;
    protected double xSpeed;
    protected double ySpeed;
    protected double angle;
    protected double rotationSpeed;
    protected double mass;
    protected double maxSpeed;
    protected double elasticity;
    protected String type;
    boolean Movable;
    Point2D points[]= new Point2D[4];

    public double getX() { return x; }

    public void setX(double x) { this.x = x; }

    public double getY() { return y; }

    public void setY(double y) { this.y = y; }

    public double getxSpeed() { return xSpeed; }

    public void setxSpeed(double xSpeed) { this.xSpeed = xSpeed; }

    public double getySpeed() { return ySpeed; }

    public void setySpeed(double ySpeed) { this.ySpeed = ySpeed; }

    public double getSpeed()
    {
        return Math.sqrt(Math.pow(getxSpeed(),2)+Math.pow(getySpeed(),2));
    }

    public void setSpeed(Vector vector)
    {
        xSpeed = vector.getX();
        ySpeed = vector.getY();
    }

    public double getSpeedAngle()
    {
        return Math.atan2(getySpeed(), getxSpeed());
    }

    public Vector getSpeedVector()
    {
        return new Vector(this.xSpeed, this.ySpeed);
    }

    public void setSpeedVector(Vector vector)
    {
        xSpeed = vector.getX();
        ySpeed = vector.getY();
    }

    public double getMass() { return mass; }

    public void setMass(double mass) { this.mass = mass; }

    public double getElasticity()
    {
        return elasticity;
    }

    public int getDiameter() { return 0; }

    public void draw(Graphics2D g2d)
    {
        // implemented in extended classes
    }

    public Point2D[] getPoints()
    {
        return points;
    }
}
