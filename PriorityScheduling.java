//PRIORITY SCHDULING

// Enter number of processes: 4
// Enter data (Arrival Time, Burst Time, Priority):
// 0 5 3
// 1 4 2
// 2 3 1
// 3 2 4

import java.util.Scanner;

class Process {
    int processId;
    int arrivalTime;
    int burstTime;
    int waitingTime;
    int finishTime;
    int turnAroundTime;
    int priority;
    boolean completed;

    public Process(int processId, int arrivalTime, int burstTime, int priority) {
        this.processId = processId;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.completed = false;
    }
}

public class PriorityScheduling {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input the number of processes
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        Process[] proc = new Process[n];

        // Input process data
        System.out.println("Enter data (Arrival Time, Burst Time, Priority):");
        for (int i = 0; i < n; i++) {
            int arrivalTime = sc.nextInt();
            int burstTime = sc.nextInt();
            int priority = sc.nextInt();
            proc[i] = new Process(i + 1, arrivalTime, burstTime, priority);
        }

        // Sort processes by arrival time
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (proc[j].arrivalTime > proc[j + 1].arrivalTime) {
                    Process temp = proc[j];
                    proc[j] = proc[j + 1];
                    proc[j + 1] = temp;
                }
            }
        }

        int currentTime = 0;
        int completedProcesses = 0;
        int totalTAT = 0, totalWT = 0;

        // Priority Scheduling Logic
        while (completedProcesses < n) {
            int minPriority = Integer.MAX_VALUE;
            int idx = -1;

            // Find the process with the highest priority (lowest priority number) that has arrived
            for (int i = 0; i < n; i++) {
                if (proc[i].arrivalTime <= currentTime && !proc[i].completed && proc[i].priority < minPriority) {
                    minPriority = proc[i].priority;
                    idx = i;
                }
            }

            if (idx != -1) {
                // Process the selected process
                proc[idx].finishTime = currentTime + proc[idx].burstTime;
                proc[idx].turnAroundTime = proc[idx].finishTime - proc[idx].arrivalTime;
                proc[idx].waitingTime = proc[idx].turnAroundTime - proc[idx].burstTime;
                currentTime = proc[idx].finishTime;
                proc[idx].completed = true;
                completedProcesses++;
            } else {
                // If no process has arrived, increment the current time
                currentTime++;
            }
        }

        // Output results
        System.out.println("Process\tArrival Time\tBurst Time\tPriority\tFinish Time\tTurn-Around Time\tWaiting Time");
        for (int i = 0; i < n; i++) {
            totalTAT += proc[i].turnAroundTime;
            totalWT += proc[i].waitingTime;
            System.out.printf("%d\t\t%d\t\t%d\t\t%d\t\t%d\t\t%d\t\t\t%d\n", proc[i].processId, proc[i].arrivalTime,
                    proc[i].burstTime, proc[i].priority, proc[i].finishTime, proc[i].turnAroundTime, proc[i].waitingTime);
        }

        // Display averages
        System.out.printf("\nAverage Turnaround Time: %.2f\n", (float) totalTAT / n);
        System.out.printf("Average Waiting Time: %.2f\n", (float) totalWT / n);
    }
}
