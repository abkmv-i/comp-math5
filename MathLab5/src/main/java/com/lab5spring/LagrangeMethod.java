package com.lab5spring;

public class LagrangeMethod {
    private double[][] points;
    private double x;

    public double l(double[][] points, double x, int number) {
        double l = 1;
        for (int i = 0; i < points[0].length; i++) {
            if (i != number) {
                l *= (x - points[0][i]) / (points[0][number] - points[0][i]);
            }
        }
        return l;


    }

    public Double L(double[][] points, double x) {
        double result = 0;
        for (int i = 0; i < points[0].length; i++) {
            result += points[1][i] * l(points, x, i);
        }
        if (Double.isNaN(result) || Double.isInfinite(result)) {
            return null;
        }
        else {
            return result;
        }
    }
    public double[][] Lpoint(double[][] points) {
        double[][] result = new double[2][points[0].length];
        for (int i = 0; i < points[0].length; i++) {
            double x = points[0][i];
            double y = L(points, x);
            result[0][i] = y;
        }
        return result;
    }



    public double[][] interpolation(double[][] points) {
        Function function = new Function();
        double result[][] = new double[points[0].length][points[0].length + 1];
        for (int i = 0; i < points[0].length; i++) {
            result[i][0] = points[0][i];
            result[i][1] = points[1][i];
        }
        int countOfIterations = result.length - 1;
        for (int j = 1; j < points[0].length; j++) {
            for (int i = 0; i < countOfIterations; i++) {
                result[i][j + 1] = result[i + 1][j] - result[i][j];
            }
            countOfIterations--;
        }
        if (function.checkNanTable(result) && function.checkDuplicates(points)) {
            return result;
        }
        else {
            return null;
        }
    }

}

