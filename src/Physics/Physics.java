package Physics;

import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Physics extends Thread
{
    int ScreenSizeX, ScreenSizeY;
    ArrayList<PhysicsObject> objects;
    ArrayList<Force> Forces;
    private static DecimalFormat df3 = new DecimalFormat("0.###");

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
            CheckWalls(Objects.get(i));
            for(int j=0; j<Objects.size(); j++)
                if(j>i) CheckCollisions(Objects.get(i), Objects.get(j));

            for(Force f : Forces)
                ApplyForce(Objects.get(i), f);

            move(Objects.get(i));
        }
    }

    public void move(PhysicsObject obj)
    {
        //  System.out.println( Math.sqrt(Math.pow(ball.getxSpeed(),2)+Math.pow(ball.getySpeed(),2)) );

        if(obj.getSpeed() > Math.pow(obj.maxSpeed,2)) // x^2 + y^2 > maxSpeed^2
        {
            obj.setxSpeed(obj.maxSpeed*Math.cos(obj.getSpeedAngle()));
            obj.setySpeed(obj.maxSpeed*Math.sin(obj.getSpeedAngle()));
        }

        obj.setX(obj.getX()+obj.getxSpeed());
        obj.setY(obj.getY()+obj.getySpeed());

        obj.angle+=obj.rotationSpeed;
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

    public void CheckWalls(PhysicsObject obj)
    {
        if(obj.type.equals("Circle"))
        {
            if (obj.getX()-obj.getDiameter()/2 < 0)
            {
                obj.setX(obj.getDiameter()/2);
                obj.setxSpeed(-obj.getxSpeed() * obj.elasticity);
            }
            else if (obj.getX()+obj.getDiameter()/2 > ScreenSizeX-17)
            {
                obj.setX(ScreenSizeX-20-obj.getDiameter()/2);
                obj.setxSpeed(-obj.getxSpeed() * obj.elasticity);
            }

            if (obj.getY() - obj.getDiameter()/2 < 0)
            {
                obj.setY(obj.getDiameter()/2);
                obj.setySpeed(-obj.getySpeed()*obj.elasticity);
            }
            else if (obj.getY()+obj.getDiameter()/2 > ScreenSizeY-40)
            {
                obj.setY(ScreenSizeY-40-obj.getDiameter()/2);
                obj.setySpeed(-obj.getySpeed()*obj.elasticity);
            }
        }
        else if(obj.type.equals("Rectangle"))
        {
            Point2D points[] = obj.getPoints();
            for(int i=0; i<4; i++)
            {
                if (points[i].getX()<0)
                {
                 //   while(points[i].getX()<0)
                 //       obj.x++;
                    obj.setxSpeed(-obj.getxSpeed() * obj.elasticity);
                    obj.rotationSpeed=-obj.rotationSpeed;
                }
                else if (points[i].getX()>ScreenSizeX-17)
                {
                 //   while(points[i].getX()>ScreenSizeX-17)
                 //       obj.x--;
                    obj.setxSpeed(-obj.getxSpeed() * obj.elasticity);
                    obj.rotationSpeed=-obj.rotationSpeed;
                }

                if (points[i].getY()<0)
                {
                    int temp = (int) points[i].getY();
                    obj.y-=temp+2;

                    obj.setySpeed(-obj.getySpeed()*obj.elasticity);
                    obj.rotationSpeed=-obj.rotationSpeed;
                }
                else if (points[i].getY()>ScreenSizeY-40)
                {
                    int temp = (int) points[i].getY()-(ScreenSizeY-40);
                    obj.y-=temp+2;

                    obj.setySpeed(-obj.getySpeed()*obj.elasticity);
                    obj.rotationSpeed=-obj.rotationSpeed;
                }
            }
        }
    }

    double AngleBetween(PhysicsObject objOne, PhysicsObject objTwo)
    {
        return Math.atan2(objTwo.getY() - objOne.getY(), objTwo.getX() - objOne.getX());
    }

    public void CheckCollisions(PhysicsObject objOne, PhysicsObject objTwo)
    {
        if(objOne.type.equals("Circle") && objTwo.type.equals("Circle"))
        {
            double distance = Math.sqrt( Math.pow(objOne.getX()-objTwo.getX(), 2) + Math.pow(objOne.getY()-objTwo.getY(), 2) );

            if(distance <= (objOne.getDiameter()/2 + objTwo.getDiameter()/2))
            {
                double commonMass = objOne.getMass()+objTwo.getMass();
                double commonElasticy = objOne.getElasticity()*objTwo.getElasticity();

                // prevents objects from entering each other
                    Vector D1 = new Vector(((objOne.getDiameter() / 2 + objTwo.getDiameter() / 2) - distance) * objOne.mass / commonMass, AngleBetween(objTwo, objOne), true);
                    Vector D2 = new Vector(((objOne.getDiameter() / 2 + objTwo.getDiameter() / 2) - distance) * objTwo.mass / commonMass, AngleBetween(objOne, objTwo), true);

                    objOne.setX(objOne.getX() + D1.getX());
                    objOne.setY(objOne.getY() + D1.getY());
                    objTwo.setX(objTwo.getX() + D2.getX());
                    objTwo.setY(objTwo.getY() + D2.getY());

                //momentums
                Vector M1 = objOne.getSpeedVector().mul(objOne.getMass());
                Vector M2 = objTwo.getSpeedVector().mul(objTwo.getMass());

                // non elastic parts
                Vector M1d = objTwo.getSpeedVector().mul(objTwo.getMass());
                Vector M2d = objOne.getSpeedVector().mul(objOne.getMass());

                //elastic parts
                Vector M1dE = new Vector(objTwo.getSpeed()*Math.cos(objTwo.getSpeedAngle()-AngleBetween(objTwo, objOne)), AngleBetween(objTwo, objOne), true);
                Vector M2dE = new Vector(objOne.getSpeed()*Math.cos(objOne.getSpeedAngle()-AngleBetween(objOne, objTwo)), AngleBetween(objOne, objTwo), true);

                objOne.setSpeed(M1.add(M1d).add( M1dE.sub(M2dE).mul(objTwo.getMass()).mul(commonElasticy) ).div(commonMass));
                objTwo.setSpeed(M2.add(M2d).add( M2dE.sub(M1dE).mul(objOne.getMass()).mul(commonElasticy) ).div(commonMass));
            }
        }
    }
}
