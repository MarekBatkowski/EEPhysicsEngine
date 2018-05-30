package Physics;

public final class Vector
{
    private double x;
    private double y;

    public Vector(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public Vector(double angle, double value, boolean fromvector)
    {
        this.x = value*Math.cos(angle);
        this.y = value*Math.sin(angle);
    }

    public void setX(double x) { this.x = x; }

    public double getX() { return x; }

    public void setY(double y) { this.y = y; }

    public double getY() { return y; }

    public double getAngle()
    {
        return Math.atan2(x, y);
    }

    public double getValue()
    {
        return Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
    }

    public Vector add(Vector other)
    {
        return new Vector(x + other.x, y + other.y);
    }

    public Vector sub(Vector other)
    {
        return new Vector(x - other.x, y - other.y);
    }

    public Vector mul(Vector other)
    {
        return new Vector(x * other.x, y * other.y);
    }

    public Vector mul(double multipler)
    {
        return new Vector(x * multipler, y * multipler);
    }
}
