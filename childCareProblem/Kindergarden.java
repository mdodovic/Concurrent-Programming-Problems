package kdp.childCareProblem;

import java.util.concurrent.Semaphore;

public class Kindergarden {

	public static final int C = 3;
	
	private Semaphore mutex = new Semaphore(1);
	
	private Semaphore nannyLeft = new Semaphore(0);
	private Semaphore nannyExit = new Semaphore(0);
	
	private int nChildren = 0;
	private int nNannies = 0;
	private int nWaitingNannies = 0;
	
	public boolean bringChildren(int num) {
		mutex.acquireUninterruptibly();
		if( (nChildren + num) <= nNannies * C) {
			nChildren += num;
			mutex.release();
			return true;
		}
		mutex.release();
		return false;
	}

	public void returnChildren(int num) {
		mutex.acquireUninterruptibly();
		nChildren -= num;
		int freeNannies = nNannies - nChildren / C;

		if(nWaitingNannies < freeNannies) {
			freeNannies = nWaitingNannies;
		}
		
		for(int i = 0; i< freeNannies; i++) {
			nannyExit.release();
			nannyLeft.acquireUninterruptibly();
		}		
		mutex.release();
	}

	public void nannyEnter() {
		mutex.acquireUninterruptibly();
		nNannies ++; 
		if(nWaitingNannies > 0) {
			nannyExit.release();
			nannyLeft.acquireUninterruptibly();
		}
		mutex.release();
	}

	public void nannyExit() {
		mutex.acquireUninterruptibly();
		
		if( nChildren <= (nNannies - 1) * C) {
			nNannies--;
			mutex.release();
		} else {
			nWaitingNannies ++;
			mutex.release();
			nannyExit.acquireUninterruptibly();
			nWaitingNannies--;
			nNannies--;
			nannyLeft.release();
		}
	}

}
