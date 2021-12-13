package kdp.ProducerConsumer;

import java.util.concurrent.Semaphore;

public class SemaphoreBoundedBuffer<T> implements BoundedBuffer<T> {
	private T[] buffer;
	
	private int counter = 0;
	private int head = 0;
	private int tail = 0;

	private Semaphore empty;
	private Semaphore full;
	private Semaphore mutexP = new Semaphore(1);
	private Semaphore mutexC = new Semaphore(1);
	
	@SuppressWarnings("unchecked")
	public SemaphoreBoundedBuffer(int size) {
		super();
		this.buffer = (T[]) new Object[size];
		this.empty = new Semaphore(0);
		this.full = new Semaphore(size);
	}

	@Override
	public T get() {		
		T item = null;

			empty.acquireUninterruptibly();
			mutexC.acquireUninterruptibly();
			item = buffer[tail];
			tail = (tail + 1) % buffer.length;
			counter--;
			mutexC.release();			
			full.release();

		return item;
	}

	@Override
	public void put(T item) {

			full.acquireUninterruptibly();
			mutexP.acquireUninterruptibly();
			buffer[head] = item;
			head = (head + 1) % buffer.length;
			counter++;
			mutexP.release();
			empty.release();

	} 
	
	
	
	

}
