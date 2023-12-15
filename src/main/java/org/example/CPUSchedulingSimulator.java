package org.example;

import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class CPUSchedulingSimulator extends JFrame {
    // Scheduling Parameter
    private static JFrame main;


    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new CPUSchedulingSimulator().setVisible(true);
        });
    }

    public CPUSchedulingSimulator() {
        initComponents();
        chartPanel.setVisible(false);
        dataPanel.setVisible(false);
        jOverheadLabel.setVisible(false);
        jPanel1.setVisible(false);
        jSchedulingLabel.setVisible(false);
        jScrollPane1.setVisible(false);
        jScrollPane2.setVisible(false);
        jTable1.setVisible(false);
        jTable2.setVisible(false);
        jTurnLabel.setVisible(false);
        jTurnaroundLabel.setVisible(false);
        jWaitingLabel.setVisible(false);

        SchedulerSimulation s = new SchedulerSimulation();
        s.run(this);
    }

    public void excute(String SchedulingType, double AverageTT, double AverageWT, Object[][] timeArray, Map<Process, List<Interval>> processesList, Vector<Color> processesColor) {
        initComponents();
        this.setResizable(true);
        this.setLocationRelativeTo(null);

        this.jSchedulingLabel.setText(SchedulingType);
        if (SchedulingType.equals("AG"))
            this.jScrollPane2.setVisible(true);
        if (SchedulingType.equals("AG"))
            this.jTable2.setVisible(true);
        this.jTurnLabel.setText(String.valueOf(AverageTT));
        this.jWaitingLabel.setText(String.valueOf(AverageWT));
        GanttChart chart = new GanttChart();
        IntervalCategoryDataset dataset = getCategoryDataset(processesList);
        this.jTable1.setModel(new DefaultTableModel(timeArray,
                new String[]{
                        "Process", "PID", "ArrivalT", "FinishT", "TurnaroundT", "WaitingT"
                }
        ));
        this.jTable1.setShowGrid(true);
        this.jTable1.setEnabled(false);
        this.jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
//        this.jTable2.setModel(new DefaultTableModel(
//                CPUSchedulingSimulator.scheduling.getQuantumRows(),
//                new String [] {
//                        "Process", "Current Time", "Quantum Before", "Quantum After"
//                }
//        ));
        this.jTable2.setEnabled(false);
        this.jTable2.setShowGrid(true);
        chart.setDataset(dataset, processesColor, "CPU Scheduling Gantt Chart", "Timeline", "Process");
        this.chartPanel.add(chart);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        chartPanel = new javax.swing.JPanel();
        dataPanel = new javax.swing.JPanel();
        jSchedulingLabel = new JLabel();
        jTurnLabel = new JLabel();
        jWaitingLabel = new JLabel();
        jOverheadLabel = new JLabel();
        jTurnaroundLabel = new JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new JTable();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        chartPanel.setLayout(new BorderLayout());

        jSchedulingLabel.setFont(new Font("Segoe UI", 3, 24)); // NOI18N
        jSchedulingLabel.setForeground(new Color(0, 0, 0));
        jSchedulingLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jSchedulingLabel.setText("CPU SCHEDULING TYPE");
        jSchedulingLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jTurnLabel.setFont(new Font("sansserif", 1, 14)); // NOI18N
        jTurnLabel.setForeground(new Color(0, 0, 0));
        jTurnLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jTurnLabel.setText("30");

        jWaitingLabel.setFont(new Font("sansserif", 1, 14)); // NOI18N
        jWaitingLabel.setForeground(new Color(0, 0, 0));
        jWaitingLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jWaitingLabel.setText("40");

        jOverheadLabel.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        jOverheadLabel.setForeground(new Color(0, 0, 0));
        jOverheadLabel.setText("Average Waiting Time :");

        jTurnaroundLabel.setFont(new Font("Segoe UI", 1, 14)); // NOI18N
        jTurnaroundLabel.setForeground(new Color(0, 0, 0));
        jTurnaroundLabel.setText("Average Turnaround Time : ");

        jTable2.setModel(new DefaultTableModel(
                new Object[][]{

                },
                new String[]{
                        "Process", "Current Time", "Quantum Before", "Quantum After"
                }
        ) {
            Class[] types = new Class[]{
                    String.class, Object.class, Object.class, Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        });
        jTable2.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        jScrollPane2.setViewportView(jTable2);

        jTable1.setModel(new DefaultTableModel(
                new Object[][]{

                },
                new String[]{
                        "Process", "PID", "Arrival", "Finish", "TurnaroundT", "Waiting"
                }
        ) {
            Class[] types = new Class[]{
                    String.class, Object.class, Object.class, Object.class, Object.class, Object.class
            };
            boolean[] canEdit = new boolean[]{
                    false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTable1.setEnabled(false);
        jTable1.setShowGrid(true);
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 475, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addComponent(jScrollPane2))
                                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                                .addContainerGap())
        );

        javax.swing.GroupLayout dataPanelLayout = new javax.swing.GroupLayout(dataPanel);
        dataPanel.setLayout(dataPanelLayout);
        dataPanelLayout.setHorizontalGroup(
                dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(dataPanelLayout.createSequentialGroup()
                                .addGroup(dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(dataPanelLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(dataPanelLayout.createSequentialGroup()
                                                                .addGap(6, 6, 6)
                                                                .addGroup(dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jTurnaroundLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addComponent(jOverheadLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                        .addComponent(jWaitingLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addComponent(jTurnLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addComponent(jSchedulingLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        dataPanelLayout.setVerticalGroup(
                dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(dataPanelLayout.createSequentialGroup()
                                .addComponent(jSchedulingLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jTurnaroundLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jTurnLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(dataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jOverheadLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jWaitingLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(dataPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(chartPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 752, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(chartPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addComponent(dataPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_formWindowClosing
        main.setVisible(true);
    }// GEN-LAST:event_formWindowClosing


    private IntervalCategoryDataset getCategoryDataset(Map<Process, List<Interval>> intervals) {
        TaskSeriesCollection dataset = new TaskSeriesCollection();
        // Format for the fixed time
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date fixedTime;
        try {
            fixedTime = dateFormat.parse("00:00:00");
        } catch (ParseException e) {
            // Handle the exception (unlikely in this case)
            e.printStackTrace();
            return dataset;
        }
        for (Map.Entry<Process, List<Interval>> entry : intervals.entrySet()) {
            TaskSeries series = getTaskSeries(entry.getKey(), fixedTime, entry.getValue());

            dataset.add(series);
        }
        return dataset;
    }

    private static TaskSeries getTaskSeries(Process process, Date fixedTime, List<Interval> intervalList) {
        System.out.println(process.name);
//        System.out.println(process.arrivalTime);
//        System.out.println(process.waitingTime);


        TaskSeries series = new TaskSeries(process.name);
        Date startTime = new Date(fixedTime.getTime() + process.arrivalTime);
        Date endTime = new Date(fixedTime.getTime() + process.endTime);
        Task task = new Task(
                process.name,
                startTime,
                endTime);
        for (Interval interval : intervalList) {
            System.out.println(interval.getStart());
            System.out.println(interval.getEnd());
            task.addSubtask(
                    new Task(
                            process.name,
                            new Date(fixedTime.getTime() + interval.getStart()),
                            new Date(fixedTime.getTime() + interval.getEnd())));
        }
        series.add(task);
        return series;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel chartPanel;
    private javax.swing.JPanel dataPanel;
    private JLabel jOverheadLabel;
    private javax.swing.JPanel jPanel1;
    private JLabel jSchedulingLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private JTable jTable1;
    private JTable jTable2;
    private JLabel jTurnLabel;
    private JLabel jTurnaroundLabel;
    private JLabel jWaitingLabel;
    // End of variables declaration//GEN-END:variables

}
