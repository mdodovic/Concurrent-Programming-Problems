package kdp.unisexBathroom.MenWomenChildrenJanitor;

import kdp.unisexBathroom.Toilet;

public class ToiletMonitor implements Toilet {

	public static int N = 10;
	private int numW = 0;
	private int numM = 0;
	private int numC = 0;
	private int numJ = 0;
	
	private int numWaitingW = 0;
	private int numWaitingM = 0;
	private int numWaitingJ = 0;
	private int numWaitingC = 0;
	
	private int numAheadW = 0;
	private int numAheadM = 0;
	private int numAheadJ = 0;
	private int numAheadC = 0;

	public synchronized void enterWoman() {
		numWaitingW++;
		while((numW + numC == N) || (numM != 0) || (numJ != 0) || numAheadW >= 5) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		numWaitingW--;
		numW++;
		if(numWaitingM != 0 || numWaitingJ != 0 || numWaitingC != 0) {
			numAheadW++;
		}
		if((numW == 2) && (numC != 0))
			notifyAll();
	}

	public synchronized void exitWoman() {
		while((numW == 1) && (numC != 0)) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		numW--;
		if(numW == 0) {
			numAheadJ = 0;
			numAheadC = 0;
			numAheadM = 0;
		}
		notifyAll();
	}

	public synchronized void enterMan() {
		numWaitingM++;
		while((numM + numC == N) || (numW != 0) || (numJ != 0) || numAheadM >= 5) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		numWaitingM--;
		numM++;
		if(numWaitingW != 0 || numWaitingJ != 0 || numWaitingC != 0) {
			numAheadM++;
		}
		if((numM == 2) && (numC != 0))
			notifyAll();
	}

	public synchronized void exitMan() {
		while((numM == 1) && (numC != 0)) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		numM--;
		if(numM == 0) {
			numAheadJ = 0;
			numAheadW = 0;
			numAheadC = 0;
		}
		notifyAll();
	}
	
	public synchronized void enterChild() {
		numWaitingC++;
		while((numM + numW == 0) || (numM + numW + numC == N) || (numJ != 0) || numAheadC >= 5) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		numWaitingC--;
		if(numWaitingJ != 0 || numWaitingM != 0 || numWaitingW != 0) {
			numAheadC++;
		}
		numC++;			
	}
	
	public synchronized void exitChild() {
		numC--;
		if(numM == 0) {
			numAheadJ = 0;
			numAheadM = 0;			
			numAheadW = 0;			
		}
		notifyAll();
	}

	public synchronized void enterJanitor() {
		numWaitingJ++;
		while( (this.numM + this.numC + this.numW != 0) || this.numJ != 0 || numAheadJ >= 1) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		numWaitingJ--;
		if(numWaitingC != 0 || numWaitingM != 0 || numWaitingW != 0) {
			numAheadJ++;
		}		
		this.numJ++;
	}
	public synchronized void exitJanitor() {
		this.numJ--;
		if(numJ == 0) {
			numAheadM = 0;			
			numAheadW = 0;			
			numAheadC = 0;			
		}
		notifyAll();
	}
	
}
