package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SRTFAlgorithm {
    public double averageWaitingTime;
    public double averageTurnAroundTime;
    Process[] myList = new Process[1000];


    List<Process> processes;
    public SRTFAlgorithm(List<Process> processes) {
        this.processes = processes;
        simulateSRTF();
    }

    private int[] calcStarvationValue(int[] burstTime) {
        int[] starvationValue = new int[burstTime.length];

        // Calculate the Standard Deviation of the burst time
        double sum = 0.0;
        for (double i : burstTime) {
            sum += i;
        }

        // Calculate the mean of array
        double mean = sum / burstTime.length;

        // calculate the standard deviation
        double standardDeviation = 0.0;
        for (double num : burstTime) {
            standardDeviation += Math.pow(num - mean, 2);
        }

        standardDeviation = Math.sqrt(standardDeviation / burstTime.length);

        StarvationValues starvationValues = new StarvationValues();
        for (int i = 0; i < burstTime.length; i++) {
            starvationValue[i] = starvationValues.highStarvation - (int) (burstTime[i] / standardDeviation);
        }
        return starvationValue;
    }

    // Function that return start and end for each process and waiting time
    private int[] findWaitingTime(List<Process> processes, int[] waitingTime) {
        int[] burstTime = new int[processes.size()];

        /*
         * To solve the starvation problem we will give high value for short process and low value for long process
         * and every 10s (for example) we will decrement this value for all waiting process
         * I assume three default values for lowStarvation, highStarvation and incrementTimeCycle
         * We will use starvationValue = highStarvation - (burstTime / scalingFactor)
         * I used scalingFactor as standard Deviation of the burst time
         * */

        StarvationValues starvationValues = new StarvationValues();
        int[] starvationValue = calcStarvationValue(burstTime);
        // Copy burst time of each process
        for (int i = 0; i < processes.size(); i++) {
            burstTime[i] = processes.get(i).burstTime;
        }

        int completedProcesses = 0, currentTime = 0;

        // Iterate over all processes
        while (completedProcesses != processes.size()) {
            // Find process with the minimum time
            int minimumValue = Integer.MAX_VALUE, minimumStarvation = Integer.MAX_VALUE, minimumIndex = -1;
            for (int i = 0; i < processes.size(); i++) {
                if (burstTime[i] <= minimumValue && burstTime[i] > 0 && processes.get(i).arrivalTime <= currentTime) {
                    // Give the priority to the lowest starvation value to solve the problem of the starvation
                    if (burstTime[i] == minimumValue && minimumStarvation < starvationValue[i]) continue;

                    minimumValue = burstTime[i];
                    minimumIndex = i;
                    minimumStarvation = starvationValue[i];

                }
            }

            myList[currentTime] = processes.get(minimumIndex);

            // If we didn't find any process increment time and continue
            if (minimumIndex == -1) {
                currentTime++;
                continue;
            }

            // Reduce time of the shortest process by one
            burstTime[minimumIndex]--;

            // If a process gets completely excuted
            if (burstTime[minimumIndex] == 0) {
                completedProcesses++;

                int currentFinishTime = currentTime + 1;
                int currentWaitingTime = currentFinishTime - processes.get(minimumIndex).burstTime - processes.get(minimumIndex).arrivalTime;
                waitingTime[minimumIndex] = currentWaitingTime;

            }

            // After every time cycle, decrease the starvation value of all waiting processes
            if (currentTime % starvationValues.incrementTimeCycle == 0) {
                for (int i = 0; i < processes.size(); i++) {
                    // Check if it's waiting process and make sure that starvationValue doesn't exceed the minimum
                    if (i != minimumIndex)
                        starvationValue[i] = Math.min(starvationValues.lowStarvation, starvationValue[i] - 1);
                }
            }

            currentTime++;
        }
        return waitingTime;
    }

    private int[] findTurnAroundTime(List<Process> processes, int[] waitingTime, int[] turnAroundTime) {

        for (int i = 0; i < processes.size(); i++) {
            turnAroundTime[i] = waitingTime[i] + processes.get(i).burstTime;
        }
        return turnAroundTime;
    }

    private void simulateSRTF() {
        int[] waitingTime = new int[processes.size()];
        int[] turnAroundTime = new int[processes.size()];

        findWaitingTime(processes, waitingTime);
        findTurnAroundTime(processes, waitingTime, turnAroundTime);

        int totalWaitingTime = 0, totalTurnAroundTime = 0;

        for (int i = 0; i < processes.size(); i++) {
            totalWaitingTime += waitingTime[i];
            totalTurnAroundTime += turnAroundTime[i];
            processes.get(i).waitingTime = waitingTime[i];
            processes.get(i).turnaroundTime = turnAroundTime[i];
        }

        averageWaitingTime = totalWaitingTime / (double) processes.size();
        averageTurnAroundTime = totalTurnAroundTime / (double) processes.size();


        for (int i = 0; i < myList.length; i++) {
            if(myList[i] == null) break;
            System.out.println(i + "->" + (i + 1) + " " + myList[i].name);
        }

    }

    public Map<Process, List<Interval>> returnIntervals() {
        Map<Process, List<Interval>> intervals = new HashMap<>();


        for (int i = 0; i < myList.length; i++) {
            Process currentProcess = myList[i];

            if (currentProcess != null) {
                currentProcess.endTime = i;

                if (!intervals.containsKey(currentProcess)) {
                    intervals.put(currentProcess, new ArrayList<>());
                }

                List<Interval> processIntervals = intervals.get(currentProcess);
                if (!processIntervals.isEmpty() && processIntervals.get(processIntervals.size() - 1).getEnd() == i) {
                    processIntervals.get(processIntervals.size() - 1).setEnd(i + 1);
                } else {
                    processIntervals.add(new Interval(i, i + 1));
                }
            }
        }

        return intervals;
    }
}
