package api;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import api.RemoteComputer;

public interface Space extends Remote 
{
    public static int PORT = 8001;
    public static String SERVICE_NAME = "Space";
    
    
    <T extends Result<?>> void putAll ( List<Task<T>> subTasks ) throws RemoteException;

    ArrayList<Result<?>> takeResults(String jobID) throws RemoteException;

    void exit() throws RemoteException;
    
    void register( RemoteComputer computer ) throws RemoteException;
}