/* illustration of the fork join framework */

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

class SimpleRecursiveTask extends RecursiveTask<Integer> {
	int work_load, max_load;

	SimpleRecursiveTask(int work_load) {
		this.work_load = work_load;
		this.max_load = 100;
	}
	
	@Override
	protected Integer compute() {
		if (work_load > max_load) {
			// splitting the task if it is too large
			System.out.println("Parallel execution needed...\nSplitting the task.");
			
			SimpleRecursiveTask task1 = new SimpleRecursiveTask(work_load / 2);
			SimpleRecursiveTask task2 = new SimpleRecursiveTask(work_load - work_load / 2);

			task1.fork();
			task2.fork();

			int result = 0;
			result += task1.join();
			result += task2.join();

			return result;
		}

		else {
			System.out.println("Sequential execution...");
			return 2 * work_load;
		}
	}
}

public class Example2 {
	public static void main(String[] args) {
		// as many threads as # of available processors
		ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());

		int work_load = 120;
		SimpleRecursiveTask task = new SimpleRecursiveTask(work_load);
		System.out.println(pool.invoke(task));
	}
}
