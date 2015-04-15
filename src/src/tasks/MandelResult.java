package tasks;

import java.util.List;

import api.Result;

public class MandelResult extends Result<Integer[][]>{
	private int x;
	private int y;
	public MandelResult(Integer[][] taskReturnValue, long taskRunTime,
			String jobID, int seqNr, int x, int y) {
		super(taskReturnValue, taskRunTime, jobID, seqNr);
		this.x = x;
		this.y = y;
	}
	
	
	public int getX(){
		return x;
		
	}
	
	public int getY(){
		return y;
	}

}