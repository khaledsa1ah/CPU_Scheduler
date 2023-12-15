package org.example;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Process {
    public String name;
    public Color color;
    public int arrivalTime;
    public int burstTime;
    public int priority;
    public int waitingTime;
    public int turnaroundTime;
    public int agFactor;
    public int quantumTime;
    public int endTime;
    List<Integer> quantumHistory;
    List<Interval> intervalList;

    public Process(String name, Color color, int arrivalTime, int burstTime, int priority, int quantumTime, int temp) {
        this.name = name;
        this.color = color;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.waitingTime = 0;
        this.turnaroundTime = 0;
        this.quantumTime = quantumTime;
        agFactor = arrivalTime + burstTime;
        calculateAGFactor();
        this.agFactor = temp;
        this.quantumHistory = new ArrayList<>();
        this.intervalList = new ArrayList<>();
    }

    public Process(String name, Color color, int arrivalTime, int burstTime, int priority, int quantumTime){
        this.name = name;
        this.color = color;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.quantumTime = quantumTime;
        agFactor = arrivalTime + burstTime;
        calculateAGFactor();
    }

    private void calculateAGFactor() {
        Random random = new Random();

        int randomNumber = random.nextInt(21);

        if (randomNumber < 10) {
            agFactor += randomNumber;
        } else if (randomNumber > 10) {
            agFactor += 10;
        } else
            agFactor += priority;
    }
}
