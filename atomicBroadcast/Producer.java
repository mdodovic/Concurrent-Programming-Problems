package kdp.atomicBroadcast;

public class Producer extends Thread {

	private AtomicBroadcastBuffer<Integer> buffer;
	
	public Producer(String name, AtomicBroadcastBuffer<Integer> buffer) {
		super(name);
		this.buffer = buffer;
	}
	
	@Override
	public void run() {
		while(true) {
			int item = (int)(Math.random() * 100 + 1); // produce

			buffer.put(item);
			
			System.out.println(this.getName() + " produced " + item);
			try {
				sleep((int)(Math.random() * 1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
