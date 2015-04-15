package api;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteComputer extends Remote {
	
	public int PORT = 1099;
	public String SERVICE_NAME = "Computer";
	
	public <T> Result<T> execute(Task<T> task) throws RemoteException ;
	
}
