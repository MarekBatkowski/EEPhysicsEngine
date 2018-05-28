package Physics;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.JPanel;

public class Gui extends JPanel implements Runnable
{
    Thread animator;

    Physics physics;
    ArrayList<PhysicsObject> Objects = new ArrayList<>();
    ArrayList<Force> Forces = new ArrayList<>();

    Ball ball1, ball2;

    public Gui(Physics physics)
    {
        this.physics = physics;
        double elasticity = 1;

        ball1 = new Ball(50, 100, 1, 1, 0, 0,100, 50, elasticity, Color.black, Color.green, 60, 10);
        ball2 = new Ball(250, 300, 2, 0.5, 0, 0,25, 50, elasticity, Color.black, Color.red, 40, 10);

        Objects.add(ball1);
        Objects.add(ball2);
    //    Objects.add(new Rectangle(300, 150, 0, 0, 0,1,500,50, elasticity, Color.gray, Color.red,80, 50));

        Force wind = new Force(10, 0, false);
        Force gravity = new Force(0.01, Math.PI/2, true);
     //   Forces.add(gravity);
     //   Forces.add(wind);

        physics.setObjects(Objects);
        physics.setForces(Forces);

        animator = new Thread(this);
        animator.start();
        physics.start();
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for(PhysicsObject object : Objects)
            object.draw(g2d);

        double bounceOne = physics.BounceAngle(ball1, ball2);
        double bounceTwo = physics.BounceAngle(ball2, ball1);

        double bounceAngle = -ball1.getSpeedAngle() + 2*physics.AngleBetween(ball1, ball2);

        g.setColor(Color.white);
        g2d.drawLine((int) ball1.getX(), (int) ball1.getY(), (int) (ball1.getX() + Math.cos(ball1.getSpeedAngle())*ball1.getDiameter()/2), (int) (ball1.getY() + Math.sin(ball1.getSpeedAngle())*ball1.getDiameter()/2));
        g2d.drawLine((int) ball2.getX(), (int) ball2.getY(), (int) (ball2.getX() + Math.cos(ball2.getSpeedAngle())*ball2.getDiameter()/2), (int) (ball2.getY() + Math.sin(ball2.getSpeedAngle())*ball2.getDiameter()/2));

        g.setColor(Color.yellow);
        g.drawLine((int) ball1.getX(), (int) ball1.getY(), (int) (ball1.getX() + Math.cos(physics.AngleBetween(ball1, ball2))*ball1.getDiameter()/2), (int) (ball1.getY() + Math.sin(physics.AngleBetween(ball1, ball2))*ball1.getDiameter()/2));
        g.drawLine((int) ball1.getX(), (int) ball1.getY(), (int) (ball1.getX() + Math.cos(physics.AngleBetween(ball1, ball2)+Math.PI)*ball1.getDiameter()/2), (int) (ball1.getY() + Math.sin(physics.AngleBetween(ball1, ball2)+Math.PI)*ball1.getDiameter()/2));

        g.drawLine((int) ball2.getX(), (int) ball2.getY(), (int) (ball2.getX() + Math.cos(physics.AngleBetween(ball2, ball1))*ball2.getDiameter()/2), (int) (ball2.getY() + Math.sin(physics.AngleBetween(ball2, ball1))*ball2.getDiameter()/2));
        g.drawLine((int) ball2.getX(), (int) ball2.getY(), (int) (ball2.getX() + Math.cos(physics.AngleBetween(ball2, ball1)+Math.PI)*ball2.getDiameter()/2), (int) (ball2.getY() + Math.sin(physics.AngleBetween(ball2, ball1)+Math.PI)*ball2.getDiameter()/2));

        g2d.setColor(Color.green);
        g2d.drawLine((int) ball1.getX(), (int) ball1.getY(), (int) (ball1.getX() + Math.cos(bounceOne)*ball1.getDiameter()/2), (int) (ball1.getY() + Math.sin(bounceOne)*ball1.getDiameter()/2));

        g2d.drawLine((int) ball2.getX(), (int) ball2.getY(), (int) (ball2.getX() + Math.cos(bounceTwo)*ball2.getDiameter()/2), (int) (ball2.getY() + Math.sin(bounceTwo)*ball2.getDiameter()/2));
    }

    @Override
    public void run()
    {
        while(true)
        {
            try
            {
                repaint();
                Thread.sleep(20);
            }
            catch (InterruptedException ex){ }
        }
    }
}
