public class MergeSort {
	int arr[];
	int tempArr[];

	public MergeSort(int[] arr) {
		this.arr = arr;
		tempArr = new int[arr.length];
	}

	// l = lowest index of subarray, h = highest index of subarray
	public void mergeSort(int l, int h) {
		if (l >= h)
			return;
		int m = (l + h) / 2;
		mergeSort(l, m);
		mergeSort(m + 1, h);
		merge(l, m, h);
	}

	public void merge(int l, int m, int h) {
		for (int i = l; i <= h; i++)
			tempArr[i] = arr[i];
		int i = l, j = m + 1, k = l;
		
		while (i <= m && j <= h)
			if (tempArr[i] <= tempArr[j])
				arr[k++] = tempArr[i++];
			else
				arr[k++] = tempArr[j++];

		while (i <= m)
			arr[k++] = tempArr[i++];

		while (j <= h)
			arr[k++] = tempArr[j++];
	}

	public void show() {
		for (int i = 0; i < arr.length; i++)
			System.out.print(arr[i] + " ");
		System.out.println();
	}

	/* for parallel merge sort*/
	public Thread mergeSortParallel(int l, int h, int nthreads) {
		return new Thread() {
			@Override
			public void run() {
				parallelMergeSort(l, h, nthreads / 2);
			}
		};
	}

	public void parallelMergeSort(int l, int h, int nthreads) {
		if (nthreads <= 1) {
			mergeSort(l, h);
			return;
		}
		
		int m = (l + h) / 2;
		Thread lsorter = mergeSortParallel(l, m, nthreads); // left sorter
		Thread rsorter = mergeSortParallel(m + 1, h, nthreads); // right sorter
		lsorter.start();
		rsorter.start();

		try {
			lsorter.join();
			rsorter.join();
		}
		catch (InterruptedException e) {
			System.out.println("InterruptedException");
		}
		
		merge(l, m, h);
	}
}
