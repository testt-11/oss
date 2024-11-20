// Enter number of processes: 5
// Process 1 Arrival Time: 0
// Process 1 Burst Time: 4
// Process 1 Priority (lower number = higher priority): 4
// Process 2 Arrival Time: 1
// Process 2 Burst Time: 3
// Process 2 Priority (lower number = higher priority): 3
// Process 3 Arrival Time: 2
// Process 3 Burst Time: 1
// Process 3 Priority (lower number = higher priority): 2
// Process 4 Arrival Time: 3
// Process 4 Burst Time: 5
// Process 4 Priority (lower number = higher priority): 1
// Process 5 Arrival Time: 4
// Process 5 Burst Time: 2
// Process 5 Priority (lower number = higher priority): 1


import java.util.*;

class Process {
    int pid;
    int burstTime;
    int arrivalTime;
    int priority;
    int waitingTime;
    int turnaroundTime;
    boolean completed;

    public Process(int pid, int arrivalTime, int burstTime, int priority) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.completed = false;
    }
}

public class NonPreemptivePriorityScheduling {

    // Method to compare processes based on arrival time
    public static int compareArrival(Process a, Process b) {
        return a.arrivalTime - b.arrivalTime;
    }

    // Non-Preemptive Priority Scheduling Algorithm
    public static void nonPreemptivePriorityScheduling(List<Process> processes) {
        // Sort processes by arrival time
        processes.sort(NonPreemptivePriorityScheduling::compareArrival);

        int currentTime = 0;
        float totalWaitingTime = 0, totalTurnaroundTime = 0;
        int completedProcesses = 0;

        // Process the tasks
        while (completedProcesses < processes.size()) {
            int highestPriorityJob = -1;
            int minPriority = Integer.MAX_VALUE;

            // Find the process with the highest priority that has arrived
            for (int i = 0; i < processes.size(); i++) {
                Process p = processes.get(i);
                if (!p.completed && p.arrivalTime <= currentTime) {
                    if (p.priority < minPriority) {
                        highestPriorityJob = i;
                        minPriority = p.priority;
                    }
                }
            }

            if (highestPriorityJob == -1) {
                currentTime++;
                continue;
            }

            // Process the selected process
            Process selectedProcess = processes.get(highestPriorityJob);
            currentTime += selectedProcess.burstTime;
            selectedProcess.turnaroundTime = currentTime - selectedProcess.arrivalTime;
            selectedProcess.waitingTime = selectedProcess.turnaroundTime - selectedProcess.burstTime;
            selectedProcess.completed = true;

            totalWaitingTime += selectedProcess.waitingTime;
            totalTurnaroundTime += selectedProcess.turnaroundTime;
            completedProcesses++;
        }

        // Print the results
        System.out.println("\nNon-Preemptive Priority Scheduling Results:");
        System.out.println("PID\tArrival Time\tBurst Time\tPriority\tWaiting Time\tTurnaround Time");

        for (Process p : processes) {
            System.out.printf("%d\t%d\t\t%d\t\t%d\t\t%d\t\t%d\n",
                    p.pid, p.arrivalTime, p.burstTime, p.priority, p.waitingTime, p.turnaroundTime);
        }

        System.out.printf("\nAverage Waiting Time: %.2f\n", totalWaitingTime / processes.size());
        System.out.printf("Average Turnaround Time: %.2f\n", totalTurnaroundTime / processes.size());
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = scanner.nextInt();

        List<Process> processes = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            System.out.print("Process " + (i + 1) + " Arrival Time: ");
            int arrivalTime = scanner.nextInt();
            System.out.print("Process " + (i + 1) + " Burst Time: ");
            int burstTime = scanner.nextInt();
            System.out.print("Process " + (i + 1) + " Priority (lower number = higher priority): ");
            int priority = scanner.nextInt();
            processes.add(new Process(i + 1, arrivalTime, burstTime, priority));
        }

        // Apply the Non-Preemptive Priority Scheduling algorithm
        nonPreemptivePriorityScheduling(processes);

        scanner.close();
    }
}
