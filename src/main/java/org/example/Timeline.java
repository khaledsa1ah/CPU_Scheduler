/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.IntervalCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class Timeline extends JPanel {

    public Timeline() {
        setLayout(new BorderLayout());
    }

    public void setDataset(IntervalCategoryDataset dataset, Vector<Color> colors, String name, String xLabel, String yLabel) {
        removeAll();

        // Create a Gantt chart
        JFreeChart chart = ChartFactory.createGanttChart(name, yLabel, xLabel, dataset, true, false, false);

        // Customize the chart
        customizeChart(chart, colors);

        // Create a chart panel and add it to the panel
        ChartPanel panel = new ChartPanel(chart);
        add(panel);

        // Repaint and revalidate the panel
        repaint();
        revalidate();
    }

    private void customizeChart(JFreeChart chart, Vector<Color> colors) {
        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setItemMargin(-2);

        // Set colors for each series
        for (int i = 0; i < colors.size(); i++) {
            renderer.setSeriesPaint(i, colors.get(i));
        }

        // Customize the date axis format
        DateAxis axis = (DateAxis) plot.getRangeAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("S"));
    }
}
