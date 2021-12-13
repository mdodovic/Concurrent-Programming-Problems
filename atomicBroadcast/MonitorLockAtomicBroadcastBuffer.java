package kdp.atomicBroadcast;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MonitorLockAtomicBroadcastBuffer<T> implements AtomicBroadcastBuffer<T> {
	
	private T[] buffer;
	
	private int n;
	private int b;

	private int head;
	
	private int[] tail;
	
	private int[] counter;

	private int writeNext;
	private int[] readNext;

	private Lock lock;
	private Condition write;
	private Condition[] read;
	
	@SuppressWarnings("unchecked")
	public MonitorLockAtomicBroadcastBuffer(int n, int b) {
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
		
		writeNext = 0;
		readNext = new int[n];
		for(int i = 0; i < n; i++)
			readNext[i] = 0;
		
		lock = new ReentrantLock();
		write = lock.newCondition();
		read = new Condition[n];
		for(int i = 0; i < n; i++)
			read[i] = lock.newCondition();

 	}

	@Override
	public T get(int id) {
		lock.lock();
		try{
			while(readNext[id] == writeNext)
				read[id].await();

			T item = buffer[tail[id]];
			counter[tail[id]] ++;
			if(counter[tail[id]] == n) {
				write.signalAll();
			}
			readNext[id]++;
			tail[id] = (tail[id] + 1) % b;
			return item;

		} catch (InterruptedException e) {
			e.printStackTrace();
		
		} finally{
			lock.unlock();
		}
		return null;
	}

	@Override
	public void put(T item) {
		lock.lock();
		try{
			while(counter[head] != n)
				write.await();
			
			buffer[head] = item;
			
			counter[head] = 0;
			writeNext++;
			
			read[head].signalAll();

			head = (head + 1) % b;		
		
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
}
