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
        double platformElasticy = 1;

        physics.setObjects(Objects);
        physics.setForces(Forces);

        /////WALLS/////
        Objects.add(new Rectangle(390, 760, 0, 0, 0, 0, 100, 50, platformElasticy, false, Color.gray, Color.black, 1000, 20));
        Objects.add(new Rectangle(390, 0, 0, 0, 0, 0, 100, 50, platformElasticy, false, Color.gray, Color.black, 1000, 20));
        Objects.add(new Rectangle(0, 400, 0, 0, 90, 0, 100, 50, platformElasticy, false, Color.gray, Color.black, 1000, 20));
        Objects.add(new Rectangle(780, 400, 0, 0, 90, 0, 100, 50, platformElasticy, false, Color.gray, Color.black, 1000, 20));
        /////WALLS/////

        //X//
        Objects.add(new Rectangle(390, 380, 0, 0, 45, 0, 100, 50, platformElasticy, false, Color.gray, Color.black, 400, 20));
        Objects.add(new Rectangle(390, 380, 0, 0, -45, 0, 100, 50, platformElasticy, false, Color.gray, Color.black, 400, 20));
        Objects.add(new Ball(390, 380, 0, 0, 0, 0, 10, 50, platformElasticy, false, Color.gray, Color.black, 100, 10));
        //X//

        Objects.add(new Ball(300, 280, 4, 3, 0, 0, 100, 50, elasticity, true, Color.black, Color.red, 60, 10));
        Objects.add(new Ball(500, 342, 0, 0, 0, 0, 100, 25, elasticity, true, Color.black, Color.yellow, 40, 10));
        for(int i=0;i <10; i++)
            Objects.add(new Ball(200+i*20, 600, 0, 0, 0, 0, 100, 50, elasticity, true, Color.black, Color.green, 30, 10));
     //   Forces.add(new Force(0.1, Math.PI/2, true)); //  gravity
     //   Forces.add(new Force(1, 0, false));        //  wind from left


    //    Objects.add(new Ball(100, 600, 1, 0.5, 0, 0, 100, 50, elasticity, true, Color.black, Color.red, 60, 10));
    //    Objects.add(new Ball(350, 375, 0, 0, 0, 0, 10, 50, platformElasticy, false, Color.gray, Color.black, 100, 10));
    //    Objects.add(new Rectangle(500, 300, 0, 0, 45, 0, 100, 50, 1, false, Color.black, Color.red, 300, 50));

    //    Forces.add(new Force(0.1, Math.PI/2, true)); //  gravity
    //    Forces.add(new Force(0.3, 0, false));        //  wind from left
    //    Forces.add(new Force(0.2, Math.PI*1.5, false));  // wind from below

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
