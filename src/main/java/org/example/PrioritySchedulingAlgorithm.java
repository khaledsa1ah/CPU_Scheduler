package org.example;

import java.util.*;

public class PrioritySchedulingAlgorithm {
    List<Process> processes;
    Process[] timeProcesses = new Process[1000];
    public double averageWaitingTime;
    public double averageTurnAroundTime;

    public PrioritySchedulingAlgorithm(List<Process> processes) {
        this.processes = processes;
    }

    public void simulatePriorityScheduling() {
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
                timeProcesses[currentTime] = currentProcess;

                currentProcess.waitingTime = currentTime - currentProcess.arrivalTime;

                currentProcess.turnaroundTime = currentProcess.waitingTime + currentProcess.burstTime;

                totalWaitingTime += currentProcess.waitingTime;
                totalTurnaroundTime += currentProcess.turnaroundTime;

                currentTime += currentProcess.burstTime;
                currentProcess.endTime = currentTime;
                processes.remove(currentProcess);

//                Interval interval = new Interval(currentTime - currentProcess.burstTime, currentTime);
//                List<Interval> processIntervals = intervals.computeIfAbsent(currentProcess, k -> new ArrayList<>());
//                processIntervals.add(interval);

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
        Map<Process, List<Interval>> intervals = new HashMap<>();

        for (int i = 0; i < timeProcesses.length; i++) {
            Process currentProcess = timeProcesses[i];

            if (currentProcess != null) {
                if (!intervals.containsKey(currentProcess)) {
                    intervals.put(currentProcess, new ArrayList<>());
                }

                List<Interval> processIntervals = intervals.get(currentProcess);
                if (!processIntervals.isEmpty() && processIntervals.get(processIntervals.size() - 1).getEnd() == i - 1) {
                    processIntervals.get(processIntervals.size() - 1).setEnd(i);
                } else {
                    processIntervals.add(new Interval(i, currentProcess.endTime));
                }
            }
        }

        for (Map.Entry<Process, List<Interval>> entry : intervals.entrySet()) {
            for (Interval interval : entry.getValue()) {
                System.out.println(entry.getKey().name + " : " + interval.getStart() + " - " + interval.getEnd());
            }
        }
        return intervals;
    }
}
