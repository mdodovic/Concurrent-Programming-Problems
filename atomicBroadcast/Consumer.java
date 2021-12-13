package kdp.atomicBroadcast;

public class Consumer extends Thread {

	private AtomicBroadcastBuffer<Integer> buffer;
	
	private int id;
	
	public Consumer(String name, int id, AtomicBroadcastBuffer<Integer> buffer) {
		super(name);
		this.id = id;
		this.buffer = buffer;
	}
	
	@Override
	public void run() {
		while(true) {

			int item = buffer.get(id);

			System.out.println(this.getName() + " took " + item);
			try {
				sleep((int)(Math.random() * 1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(this.getName() + " consumed " + item);		
		}
	}
	
}
