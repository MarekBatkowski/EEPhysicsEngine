package Physics;

public class Force
{
    double value;
    double angle;
    boolean gravityLike;    //  independent of object's mass

    public Force(double value, double angle, boolean gravityLike)
    {
        this.value = value;
        this.angle = angle;
        this.gravityLike = gravityLike;
    }
}