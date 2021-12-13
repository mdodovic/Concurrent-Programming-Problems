package kdp.readersWriters;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MonitorLockRW implements ReadersWriters {

//	private Lock lock = new ReentrantLock();
	private Lock lock = new ReentrantLock(true);
	
	private Condition cr = lock.newCondition();
	private Condition cw = lock.newCondition();
	
	private int nw = 0;
	private int nr = 0;
//	private int ww = 0;
	
	@Override
	public void startRead() {
		lock.lock();
		try {
			while(nw>0)// || ww > 0)
				cr.await();
			nr++;
		} catch (InterruptedException e) {
			
		} finally {
			lock.unlock();
		}		
	}

	@Override
	public void endRead() {
		lock.lock();
		try {
			nr--;
			if(nr == 0)
				cw.signal();
		}finally {
			lock.unlock();
		}		
	}

	@Override
	public void startWrite() {
		lock.lock();
		try {
//			ww++;
			while(nw > 0 || nr > 0)
				cw.await();
			nw++;
//			ww--;
		} catch (InterruptedException e) {

		}finally {
			lock.unlock();
		}		
	}

	@Override
	public void endWrite() {
		lock.lock();
		try {
			nw--;
			cr.signalAll();
			cw.signal();
		}finally {
			lock.unlock();
		}		
	}

}
