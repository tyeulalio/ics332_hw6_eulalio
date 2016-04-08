package schedulingalgorithms;

import schedulingsimulation.SchedulingAlgorithm;
import schedulingsimulation.SchedulingMechanisms;
import schedulingsimulation.SimulatedProcess;

import java.util.LinkedList;
import java.util.Queue;

public class SchedulingAlgorithmNonPreemptiveFCFS implements SchedulingAlgorithm {
	private Queue<SimulatedProcess> processQueue;
	private SimulatedProcess currentProcess; 

	public SchedulingAlgorithmNonPreemptiveFCFS() {
		// Whatever initialization here for data structures
		// Needed by the scheduler
		processQueue = new LinkedList<SimulatedProcess>();
		currentProcess = null; 
	}

	
	public void handleCPUBurstCompletionEvent(SimulatedProcess process) {
		// Check if the queue has any ready processes waiting
		// if it does, then dispatch the process at the head of queue
		// until the queue is empty
		if (!processQueue.isEmpty()) {
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
	}
}