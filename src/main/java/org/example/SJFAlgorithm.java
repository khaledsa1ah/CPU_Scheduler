package org.example;

import java.util.*;

public class SJFAlgorithm {
    double averageWaitingTime = 0, averageTurnaroundTime = 0;
    List<Process> processes;
    Process[] timeProcesses = new Process[1000];
    public SJFAlgorithm(List<Process> processes){
        this.processes = processes;
    }
    public void simulateSJF() {
        // Sort processes based on arrival time
        processes.sort(Comparator.comparingInt(process -> process.arrivalTime));
        int currentTime = 0, numProcesses = processes.size();
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
            timeProcesses[currentTime] = process;
            currentTime += process.burstTime;
            process.endTime = currentTime;
            processes.remove(process);

            for (int i = 0; i < processes.size(); i++) {
                if(processes.get(i).arrivalTime<=currentTime){
                    break;
                }
            }
//            // If there are more waiting process  ---> Add context Switch Time
//            if(queue.size()>1)
//                currentTime+=contextSwitchingTime;
            averageWaitingTime += process.waitingTime;
            averageTurnaroundTime += process.turnaroundTime;
            System.out.println("Process: " + process.name + " Waiting Time = " + process.waitingTime + " Turnaround Time = " + process.turnaroundTime);
        }
        averageWaitingTime /= numProcesses;
        averageTurnaroundTime /= numProcesses;
        System.out.println("Average Waiting Time = " + averageWaitingTime);
        System.out.println("Average Turnaround Time = " + averageTurnaroundTime);
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
