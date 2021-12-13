package kdp.lab.FilmMakers;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BufferDataByLocks<T> implements Buffer<T> {

	private T[] buffer;
	private int counter = 0;
	private int head = 0;
	private int tail;
	
	private Lock lock = new ReentrantLock();
	private Condition notFull = lock.newCondition();
	private Condition notEmpty = lock.newCondition();
	
	@SuppressWarnings("unchecked")
	public BufferDataByLocks(int size) {
		buffer = (T[]) new Object[size];
	}

	@Override
	public void put(T item) {
		lock.lock();
		try {
			while(counter == buffer.length)
				notFull.await();

			buffer[head] = item;
			head = (head + 1) % buffer.length;
			
			counter++;
			notEmpty.signal();

		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}

	}

	@Override
	public T get() {
		T item = null;
		lock.lock();
		try {
			while(counter == 0)
				notEmpty.await();

			item = buffer[tail];
			tail = (tail + 1) % buffer.length;
			counter--;
			notFull.signal();

		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		return item;
	}

}
