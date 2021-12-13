package kdp.ProducerConsumer;

public interface BoundedBuffer<T> {
	public T get();
	public void put(T item);
}
