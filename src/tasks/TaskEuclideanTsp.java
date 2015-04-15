package tasks;
import api.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class TaskEuclideanTsp implements Task<List<Integer>> {

	private double[][] cities;
	private List<Integer> cityPerm;
	public TaskEuclideanTsp(double[][] cities2){
		this.cities = cities2;
		this.cityPerm = IntStream.range(0, cities2.length).boxed().collect(Collectors.toList());
	}

	


	 public List<List<Integer>> generatePerm(List<Integer> original) {
	     if (original.size() == 0) { 
	       List<List<Integer>> result = new ArrayList<List<Integer>>();
	       result.add(new ArrayList<Integer>());
	       return result;
	     }
	     Integer firstElement = original.remove(0);
	     List<List<Integer>> returnValue = new ArrayList<List<Integer>>();
	     List<List<Integer>> permutations = generatePerm(original);
	     for (List<Integer> smallerPermutated : permutations) {
	       for (int index=0; index <= smallerPermutated.size(); index++) {
	         List<Integer> temp = new ArrayList<Integer>(smallerPermutated);
	         temp.add(index, firstElement);
	         returnValue.add(temp);
	       }
	     }
	     return returnValue;
	   }
	 
	 
	 double calculateTrip(List<Integer> permutation){
		
		 double trip = 0.0;
		 
		 double [] current = this.cities[permutation.get(0)];
		 for (int i = 1; i < permutation.size(); i++){
			double[] next =  this.cities[permutation.get(i)];
			trip += Math.abs(next[1] - current[1]) + Math.abs(next[0] - current[0]);
			current = next;
		 }
		 
		 double [] first = this.cities[permutation.get(0)];
		 trip += Math.abs(first[1] - current[1]) + Math.abs(first[0] - current[0]);
		 return trip;
	 }
	 
	
	@Override
	public List<Integer> call() {
		List<List<Integer>> permutations = generatePerm(this.cityPerm);
		List<Integer> besttrip = permutations.get(0);
		double bestScore = Integer.MAX_VALUE;
		
		
		for (List<Integer> permutation : permutations){
			
			double score = calculateTrip(permutation);
			
			if (score < bestScore){
				System.out.println(score);
				bestScore = score;
				besttrip = permutation;
			}
			
		}
		return besttrip;
	}
	
}
