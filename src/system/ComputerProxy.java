package system;

import java.util.function.Consumer;
import java.util.concurrent.LinkedBlockingQueue;

import api.Task;
import api.RemoteComputer;
import api.Result;

public class ComputerProxy implements Runnable{
	private Computer computer;
	private LinkedBlockingQueue<Task> taskQueue;
	private LinkedBlockingQueue<Result> resultQueue;
	public ComputerProxy(RemoteComputer computer, LinkedBlockingQueue<Task> taskQueue,
		LinkedBlockingQueue<Result> resultQueue){
		computer = computer;
		this.taskQueue = taskQueue;

	}

	public void run(){
		Task task = taskQueue.poll();
		if (task != null){
			try {
				resultQueue.put(computer.execute(task));
			}
			catch(Exception e){
				System.out.println("something happened");
			}
		}
	}
}