package com.lab5spring.Charts;

import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.util.function.UnaryOperator;

public class Chart {

    public JFreeChart drawChart(double[][] points, double x, double result, int number,
                                double[][] resultPoints, String titleResult) {

        XYSeries series = new XYSeries("Points");

        UnaryOperator<Double> f1 = Math::sqrt;

        UnaryOperator<Double> f3 = Math::sin;

        UnaryOperator<Double> f2 = (o) -> 2 * Math.pow(o, 2) + 3 * x - 2;
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;

        for (int i = 0; i < points[0].length; i++) {
            min = Math.min(min, points[0][i]);
            max = Math.max(max, points[0][i]);
        }


        double minY = points[1][0];

        double maxY = points[1][points.length - 1];


        if (number == 1) {
            for (double i = min - 0.5; i <= max + 0.5; i += 0.1) {
                series.add(i, f1.apply(i));
            }
        } else if (number == 2) {
            for (double i = min - 0.5; i <= max + 0.5; i += 0.1) {
                series.add(i, f2.apply(i));
            }
        } else if (number == 3) {
            for (double i = min - 0.5; i <= max + 0.5; i += 0.1) {
                series.add(i, f3.apply(i));
            }
        } else {
            for (int i = 0; i < points[0].length; i++) {
                series.add(points[0][i], points[1][i]);
            }
        }

        XYSeries resultSeries = new XYSeries(titleResult);
        for (int i = 0; i < resultPoints[0].length ; i++) {
            resultSeries.add(points[0][i], resultPoints[0][i]);
        }

        XYDataset dataset = new XYSeriesCollection();
        ((XYSeriesCollection) dataset).addSeries(series);
        ((XYSeriesCollection) dataset).addSeries(resultSeries);

        JFreeChart lineChart = ChartFactory.createXYLineChart("Fi(x)", "x",
                "Y", dataset, PlotOrientation.VERTICAL,
                true, true, false);

        XYPlot plot = lineChart.getXYPlot();
        XYSplineRenderer renderer = new XYSplineRenderer();

        plot.setRenderer(renderer);
        plot.setDataset(dataset);

        renderer.setSeriesPaint(0, Color.decode("#19674D")); // 0 - индекс серии данных
        if (titleResult.equals("Lagrange")) {
            renderer.setSeriesPaint(1, Color.BLUE); // 1 - индекс серии данных
        } else {
            renderer.setSeriesPaint(1, Color.RED); // 1 - индекс серии данных
        }
        renderer.setSeriesShapesVisible(0, true); // 0 - индекс серии данных
        renderer.setSeriesShapesVisible(1, true); // 1 - индекс серии данных
        renderer.setSeriesShape(0, new java.awt.geom.Ellipse2D.Double(-3, -3, 6, 6)); // Форма точек
        renderer.setSeriesPaint(0, Color.decode("#19674D")); // Цвет точек

        return lineChart;
    }
}


/*import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.util.function.UnaryOperator;

public class Chart {

    public JFreeChart drawChart(double[][] points, double x, double result, int number,
                                double[][] resultPoints, String titleResult) {

        XYSeries series = new XYSeries("Points");

        UnaryOperator<Double> f1 = Math::sqrt;

        UnaryOperator<Double> f3 = Math::sin;

        UnaryOperator<Double> f2 = (o) -> 2 * Math.pow(o, 2) + 3 * x - 2;
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;

        for (int i = 0; i < points[0].length; i++) {
            min = Math.min(min, points[0][i]);
            max = Math.max(max, points[0][i]);
        }


        double minY = points[1][0];

        double maxY = points[1][points.length - 1];


        if (number == 1) {
            for (double i = min - 0.5; i <= max + 0.5; i += 0.1) {
                series.add(i, f1.apply(i));
            }
        } else if (number == 2) {
            for (double i = min - 0.5; i <= max + 0.5; i += 0.1) {
                series.add(i, f2.apply(i));
            }
        } else if (number == 3) {
            for (double i = min - 0.5; i <= max + 0.5; i += 0.1) {
                series.add(i, f3.apply(i));
            }
        } else {
            for (int i = 0; i < points[0].length; i++) {
                series.add(points[0][i], points[1][i]);
            }
        }

        XYSeries resultSeries = new XYSeries(titleResult);
        for (int i = 0; i < resultPoints[0].length ; i++) {
            resultSeries.add(points[0][i], resultPoints[0][i]);// хуета полная поебень
        }


        XYDataset dataset = new XYSeriesCollection();
        ((XYSeriesCollection) dataset).addSeries(series);
        ((XYSeriesCollection) dataset).addSeries(resultSeries);

        JFreeChart lineChart = ChartFactory.createXYLineChart("Fi(x)", "x",
                "Y", dataset, PlotOrientation.VERTICAL,
                true, true, false);

        XYPlot plot = lineChart.getXYPlot();
        //XYSplineRenderer renderer = new XYSplineRenderer();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        //renderer.setPrecision(8);
        plot.setRenderer(renderer);

        plot.setDataset(dataset);

        renderer.setSeriesPaint(0, Color.decode("#19674D")); // 0 - индекс серии данных
        if(titleResult=="Lagrange") renderer.setSeriesPaint(1, Color.BLUE); // 1 - индекс серии данных
        else renderer.setSeriesPaint(1, Color.RED); // 1 - индекс серии данных
        renderer.setSeriesShapesVisible(0, true); // 0 - индекс серии данных
        renderer.setSeriesShapesVisible(1, true); // 1 - индекс серии данных
        renderer.setSeriesShape(0, new java.awt.geom.Ellipse2D.Double(-3, -3, 6, 6)); // Форма точек
        renderer.setSeriesPaint(0, Color.decode("#19674D")); // Цвет точек

        return lineChart;
    }
}
*/