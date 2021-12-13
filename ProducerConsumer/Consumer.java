package kdp.ProducerConsumer;

public class Consumer extends Thread {

	private BoundedBuffer<Integer> b;
	
	public Consumer(String string, BoundedBuffer<Integer> b) {
		super(string);
		this.b = b;
	}

	@Override
	public void run() {
		while(true) {
			int item = b.get();
			consume(item);	
		}
	}

	private void consume(int item) {
		System.out.println("Consumer " + getName() + " took item: " + item);
		try {
			sleep((int)(Math.random() * 1000));
		} catch (InterruptedException e) {}
		System.out.println("Consumer " + getName() + " consumed item: " + item);
	}
	
}
