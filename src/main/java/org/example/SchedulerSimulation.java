package org.example;

import java.awt.*;
import java.util.*;
import java.util.List;

class StarvationValues {
    public final int lowStarvation = 1;
    public final int highStarvation = 128;
    public final int incrementTimeCycle = 15;
}

public class SchedulerSimulation {


    public Object[][] returnRows(Map<Process, List<Interval>> intervals) {
        int i = 0;
        Object[][] rows = new Object[intervals.size()][];
        for (Map.Entry<Process, List<Interval>> entry : intervals.entrySet()) {
            Object[] row = {entry.getKey().name, 1, entry.getKey().arrivalTime, entry.getKey().endTime, entry.getKey().turnaroundTime, entry.getKey().waitingTime};
            rows[i++] = row;
        }
        return rows;
    }

    public Vector<Color> processesColor(List<Process> processList) {
        Vector<Color> colors = new Vector<>();
        for (int i = 0; i < processList.size(); i++) {
            colors.add(processList.get(i).color);
        }
        return colors;
    }

    public void run(CPUSchedulingSimulator cpuSchedulingSimulator) {

        Scanner scanner = new Scanner(System.in);
//
//        // Input parameters
//        System.out.print("Enter the number of processes: ");
//        int numProcesses = scanner.nextInt();
//
//        System.out.print("Enter the Round Robin Time Quantum: ");
//        int timeQuantum = scanner.nextInt();
//
//        System.out.print("Enter context switching time: ");
//        int contextSwitchingTime = scanner.nextInt();
//
//        List<Process> processes = new ArrayList<>();
//        for (int i = 0; i < numProcesses; i++) {
//            System.out.println("Enter details for Process " + (i + 1) + ":");
//            System.out.print("Name: ");
//            String name = scanner.next();
//
//            System.out.print("Color: ");
//            String color = scanner.next();
//
//            System.out.print("Arrival Time: ");
//            int arrivalTime = scanner.nextInt();
//
//            System.out.print("Burst Time: ");
//            int burstTime = scanner.nextInt();
//
//            System.out.print("Priority: ");
//            int priority = scanner.nextInt();
//
//            processes.add(new Process(name, color, arrivalTime, burstTime, priority, timeQuantum));
//        }
//
//        simulateSJF(processes, contextSwitchingTime);
//        simulateSRTF(processes);
//        simulatePriorityScheduling(processes, contextSwitchingTime);
//        AGSimulator a = new AGSimulator(processes);


        Process p1 = new Process("p1", Color.red, 0, 17, 4, 4, 20);
        Process p2 = new Process("p2", Color.green, 3, 6, 9, 4, 17);
        Process p3 = new Process("p3", Color.black, 4, 10, 2, 4, 16);
        Process p4 = new Process("p4", Color.yellow, 29, 4, 8, 4, 43);
//        Process p1 = new Process("p1", Color.red, 0, 10, 3, 4, 20);
//        Process p2 = new Process("p2", Color.green, 0, 1, 1, 4, 17);
//        Process p3 = new Process("p3", Color.black, 0, 2, 4, 4, 16);
//        Process p4 = new Process("p4", Color.yellow, 0, 1, 5, 4, 43);
//        Process p5 = new Process("p5", Color.gray, 0, 5, 2, 4, 43);

        java.util.List<Process> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p4);


        String algorithmName;
        algorithmName = scanner.nextLine();

        double avgWaitingTime = 0;
        double avgTurnaroundTime = 0;
        Vector<Color> processesColor = processesColor(list);
        Map<Process, List<Interval>> intervals = null;
        Object[][] rows;

        if (algorithmName.equals("AG")) {
            AGSimulator a = new AGSimulator(list);
            a.run();
            avgTurnaroundTime = a.averageTurnaroundTIme;
            avgWaitingTime = a.averageWaitingTime;
            intervals = a.returnIntervals(); // AG

        }
        else if (algorithmName.equals("Pr")){
            PrioritySchedulingAlgorithm p = new PrioritySchedulingAlgorithm(list);
            p.simulatePriorityScheduling();
            avgTurnaroundTime = p.averageTurnAroundTime;
            avgWaitingTime = p.averageWaitingTime;
            intervals = p.returnIntervals();
        }
        else if (algorithmName.equals("SRTF")) {
            SRTFAlgorithm s = new SRTFAlgorithm(list);
            avgTurnaroundTime = s.averageTurnAroundTime;
            avgWaitingTime = s.averageWaitingTime;
            intervals = s.returnIntervals();
        } else if (algorithmName.equals("SJF")) {
            SJFAlgorithm sjf = new SJFAlgorithm(list);
            sjf.simulateSJF();
            avgTurnaroundTime = sjf.averageTurnaroundTime;
            avgWaitingTime = sjf.averageWaitingTime;
            intervals = sjf.returnIntervals();
        }

        rows = returnRows(intervals);
        cpuSchedulingSimulator.excute(algorithmName, avgTurnaroundTime, avgWaitingTime, rows, intervals, processesColor);
    }

}
