package schedulingalgorithms;

import java.util.LinkedList;
import java.util.Queue;

import schedulingsimulation.SchedulingAlgorithm;
import schedulingsimulation.SchedulingMechanisms;
import schedulingsimulation.SimulatedProcess;

public class SchedulingAlgorithmRoundRobin implements SchedulingAlgorithm {
	private Queue<SimulatedProcess> processQueue;

	public SchedulingAlgorithmRoundRobin() {
		// Whatever initialization here for data structures
		// Needed by the scheduler (nothing for the Bogus scheduler)
		processQueue = new LinkedList<SimulatedProcess>();
	}

	
	public void handleCPUBurstCompletionEvent(SimulatedProcess process) {
		if (SchedulingMechanisms.getRunningProcess() == null){
			// no process running, check if any are in the queue
			if (!processQueue.isEmpty()) {
				// start the process at the head of the queue
				//currentProcess = processQueue.poll();
				SchedulingMechanisms.dispatchProcess(processQueue.poll(), 10);
			}
			return;
		}
	}

	public void handleExpiredTimeSliceEvent(SimulatedProcess process) {
		// check if there's a process running
		if (SchedulingMechanisms.getRunningProcess() == null){
			// no process running, check if any are in the queue
			if (!processQueue.isEmpty()) {
				// start the process at the head of the queue
				SchedulingMechanisms.dispatchProcess(processQueue.poll(), 10);
			}
			return;
		}
	}
	public void handleProcessReadyEvent(SimulatedProcess process) {	
		// Add ready processes to a queue as they come in
		processQueue.add(process);
		// if there's no process running, then run process at head of queue
		if (SchedulingMechanisms.getRunningProcess() == null) {
			SchedulingMechanisms.dispatchProcess(processQueue.poll(), 10);
		}
	}
}