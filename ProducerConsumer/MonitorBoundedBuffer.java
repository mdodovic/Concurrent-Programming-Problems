package kdp.ProducerConsumer;

public class MonitorBoundedBuffer<T> implements BoundedBuffer<T> {

	private T[] buffer;
	private int counter = 0;
	private int head = 0;
	private int tail = 0;
	
	@SuppressWarnings("unchecked")
	public MonitorBoundedBuffer(int size) {
		
		buffer = (T[]) new Object[size];
	}

	@Override
	public synchronized T get() {
		while(counter == 0) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		T item = buffer[head];
		head = (head + 1) % buffer.length;
		counter--;
		notifyAll();
		return item;
	}

	@Override
	public synchronized void put(T item) {
		while(counter == buffer.length) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		buffer[tail] = item;
		tail = (tail + 1) % buffer.length;
		counter++;
		notifyAll();		
	}

	public String p() {
		String s = "";
		for(int i = 0; i < buffer.length; i++)
		s += buffer[0] + " ";
		return s;
	}
}
