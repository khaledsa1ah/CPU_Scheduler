package org.example;

import java.util.*;

public class PrioritySchedulingAlgorithm {
    static Map<Process, List<Interval>> intervals = new HashMap<>();
    public double averageWaitingTime;
    public double averageTurnAroundTime;

    public PrioritySchedulingAlgorithm(List<Process> list, int i) {
        simulatePriorityScheduling(list, i);
    }

    private static void simulatePriorityScheduling(List<Process> processes, int contextSwitchingTime) {
        int currentTime = 0;
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;
        int processedProcesses = 0;
        final int AGE_THRESHOLD = 5;
        // Sort processes based on arrival time
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));

        // Priority Scheduling
        while (!processes.isEmpty()) {
            Process currentProcess = null;

            // Find the process with the highest priority among the arrived processes
            for (Process process : processes) {
                if (process.arrivalTime <= currentTime) {
                    if (currentProcess == null || process.priority < currentProcess.priority) {
                        currentProcess = process;
                    }
                }

                if (process != currentProcess) {
                    process.waitingTime++;
                    if (process.waitingTime >= AGE_THRESHOLD) {
                        process.priority--;
                        process.waitingTime = 0;
                    }
                }
            }

            if (currentProcess != null) {

                currentProcess.waitingTime = currentTime - currentProcess.arrivalTime;

                currentProcess.turnaroundTime = currentProcess.waitingTime + currentProcess.burstTime;

                totalWaitingTime += currentProcess.waitingTime;
                totalTurnaroundTime += currentProcess.turnaroundTime;

                currentTime += currentProcess.burstTime;

                processes.remove(currentProcess);

                Interval interval = new Interval(currentTime - currentProcess.burstTime, currentTime);
                List<Interval> processIntervals = intervals.computeIfAbsent(currentProcess, k -> new ArrayList<>());
                processIntervals.add(interval);

                System.out.println("Process " + currentProcess.name + " executed from " +
                        (currentTime - currentProcess.burstTime) + " to " + currentTime);

                processedProcesses++;
            } else {
                currentTime++;
            }
        }

        double avgWaitingTime = processedProcesses > 0 ? (double) totalWaitingTime / processedProcesses : 0;
        double avgTurnaroundTime = processedProcesses > 0 ? (double) totalTurnaroundTime / processedProcesses : 0;

        System.out.println("\nPriority Scheduling Results:");
        System.out.println("Average Waiting Time: " + avgWaitingTime);
        System.out.println("Average Turnaround Time: " + avgTurnaroundTime);
    }

    public Map<Process, List<Interval>> returnIntervals() {
        return intervals;
    }
}
