package Physics;

import javax.swing.JFrame;

public class Main
{
    public static void main(String[] args)
    {
        int sizeX = 800;
        int sizeY = 800;

        JFrame frame = new JFrame ();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(sizeX, sizeY);
        //frame.setUndecorated(true);
        Physics physics = new Physics(sizeX, sizeY);
        Gui balls = new Gui(physics);
        frame.add(balls);
        frame.setVisible(true);
    }
}