package Physics;

import java.util.ArrayList;

public class Physics
{
    int ScreenSizeX, ScreenSizeY;
    ArrayList<Ball> Balls;
    ArrayList<Force> Forces;
    float Friction = (float) 2;   // speed lost with every hit

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
            for(Force f : Forces)
                ApplyForce(Balls.get(i), f);

            CheckWalls(Balls.get(i));

            for(int j=0; j<Balls.size(); j++)
                if(j>i) CheckCollisions(Balls.get(i), Balls.get(j));
            move(Balls.get(i));
        }
    }

    public void move(Ball ball)
    {
        //  System.out.println( Math.sqrt(Math.pow(ball.getxSpeed(),2)+Math.pow(ball.getySpeed(),2)) );

        if(ball.getSpeed() > Math.pow(ball.maxSpeed,2)) // x^2 + y^2 > maxSpeed^2
        {
            ball.setxSpeed(ball.maxSpeed*Math.cos(ball.getSpeedAngle()));
            ball.setySpeed(ball.maxSpeed*Math.sin(ball.getSpeedAngle()));
        }

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
            ball.setxSpeed(-ball.getxSpeed() * ball.elasticity);
        //    ApplyFriction(ball);
        }
        else if (ball.getX()+ball.getDiameter()/2 > ScreenSizeX-17)
        {
            ball.setX(ScreenSizeX-17-ball.getDiameter()/2);
            ball.setxSpeed(-ball.getxSpeed() * ball.elasticity);
        //    ApplyFriction(ball);
        }

        if (ball.getY() - ball.getDiameter()/2 < 0)
        {
            ball.setY(ball.getDiameter()/2);
            ball.setySpeed(-ball.getySpeed()*ball.elasticity);
        //    ApplyFriction(ball);
        }
        else if (ball.getY()+ball.getDiameter()/2 > ScreenSizeY-40)
        {
            ball.setY(ScreenSizeY-40-ball.getDiameter()/2);
            ball.setySpeed(-ball.getySpeed()*ball.elasticity);
        //    ApplyFriction(ball);
        }
    }

    void ApplyFriction(Ball ball)
    {
        if(ball.getSpeed()> Friction)
        {
            ball.setxSpeed(ball.getxSpeed() - Friction * Math.cos(ball.getSpeedAngle()));
            ball.setySpeed(ball.getySpeed() - Friction * Math.sin(ball.getSpeedAngle()));
        }
        else
        {
            ball.setxSpeed(0);
            ball.setySpeed(0);
        }
    }

    public void CheckCollisions(Ball ballOne, Ball ballTwo)
    {
        double deltaX = Math.abs(ballOne.getX() - ballTwo.getX());
        double deltaY = Math.abs(ballOne.getY() - ballTwo.getY());
        double distance = Math.pow(deltaX, 2) + Math.pow(deltaY, 2);

        if(distance <= (ballOne.getDiameter() / 2 + ballTwo.getDiameter() / 2) * (ballOne.getDiameter() / 2 + ballTwo.getDiameter() / 2))
        {
            double angle = Math.atan2(ballOne.getY()-ballTwo.getY(), ballOne.getX()-ballTwo.getX());

            double newxSpeed1 = (ballOne.getxSpeed() * (-3) + (14 * ballTwo.getxSpeed())) / 11;
            double newySpeed1 = (ballOne.getySpeed() * (-3) + (14 * ballTwo.getySpeed())) / 11;

            double newxSpeed2 = (ballTwo.getxSpeed() * (3) + (8 * ballOne.getxSpeed())) / 11;
            double newySpeed2 = (ballTwo.getySpeed() * (3) + (8 * ballOne.getySpeed())) / 11;

            ballOne.setxSpeed(newxSpeed1*ballOne.elasticity);
            ballOne.setySpeed(newySpeed1*ballOne.elasticity);
            ballTwo.setxSpeed(newxSpeed2*ballTwo.elasticity);
            ballTwo.setySpeed(newySpeed2*ballTwo.elasticity);
            //ApplyFriction(ballOne);
            //ApplyFriction(ballTwo);

            if(distance <= (ballOne.getDiameter() / 2 + ballTwo.getDiameter() / 2) * (ballOne.getDiameter() / 2 + ballTwo.getDiameter() / 2))
            {
                ballOne.setX(ballOne.getX()+ Math.cos(ballOne.getSpeedAngle()+Math.PI));
                ballOne.setY(ballOne.getY()+ Math.sin(ballOne.getSpeedAngle()+Math.PI));

                ballTwo.setX(ballTwo.getX()+ Math.cos(ballTwo.getSpeedAngle()+Math.PI));
                ballTwo.setY(ballTwo.getY()+ Math.sin(ballTwo.getSpeedAngle()+Math.PI));
            }
        }
    }

    double AngleBetween(Ball One, Ball Two)
    {
        return Math.atan2(Two.getY()-One.getY(), Two.getX()-One.getX());
    }
}
