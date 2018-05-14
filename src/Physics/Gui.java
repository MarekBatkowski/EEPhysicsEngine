package Physics;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

public class Gui extends JPanel implements Runnable
{
    Ball ball1, ball2, ball3;

    Thread animator;

    Physics physics;
    ArrayList<Ball> Balls = new ArrayList<>();
    ArrayList<Force> Forces = new ArrayList<>();

    public Gui(Physics physics)
    {
        this.physics = physics;

        ball1 = new Ball(50, 100, 0, 0, 40, 10, Color.black, Color.blue, 50);
        ball2 = new Ball(250, 300, 0, 0, 70, 10, Color.black, Color.red, 70);
        ball3 = new Ball(200, 200, 0, 0, 50, 10, Color.black, Color.green, 50);

        Balls.add(ball1);
        Balls.add(ball2);
        Balls.add(ball3);


        Force wind = new Force(0.02, 0);
        Force gravity = new Force(1, Math.toRadians(90));
        Forces.add(gravity);
        Forces.add(wind);

        physics.setBalls(Balls);
        physics.setForces(Forces);

        animator = new Thread(this);
        animator.start();
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        for(Ball b : Balls)
        {
            g.setColor(b.color);
            g.fillOval((int) (b.getX() - b.getDiameter()/2), (int) (b.getY() - b.getDiameter()/2), b.getDiameter(), b.getDiameter());
            g.setColor(b.color2);
            g.fillOval((int) (b.getX() - b.getRingSize()/2), (int) (b.getY() - b.getRingSize()/2), b.getRingSize(), b.getRingSize());
            double angle = b.getSpeedVector();
            g.drawLine((int) b.getX(), (int) b.getY(), (int) (b.getX() + Math.cos(angle)*b.getDiameter()/2), (int) (b.getY() + Math.sin(angle)*b.getDiameter()/2));
        }
    }

    @Override
    public void run()
    {
        while (true)
        {
            physics.Update(Balls);
            repaint();

            try
            {
                Thread.sleep(10);
            }
            catch (InterruptedException ex)
            {

            }
        }
    }
}
