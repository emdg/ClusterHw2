package tasks;
import helpers.Permutations;
import helpers.TSPHelpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import api.Job;
import api.Result;
import api.Task;
import helpers.TSPHelpers;

public class TSPJob implements Job<TSPResult> {
	
	private String jobID;
	private int div;
	
	private double[][] cities;
	private List<Integer> cityPerm;
	private List<TSPResult> receivedResults = new ArrayList<TSPResult>();
	private List<Task<TSPResult>> tasks;
	public TSPJob(double[][] cities, String jobID, int div){
		this.jobID = jobID;
		this.div = div;
		this.cities = cities;
		this.cityPerm = IntStream.range(0, cities.length).boxed().collect(Collectors.toList());
		this.tasks = new ArrayList<Task<TSPResult>>();

	}
	
	

	
	public List<Task<TSPResult>> createSubTasks() {
		List<Task<TSPResult>> tasks = new ArrayList<Task<TSPResult>>();
		int j = 0;
		for (int i = 1; i < cities.length; i++){
			
			List<Integer> start = Arrays.asList(0, i);
			
			TSPTask task = new TSPTask(cities, start, jobID, j);
			tasks.add(task);
			j += 1;
		}
		
		
		this.tasks = tasks;
		
		return tasks;
	}

	public boolean finished(){
		return receivedResults.size() == tasks.size();
	}
	
	public String getJobID(){
		return this.jobID;
	}
	
	public TSPResult createResult(){
		System.out.println(this.tasks);
		TSPResult best = this.receivedResults.get(0);
		for (TSPResult result : this.receivedResults){
			if (result.getPathLength() < best.getPathLength()){
				best = result;
			}
		}
		return best;
	}


	@Override
	public void fetchResult(List<TSPResult> list) {
		receivedResults.addAll(list);
	}
}
