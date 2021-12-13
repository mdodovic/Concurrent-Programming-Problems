package kdp.unisexBathroom.MenWomen;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import kdp.unisexBathroom.Toilet;

public class ToiletLock implements Toilet {

	
	public static final int CAPACITY = 3;
	public static final int WAIT_TIME = 5;

	private int numberMen = 0;
	private int numberWomen = 0;
	
	private int numberWaitingMen = 0;
	private int numberWaitingWomen = 0;
	
	private int numberAheadMen = 0;
	private int numberAheadWomen = 0;
	
	private Lock lock = new ReentrantLock(true);
	private Condition men = lock.newCondition();
	private Condition women = lock.newCondition();

	
	public void enterMan() {
		lock.lock();
		try {
			numberWaitingMen++;
			while(numberWomen > 0 || numberMen >= CAPACITY || numberAheadMen >= WAIT_TIME) {
				try {
					men.await();
				} catch (InterruptedException e) {}
			}
			numberWaitingMen--;
			numberMen++;
			if(numberWaitingWomen > 0) {
				numberAheadMen++;
			}
		} finally {
			lock.unlock();
		}
	}

	public void enterWoman() {
		lock.lock();
		try {
			numberWaitingWomen++;
			while(numberMen > 0 || numberWomen >= CAPACITY ||numberAheadWomen >= WAIT_TIME) {
				try {
					women.await();
				} catch (InterruptedException e) {}
			}
			numberWomen++;
			numberWaitingWomen--;
			if(numberWaitingMen > 0) {
				numberAheadWomen++;
			}
		} finally {
			lock.unlock();
		}
	}

	public void exitMan() {
		lock.lock();
		try {
			numberMen--;
			if(numberMen == 0) {
				numberAheadWomen = 0;
				women.signalAll();
			} else {
				men.signalAll();
			}
		} finally {
			lock.unlock();
		}
	}

	public void exitWoman() {
		lock.lock();
		try {
			numberWomen--;
			if(numberWomen == 0) {
				numberAheadMen = 0;
				men.signalAll();
			} else {
				women.signalAll();
			}
		}finally {
			lock.unlock();
		}
	}


	@Override
	public void enterChild() {}

	@Override
	public void exitChild() {}

	@Override
	public void enterJanitor() {}

	@Override
	public void exitJanitor() {}

}
