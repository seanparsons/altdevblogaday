package com.altdevblogaday.dodinjava;

public class Vector
{
    private double x;
    private double y;
    private double z;

    public Vector(final double x, final double y, final double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void multiply(final double factor)
    {
        x *= factor;
        y *= factor;
        z *= factor;
    }

    public double total()
    {
        return x + y + z;
    }
}