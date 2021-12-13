package kdp.readersWriters;

public class MonitorRW implements ReadersWriters {

	private int currentReadiers = 0;
	private int currentWritiers = 0;
	private int waitingW = 0; // both of them are blocked
	
	@Override
	public synchronized void startRead() {
		
		while(currentWritiers > 0 || waitingW > 0) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		
		currentReadiers++;

	}

	@Override
	public synchronized void endRead() {
		currentReadiers--;
		if(currentReadiers == 0)
			notifyAll();
	}

	@Override
	public synchronized void startWrite() {
		waitingW ++;
		while(currentWritiers > 0 || currentReadiers > 0) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		waitingW --;
		currentWritiers++;
	}

	@Override
	public synchronized void endWrite() {
		currentWritiers--;
		notifyAll();
	}

}
