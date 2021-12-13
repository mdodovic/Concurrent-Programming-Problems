package kdp.lab.FilmMakers;

public class BufferDataByMonitors<T> implements Buffer<T> {

	private T[] buffer;

	private int counter = 0;
	private int head = 0;
	private int tail = 0;
	
	@SuppressWarnings("unchecked")
	public BufferDataByMonitors(int size) {
		buffer = (T[]) new Object[size];
	}

	@Override
	public synchronized void put(T item) {
		while(buffer.length == counter)
			try {
				wait();
			} catch (InterruptedException e) {}
		
		buffer[head] = item;
		head = (head + 1) % buffer.length;
		counter++;
		notifyAll();
	}

	@Override
	public synchronized T get() {
		T item = null;
		while(counter == 0)
			try {
				wait();
			} catch (InterruptedException e) {}
		
		item = buffer[tail];
		tail = (tail + 1) % buffer.length;
		counter--;
		notifyAll();
		return item;
	}

}
