/* multithreading with Runnable interface */
class Runner1 extends Thread {
	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			System.out.println("Runner1: " + i);
			try { this.sleep(100); }
			catch (InterruptedException e) {
				System.out.println("Exception.");
			}
		}
	}
}

class Runner2 extends Thread {
	@Override
	public void run() {
		for (int i = 0; i < 50; i++) {
			System.out.println("Runner2: " + i);
			try { this.sleep(100); }
			catch (InterruptedException e) {
				System.out.println("Exception.");
			}
		}
	}
}

public class MultiProcThread {
	public static void main(String[] args) {
		Runner1 r1 = new Runner1();
		Runner2 r2 = new Runner2();
		r1.start();
		r2.start();

		try {
			r1.join();
			// waitin for r1 to die but not for r2
		}
		catch (InterruptedException e) {
			System.out.println("Exception.");
		}

		System.out.println("Finished...");
	}
}
