package kdp.readersWriters;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockRW implements ReadersWriters {
	
	private ReadWriteLock rw;
//	private Lock readLock;
//	private Lock writeLock;
	
	public ReadWriteLockRW(boolean fair) {
		rw = new ReentrantReadWriteLock(fair);
//		readLock = rw.readLock();
//		writeLock = rw.writeLock();
	}

	@Override
	public void startRead() {
//		readLock.lock();
		rw.readLock().lock();
	}

	@Override
	public void endRead() {
//		readLock.unlock();
		rw.readLock().unlock();
	}

	@Override
	public void startWrite() {
//		writeLock.lock();
		rw.writeLock().lock();
	}

	@Override
	public void endWrite() {
//		writeLock.unlock();
		rw.writeLock().unlock();
	}

}
