package api;
import java.util.List;

public interface Job<T extends Result<?>> {
	public List<Task<T>> createSubTasks();
	public boolean finished();
	public String getJobID();
	public T createResult();
	
	
	public void fetchResult(List<T> list);
}
