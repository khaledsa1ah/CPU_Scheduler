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

public class GanttChart extends JPanel {

    public GanttChart() {
        setLayout(new BorderLayout());
    }

    public void setDataset(IntervalCategoryDataset dataset , Vector<Color> processesColor, String title, String x_label, String y_label){
        removeAll();
        JFreeChart chart = ChartFactory.createGanttChart(title, y_label, x_label, dataset,true,false,false);
        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer br = (BarRenderer) plot.getRenderer();
        br.setItemMargin(-2);
        for(int i = 0 ; i < processesColor.size() ; i++){
            br.setSeriesPaint(i , processesColor.get(i));
        }
        DateAxis axis = (DateAxis) plot.getRangeAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("S"));
        ChartPanel panel = new ChartPanel(chart);
        add(panel);
        repaint();
        revalidate();
    }
}
    
  
  
  
   



