package kdp.lab.FilmMakers;

import java.util.concurrent.Semaphore;

public class BufferDataBySemaphore<T> implements Buffer<T> {

	private T buffer[];
	private int counter = 0;
	
	private Semaphore notFull;
	private Semaphore notEmpty;
	
	private int head = 0;
	private int tail = 0;
	
	private Semaphore mutexGet = new Semaphore(1);
	private Semaphore mutexPut = new Semaphore(1);
	
	@SuppressWarnings("unchecked")
	public BufferDataBySemaphore(int size) {
		this.buffer = (T[]) new Object[size];
		this.counter = 0;
		this.notFull = new Semaphore(size);
		this.notEmpty = new Semaphore(0);
	}

	@Override
	public void put(T item) {
		
		notFull.acquireUninterruptibly();
		mutexPut.acquireUninterruptibly();		

		buffer[head] = item;
		head = (head + 1) % buffer.length;
		counter++;

		mutexPut.release();
		notEmpty.release();
	}

	@Override
	public T get() {
		T item = null;
		notEmpty.acquireUninterruptibly();
		
		mutexGet.acquireUninterruptibly();
		item = buffer[tail];
		tail = (tail + 1) % buffer.length;
		
		counter--;
		mutexGet.release();
		notFull.release();
		return item;
	}

	@Override
	public String toString() {
		String s = "";
		for(T iS : buffer)
				s+= iS + "\n";
		return s;
	}

	
}
