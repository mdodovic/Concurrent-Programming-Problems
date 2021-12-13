package kdp.lab.FilmMakers;

public class BufferDataByRegions<T> implements Buffer<T> {

	private BufferGroup<T> b;
	
	public BufferDataByRegions(int size) {
		b = new BufferGroup<T>(size);
	}
	

	@Override
	public void put(T item) {
		synchronized (b) {
			while (b.counter == b.buffer.length) {
				try {
					b.wait();
				} catch (InterruptedException e) {}
			}
			b.buffer[b.head] = item;
			b.head = (b.head + 1) % b.buffer.length;
			b.counter++;
			b.notifyAll();
		}
	}

	@Override
	public T get() {
		T item = null;
		synchronized (b) {
			while (b.counter == 0) {
				try {
					b.wait();
				} catch (InterruptedException e) {}
			}
			item = b.buffer[b.tail];
			b.tail = (b.tail + 1) % b.buffer.length;
			b.counter--;
			b.notifyAll();			
		}
		return item;
	}
}

class BufferGroup<T> {
	
	public T[] buffer;
	
	public int counter = 0;
	
	public int head = 0;
	public int tail = 0;

	@SuppressWarnings("unchecked")
	public BufferGroup(int size) {
		buffer = (T[]) new Object[size];
	}

}