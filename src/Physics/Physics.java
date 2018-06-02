package Physics;

import java.awt.geom.Point2D;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Physics extends Thread
{
    private static DecimalFormat df = new DecimalFormat("0.###");
    int ScreenSizeX, ScreenSizeY;
    ArrayList<PhysicsObject> objects;
    ArrayList<Force> Forces;

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
                obj.setxSpeed(-obj.getxSpeed()*obj.elasticity);
            }
            else if (obj.getX()+obj.getDiameter()/2 > ScreenSizeX-20)
            {
                obj.setX(ScreenSizeX-20-obj.getDiameter()/2);
                obj.setxSpeed(-obj.getxSpeed()*obj.elasticity);
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
            Rectangle temp = (Rectangle) obj;

            Point2D points[] = temp.getPoints();
            for(int i=0; i<1; i++)
            {
                if (points[i].getX()<0)
                {
                    System.out.println(i);
                    obj.setxSpeed(-obj.getxSpeed()*obj.elasticity);
                }
                else if (points[i].getX()>ScreenSizeX-20)
                {
                    System.out.println(i);
                    obj.setxSpeed(-obj.getxSpeed()*obj.elasticity);
                }

                if (points[i].getY()<0)
                {
                    System.out.println(i);
                    obj.setySpeed(-obj.getySpeed()*obj.elasticity);
                }
                else if (points[i].getY()>ScreenSizeY-40)
                {
                    System.out.println(i);
                    obj.setySpeed(-obj.getySpeed()*obj.elasticity);
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
            double distance = Math.sqrt( Math.pow(objOne.getX()-objTwo.getX(),2) + Math.pow(objOne.getY()-objTwo.getY(),2) );

            if(distance <= (objOne.getDiameter()/2 + objTwo.getDiameter()/2))
            {
                double commonMass = objOne.getMass()+objTwo.getMass();
                double commonElasticy = objOne.getElasticity()*objTwo.getElasticity();

                // prevents objects from entering each other
                Vector D1 = new Vector(((objOne.getDiameter()/2 + objTwo.getDiameter()/2) - distance) * objOne.mass / commonMass, AngleBetween(objTwo, objOne), true);
                Vector D2 = new Vector(((objOne.getDiameter()/2 + objTwo.getDiameter()/2) - distance) * objTwo.mass / commonMass, AngleBetween(objOne, objTwo), true);

                objOne.setX(objOne.getX() + D1.getX());
                objOne.setY(objOne.getY() + D1.getY());
                objTwo.setX(objTwo.getX() + D2.getX());
                objTwo.setY(objTwo.getY() + D2.getY());

                //momentums
                Vector V1 = objOne.getSpeedVector();
                Vector V2 = objTwo.getSpeedVector();

                //elastic
                Vector CosV1 = new Vector(objOne.getSpeed()*Math.cos(objOne.getSpeedAngle()-AngleBetween(objTwo, objOne)), AngleBetween(objTwo, objOne), true);
                Vector SinV1 = V1.sub(CosV1);
                Vector CosV2 = new Vector(objTwo.getSpeed()*Math.cos(objTwo.getSpeedAngle()-AngleBetween(objOne, objTwo)), AngleBetween(objOne, objTwo), true);
                Vector SinV2 = V2.sub(CosV2);

                System.out.println("> " + objOne.getSpeedVector().toString() + " energy: " + df.format(Math.pow(objOne.getSpeed(),2)*objOne.getMass()) + " angle = " + df.format(Math.toDegrees(objOne.getSpeedAngle()-AngleBetween(objTwo, objOne))));
                System.out.println("CosV1 = " + df.format(Math.cos(objOne.getSpeedAngle()-AngleBetween(objTwo, objOne))) + "      " + CosV1.toString());
                System.out.println("SinV1 = " + df.format(Math.sin(objOne.getSpeedAngle()-AngleBetween(objTwo, objOne))) + "      " + SinV1.toString());
                System.out.println("");
                System.out.println("> " + objTwo.getSpeedVector().toString() + " energy: " + df.format(Math.pow(objTwo.getSpeed(),2)*objTwo.getMass()) + " angle = " + df.format(Math.toDegrees(objTwo.getSpeedAngle()-AngleBetween(objOne, objTwo))));
                System.out.println("CosV2 = " + df.format(Math.cos(objTwo.getSpeedAngle()-AngleBetween(objOne, objTwo))) + "      " + CosV2.toString());
                System.out.println("SinV2 = " + df.format(Math.sin(objTwo.getSpeedAngle()-AngleBetween(objOne, objTwo))) + "      " + SinV2.toString());
                System.out.println("");

                //  non elastic collision speed - common for both objects
                Vector VnE = V1.mul(objOne.getMass()).add( (V2).mul(objTwo.getMass()) ).div(commonMass);

                //  elastic parts
                Vector V1E = V1.add( CosV2.mul(objTwo.getMass()).sub( CosV1.mul(objTwo.getMass()) ).div(commonMass/2) );
                Vector V2E = V2.add( CosV1.mul(objOne.getMass()).sub( CosV2.mul(objOne.getMass()) ).div(commonMass/2) );

                //objOne.setSpeed(VnE.mul(1-commonElasticy));
                //objTwo.setSpeed(VnE.mul(1-commonElasticy));

                objOne.setSpeed(V1E);
                objTwo.setSpeed(V2E);

                System.out.println("> " + objOne.getSpeedVector().mul(objOne.getMass()).toString() + " energy: " + df.format(Math.pow(objOne.getSpeed(),2)*objOne.getMass()));
                System.out.println("> " + objTwo.getSpeedVector().mul(objOne.getMass()).toString() + " energy: " + df.format(Math.pow(objTwo.getSpeed(),2)*objTwo.getMass()));
                System.out.println("");
            }
        }

        if(objOne.type.equals("Rectangle") && objTwo.type.equals("Circle") || objOne.type.equals("Circle") && objTwo.type.equals("Rectangle"))
        {
            if(objOne.type.equals("Rectangle") && objTwo.type.equals("Circle"))
            {
                PhysicsObject temp = objOne;
                objOne = objTwo;    //  circle
                objTwo = temp;      //  rectangle
            }

            for(int i=0; i<4; i++)
                objOne.getPoints()[i] = new Point2D.Double(objOne.getDiameter()*Math.cos(objTwo.angle+Math.PI/2*i),Math.sin(objTwo.angle+Math.PI/2*i));
        }

        if(objOne.type.equals("Rectangle") && objTwo.type.equals("Rectangle"))
        {

        }
    }
}
