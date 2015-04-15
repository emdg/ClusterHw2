package system;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.concurrent.LinkedBlockingQueue;

import api.Task;
import api.RemoteComputer;
import api.Result;

public class ComputerProxy implements Runnable{
	private RemoteComputer computer;
	private List<Task<? extends Result>> taskList;
	private List<Result<?>> resultList;
	
	public ComputerProxy(RemoteComputer computer, List<Task<? extends Result>> taskList,
		List<Result<?>> resultList2){
		this.computer =  computer;
		this.taskList = taskList;
		this.resultList = resultList2;
	}

	public void run(){
		while(true){
			if (!taskList.isEmpty()){
				Task<? extends Result> task = taskList.get(0);
				try {
					Result<?> res = computer.execute(task);
					this.taskList.remove(task);
					this.resultList.add(res);
				}
				catch(Exception e){
					this.taskList.add(task);
					System.out.println(e);
				}
			}
		}
	}
}