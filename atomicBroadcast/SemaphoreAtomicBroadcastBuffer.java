package kdp.atomicBroadcast;

import java.util.concurrent.Semaphore;

public class SemaphoreAtomicBroadcastBuffer<T> implements AtomicBroadcastBuffer<T> {

	private T[] buffer;
	
	private int n;
	private int b;

	private int[] cnt;

	
	private int head;
	private Semaphore mutexP;
	
	private int[] tail;
	private Semaphore[] mutexC;

	private Semaphore empty;
	private Semaphore[] full;
	
	@SuppressWarnings("unchecked")
	public SemaphoreAtomicBroadcastBuffer(int n, int b) {
		super();
		this.n = n; // number of consumer's thread
		this.b = b; // size of buffer

		buffer = (T[]) new Object[this.b];
		
		cnt = new int[this.b]; // does everyone read this element
		for(int i = 0; i < this.b; i++)
			cnt[i] = 0;
				
		// producers
		head = 0; // where to put next element
		mutexP = new Semaphore(1);
		
		// consumers
		tail = new int[this.n]; // every thread has their own next element to read
		for(int i = 0; i < this.n; i++)
			tail[i] = 0;
		mutexC = new Semaphore[this.b]; // for every place in the array
		for(int i = 0; i < this.b; i++)
			mutexC[i] = new Semaphore(1);

		empty = new Semaphore(this.b); // can put to b places

		full = new Semaphore[this.n]; // private semaphore - "you can use your element"		
		for(int i = 0; i < this.n; i++)
			full[i] = new Semaphore(0);
	
	}

	@Override
	public T get(int id) { // consumers!
		full[id].acquireUninterruptibly();
		// I use my own semaphore
		T item = buffer[tail[id]];
		
		mutexC[tail[id]].acquireUninterruptibly();
		
		cnt[tail[id]] ++;
		if(cnt[tail[id]] == this.n) {
			cnt[tail[id]] = 0;
			empty.release();			
		}
	
		mutexC[tail[id]].release();

		tail[id] = (tail[id] + 1) % this.b;
		
		return item;
	}

	@Override
	public void put(T item) {
		empty.acquireUninterruptibly();
		
		mutexP.acquireUninterruptibly();
		
		buffer[head] = item;
		head = (head + 1) % this.b;

		mutexP.release();

		for(int i = 0; i < this.n; i++)
			full[i].release();
	}

}
