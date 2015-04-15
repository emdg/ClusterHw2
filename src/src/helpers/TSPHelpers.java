package helpers;

import java.util.ArrayList;
import java.util.List;

public class TSPHelpers {
	
	public static double distanceBetweenCities(double[][] cities, Integer city1, Integer city2){
		return Math.abs(cities[city2][1] - cities[city1][1]) + Math.abs(cities[city2][0] - cities[city1][0]);
	}
	
	
	public static double calculateRoundTrip(double[][] cities, List<Integer> start, List<Integer> permutation){
		
		double score = distanceBetweenCities(cities, start.get(0), start.get(1));
		score += distanceBetweenCities(cities, start.get(1), permutation.get(0));
		for (int i = 1; i < permutation.size(); i++){
			Integer city2 = permutation.get(i);
			Integer city1 = permutation.get(i - 1);
			score += distanceBetweenCities(cities, city1, city2);
		}
		
		Integer city2 = start.get(0);
		Integer city1 = permutation.get(permutation.size() - 1);
		score += distanceBetweenCities(cities, city1, city2);
		return score;
	}
	
	
}

