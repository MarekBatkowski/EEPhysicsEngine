package Physics;

import java.awt.*;

public class Ball extends PhysicsObject
{
    private Color color;
    private Color color2;       // inner
    private int ringSize;       // inner
    private int diameter;

    public Ball(float x, float y, float xSpeed, float ySpeed, double angle, double rotationSpeed, float mass, double maxSpeed, double elasticity, Color color, Color color2, int diameter, int ringSize)
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

        double angle = getSpeedAngle();
        g2d.drawLine((int) getX(), (int) getY(), (int) (getX() + Math.cos(angle)*getDiameter()/2), (int) (getY() + Math.sin(angle)*getDiameter()/2));
    }
}

