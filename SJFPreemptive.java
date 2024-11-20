import java.util.*;

public class SJFPreemptive {
    public static void main(String[] args) {
        // taking no.of processes , arrival time and burst time from user
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number of processes : ");
        int processes = sc.nextInt();

        int [] arrivalTimes = new int[processes];
        int [] burstTimes = new int[processes];
        int [] remainingTimes = new int[processes];
        for(int i = 0; i < processes; i++){
            System.out.println("Enter arrival time for process P"+i+" : ");
            arrivalTimes[i] = sc.nextInt();
            System.out.println("Enter burst time for process P"+i+" : ");
            burstTimes[i] = sc.nextInt();
            remainingTimes[i] = burstTimes[i];
        }

        // find min arrival time
        int minArrivalTime = Integer.MAX_VALUE;
        for(int arrivalTime : arrivalTimes){
            minArrivalTime = Math.min(minArrivalTime,arrivalTime);
        }

        Queue<Integer> readyQueue = new LinkedList<>(); // declare ready queue
        Set<Integer> executed = new HashSet<>(); // to keep track of executed processes

        // put the process which arrived at very beginning to the queue. (initialize ready queue with first arrival process)
        for(int j = 0; j < processes; j++){
            if(arrivalTimes[j] == minArrivalTime){
                readyQueue.offer(j);
            }
        }


        int time = minArrivalTime; // to keep track of time
        int [] finishTimes = new int[processes];
        int [] turnaroundTimes = new int[processes];
        int [] waitingTimes = new int[processes];

        while (!readyQueue.isEmpty()){
            int process = findMinRemainingTimeProcess(readyQueue,remainingTimes,arrivalTimes,executed); // select the process

            time += 1; // execute it for time quantum 1
            remainingTimes[process] -=1; // reduce processes remaining time by 1

            // check whether process is executed completely
            if(remainingTimes[process] <= 0) {
                finishTimes[process] = time; // set finish time for that process
                readyQueue.remove(process);// remove that process from RQ and add to executed
                executed.add(process); // add that to executed
            }
            // Update ready queue. (in that time (1), if any new process arrives add that to the ready queue)
            for(int i = 0; i < processes; i++){
                if(!executed.contains(i) && !readyQueue.contains(i) && arrivalTimes[i] <= time){
                    readyQueue.add(i);
                }
            }
        }

        // Now calculate turnaround and waiting

        for(int i = 0; i < processes; i++){
            turnaroundTimes[i] = finishTimes[i] - arrivalTimes[i];
            waitingTimes[i] = turnaroundTimes[i] - burstTimes[i];
        }

         // Print the results
        System.out.println("Process \t Arrival Time \t Burst Time \t Finish Time \t Turnaround Time \t Waiting Time");
        for (int i = 0; i < processes; i++) {
            System.out.printf("P%d\t\t %d\t\t %d\t\t %d\t\t\t %d\t\t %d\n",
                    i, arrivalTimes[i], burstTimes[i], finishTimes[i], turnaroundTimes[i], waitingTimes[i]);
        }

    }

    private static int findMinRemainingTimeProcess(Queue<Integer> readyQueue, int [] remainingTimes,int [] arrivalTimes,Set<Integer>executed){
        // Find min remaining time
        int minRemTime = Integer.MAX_VALUE;
        for (int process : readyQueue) {
            if (!executed.contains(process) && remainingTimes[process] < minRemTime) {
                minRemTime = remainingTimes[process];
            }
        }

        // add process with remaining time equals minRemTime to the list
        List<Integer> processesWithSameRemTime = new ArrayList<>();
        for(int process : readyQueue){
            if(!executed.contains(process) && remainingTimes[process] == minRemTime){
                processesWithSameRemTime.add(process);
            }
        }
        // if there is only 1 process with min remaining time then just return that process
        if(processesWithSameRemTime.size() == 1){
            return processesWithSameRemTime.get(0);
        }

        // since there are more than 1 processes with same minimum remaining time , now compare them according to
        // their arrival time.
        int minArriTime = Integer.MAX_VALUE;
        int processId = -1;
        for(int process : processesWithSameRemTime){
            if(!executed.contains(process) && arrivalTimes[process] < minArriTime){
                minArriTime = arrivalTimes[process];
                processId = process;
            }
        }
        return processId;
    }
}

/*
input : 4 0 5 1 3 2 4 4 1
output :
Process 		 Arrival Time 		 Burst Time 		 Finish Time 		 Turnaround Time 		 Waiting Time
P0					0					5					9					9						4
P1					1					3					4					3						0
P2					2					4					13					11						7
P3					4					1					5					1						0



 */
