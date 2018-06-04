package Physics;

public class Force
{
    double value;
    double angle;
    boolean gravityLike;    //  independent of object's mass

    public Force(double value, double angle, boolean gravityLike)
    {
        this.value = value;
        this.angle = Math.toRadians(angle);
        this.gravityLike = gravityLike;
    }

    public void ApplyForce(PhysicsObject obj)
    {
        if(obj.movable)
        {
            double deltaX, deltaY;

            if(gravityLike)
            {
                deltaX = Math.cos(angle) * value;
                deltaY = Math.sin(angle) * value;
            }
            else
            {
                deltaX = Math.cos(angle) * value/obj.mass;
                deltaY = Math.sin(angle) * value/obj.mass;
            }

            obj.setxSpeed(obj.getxSpeed()+deltaX);
            obj.setySpeed(obj.getySpeed()+deltaY);
        }
    }
}