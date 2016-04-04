package schedulingalgorithms;

import schedulingsimulation.SchedulingAlgorithm;
import schedulingsimulation.SchedulingMechanisms;
import schedulingsimulation.SimulatedProcess;

public class SchedulingAlgorithmBogus implements SchedulingAlgorithm {

	public SchedulingAlgorithmBogus() {
		// Whatever initialization here for data structures
		// Needed by the scheduler (nothing for the Bogus scheduler)
	}

	
	public void handleCPUBurstCompletionEvent(SimulatedProcess process) {
		// When a CPU burst completes I do nothing because a new process will
		// become ready
		return;
	}

	public void handleExpiredTimeSliceEvent(SimulatedProcess process) {
		// Expired time slices won't happen because I dispatch processes
		// forever (i.e., infinite time slices)
		return;
	}

	public void handleProcessReadyEvent(SimulatedProcess process) {	
		// When a process becomes ready, I check if nobody is running, 
		// and in this case I dispatch the process with an infinite time slice
		if (SchedulingMechanisms.getRunningProcess() == null) {
			SchedulingMechanisms.dispatchProcess(process, -1);
		}
	}
}