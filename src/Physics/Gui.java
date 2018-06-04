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
        double platformElasticy = 1;

        physics.setObjects(Objects);
        physics.setForces(Forces);


        Objects.add(new Ball(300, 180, 0, -3, 0, 0, 100, 50, elasticity, true, Color.black, Color.red, 60, 10));
        for(int i=0;i <2; i++)
            Objects.add(new Ball(500+i*50, 150, 0, 0, 0, 0, 50, 25, elasticity, true, Color.black, Color.yellow, 40, 10));
        for(int i=0;i <3; i++)
            Objects.add(new Ball(200+i*20, 100, 0, 0, 0, 0, 20, 50, elasticity, true, Color.black, Color.green, 30, 10));

        //WALLS//
        Objects.add(new Rectangle(390, 760, 0, 0, 0, 0, 100, 50, platformElasticy, false, Color.gray, Color.black, 1000, 20));
        Objects.add(new Rectangle(390, 0, 0, 0, 0, 0, 100, 50, platformElasticy, false, Color.gray, Color.black, 1000, 20));
        Objects.add(new Rectangle(0, 400, 0, 0, 90, 0, 100, 50, platformElasticy, false, Color.gray, Color.black, 1000, 20));
        Objects.add(new Rectangle(780, 400, 0, 0, 90, 0, 100, 50, platformElasticy, false, Color.gray, Color.black, 1000, 20));
        //WALLS//

        /*/X//
        Objects.add(new Rectangle(390, 380, 0, 0, 45, 0.1, 100, 50, platformElasticy, false, Color.gray, Color.black, 400, 20));
        Objects.add(new Rectangle(390, 380, 0, 0, -45, 0.1, 100, 50, platformElasticy, false, Color.gray, Color.black, 400, 20));
        Objects.add(new Ball(390, 380, 0, 0, 0, 0, 10, 50, platformElasticy, false, Color.gray, Color.black, 100, 10));
        //X/*/

        //SLIDES//
        Objects.add(new Rectangle(230, 200, 0, 0, 15, 0, 0, 50, platformElasticy, false, Color.gray, Color.black, 500, 20));
        Objects.add(new Rectangle(420, 400, 0, 0, -15, 0, 0, 50, platformElasticy, false, Color.gray, Color.black, 500, 20));
        Objects.add(new Rectangle(250, 650, 0, 0, 25, 0, 0, 50, platformElasticy, false, Color.gray, Color.black, 600, 20));
        Objects.add(new Rectangle(660, 380, 0, 0, 90, 0, 100, 50, platformElasticy, false, Color.gray, Color.black, 540, 20));
        Objects.add(new Rectangle(495, 120, 0, 0, 0, 0, 100, 50, platformElasticy, false, Color.gray, Color.black, 350, 20));
        Forces.add(new Forcefield(0.3,-90, true, 720, 430, 640, 100, true));
        Forces.add(new Forcefield(0.3, 180, true, 620, 60, 300, 100, true));
        Forces.add(new Forcefield(0.3, 0, true, 620, 700, 100, 100, true));
        //////////

        Forces.add(new Force(0.25, 90, true)); //  gravity
        //Forces.add(new Force(0.3, 0, false));        //  wind from left

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
        for(Force force : Forces)
            if(force instanceof Forcefield) ((Forcefield) force).draw(g2d);
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
