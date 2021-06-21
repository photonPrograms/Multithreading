/* wait() and notify() to pass control between threads */

class Processor {
	public void produce() throws InterruptedException {
		synchronized (this) {
			System.out.println("In produce() 1.");
			wait();
			System.out.println("In produce() 2.");
		}
	}

	public void consume() throws InterruptedException {
		synchronized (this) {
			System.out.println("In consume() 1.");
			notify();
			System.out.println("In consume() 2.");
		}
	}
}

public class WaitAndNotify {
	public static void main(String[] args) {
		Processor p1 = new Processor();
		
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				try { 
					p1.produce();
				}
				catch (InterruptedException e) {
					System.out.println("Exception.");
				}
			}
		});
		Thread t2 = new Thread(new Runnable() {
			public void run() {
				try { 
					p1.consume();
				}
				catch (InterruptedException e) {
					System.out.println("Exception.");
				}
			}
		});

		t1.start();
		t2.start();
	}
}
