package space;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;

import api.Space;
import api.Task;
import api.Result;
import api.RemoteComputer;
import system.ComputerProxy;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class SpaceImpl extends UnicastRemoteObject implements Space {

	private List<Task<? extends Result>> taskList;
	private List<Result<?>> resultList;
	public SpaceImpl() throws RemoteException {
		taskList = (List<Task<? extends Result>>) Collections.synchronizedList(new ArrayList<Task<? extends Result>>());
		resultList = (List<Result<?>>) Collections.synchronizedList(new ArrayList<Result<?>>());
	}

	public <T extends Result<?>> void putAll(List<Task<T>> taskList) throws RemoteException {
		// TODO Auto-generated method stub
    	this.taskList.addAll(taskList);
	}
	

    public ArrayList<Result<?>> takeResults(String jobID) throws RemoteException {
    	ArrayList<Result<?>> resultsForID = new ArrayList<Result<?>>();
    	synchronized(resultList){
    		for (Result<?> result : resultList){
    			if (result.getJobID().equals(jobID)){
    				resultsForID.add(result);
    			}
    		}
    	}
    	
    	resultList.removeAll(resultsForID);
		return resultsForID;
    }

    public void exit()  {
        System.exit(0);
    }
    
    public void register( RemoteComputer computer ) throws RemoteException {
        System.out.println("computer registered");

    	new ComputerProxy(computer, taskList, resultList).run();
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