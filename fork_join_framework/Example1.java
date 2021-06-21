/* illustration of the fork join framework */

import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

class SimpleRecursiveAction extends RecursiveAction {
	int work_load, max_load;

	SimpleRecursiveAction(int work_load) {
		this.work_load = work_load;
		this.max_load = 100;
	}
	
	@Override
	protected void compute() {
		if (work_load > max_load) {
			// splitting the task if it is too large
			System.out.println("Parallel execution needed...\nSplitting the task.");
			
			SimpleRecursiveAction task1 = new SimpleRecursiveAction(work_load / 2);
			SimpleRecursiveAction task2 = new SimpleRecursiveAction(work_load - work_load / 2);

			task1.fork();
			task2.fork();

			task1.join();
			task2.join();
		}

		else {
			System.out.println("Sequential execution...\n" + work_load);
		}
	}
}

public class Example1 {
	public static void main(String[] args) {
		// as many threads as # of available processors
		ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());

		int work_load = 600;
		SimpleRecursiveAction task = new SimpleRecursiveAction(work_load);
		pool.invoke(task);
	}
}
