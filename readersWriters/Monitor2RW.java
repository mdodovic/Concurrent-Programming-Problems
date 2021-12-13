package kdp.readersWriters;

public class Monitor2RW implements ReadersWriters {

	private int currentReadiers = 0;
	private int currentWritiers = 0;
	private int waitingBoth = 0; // both of them are blocked
	
	@Override
	public synchronized void startRead() {
		if( (waitingBoth != 0) || (currentWritiers != 0)) {
			waitingBoth++;
			while(currentWritiers > 0) {
				try {
					wait();
				} catch (InterruptedException e) {}
			}
			waitingBoth--;
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
		if((waitingBoth != 0) || (currentReadiers != 0) || (currentWritiers != 0)) {
			waitingBoth ++;
			while(currentWritiers > 0 || currentReadiers > 0) {
				try {
					wait();
				} catch (InterruptedException e) {}
			}
			waitingBoth --;
		}
		currentWritiers++;
	}

	@Override
	public synchronized void endWrite() {
		currentWritiers--;
		notifyAll();
	}

}
