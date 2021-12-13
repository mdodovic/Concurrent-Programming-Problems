package kdp.atomicBroadcast;

public interface AtomicBroadcastBuffer<T> {

	public T get(int id);
	public void put(T item);
	
}
