package tasks;

import java.util.List;

import api.Result;

public class TSPResult extends Result<List<Integer>> {
	private double pathLength = -1;
	public TSPResult(List<Integer> taskReturnValue, double pathLength, long taskRunTime, String jobID, int seqNr) {
		super(taskReturnValue, taskRunTime, jobID, seqNr);
		this.pathLength = pathLength;
	}

	
	public double getPathLength(){
		return this.pathLength;
	}

}
