package schedulingalgorithms;

import java.util.LinkedList;
import java.util.Queue;

import schedulingsimulation.SchedulingAlgorithm;
import schedulingsimulation.SchedulingMechanisms;
import schedulingsimulation.SimulatedProcess;

public class SchedulingAlgorithmInteractivePreferred implements SchedulingAlgorithm {
	private Queue<SimulatedProcess> interactiveQueue;
	private	Queue<SimulatedProcess> nonInteractiveQueue;

	public SchedulingAlgorithmInteractivePreferred() {
		// Whatever initialization here for data structures
		// Needed by the scheduler (nothing for the Bogus scheduler)
		interactiveQueue = new LinkedList<SimulatedProcess>();
		nonInteractiveQueue = new LinkedList<SimulatedProcess>();
	}

	
	public void handleCPUBurstCompletionEvent(SimulatedProcess process) {
		runPreferredProcess();
		return;
	}

	public void handleExpiredTimeSliceEvent(SimulatedProcess process) {
		// if the time slice expired and the process isn't done,
		// add it to the nonInteractiveQueue
		nonInteractiveQueue.add(process);
		//System.out.println(process.getName() + " has been added to nonInteractiveQueue");
		runPreferredProcess();
		return;
	}
	public void handleProcessReadyEvent(SimulatedProcess process) {	
		// Add ready processes to the interactiveQueue if it is beginning a job
		// if it's continuing a time expired job, then it's already
		// on the nonInteractiveQueue
		if (!nonInteractiveQueue.contains(process)){
			//System.out.println(process.getName() + " is not in nonInteractiveQueue");
			interactiveQueue.add(process);
			//System.out.println(process.getName() + " has been added to interactiveQueue");
		}
		runPreferredProcess();
	}
	
	public void runPreferredProcess(){
		if (SchedulingMechanisms.getRunningProcess() == null) {
			// get the process from head of queue without removing it from queue
			// if there's no process running, then run process at head of queue
			// if interactiveQueue has processes, then give them priority
			// and run them first
			if (!interactiveQueue.isEmpty())
				SchedulingMechanisms.dispatchProcess(interactiveQueue.poll(), 10);
			// if interactiveQueue is empty, then run processes in nonInteractiveQueue
			else{
				if (!nonInteractiveQueue.isEmpty())
					SchedulingMechanisms.dispatchProcess(nonInteractiveQueue.poll(), 10);
			}
		}
	}
}