package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Process {
    int id;             // Process ID
    int burstTime;      // Burst time of the process
    int priority;       // Priority (not used in FCFS or SJF)
    int waitingTime;    // Waiting time calculated
    int turnaroundTime; // Turnaround time calculated

    public Process(int id, int burstTime, int priority) {
        this.id = id;
        this.burstTime = burstTime;
        this.priority = priority;
    }
}

public class Scheduling {

    // Function to calculate waiting time for FCFS scheduling
    public static void calculateWaitingTimeFCFS(List<Process> processes) {
        processes.get(0).waitingTime = 0; // First process has no waiting time

        for (int i = 1; i < processes.size(); i++) {
            processes.get(i).waitingTime = processes.get(i - 1).waitingTime + processes.get(i - 1).burstTime;
        }
    }

    // Function to calculate turnaround time for both FCFS and SJF scheduling
    public static void calculateTurnaroundTime(List<Process> processes) {
        for (Process process : processes) {
            process.turnaroundTime = process.waitingTime + process.burstTime;
        }
    }

    // Function to calculate waiting time for SJF scheduling
    public static void calculateWaitingTimeSJF(List<Process> processes) {
        Collections.sort(processes, Comparator.comparingInt(p -> p.burstTime)); // Sort by burst time

        processes.get(0).waitingTime = 0;

        for (int i = 1; i < processes.size(); i++) {
            processes.get(i).waitingTime = processes.get(i - 1).waitingTime + processes.get(i - 1).burstTime;
        }
    }

    // Function to display results and calculate average waiting and turnaround times
    public static void displayResults(List<Process> processes, String schedulingType) {
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;

        System.out.println("\n--- " + schedulingType + " Scheduling ---");
        System.out.println("Process ID | Waiting Time | Turnaround Time");

        for (Process process : processes) {
            System.out.printf("    %d      |      %d       |       %d%n", process.id, process.waitingTime, process.turnaroundTime);
            totalWaitingTime += process.waitingTime;
            totalTurnaroundTime += process.turnaroundTime;
        }

        System.out.printf("\nAverage Waiting Time: %.2f%n", (double) totalWaitingTime / processes.size());
        System.out.printf("Average Turnaround Time: %.2f%n", (double) totalTurnaroundTime / processes.size());
    }

    public static void main(String[] args) {
        List<Process> processes = new ArrayList<>();
        processes.add(new Process(1, 2, 2));
        processes.add(new Process(2, 1, 1));
        processes.add(new Process(3, 8, 4));
        processes.add(new Process(4, 4, 2));
        processes.add(new Process(5, 5, 3));

        // FCFS Scheduling
        calculateWaitingTimeFCFS(processes);
        calculateTurnaroundTime(processes);
        displayResults(processes, "FCFS");

        // Reset waiting times for SJF
        for (Process process : processes) {
            process.waitingTime = 0;
            process.turnaroundTime = 0;
        }

        // SJF Scheduling
        calculateWaitingTimeSJF(processes);
        calculateTurnaroundTime(processes);
        displayResults(processes, "SJF");
    }
}