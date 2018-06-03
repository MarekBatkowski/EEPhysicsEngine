package Physics;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class Ball extends PhysicsObject
{
    private Color color;
    private Color color2;       // inner
    private int ringSize;       // inner
    private int diameter;

    public Ball(double x, double y, double xSpeed, double ySpeed, double angle, double rotationSpeed, double mass, double maxSpeed, double elasticity, boolean movable, Color color, Color color2, int diameter, int ringSize)
    {
        super(x, y, xSpeed, ySpeed, angle, rotationSpeed, mass, maxSpeed, elasticity, movable, "Circle");

        this.diameter = diameter;
        this.ringSize = ringSize;
        this.color = color;
        this.color2 = color2;

        points = new Point2D[4];
        for(int i=0; i<4; i++)
            getPoints()[i] = new Point2D.Double(x,y);
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

    @Override
    public void draw(Graphics2D g2d)
    {
        g2d.setColor(color);
        g2d.fillOval((int) (getX() - getDiameter()/2), (int) (getY() - getDiameter()/2), getDiameter(), getDiameter());

        g2d.setColor(color2);
        g2d.fillOval((int) (getX() - getRingSize()/2), (int) (getY() - getRingSize()/2), getRingSize(), getRingSize());
        g2d.drawOval((int) (getX() - getDiameter()/2), (int) (getY() - getDiameter()/2), getDiameter(), getDiameter());


        // for( Point2D p : points) g2d.fillOval((int) (p.getX()-2), (int) (p.getY()-2), 5, 5);

    }
}

