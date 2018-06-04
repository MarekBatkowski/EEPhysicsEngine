package Physics;

import javax.sound.sampled.Line;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class Forcefield extends Force
{
    private final double x;
    private final double y;
    private final double width;
    private final double height;
    private final boolean render;

    private Rectangle2D rectangle;

    public Forcefield(double value, double angle, boolean gravityLike, double x, double y, double width, double height, boolean render)
    {
        super(value, angle, gravityLike);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.render = render;
        rectangle = new Rectangle2D.Double(x - width/2, y - height/2, width, height);
    }

    public void draw(Graphics2D g2d)
    {
        AffineTransform rotation = AffineTransform.getRotateInstance(angle, x, y);

        rectangle.setRect(x - width/2, y - height/2, width, height);
        Line2D line1 = new Line2D.Double(x - width/2, y - height/2, x + width/2, y);
        Line2D line2 = new Line2D.Double(x - width/2, y + height/2, x + width/2, y);

        g2d.setColor(Color.cyan);
        g2d.draw(rotation.createTransformedShape(rectangle));
        g2d.draw(rotation.createTransformedShape(line1));
        g2d.draw(rotation.createTransformedShape(line2));
    }

    @Override
    public void ApplyForce(PhysicsObject obj)
    {
        AffineTransform rotation = AffineTransform.getRotateInstance(angle, x, y);
        rectangle.setRect(x - width/2, y - height/2, width, height);
        if(obj.movable && rotation.createTransformedShape(rectangle).contains(obj.getXY()))
        {
            super.ApplyForce(obj);
        //    if(gravityLike)
        //        obj.setSpeed(new Vector(value, angle, true));
        //    else
        //       obj.setSpeed(new Vector(value/obj.getMass(), angle, true));
        }
    }
}
