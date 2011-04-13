package com.altdevblogaday.dodinjava;

public class Vectors
{
    private double[] x;
    private double[] y;
    private double[] z;

    public Vectors(final double[] x, final double[] y, final double[] z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void multiply(final double factor)
    {
        for (int position = 0; position < x.length; position++)
        {
            x[position] *= factor;
            y[position] *= factor;
            z[position] *= factor;
        }
    }

    public double total()
    {
        double workingTotal = 0;
        for (int position = 0; position < x.length; position++)
        {
            workingTotal += x[position] + y[position] + z[position];
        }
        return workingTotal;
    }
}
