package Physics;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Rectangle extends PhysicsObject
{
    private Color color;
    private Color color2;
    double width;
    double height;

    private Rectangle2D rectangle;
    Point2D points[];

    public Rectangle(double x, double y, double xSpeed, double ySpeed, double angle, double rotationSpeed, double mass, double maxSpeed, double elasticity, boolean movable, Color color, Color color2, double width, double height)
    {
        super(x, y, xSpeed, ySpeed, angle, rotationSpeed, mass, maxSpeed, elasticity, movable, "Rectangle");

        this.width = width;
        this.height = height;
        this.color = color;
        this.color2 = color2;

        rectangle = new Rectangle2D.Double(x - width/2, y - height/2, width, height);

        points = new Point2D[4];
        points[0] = new Point2D.Double(x - width/2, y - height/2);
        points[1] = new Point2D.Double(x + width/2, y - height/2);
        points[2] = new Point2D.Double(x - width/2, y + height/2);
        points[3] = new Point2D.Double(x + width/2, y + height/2);
    }

    @Override
    public void draw(Graphics2D g2d)
    {
        AffineTransform rotation = AffineTransform.getRotateInstance(Math.toRadians(angle), x, y);

        rectangle.setRect(x - width/2, y - height/2, width, height);

        points[0].setLocation(x - width/2, y - height/2);
        points[1].setLocation(x + width/2, y - height/2);
        points[2].setLocation(x - width/2, y + height/2);
        points[3].setLocation(x + width/2, y + height/2);

        for(int i=0; i<4; i++)
            rotation.transform(points[i], points[i]);

        g2d.setColor(color);
        g2d.fill(rotation.createTransformedShape(rectangle));

        g2d.setColor(color2);
        g2d.draw(rotation.createTransformedShape(rectangle));

        g2d.draw(new Line2D.Double(points[0], points[3]));
        g2d.draw(new Line2D.Double(points[1], points[2]));

        g2d.fillOval((int) x-2, (int) y-2, 5, 5);
        for(int i=0; i<4; i++)
            g2d.fillOval((int) points[i].getX()-2, (int) points[i].getY()-2, 5, 5);
    }

    @Override
    public Point2D[] getPoints()
    {
        return points;
    }
}
