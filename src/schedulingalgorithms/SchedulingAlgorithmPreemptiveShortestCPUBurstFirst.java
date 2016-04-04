package schedulingalgorithms;

import java.util.PriorityQueue;

import schedulingsimulation.SchedulingAlgorithm;
import schedulingsimulation.SchedulingMechanisms;
import schedulingsimulation.SimulatedProcess;

public class SchedulingAlgorithmPreemptiveShortestCPUBurstFirst implements SchedulingAlgorithm {
	private PriorityQueueSort processSort = new PriorityQueueSort();
	private PriorityQueue<SimulatedProcess> processQueue = new PriorityQueue<SimulatedProcess>(10, processSort);
	private SimulatedProcess currentProcess = null;
	private SimulatedProcess runningProcess = null;
	private Long cpBurst, rpBurst = (long) 0;
	
	public SchedulingAlgorithmPreemptiveShortestCPUBurstFirst() {
		// Whatever initialization here for data structures
		// Needed by the scheduler (nothing for the Bogus scheduler)
	}

	
	public void handleCPUBurstCompletionEvent(SimulatedProcess process) {
		while (!processQueue.isEmpty()) {
			currentProcess = processQueue.poll();
			SchedulingMechanisms.dispatchProcess(currentProcess, -1);
		}
		return;
	}

	public void handleExpiredTimeSliceEvent(SimulatedProcess process) {
		// Expired time slices won't happen because I dispatch processes
		// forever (i.e., infinite time slices)
		return;
	}

	public void handleProcessReadyEvent(SimulatedProcess process) {	
		// Add ready processes to a queue as they come in
				processQueue.add(process);
				// get the process from head of queue without removing it from queue
				currentProcess = processQueue.peek();
				// if there's no process running, then run process at head of queue
				if (SchedulingMechanisms.getRunningProcess() == null) {
					SchedulingMechanisms.dispatchProcess(currentProcess, -1);
					processQueue.poll(); // remove the process from the queue
				}
				// if there's a process running, then check if the CPU burst time
				// is less than the new ready process' CPU burst time
				// if it is, then keep it running
				// if not, then preempt it and run new ready process
				else{
					runningProcess = SchedulingMechanisms.getRunningProcess();
					cpBurst = currentProcess.getCPUBurstDuration();
					rpBurst = runningProcess.getCPUBurstDuration();
					
					if (cpBurst < rpBurst){
						SchedulingMechanisms.preemptRunningProcess();
						SchedulingMechanisms.dispatchProcess(currentProcess, -1);
						processQueue.poll();
					}
				}
	}
}