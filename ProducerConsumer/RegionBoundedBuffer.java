package kdp.ProducerConsumer;

public class RegionBoundedBuffer<T> implements BoundedBuffer<T> {

	private Buffer<T> b;

	
	
	public RegionBoundedBuffer(int size) {
		b = new Buffer<T>(size);
	}

	@Override
	public T get() {
		T item = null;
		synchronized (b) {
			while(b.counter == 0) {
				try {
					b.wait();
				} catch (InterruptedException e) {}
			}
			item = b.buffer[b.tail];
			b.tail = (b.tail + 1) % b.buffer.length;
			b.counter --;
			b.notifyAll();
		}
		return item;
	}

	@Override
	public void put(T item) {
		synchronized (b) {
			while(b.counter == b.buffer.length) {
				try {
					b.wait();
				} catch (InterruptedException e) {}
			}
			b.buffer[b.head] = item;
			b.head = (b.head + 1) % b.buffer.length;
			b.counter ++;
			b.notifyAll();
		}
	}

}

class Buffer<T>{
	public T[] buffer;
	public int counter = 0;
	public int head = 0;
	public int tail = 0;

	@SuppressWarnings("unchecked")
	public Buffer(int size) {
		buffer = (T[]) new Object[size];
	}
	
}
