package kdp.readersWriters;

import java.util.concurrent.locks.StampedLock;

public class StampedLock2RW implements ReadersWriters {

	private StampedLock sl = new StampedLock();
	private ThreadLocal<Long> localThreadData = new ThreadLocal<Long>();

	@Override
	public void startRead() {
		long stamp = sl.readLock();
		localThreadData.set(stamp);
	}

	@Override
	public void endRead() {
		sl.unlockRead(localThreadData.get());
	}

	@Override
	public void startWrite() {
		long stamp = sl.writeLock();
		localThreadData.set(stamp);
	}

	@Override
	public void endWrite() {
		sl.unlockWrite(localThreadData.get());
	}

}
