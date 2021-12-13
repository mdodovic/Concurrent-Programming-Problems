package kdp.readersWriters;

import java.util.concurrent.Semaphore;

public class SemaphoreTurnSideRW implements ReadersWriters {

	private int numberR = 0;
	
	private Semaphore turn = new Semaphore(1); // choose the side
	private Semaphore writer = new Semaphore(1); // only one writer
	private Semaphore mutex = new Semaphore(1);
		
	
	@Override
	public void startRead() {
		turn.acquireUninterruptibly();
		turn.release(); // This can be after mutex.release() but here is larger concurrency
		
		mutex.acquireUninterruptibly();
		numberR++;
		if(numberR == 1) {
			writer.acquireUninterruptibly();
		}
		mutex.release();

	}

	@Override
	public void endRead() {
		mutex.acquireUninterruptibly();
		numberR--;
		if(numberR == 0) {
			writer.release();
		}
		mutex.release();
	}

	@Override
	public void startWrite() {
		turn.acquireUninterruptibly();
		writer.acquireUninterruptibly();		
	}

	@Override
	public void endWrite() {
		writer.release();
		turn.release();
	}

}
