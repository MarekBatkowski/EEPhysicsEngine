package Physics;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Physics extends Thread
{
    int ScreenSizeX, ScreenSizeY;
    ArrayList<PhysicsObject> objects;
    ArrayList<Force> Forces;
    float Friction = (float) 0;   // speed lost with every hit

    public Physics(int ScreenSizeX, int ScreenSizeY)
    {
        this.ScreenSizeX = ScreenSizeX;
        this.ScreenSizeY = ScreenSizeY;
    }

    @Override
    public void run()
    {
        while (true)
        {
            try
            {
                Update(objects);
                Thread.sleep(5);
            }
            catch (InterruptedException ex) { }
        }
    }

    public void setObjects(ArrayList<PhysicsObject> Balls)
    {
        this.objects = Balls;
    }

    public void setForces(ArrayList<Force> Forces)
    {
        this.Forces = Forces;
    }

    public void Update(ArrayList<PhysicsObject> Objects)
    {
        for(int i=0; i<Objects.size(); i++)
        {
            for(Force f : Forces)
                ApplyForce(Objects.get(i), f);

            CheckWalls(Objects.get(i));

            for(int j=0; j<Objects.size(); j++)
                if(j>i) CheckCollisions(Objects.get(i), Objects.get(j));
            move(Objects.get(i));
        }
    }

    public void move(PhysicsObject object)
    {
        //  System.out.println( Math.sqrt(Math.pow(ball.getxSpeed(),2)+Math.pow(ball.getySpeed(),2)) );

        if(object.getSpeed() > Math.pow(object.maxSpeed,2)) // x^2 + y^2 > maxSpeed^2
        {
            object.setxSpeed(object.maxSpeed*Math.cos(object.getSpeedAngle()));
            object.setySpeed(object.maxSpeed*Math.sin(object.getSpeedAngle()));
        }

        object.setX(object.getX()+object.getxSpeed());
        object.setY(object.getY()+object.getySpeed());

        object.angle+=object.rotationSpeed;
    }

    public void ApplyForce(PhysicsObject physicsObject, Force force)
    {
        double deltaX, deltaY;

        if(force.gravityLike)
        {
            deltaX = Math.cos(force.angle) * force.value;
            deltaY = Math.sin(force.angle) * force.value;
        }
        else
        {
            deltaX = Math.cos(force.angle) * force.value/physicsObject.mass;
            deltaY = Math.sin(force.angle) * force.value/physicsObject.mass;
        }

        physicsObject.setxSpeed(physicsObject.getxSpeed()+deltaX);
        physicsObject.setySpeed(physicsObject.getySpeed()+deltaY);
    }

    public void CheckWalls(PhysicsObject object)
    {
        if(object.type.equals("Circle"))
        {
            if (object.getX()-object.getDiameter()/2 < 0)
            {
                object.setX(object.getDiameter()/2);
                object.setxSpeed(-object.getxSpeed() * object.elasticity);
                ApplyFriction(object);
            }
            else if (object.getX()+object.getDiameter()/2 > ScreenSizeX-17)
            {
                object.setX(ScreenSizeX-17-object.getDiameter()/2);
                object.setxSpeed(-object.getxSpeed() * object.elasticity);
                ApplyFriction(object);
            }

            if (object.getY() - object.getDiameter()/2 < 0)
            {
                object.setY(object.getDiameter()/2);
                object.setySpeed(-object.getySpeed()*object.elasticity);
                ApplyFriction(object);
            }
            else if (object.getY()+object.getDiameter()/2 > ScreenSizeY-40)
            {
                object.setY(ScreenSizeY-40-object.getDiameter()/2);
                object.setySpeed(-object.getySpeed()*object.elasticity);
                ApplyFriction(object);
            }
        }
        else if(object.type.equals("Rectangle"))
        {
            Point2D points[] = object.getPoints();
            for(int i=0; i<4; i++)
            {
                if (points[i].getX()<0)
                {
                 //   while(points[i].getX()<0)
                 //       object.x++;
                    object.setxSpeed(-object.getxSpeed() * object.elasticity);
                    ApplyFriction(object);
                    object.rotationSpeed=-object.rotationSpeed;
                }
                else if (points[i].getX()>ScreenSizeX-17)
                {
                 //   while(points[i].getX()>ScreenSizeX-17)
                 //       object.x--;
                    object.setxSpeed(-object.getxSpeed() * object.elasticity);
                    ApplyFriction(object);
                    object.rotationSpeed=-object.rotationSpeed;
                }

                if (points[i].getY()<0)
                {
                    int temp = (int) points[i].getY();
                    object.y-=temp+2;

                    object.setySpeed(-object.getySpeed()*object.elasticity);
                    ApplyFriction(object);
                    object.rotationSpeed=-object.rotationSpeed;
                }
                else if (points[i].getY()>ScreenSizeY-40)
                {
                    int temp = (int) points[i].getY()-(ScreenSizeY-40);
                    object.y-=temp+2;

                    object.setySpeed(-object.getySpeed()*object.elasticity);
                    ApplyFriction(object);
                    object.rotationSpeed=-object.rotationSpeed;
                }
            }
        }
    }

    void ApplyFriction(PhysicsObject object)
    {
        if(object.getSpeed()> Friction)
        {
            object.setxSpeed(object.getxSpeed() - Friction * Math.cos(object.getSpeedAngle()));
            object.setySpeed(object.getySpeed() - Friction * Math.sin(object.getSpeedAngle()));
        }
        else
        {
            object.setxSpeed(0);
            object.setySpeed(0);
        }
    }

    double BounceAngle(PhysicsObject objectOne, PhysicsObject objectTwo)
    {
       return -objectOne.getSpeedAngle()+ 2*AngleBetween(objectOne, objectTwo)+Math.PI;
    }

    double AngleBetween(PhysicsObject One, PhysicsObject Two)
    {
        return Math.atan2(Two.getY()-One.getY(), Two.getX()-One.getX());
    }

    public void CheckCollisions(PhysicsObject objectOne, PhysicsObject objectTwo)
    {
        if(objectOne.type.equals("Circle") && objectTwo.type.equals("Circle"))
        {
            double distance = Math.pow(objectOne.getX()-objectTwo.getX(), 2) + Math.pow(objectOne.getY()-objectTwo.getY(), 2);

            if(distance <= (objectOne.getDiameter()/2 + objectTwo.getDiameter()/2) * (objectOne.getDiameter()/2 + objectTwo.getDiameter()/2))
            {
                objectOne.setX(objectOne.getX()-objectOne.getxSpeed());
                objectOne.setY(objectOne.getY()-objectOne.getySpeed());
                objectTwo.setX(objectTwo.getX()-objectTwo.getxSpeed());
                objectTwo.setY(objectTwo.getY()-objectTwo.getySpeed());

                double newxSpeed1 = objectOne.getxSpeed() + objectOne.getSpeed()*Math.cos(BounceAngle(objectOne, objectTwo));
                double newySpeed1 = objectOne.getySpeed() + objectOne.getSpeed()*Math.sin(BounceAngle(objectOne, objectTwo));

                double newxSpeed2 = objectTwo.getxSpeed() + objectTwo.getSpeed()*Math.cos(BounceAngle(objectTwo, objectOne));
                double newySpeed2 = objectTwo.getySpeed() + objectTwo.getSpeed()*Math.sin(BounceAngle(objectTwo, objectOne));

                // well fug :DDDDD
/*
                double newxSpeed1 = (objectOne.getxSpeed() * (-3) + (14 * objectTwo.getxSpeed())) / 11;
                double newySpeed1 = (objectOne.getySpeed() * (-3) + (14 * objectTwo.getySpeed())) / 11;

                double newxSpeed2 = (objectTwo.getxSpeed() * (3) + (8 * objectOne.getxSpeed())) / 11;
                double newySpeed2 = (objectTwo.getySpeed() * (3) + (8 * objectOne.getySpeed())) / 11;
*/
                objectOne.setxSpeed(newxSpeed1*objectOne.elasticity);
                objectOne.setySpeed(newySpeed1*objectOne.elasticity);
                objectTwo.setxSpeed(newxSpeed2*objectTwo.elasticity);
                objectTwo.setySpeed(newySpeed2*objectTwo.elasticity);
            }
        }
    }
}
