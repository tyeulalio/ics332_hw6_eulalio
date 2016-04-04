package schedulingalgorithms;

import java.util.Comparator;

import schedulingsimulation.SimulatedProcess;

public class PriorityQueueSort implements Comparator<SimulatedProcess>{
	
	public int compare(SimulatedProcess process1, SimulatedProcess process2){
		Long p1Burst = process1.getCPUBurstDuration();
		Long p2Burst = process2.getCPUBurstDuration();
		
		return p1Burst.compareTo(p2Burst);
	}
	
}
