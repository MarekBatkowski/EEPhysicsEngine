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

    public Gui(Physics physics)
    {
        this.physics = physics;
        double elasticity = 0.8;

        Objects.add(new Ball(50, 100, 0, 0, 0, 1,1000, 50, elasticity, Color.black, Color.blue, 70, 10));
        Objects.add(new Ball(250, 300, 1, 0, 0, 0,25, 50, elasticity, Color.black, Color.red, 40, 10));
        Objects.add(new Rectangle(300, 150, 0, 0, 0,1,500,50, 1, Color.gray, Color.red,80, 50));

        Force wind = new Force(10, 0, false);
        Force gravity = new Force(0.01, Math.PI/2, true);
        Forces.add(gravity);
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
