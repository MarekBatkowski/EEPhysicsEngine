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
    protected boolean movable;
    protected String type;
    Point2D points[]= new Point2D[4];

    public PhysicsObject(double x, double y, double xSpeed, double ySpeed, double angle, double rotationSpeed, double mass, double maxSpeed, double elasticity, boolean movable, String type)
    {
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.angle = angle;
        this.rotationSpeed = rotationSpeed;
        this.mass = mass;
        this.maxSpeed = maxSpeed;
        this.elasticity = elasticity;
        this.movable = movable;
        this.type = type;
    }

    public void setXY(Point2D point)
    {
        this.x = point.getX();
        this.y = point.getY();
    }

    public Point2D getXY()
    {
        return new Point2D.Double(this.x, this.y);
    }

    public double getX() { return x; }

    public void setX(double x) { this.x = x; }

    public double getY() { return y; }

    public void setY(double y) { this.y = y; }

    public double getxSpeed() { return xSpeed; }

    public void setxSpeed(double xSpeed) { this.xSpeed = xSpeed; }

    public double getySpeed() { return ySpeed; }

    public double getAngle() { return angle; }

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
