package kdp.readersWriters;

import java.util.concurrent.Semaphore;

public class SemaphorePassingTheButtonRW implements ReadersWriters {

	private int currentR = 0;
	private int currentW = 0;
	private int waitingR = 0;
	private int waitingW = 0;
	
	private Semaphore enter = new Semaphore(1);
	private Semaphore r = new Semaphore(0);
	private Semaphore w = new Semaphore(0);
	
	private void signal() {
		if(waitingR > 0 && currentW == 0) {
			r.release();
		} else {
		if(waitingW > 0 && currentW == 0 && currentR == 0) {
			w.release();
		}
		else {
				enter.release();
			}
		}
	}

	
	@Override
	public void startRead() {
		enter.acquireUninterruptibly();
		
		if(currentW != 0 || waitingW > 0) {
			waitingR++;
			enter.release();
			r.acquireUninterruptibly();
			waitingR--;
		}
		currentR++;		
		
		signal();
	}


	@Override
	public void endRead() {
		enter.acquireUninterruptibly();
		currentR--;
		signal();
	}

	@Override
	public void startWrite() {
		enter.acquireUninterruptibly();
		if(currentR != 0 || currentW != 0) {
			waitingW++;
			enter.release();
			w.acquireUninterruptibly();
			waitingW--;
		}
		currentW++;
		
		signal();
	}

	@Override
	public void endWrite() {
		enter.acquireUninterruptibly();
		currentW--;
		signal();

	}

}
