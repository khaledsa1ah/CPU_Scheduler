package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SJFAlgorithm {
    private static void simulateSJF(List<Process> processes, int contextSwitchingTime) {
        // Sort processes based on arrival time
        processes.sort(Comparator.comparingInt(process -> process.arrivalTime));
        int currentTime = 0, numProcesses = processes.size();
        double avgWaitingTime = 0, avgTurnaroundTime = 0;
        while (!processes.isEmpty()) {
            // Add processes that arrived
            List<Process> queue = new ArrayList<>();
            for (Process process : processes)
                if (process.arrivalTime <= currentTime)
                    queue.add(process);

            if (queue.isEmpty()) {
                currentTime++;
                continue;
            }
            // Sort processes that arrived based on burst time
            queue.sort(Comparator.comparingInt(process -> process.burstTime));
            Process process = queue.get(0);
            process.waitingTime = currentTime - process.arrivalTime;
            process.turnaroundTime = process.waitingTime + process.burstTime;
            currentTime += process.burstTime;
            processes.remove(process);
//            // If there are more waiting process  ---> Add context Switch Time
//            if(queue.size()>1)
//                currentTime+=contextSwitchingTime;
            avgWaitingTime += process.waitingTime;
            avgTurnaroundTime += process.turnaroundTime;
            System.out.println("Process: " + process.name + " Waiting Time = " + process.waitingTime + " Turnaround Time = " + process.turnaroundTime);
        }
        avgWaitingTime /= numProcesses;
        avgTurnaroundTime /= numProcesses;
        System.out.println("Average Waiting Time = " + avgWaitingTime);
        System.out.println("Average Turnaround Time = " + avgTurnaroundTime);
    }
}
