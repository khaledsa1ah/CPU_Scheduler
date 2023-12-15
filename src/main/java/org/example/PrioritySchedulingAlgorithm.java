package org.example;

import java.util.Comparator;
import java.util.List;

public class PrioritySchedulingAlgorithm {
    private static void simulatePriorityScheduling(List<Process> processes, int contextSwitchingTime) {
        int currentTime = 0;
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;
        int processedProcesses = 0;

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
            }

            if (currentProcess != null) {
                // Update waiting time
                currentProcess.waitingTime = currentTime - currentProcess.arrivalTime;

                // Update turnaround time
                currentProcess.turnaroundTime = currentProcess.waitingTime + currentProcess.burstTime;

                // Update total waiting and turnaround time
                totalWaitingTime += currentProcess.waitingTime;
                totalTurnaroundTime += currentProcess.turnaroundTime;

                // Update current time
                currentTime += currentProcess.burstTime;

                // Remove the processed process
                processes.remove(currentProcess);

                // Print history update
                System.out.println("Process " + currentProcess.name + " executed from " +
                        (currentTime - currentProcess.burstTime) + " to " + currentTime);

                processedProcesses++;
            } else {
                // No process arrived yet, increment time
                currentTime++;
            }
        }

        // Calculate averages
        double avgWaitingTime = processedProcesses > 0 ? (double) totalWaitingTime / processedProcesses : 0;
        double avgTurnaroundTime = processedProcesses > 0 ? (double) totalTurnaroundTime / processedProcesses : 0;

        // Output results
        System.out.println("\nPriority Scheduling Results:");
        System.out.println("Average Waiting Time: " + avgWaitingTime);
        System.out.println("Average Turnaround Time: " + avgTurnaroundTime);
    }
}
