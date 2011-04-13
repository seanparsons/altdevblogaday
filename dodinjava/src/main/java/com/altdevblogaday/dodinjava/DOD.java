package com.altdevblogaday.dodinjava;


public class DOD
{
    public static void main(final String[] args)
    {
        final double[] x = new double[100000];
        final double[] y = new double[100000];
        final double[] z = new double[100000];

        for (int position = 0; position < x.length; position++)
        {
            x[position] = 1.1;
            y[position] = 2.9;
            z[position] = 3.4;
        }
        final Vectors vectors = new Vectors(x, y, z);
        multiply(vectors);
        System.out.println(total(vectors));
    }

    public static double total(final Vectors vectors)
    {
        return vectors.total();
    }

    public static void multiply(final Vectors vectors)
    {
        final double factor = 2;
        vectors.multiply(factor);
    }
}
