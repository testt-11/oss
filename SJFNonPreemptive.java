import java.util.*;

public class SJFNonPreemptive {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number of processes: ");
        int processes = sc.nextInt();

        int[] arrivalTimes = new int[processes];
        int[] burstTimes = new int[processes];

        for (int i = 0; i < processes; i++) {
            System.out.println("Enter arrival time for process P" + i + ": ");
            arrivalTimes[i] = sc.nextInt();
            System.out.println("Enter burst time for process P" + i + ": ");
            burstTimes[i] = sc.nextInt();
        }

        int time = 0;
        int[] finishTimes = new int[processes];
        int[] turnaroundTimes = new int[processes];
        int[] waitingTimes = new int[processes];
        Set<Integer> executed = new HashSet<>();

        // Process until all processes are executed
        while (executed.size() < processes) {
            // Add all available processes to the ready queue
            Queue<Integer> readyQueue = new LinkedList<>();
            for (int i = 0; i < processes; i++) {
                if (!executed.contains(i) && arrivalTimes[i] <= time) {
                    readyQueue.add(i);
                }
            }

            if (readyQueue.isEmpty()) {
                time++; // Increment time if no process is ready
                continue;
            }

            // Find the process with the minimum burst time in the ready queue
            int process = findMinBurstTimeProcess(readyQueue, burstTimes, arrivalTimes);

            // Process execution
            time += burstTimes[process];
            finishTimes[process] = time;
            executed.add(process);

            // Calculate turnaround and waiting times
            turnaroundTimes[process] = finishTimes[process] - arrivalTimes[process];
            waitingTimes[process] = turnaroundTimes[process] - burstTimes[process];
        }

         // Print the results
        System.out.println("Process \t Arrival Time \t Burst Time \t Finish Time \t Turnaround Time \t Waiting Time");
        for (int i = 0; i < processes; i++) {
            System.out.printf("P%d\t\t %d\t\t %d\t\t %d\t\t\t %d\t\t %d\n",
                    i, arrivalTimes[i], burstTimes[i], finishTimes[i], turnaroundTimes[i], waitingTimes[i]);
        }
    }

    private static int findMinBurstTimeProcess(Queue<Integer> readyQueue, int[] burstTimes, int[] arrivalTimes) {
        // Select the process with the minimum burst time
        int minBurstTime = Integer.MAX_VALUE;
        int minProcess = -1;

        for (int process : readyQueue) {
            if (burstTimes[process] < minBurstTime || (burstTimes[process] == minBurstTime && arrivalTimes[process] < arrivalTimes[minProcess])) {
                minBurstTime = burstTimes[process];
                minProcess = process;
            }
        }

        return minProcess;
    }
}
/*
input : 4 1 3 2 4 1 2 4 4
Process 	 Arrival Time 	 Burst Time 	 Finish Time 	 Turnaround Time 	 Waiting Time
P0				 1				 3				 6					 5				 2
P1				 2				 4				 10					 8				 4
P2				 1				 2				 3					 2				 0
P3				 4				 4				 14					 10				 6

 */