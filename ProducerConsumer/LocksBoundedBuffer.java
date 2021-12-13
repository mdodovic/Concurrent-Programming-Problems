package kdp.ProducerConsumer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LocksBoundedBuffer<T> implements BoundedBuffer<T> {

	private Lock lock = new ReentrantLock();
	private Condition notFull = lock.newCondition();
	private Condition notEmpty = lock.newCondition();
	
	private T[] buffer;
	private int counter = 0;
	private int head = 0;
	private int tail = 0;
	
	@SuppressWarnings("unchecked")
	public LocksBoundedBuffer(int size) {		
		buffer = (T[]) new Object[size];
	}

	@Override
	public T get() {
		lock.lock();
		try {
			while(counter == 0) {
				notEmpty.await();
			}
			T item = buffer[head];
			head = (head + 1) % buffer.length;
			counter--;
			notFull.signal();
			return item;			
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public synchronized void put(T item) {
		lock.lock();
		try {
			while(counter == buffer.length)
				notFull.await();
			buffer[tail] = item;
			tail = (tail + 1) % buffer.length;
			counter++;
			notEmpty.signal();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}

	public String p() {
		String s = "";
		for(int i = 0; i < buffer.length; i++)
		s += buffer[0] + " ";
		return s;
	}
}
