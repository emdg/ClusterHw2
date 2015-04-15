package clients;
import java.awt.BorderLayout;
import java.awt.Container;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import api.Job;
import api.Result;
import api.Space;
import api.Task;


/**
 *
 * @author Peter Cappello
 * @param <T> return type the Task that this Client executes.
 * @param <V>
 */
public class Client<V, T extends Result<V>> extends JFrame
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final protected Job<T> job;
                protected V taskReturnValue;
                private long clientStartTime;
                
                private Space space;
    
    public Client( final String title, final String domainName, final Job<T> job ) 
            throws RemoteException, NotBoundException, MalformedURLException
    {     
        this.job = job;
        setTitle( title );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        
        String url = "rmi://" + domainName + ":" + Space.PORT + "/" + Space.SERVICE_NAME;
		space = (Space) Naming.lookup( url );

    }
    
    public void begin() { clientStartTime = System.nanoTime(); }
    
    public void end() 
    {
        Logger.getLogger( Client.class.getCanonicalName() )
            .log(Level.INFO, "Client time: {0} ms.", ( System.nanoTime() - clientStartTime) / 1000000 );
    }
    
    public void add( final JLabel jLabel )
    {
        final Container container = getContentPane();
        container.setLayout( new BorderLayout() );
        container.add( new JScrollPane( jLabel ), BorderLayout.CENTER );
        pack();
        setVisible( true );
    }
    
    public V processJob() throws RemoteException {
    	List<Task<T>> subTasks = job.createSubTasks();
    	space.putAll(subTasks);
    	
    	while (true){
    		
    		if (job.finished()){
    			System.out.println("feeeerrrrdig");
    			break;
    		}
    		List<Result<?>> a = space.takeResults(job.getJobID());
    		job.fetchResult((List<T>) a);
    	}
    	
    	T res = job.createResult();
    	
  
		return res.getTaskReturnValue();
    }
    
    

}