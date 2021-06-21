import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;
import java.util.Random;

class ParallelMaxFinder extends RecursiveTask<Integer> {
	int arr[], max_size, low, high;

	ParallelMaxFinder(int arr[], int low, int high) {
		this.arr = arr;
		this.max_size = 100;
		this.low = low;
		this.high = high;
	}

	@Override
	protected Integer compute() {
		int l = (high - low + 1), max;
		if (l > max_size) {
			int mid = (low + high) / 2;

			ParallelMaxFinder m1 = new ParallelMaxFinder(arr, low, mid);
			ParallelMaxFinder m2 = new ParallelMaxFinder(arr, mid + 1, high);

			m1.fork();
			m2.fork();

			int max1 = m1.join();
			int max2 = m2.join();
			max = max1 >= max2 ? max1 : max2;
		}

		else {
			max = arr[low];
			for (int i = low + 1; i <= high; i++)
				if (arr[i] > max)
					max = arr[i];
		}

		return max;
	}
}

public class MaxFinding {
	static int[] arr_generator(int size) {
		Random random = new Random();
		int arr[] = new int[size];

		for (int i = 0; i < size; i++)
			arr[i] = random.nextInt(size);

		return arr;
	}

	public static void main(String[] args) {
		int size = 200;
		int arr[] = arr_generator(size);
		
		ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
		ParallelMaxFinder m = new ParallelMaxFinder(arr, 0, arr.length - 1);

		System.out.println(pool.invoke(m));
	} 
}
