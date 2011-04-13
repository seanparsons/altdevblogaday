package com.altdevblogaday.dodinjava;


public class OOP
{
    public static void main(final String[] args)
    {
        final Vector[] vectors = new Vector[100000];
        for (int position = 0; position < vectors.length; position++)
        {
            vectors[position] = new Vector(1.1, 2.9, 3.4);
        }
        multiply(vectors);
        System.out.println(total(vectors));
    }

    public static double total(final Vector[] vectors)
    {
        double workingTotal = 0;
        for (int position = 0; position < vectors.length; position++)
        {
            workingTotal += vectors[position].total();
        }
        return workingTotal;
    }

    public static void multiply(final Vector[] vectors)
    {
        final double factor = 2;
        for (int position = 0; position < vectors.length; position++)
        {
            vectors[position].multiply(factor);
        }
    }
}
