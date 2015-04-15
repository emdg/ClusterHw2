package tasks;

import api.Task;

public class MandelTask implements Task<MandelResult> {
	
	
	private double lower_left_x = 0.0;
	private double lower_left_y = 0.0;
	private double edge_length = 0.0;
	private int n_pixels = 0;
	private int limit = 0;
	private int x = 0;
	private int y = 0;
	
	public MandelTask(double lx, double ly, double edgelength, int n_pixels, int limit, int x, int y){
		this.lower_left_x = lx;
		this.lower_left_y = ly;
		this.edge_length = edgelength;
		this.n_pixels = n_pixels;
		this.limit = limit;
		this.x = x;
		this.y = y;
		
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
		
	public MandelResult call() {
		Integer[][] count = new Integer[n_pixels][n_pixels];
		double x,y,xtemp;
	    double sx=lower_left_x,sy=lower_left_y;
	    double step = (double)edge_length/n_pixels;
	    for (int i = 0; i <n_pixels; i++) {
	        for (int j = 0; j < n_pixels; j++) {
	            x=0.0;
	            y=0.0;
	            sx+=step;
	            int iteration =0;
	            while ( x*x + y*y < 4  &&  iteration < limit){
	                xtemp = x*x - y*y + sx;
	                y = 2*x*y + sy;
	                x = xtemp;
	                iteration++;
	            }
	            count[j][i]=iteration;
	        }
	        sx=lower_left_x;
	        sy+=step;
	    }
	    
	    
	    
	    
	    
		return new MandelResult(count, 0L, "mandel", 1, this.x, this.y);
	}

}
