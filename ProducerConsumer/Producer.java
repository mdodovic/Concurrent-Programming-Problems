package kdp.ProducerConsumer;

public class Producer extends Thread {

	private BoundedBuffer<Integer> b;
	
	public Producer(String string, BoundedBuffer<Integer> b) {
		super(string);
		this.b = b;
	}

	@Override
	public void run() {
		while(true) {
			int item = produce();
			b.put(item);
			System.out.println("Producer: " + this.getName() + " produced: " + item);			
		}
	}

	private int produce() {
		try {
			sleep((int)(Math.random() * 500));
		} catch (InterruptedException e) {}
		int item = (int)(Math.random() * 100 + 1);
		return item;
	}
}
