import java.util.*;

public class RoundRobin {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int processes = sc.nextInt();

        System.out.print("Enter the time quantum: ");
        int timeQuantum = sc.nextInt();

        int[] arrivalTimes = new int[processes];
        int[] burstTimes = new int[processes];
        int[] remainingTimes = new int[processes];
        int[] finishTimes = new int[processes];
        int[] turnaroundTimes = new int[processes];
        int[] waitingTimes = new int[processes];

        for (int i = 0; i < processes; i++) {
            System.out.println("Enter arrival time for process P" + i + ": ");
            arrivalTimes[i] = sc.nextInt();
            System.out.println("Enter burst time for process P" + i + ": ");
            burstTimes[i] = sc.nextInt();
            remainingTimes[i] = burstTimes[i]; // Initialize remaining times with burst times
        }

        Queue<Integer> readyQueue = new LinkedList<>();
        int time = 0; // Keeps track of the current time
        int completed = 0;

        // Initially, add processes that have arrived at time 0
        for (int i = 0; i < processes; i++) {
            if (arrivalTimes[i] <= time) {
                readyQueue.offer(i);
            }
        }

        while (completed < processes) {
            if (readyQueue.isEmpty()) {
                time++; // Increment time if no process is ready
                for (int i = 0; i < processes; i++) {
                    if (arrivalTimes[i] == time && !readyQueue.contains(i)) {
                        readyQueue.offer(i);
                    }
                }
                continue;
            }

            int process = readyQueue.poll();

            // Process the current process for a time quantum or until it finishes
            int execTime = Math.min(timeQuantum, remainingTimes[process]);
            time += execTime;
            remainingTimes[process] -= execTime;

            // If the process finishes execution
            if (remainingTimes[process] == 0) {
                finishTimes[process] = time;
                turnaroundTimes[process] = finishTimes[process] - arrivalTimes[process];
                waitingTimes[process] = turnaroundTimes[process] - burstTimes[process];
                completed++;
            }

            // Add any new processes that have arrived by the current time
            for (int i = 0; i < processes; i++) {
                if (arrivalTimes[i] <= time && remainingTimes[i] > 0 && !readyQueue.contains(i) && i != process) {
                    readyQueue.offer(i);
                }
            }

            // If the process is not finished, add it back to the queue
            if (remainingTimes[process] > 0) {
                readyQueue.offer(process);
            }
        }

        // Print the results
        System.out.println("Process \t Arrival Time \t Burst Time \t Finish Time \t Turnaround Time \t Waiting Time");
        for (int i = 0; i < processes; i++) {
            System.out.printf("P%d\t\t %d\t\t %d\t\t %d\t\t\t %d\t\t %d\n",
                    i, arrivalTimes[i], burstTimes[i], finishTimes[i], turnaroundTimes[i], waitingTimes[i]);
        }
    }
}
/*
input : 4 2 0 5 1 4 2 2 4 1
output :
Process 	 Arrival Time 	 Burst Time 	 Finish Time 	 Turnaround Time 	 Waiting Time
P0				 0				 5				 12					 12				 7
P1				 1				 4				 11					 10				 6
P2				 2				 2				 6					 4				 2
P3				 4				 1				 9					 5				 4
 */