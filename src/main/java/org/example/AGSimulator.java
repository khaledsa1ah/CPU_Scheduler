package org.example;

import java.awt.*;
import java.util.*;
import java.util.List;


public class AGSimulator {
    Map<String,Integer> map = new HashMap<>();
    List<Process> processes;
    List<Process> readyQueue = new ArrayList<>();
    Process[] timeProcesses = new Process[1000];
    List<Process> dieList = new ArrayList<>();
    static int currentTime = 0;

    double averageWaitingTime = 0, averageTurnaroundTIme = 0;
    public AGSimulator(List<Process> processes) {
        this.processes = processes;
    }



    public void run(){
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));

        for (int i = 0; i < processes.size(); i++)
            map.put(processes.get(i).name, processes.get(i).burstTime);

        List<Process> tempProcesses = new ArrayList<>();
        tempProcesses.addAll(processes);
        while (!processes.isEmpty()){
            for (int i = 0; i < processes.size(); i++) {
                if(processes.get(i).burstTime > 0){
                    currentTime = Math.max(currentTime, processes.get(i).arrivalTime);
                    Process tempProcess = processes.get(i);
                    processes.remove(tempProcess);
                    runProcess(tempProcess);
                }
            }
        }
        // Calculate average waiting time, average turnaroundTime
        for (int i = 0; i < dieList.size(); i++){
            averageTurnaroundTIme+=dieList.get(i).turnaroundTime;
            averageWaitingTime+=dieList.get(i).waitingTime;
        }

        averageTurnaroundTIme/=dieList.size();
        averageWaitingTime/=dieList.size();
    }


    void refreshReadyQueue(){
        for (int i = 0; i < processes.size(); i++) {
            if(processes.get(i).burstTime > 0 && currentTime >= processes.get(i).arrivalTime){
                readyQueue.add(processes.get(i));
                processes.remove(i);
            }
        }
    }
    private double calcMean(){
        int sum = 0;
        for (int i = 0; i < readyQueue.size(); i++) {
            sum += readyQueue.get(i).quantumTime;
        }

        for (int i = 0; i < processes.size(); i++) {
            sum += processes.get(i).quantumTime;
        }

        return ((double) sum / (readyQueue.size() + processes.size()));
    }
    public void runProcess(Process p){

//        if(p.burstTime == 0)return;

        int tempQuantumTime = p.quantumTime;

        int ceilQuantum = (tempQuantumTime + 1) / 2;

        for (int i = 0; i < ceilQuantum; i++) {
            if(p.burstTime == 0){
                p.quantumTime = 0;
                dieList.add(p);

                p.endTime = currentTime;
                p.turnaroundTime = p.endTime - p.arrivalTime;
                p.waitingTime += p.turnaroundTime-map.get(p.name);
//                    while (!readyQueue.isEmpty() && readyQueue.get(0).burstTime == 0)
//                        readyQueue.remove(0);

                if(!readyQueue.isEmpty()){
                    Process tempProcess = readyQueue.get(0);
                    readyQueue.remove(0);
                    runProcess(tempProcess);
                }
                return;
            }
            timeProcesses[currentTime] = p;

            System.out.println(currentTime + "  " + p.name);
            currentTime++;
            p.burstTime--;
            p.quantumTime--;
        }


//        currentTime += Math.min(p.burstTime, ceilQuantum);
//        p.burstTime = Math.max(0, p.burstTime - ceilQuantum);

        for (int i = ceilQuantum; i < tempQuantumTime; i++) {

            refreshReadyQueue();
            int minimumValue = p.agFactor, minimumIndex = -1;
            for (int j = 0; j < readyQueue.size(); j++) {
                if(readyQueue.get(j).agFactor < minimumValue && readyQueue.get(j).arrivalTime <= currentTime && !Objects.equals(readyQueue.get(j).name, p.name)){
                    minimumValue = readyQueue.get(j).agFactor;
                    minimumIndex = j;
                }
            }

            if(minimumIndex != -1){
                p.quantumTime = tempQuantumTime;
                p.quantumTime += (tempQuantumTime - i);
                readyQueue.add(p);
                Process tempProcess = readyQueue.get(minimumIndex);
                readyQueue.remove(tempProcess);
                runProcess(tempProcess);
                return;
            }

            System.out.println(currentTime + "  " + p.name);
            timeProcesses[currentTime] = p;
            p.quantumTime--;
            p.burstTime--;
            currentTime++;

            if(p.burstTime == 0){
                p.quantumTime = 0;
                dieList.add(p);

                p.endTime = currentTime;
                p.turnaroundTime = p.endTime - p.arrivalTime;
                p.waitingTime += p.turnaroundTime-map.get(p.name);
//                while (!readyQueue.isEmpty() && readyQueue.get(0).burstTime == 0)
//                    readyQueue.remove(0);

                if(!readyQueue.isEmpty()){
                    Process tempProcess = readyQueue.get(0);
                    readyQueue.remove(0);
                    runProcess(tempProcess);
                }
                return;
            }

            if(p.quantumTime == 0){

                p.quantumTime = tempQuantumTime;

                double meanCeil = Math.ceil(0.1 * calcMean());
                p.quantumTime += meanCeil;
                readyQueue.add(p);

                if(readyQueue.size() > 1){
                    Process tempProcess = readyQueue.get(0);
                    readyQueue.remove(0);
                    runProcess(tempProcess);
                }
            }
        }
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
                if (!processIntervals.isEmpty() && processIntervals.get(processIntervals.size() - 1).getEnd() == i) {
                    processIntervals.get(processIntervals.size() - 1).setEnd(i+1);
                } else {
                    processIntervals.add(new Interval(i, i+1));
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