package computer;
import api.Computer;
import api.Task;


import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class ComputerImpl extends UnicastRemoteObject implements Computer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public ComputerImpl() throws RemoteException {}

	@Override
	public <T> T execute(Task<T> task) {
		return task.execute();
	}

	
    public static void main(String[] args) throws Exception
    {
        // construct & set a security manager (unnecessary in this case)
        System.setSecurityManager( new SecurityManager() );

        // instantiate a server object
        ComputerImpl computerImpl = new ComputerImpl(); // can throw RemoteException

        // construct an rmiregistry within this JVM using the default port
        Registry registry = LocateRegistry.createRegistry( Computer.PORT );

        // bind server in rmiregistry. Can throw exceptions. See api.
        registry.rebind( Computer.SERVICE_NAME, computerImpl );

        System.out.println("computerImpl.main: Ready.");
    }
	
}
