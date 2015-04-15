package system;



import api.RemoteComputer;
import api.Task;
import api.Space;
import api.Result;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import java.net.MalformedURLException;
import java.rmi.server.UnicastRemoteObject;



public class Computer extends UnicastRemoteObject implements RemoteComputer {
	private Space space;
	public Computer(String spaceDomain) throws RemoteException, NotBoundException, MalformedURLException{
		String url = "rmi://" + spaceDomain + ":" + Space.PORT + "/" + Space.SERVICE_NAME;
		space = (Space) Naming.lookup( url );
		space.register(this);
	}


	public <T> Result<T> execute(Task<T> task){
		long a = System.currentTimeMillis();
		T tommyT = task.call();
		long b = System.currentTimeMillis();
		return new Result(tommyT, b-a);
	}

	public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException{
		System.setSecurityManager( new SecurityManager() );
        
        String domain;
        if(args.length == 0){
        	domain = "localhost";
        }
        else {
        	domain = args[0];
        }
        
        
        final Computer client = new Computer(domain);	
    }
}	