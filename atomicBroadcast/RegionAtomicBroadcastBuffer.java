package kdp.atomicBroadcast;

public class RegionAtomicBroadcastBuffer<T> implements AtomicBroadcastBuffer<T> {

	private Buffer<T> bRegion;
	
	
	public RegionAtomicBroadcastBuffer(int n, int b) {
		bRegion = new Buffer<T>(n,b);
	}

	@Override
	public T get(int id) {
		synchronized (bRegion) {
			T item = null;
			while(bRegion.nextToRead[id] == bRegion.nextToWrite)
				try {
					bRegion.wait();
				} catch(InterruptedException e) {}
			
			item = bRegion.buffer[bRegion.tail[id]];
			bRegion.counter[bRegion.tail[id]]++;
			if(bRegion.counter[bRegion.tail[id]] == bRegion.n) {
				bRegion.notifyAll();
			}
			bRegion.nextToRead[id]++;
			bRegion.tail[id] = (bRegion.tail[id] + 1) % bRegion.b;
			return item;
 		}
	}

	@Override
	public void put(T item) {
		synchronized (bRegion) {
			while(bRegion.counter[bRegion.head] != bRegion.n)
				try {
					bRegion.wait();
				} catch (InterruptedException e) {}
			
			bRegion.counter[bRegion.head] = 0;
			bRegion.buffer[bRegion.head] = item;
			bRegion.head = (bRegion.head + 1) % bRegion.b;
			bRegion.nextToWrite++;
			bRegion.notifyAll();
		}
	}

}

class Buffer<T>{
	
	public T[] buffer;
	
	public int n;
	public int b;
	
	public int head;
	public int[] tail;
	
	public int nextToWrite;
	public int[] nextToRead;
	
	public int[] counter;

	public boolean[] availableItem;
	
	@SuppressWarnings("unchecked")
	public Buffer(int n, int b) {
		this.n = n;
		this.b = b;
		
		buffer = (T[]) new Object[b];
		
		head = 0;
		tail = new int[n];
		for(int i = 0; i < n; i++)
			tail[i] = 0;

		nextToWrite = 0;
		nextToRead = new int[n];
		for(int i = 0; i < n; i++)
			nextToRead[i] = 0;
		
		counter = new int[b];
		for(int i = 0; i < b; i++)
			counter[i] = n;

		availableItem = new boolean[b];
		for(int i = 0; i < b; i++)
			availableItem[i] = false;

	}
	
	
}