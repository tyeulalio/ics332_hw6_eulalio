package schedulingalgorithms;

import java.util.LinkedList;
import java.util.Queue;

import schedulingsimulation.SchedulingAlgorithm;
import schedulingsimulation.SchedulingMechanisms;
import schedulingsimulation.SimulatedProcess;

public class SchedulingAlgorithmRoundRobin implements SchedulingAlgorithm {
	private Queue<SimulatedProcess> processQueue = new LinkedList<SimulatedProcess>();
	private SimulatedProcess currentProcess = null; 

	public SchedulingAlgorithmRoundRobin() {
		// Whatever initialization here for data structures
		// Needed by the scheduler (nothing for the Bogus scheduler)
	}

	
	public void handleCPUBurstCompletionEvent(SimulatedProcess process) {
		// When a CPU burst completes I do nothing because a new process will
		// become ready
		return;
	}

	public void handleExpiredTimeSliceEvent(SimulatedProcess process) {
		if (SchedulingMechanisms.getRunningProcess() == null){
			if (!processQueue.isEmpty()) {
				currentProcess = processQueue.poll();
				System.out.println("Current process: " + currentProcess.getName());
				SchedulingMechanisms.dispatchProcess(currentProcess, 10);
			}
			return;
		}
	}
	public void handleProcessReadyEvent(SimulatedProcess process) {	
		// Add ready processes to a queue as they come in
		processQueue.add(process);
		// get the process from head of queue without removing it from queue
		currentProcess = processQueue.peek();
		// if there's no process running, then run process at head of queue
		if (SchedulingMechanisms.getRunningProcess() == null) {
			System.out.println("Current ready process: " + currentProcess.getName());
			SchedulingMechanisms.dispatchProcess(currentProcess, 10);
			processQueue.poll(); // remove the process from the queue
		}
	}
}