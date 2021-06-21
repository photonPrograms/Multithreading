import java.util.Random;

public class App {
	public static Random random  = new Random();
	
	public static int[] createRandomArray() {
		int len = 1000000; // number of elements
		int[] a = new int[len];
		for (int i = 0; i < len; i++)
			a[i] = random.nextInt(100000);
		return a;
	}

	public static void main(String[] args) throws Throwable {
		int nthreads = Runtime.getRuntime().availableProcessors();
		// number of threads to be taken as number of processors available
		System.out.println("Number of threads: " + nthreads);
		
		int arr[] = createRandomArray();
		MergeSort obj1 = new MergeSort(arr.clone());
		MergeSort obj2 = new MergeSort(arr.clone());

		long start_tm1 = System.currentTimeMillis(); 
		obj1.parallelMergeSort(0, arr.length - 1, nthreads);
		long end_tm1 = System.currentTimeMillis();
		//obj1.show();

		long start_tm2 = System.currentTimeMillis();
		obj2.mergeSort(0, arr.length - 1);
		long end_tm2 = System.currentTimeMillis();
		//obj2.show();
		
		System.out.println("Parallel: " + (end_tm1 - start_tm1));
		System.out.println("Sequential: " + (end_tm2 - start_tm2));
	}
}
