package kdp.readersWriters;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.StampedLock;

public class StampedLockRW implements ReadersWriters {

	private StampedLock sl = new StampedLock();
	private Map<Thread, Long> stamps = new HashMap<Thread, Long>();
	
	@Override
	public void startRead() {
		long stamp = sl.readLock();
		stamps.put(Thread.currentThread(), stamp);
	}

	@Override
	public void endRead() {
		sl.unlockRead(stamps.remove(Thread.currentThread()));
	}

	@Override
	public void startWrite() {
		long stamp = sl.writeLock();
		stamps.put(Thread.currentThread(), stamp);
	}

	@Override
	public void endWrite() {
		sl.unlockWrite(stamps.remove(Thread.currentThread()));
	}

}
