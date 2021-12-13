package kdp.atomicBroadcast;

public class MonitorAtomicBroadcastBuffer<T> implements AtomicBroadcastBuffer<T> {

	private T[] buffer;
	
	private int n;
	private int b;

	private int head;
	
	private int[] tail;
	
	private int[] counter;

	private boolean[] availableItem;
	
	private long writeNext;
	private long[] readNext;
	
	@SuppressWarnings("unchecked")
	public MonitorAtomicBroadcastBuffer(int n, int b) {
		this.n = n;
		this.b = b;
		
		buffer = (T[]) new Object[b];

		head = 0;
		counter = new int[b];
		for(int i = 0; i < b; i++)
			counter[i] = n;
		
		tail = new int[n];
		for(int i = 0; i < n; i++)
			tail[i] = 0;
		
		availableItem = new boolean[b];
		for(int i = 0; i < b; i++)
			availableItem[i] = false;

		writeNext = 0;
		readNext = new long[n];
		for(int i = 0; i < n; i++)
			readNext[i] = 0;
		
	}

	@Override
	public synchronized T get(int id) {
		while(readNext[id] == writeNext)
			try {
				wait();
			} catch (InterruptedException e) {}

		T item = buffer[tail[id]];
		counter[tail[id]] ++;
		if(counter[tail[id]] == n) {
			availableItem[tail[id]] = false;
			notifyAll();
		}
		readNext[id]++;
		tail[id] = (tail[id] + 1) % b;
		return item;
	}

	@Override
	public synchronized void put(T item) {
		while(! (counter[head] == n && availableItem[head] == false))
			try {
				wait();
			} catch (InterruptedException e) {}
		
		buffer[head] = item;
		
		counter[head] = 0;
		availableItem[head] = true;
		writeNext++;
		head = (head + 1) % b;

		notifyAll();
	}

}


