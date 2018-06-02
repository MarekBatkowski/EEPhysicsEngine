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
        double elasticity = 1;

        physics.setObjects(Objects);
        physics.setForces(Forces);

        //Objects.add(new Ball(275, 100, 0.25, 2, 0, 0, 10, 50, elasticity, true, Color.black, Color.blue, 60, 10));
        //Objects.add(new Ball(360, 300, 0, 0, 0, 0, 10, 50, elasticity, true, Color.black, Color.green, 60, 10));

        Objects.add(new Ball(100, 300, 2, 0, 0, 0, 100, 50, elasticity, true, Color.black, Color.red, 60, 10));
        Objects.add(new Ball(500, 300, 0, 0, 0, 0, 10, 50, elasticity, true, Color.black, Color.yellow, 60, 10));
    //    Objects.add(new Rectangle(500, 300, 0, 1, 0, 0.5, 100, 50, 1, true, Color.black, Color.red, 80, 50));

    //    Forces.add(new Force(0.1, Math.PI/2, true)); //  gravity
    //    Forces.add(new Force(0.3, 0, false));        //  wind from left

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
