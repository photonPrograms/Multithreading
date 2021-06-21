import java.util.Random;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;
import java.util.Arrays;

class ParallelMergeSort extends RecursiveAction {
	int arr[];

	ParallelMergeSort(int arr[]) {
		this.arr = arr;
	}

	@Override
	protected void compute() {
		int l = arr.length;
		if (l <= MergeSorting.LIM)
			merge_sort(arr);

		else {
			int mid = l / 2;
			int larr[] = Arrays.copyOfRange(arr, 0, mid);
			int rarr[] = Arrays.copyOfRange(arr, mid, l);

			ParallelMergeSort t1 = new ParallelMergeSort(larr);
			ParallelMergeSort t2 = new ParallelMergeSort(rarr);

			t1.fork();
			t2.fork();

			t1.join();
			t2.join();

			merge(larr, rarr, arr);
		}
	}

	void merge_sort(int arr[]) {
		int l = arr.length;
		if(l <= 1)
			return;

		int mid = (l + 1) / 2;
		int larr[] = Arrays.copyOfRange(arr, 0, mid);
		int rarr[] = Arrays.copyOfRange(arr, mid, l);

		merge_sort(larr);
		merge_sort(rarr);
		
		merge(larr, rarr, arr);
	}

	void merge(int larr[], int rarr[], int arr[]) {
		int i, j, k;
		i = j = k = 0;
		while (i < larr.length && j < rarr.length)
			if (larr[i] <= rarr[j])
				arr[k++] = larr[i++];
			else
				arr[k++] = rarr[j++];
		
		while (i < larr.length)
			arr[k++] = larr[i++];

		while (j < rarr.length)
			arr[k++] = rarr[j++];
	}
}

public class MergeSorting {
	public static int LIM = 10;
	
	public static int[] arr_gen(int size) {
		Random random = new Random();
		int arr[] = new int[size];
		for (int i = 0; i < size; i++)
			arr[i] = random.nextInt(size);
		return arr;
	}

	public static void arr_show(int arr[]) {
		for (int i = 0; i < arr.length; i++)
			System.out.print(arr[i] + " ");
		System.out.println();
	}

	public static void main(String[] args) {
		int size = 30;
		int arr[] = arr_gen(size);
		arr_show(arr);

		ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
		ParallelMergeSort t = new ParallelMergeSort(arr);
		pool.invoke(t);

		arr_show(arr);		
	}
}
