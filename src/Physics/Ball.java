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

    public Ball(double x, double y, double xSpeed, double ySpeed, double angle, double rotationSpeed, double mass, double maxSpeed, double elasticity, Color color, Color color2, int diameter, int ringSize)
    {
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.angle = angle;
        this.rotationSpeed = rotationSpeed;
        this.diameter = diameter;
        this.ringSize = ringSize;
        this.color = color;
        this.color2 = color2;
        this.mass = mass;
        this.maxSpeed = maxSpeed;
        this.elasticity = elasticity;
        this.type = "Circle";

        points = new Point2D[4];
        points[0] = new Point2D.Double(x - diameter/2, y);
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
        AffineTransform rotation = AffineTransform.getRotateInstance(Math.toRadians(angle), x, y);

        points[0].setLocation(x - diameter/2, y);

        g2d.setColor(color);
        g2d.fillOval((int) (getX() - getDiameter()/2), (int) (getY() - getDiameter()/2), getDiameter(), getDiameter());

        g2d.setColor(color2);
        g2d.fillOval((int) (getX() - getRingSize()/2), (int) (getY() - getRingSize()/2), getRingSize(), getRingSize());
        g2d.drawOval((int) (getX() - getDiameter()/2), (int) (getY() - getDiameter()/2), getDiameter(), getDiameter());

        rotation.transform(points[0], points[0]);

        rotation = AffineTransform.getRotateInstance(Math.toRadians(60), x, y);

        /*
        for(int i=0; i<6; i++)
        {
            g2d.drawLine((int) getX(), (int) getY(), (int) points[0].getX(), (int) points[0].getY());
            rotation.transform(points[0], points[0]);
        }
        */

        /*
            double angle = getSpeedAngle();
            g2d.drawLine((int) getX(), (int) getY(), (int) (getX() + Math.cos(angle)*getDiameter()/2), (int) (getY() + Math.sin(angle)*getDiameter()/2));
        */
    }

    @Override
    public Point2D[] getPoints()
    {
        return points;
    }
}

