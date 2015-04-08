package tasks;
import api.Task;




public class TaskMandelbrotSet implements Task<Integer[][]>{
	private double lower_left_x = 0.0;
	private double lower_left_y = 0.0;
	private double edge_length = 0.0;
	private int n_pixels = 0;
	private int limit = 0;

	public TaskMandelbrotSet(double lx, double ly, double edgelength, int n_pixels, int limit){
		this.lower_left_x = lx;
		this.lower_left_y = ly;
		this.edge_length = edgelength;
		this.n_pixels = n_pixels;
		this.limit = limit;
	}

	public int mandel(double cx,  double cy){
		Complex c = new Complex(cx, cy);
		
		Complex z = new Complex(0, 0);
		
		for (int i = 0; i < limit; i++){
			z = z.times(z).add(c);
			
			if (z.real() * z.real() + z.imag() * z.imag() >= 4)
				return i;	
		}
	    return limit;
	}
	
	@Override
	public Integer[][] execute() {
		// TODO Auto-generated method stub
		
		Integer[][] count = new Integer[n_pixels][n_pixels];
		double x,y,xtemp;
	    double sx=lower_left_x,sy=lower_left_y;
	    double step = (double)edge_length/n_pixels;
	    for (int i = 0; i <n_pixels; i++) {
	        for (int j = 0; j < n_pixels; j++) {
	            x=0;
	            y=0;
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
		return count;
	}
	
	
	
	
	private class Complex{
		private double re;
		private double i;
		public Complex(double re, double i){
			this.re = re;
			this.i = re;
		}
		
		
		public Complex add(Complex c){
			return new Complex(this.re + c.re, this.i + c.i);
		}
		
		public double length(){
			return Math.sqrt(this.re * this.re - this.i * this.i);
		}
		
		public double abs(){
			return this.re;
		}
		
		public double real(){
			return this.re;
		}
		
		public double imag(){
			return this.i;
		}
		
		public Complex times(Complex w) {
		        return new Complex(re*w.real()-i*w.imag(),re*w.imag()+i*w.real());
		}
		
		public Complex power(double x) {
			    double modulus = Math.sqrt(re*re + i*i);
			    double arg = Math.atan2(i,re);
			    double log_re = Math.log(modulus);
			    double log_im = arg;
			    double x_log_re = x * log_re;
			    double x_log_im = x * log_im;
			    double modulus_ans = Math.exp(x_log_re);
			    return new Complex(modulus_ans*Math.cos(x_log_im), modulus_ans*Math.sin(x_log_im));
		}
		
	}
	
	
}
