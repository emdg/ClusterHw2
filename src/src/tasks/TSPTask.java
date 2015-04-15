package tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import helpers.Permutations;
import helpers.TSPHelpers;
import api.Task;


public class TSPTask implements Task<TSPResult> {
	
	private double[][] cities;
	private List<Integer> start;
	private String jobID = "";
	private int seqNr;
	private List basePerm;
	public TSPTask(double[][] cities, List<Integer> start, String jobID, int seqNr){
		this.cities = cities;
		this.start = start;
		this.jobID = jobID;
		this.seqNr = seqNr;
		
		this.basePerm = IntStream.range(0, cities.length).boxed().collect(Collectors.toList());
		this.basePerm.removeAll(this.start);
		
	}
	
	

	
	
	@Override
	public TSPResult call() {
		Permutations<Integer> perm = new Permutations<Integer>(this.basePerm);
		double best = Integer.MAX_VALUE;
		List<Integer> bestPerm = null;
		while (perm.hasNext()){
			List<Integer> p = perm.next();
			double curr = TSPHelpers.calculateRoundTrip(cities, start, p);
			if (curr < best){
				List<Integer> a = new ArrayList<Integer>(start);
				best = curr;
				a.addAll(p);
				bestPerm = a;
			}
		}
		
		System.out.println(bestPerm);
		
		return new TSPResult(bestPerm, best, 100 - 1000L, jobID, seqNr);
	}

}
