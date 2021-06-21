import java.util.concurrent.Semaphore;

public class BasicSemaphore {
	public static Semaphore semaphore = new Semaphore(4, true);

	public static void message() {
		try {
			semaphore.acquire();
			System.out.println("Hard at work...");
			Thread.sleep(2000);
		}
		catch (InterruptedException e) {
			System.out.println("Exception Detected.");
		}
		finally {
			semaphore.release();
		}
	}

	public static void main(String[] args) {
		Thread threads[] = new Thread[100];
		for (int i = 0; i < 100; i++)
			threads[i] = new Thread(new Runnable() {
				@Override
				public void run() {
					message();
				}
			});
		for (int i = 0; i < 100; i++)
			threads[i].start();
		try {
			for (int i = 0; i < 100; i++)
				threads[i].join();
		}
		catch (InterruptedException e) {
			System.out.println("Exception Detected.");
		}

		System.out.println("Hard work paid off.");
	}
}
