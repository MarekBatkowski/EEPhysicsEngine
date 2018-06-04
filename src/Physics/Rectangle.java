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
    private double width;
    private double height;

    private Rectangle2D rectangle;

    public Rectangle(double x, double y, double xSpeed, double ySpeed, double angle, double rotationSpeed, double mass, double maxSpeed, double elasticity, boolean movable, Color color, Color color2, double width, double height)
    {
        super(x, y, xSpeed, ySpeed, angle, rotationSpeed, mass, maxSpeed, elasticity, movable, "Rectangle");

        this.width = width;
        this.height = height;
        this.color = color;
        this.color2 = color2;

        rectangle = new Rectangle2D.Double(x - width/2, y - height/2, width, height);

        points.add(new Point2D.Double(x - width/2, y - height/2));
        points.add(new Point2D.Double(x + width/2, y - height/2));
        points.add(new Point2D.Double(x - width/2, y + height/2));
        points.add(new Point2D.Double(x + width/2, y + height/2));
    }

    @Override
    public void draw(Graphics2D g2d)
    {
        AffineTransform rotation = AffineTransform.getRotateInstance(Math.toRadians(angle), x, y);

        rectangle.setRect(x - width/2, y - height/2, width, height);

        points.get(0).setLocation(x - width/2, y - height/2);
        points.get(1).setLocation(x + width/2, y - height/2);
        points.get(2).setLocation(x - width/2, y + height/2);
        points.get(3).setLocation(x + width/2, y + height/2);

        for(Point2D p : points)
            rotation.transform(p, p);

        g2d.setColor(color);
        g2d.fill(rotation.createTransformedShape(rectangle));

        rotation.createTransformedShape(rectangle);

        g2d.setColor(color2);
        g2d.draw(rotation.createTransformedShape(rectangle));

    //   g2d.draw(new Line2D.Double(points.get(0), points.get(3)));
    //    g2d.draw(new Line2D.Double(points.get(1), points.get(2)));

        g2d.fillOval((int) x-3, (int) y-3, 5, 5);
        for(int i=0; i<4; i++)
            g2d.fillOval((int) points.get(i).getX()-2, (int) points.get(i).getY()-2, 5, 5);
    }

    public Shape getShape()
    {
        AffineTransform rotation = AffineTransform.getRotateInstance(Math.toRadians(angle), x, y);
        rectangle.setRect(x - width/2, y - height/2, width, height);
        return rotation.createTransformedShape(rectangle);
    }
}
