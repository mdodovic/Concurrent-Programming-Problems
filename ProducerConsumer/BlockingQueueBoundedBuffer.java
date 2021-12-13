package kdp.ProducerConsumer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueBoundedBuffer<T> implements BoundedBuffer<T> {

	private BlockingQueue<T> queue;
	
	
	
	public BlockingQueueBoundedBuffer(int size) {
		queue = new ArrayBlockingQueue<T>(size);
	}

	@Override
	public T get() {
		try {
			return queue.take();
		} catch (InterruptedException e) {}
		return null;
	}

	@Override
	public void put(T item) {
		try {
			queue.put(item);
		} catch (InterruptedException e) {}
	}

	
}
