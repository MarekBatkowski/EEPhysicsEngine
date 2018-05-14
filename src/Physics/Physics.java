package Physics;

import java.util.ArrayList;

public class Physics
{
    int ScreenSizeX, ScreenSizeY;
    ArrayList<Ball> Balls;
    ArrayList<Force> Forces;

    public Physics(int ScreenSizeX, int ScreenSizeY)
    {
        this.ScreenSizeX = ScreenSizeX;
        this.ScreenSizeY = ScreenSizeY;
    }

    public void setBalls(ArrayList<Ball> Balls)
    {
        this.Balls = Balls;
    }

    public void setForces(ArrayList<Force> Forces)
    {
        this.Forces = Forces;
    }

    public void Update(ArrayList<Ball> Balls)
    {
        for(int i=0; i<Balls.size(); i++)
        {
            move(Balls.get(i));
            CheckWalls(Balls.get(i));
            for(int j=0; j<Balls.size(); j++)
                if(j>i) CheckCollisions(Balls.get(i), Balls.get(j));

            for(Force f : Forces)
                ApplyForce(Balls.get(i), f);
        }
    }

    public void move(Ball ball)
    {
        ball.setX(ball.getX()+ball.getxSpeed());
        ball.setY(ball.getY()+ball.getySpeed());
    }

    public void ApplyForce(Ball ball, Force force)
    {
        double deltaX = Math.cos(force.angle)*force.value;
        double deltaY = Math.sin(force.angle)*force.value;

        ball.setxSpeed(ball.getxSpeed()+deltaX);
        ball.setySpeed(ball.getySpeed()+deltaY);
    }

    public void CheckWalls(Ball ball)
    {
        if (ball.getX()-ball.getDiameter()/2 < 0)
        {
            ball.setX(ball.getDiameter()/2);
            ball.setxSpeed(-ball.getxSpeed());
        }
        else if (ball.getX()+ball.getDiameter()/2 > ScreenSizeX-10)
        {
            ball.setX(ScreenSizeX-10-ball.getDiameter()/2);
            ball.setxSpeed(-ball.getxSpeed());
        }

        if (ball.getY() - ball.getDiameter()/2 < 0)
        {
            ball.setY(ball.getDiameter()/2);
            ball.setySpeed(-ball.getySpeed());
        }
        else if (ball.getY()+ball.getDiameter()/2 > ScreenSizeY-40)
        {
            ball.setY(ScreenSizeY-40-ball.getDiameter()/2);
            ball.setySpeed(-ball.getySpeed());
        }
    }

    public void CheckCollisions(Ball ballOne, Ball ballTwo)
    {
        double deltaX = Math.abs(ballOne.getX() - ballTwo.getX());
        double deltaY = Math.abs(ballOne.getY() - ballTwo.getY());
        double distance = deltaX * deltaX + deltaY * deltaY;

        if (distance < (ballOne.getDiameter() / 2 + ballTwo.getDiameter() / 2) * (ballOne.getDiameter() / 2 + ballTwo.getDiameter() / 2))
        {
            double newxSpeed1 = (ballOne.getxSpeed() * (-3) + (14 * ballTwo.getxSpeed())) / 11;
            double newySpeed1 = (ballOne.getySpeed() * (-3) + (14 * ballTwo.getySpeed())) / 11;

            double newxSpeed2 = (ballTwo.getxSpeed() * (3) + (8 * ballOne.getxSpeed())) / 11;
            double newySpeed2 = (ballTwo.getySpeed() * (3) + (8 * ballOne.getySpeed())) / 11;

            ballOne.setxSpeed(newxSpeed1);
            ballOne.setySpeed(newySpeed1);
            ballTwo.setxSpeed(newxSpeed2);
            ballTwo.setySpeed(newySpeed2);
        }
    }
}
