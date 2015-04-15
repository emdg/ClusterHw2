package space;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import api.Space;
import api.Task;
import api.Result;
import api.RemoteComputer;
import system.ComputerProxy;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class SpaceImpl extends UnicastRemoteObject implements Space {

	private LinkedBlockingQueue<Task> taskQueue;
	private LinkedBlockingQueue<Result> resultQueue;
	public SpaceImpl() throws RemoteException {
		taskQueue = new LinkedBlockingQueue<Task>();
		resultQueue = new LinkedBlockingQueue<Result>();
	}



    public void putAll ( List<Task> taskList ) throws RemoteException {
    	this.taskQueue.addAll(taskList);
    }

    public Result take() throws RemoteException {
    	return resultQueue.poll();
    }

    public void exit()  {
        System.exit(0);
    }
    
    public void register( RemoteComputer computer ) throws RemoteException {
        System.out.println("computer registered");
        System.out.println(taskQueue);

    	new ComputerProxy(computer, taskQueue, resultQueue).run();
    }


    public static void main(String[] args) throws RemoteException {
        System.setSecurityManager( new SecurityManager() );

        // instantiate a server object
        SpaceImpl space = new SpaceImpl();
        // construct an rmiregistry within this JVM using the default port
        Registry registry = LocateRegistry.createRegistry( Space.PORT );

        // bind server in rmiregistry. Can throw exceptions. See api.
        registry.rebind( Space.SERVICE_NAME, space);

        System.out.println("Space.main: Ready.");
    }


}