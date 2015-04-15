package tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import api.Job;
import api.Task;

public class MandelJob implements Job<MandelResult> {

	private double lower_left_x = 0.0;
	private double lower_left_y = 0.0;
	private double edge_length = 0.0;
	private int n_pixels = 0;
	private int limit = 0;
	private double x = 0;
	private double y = 0;
	private int div;
	private List<Task<MandelResult>> myTasks;
	private List<MandelResult> receivedResults = new ArrayList<MandelResult>();
	private String jobID;
	
	public MandelJob(double lx, double ly, double edgelength, int n_pixels, int limit, int div, String jobID){
		this.lower_left_x = lx;
		this.lower_left_y = ly;
		this.edge_length = edgelength;
		this.n_pixels = n_pixels;
		this.limit = limit;
		this.div = div;
		this.jobID = jobID;
	}

	@Override
	public List<Task<MandelResult>> createSubTasks() {
		List<Task<MandelResult>> tasks = new ArrayList<Task<MandelResult>>(); 
		int y = 0;
		double newEdge = (double)(edge_length)/div;
		for (int i = 0; i < n_pixels; i += n_pixels/div){
			int x = 0;
			for (int j = 0; j < n_pixels; j+= n_pixels/div){
				MandelTask task = new MandelTask(this.lower_left_x + x * newEdge, this.lower_left_y + y * newEdge, newEdge, (int) n_pixels/div, limit, i, j);
				tasks.add(task);
				x++;
			}
			y++;
		}
		myTasks = tasks;
		return tasks;
	}

	@Override
	public boolean finished() {
		return myTasks.size() == receivedResults.size();
	}

	@Override
	public String getJobID() {
		return this.jobID;
	}
	private void prettyPrintArray(Integer[][] a){
		int n = a.length;
		for (int i = 0; i < n; i++) {
		    for (int j = 0; j < n; j++) {
		        System.out.print(a[i][j] + " ");
		    }
		    System.out.print("\n");
		}
	}
	@Override
	public MandelResult createResult() {
		Integer[][] map = new Integer[n_pixels][n_pixels]; 
		for (MandelResult result : this.receivedResults){
			System.out.println("x"+ result.getX());
			System.out.println(result.getY());
			for (int i = 0; i < n_pixels/div; i++){
				for (int j = 0; j < n_pixels/div; j++){
					map[result.getY() + i][result.getX() + j] = result.getTaskReturnValue()[i][j];
				}
			}
		}
		return new MandelResult(map, 0L, "", 0, 0, 0);
		
	}

	@Override
	public void fetchResult(List<MandelResult> list) {
		receivedResults.addAll(list);		
	}

}
