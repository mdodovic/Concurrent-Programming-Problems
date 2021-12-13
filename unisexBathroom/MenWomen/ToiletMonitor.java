package kdp.unisexBathroom.MenWomen;

import kdp.unisexBathroom.Toilet;

public class ToiletMonitor implements Toilet{

	public static final int CAPACITY = 3;
	public static final int WAIT_TIME = 5;
	
	private int numberMen = 0;
	private int numberWomen = 0;
	
	private int numberWaitingMen = 0;
	private int numberWaitingWomen = 0;
	
	private int numberAheadMen = 0;
	private int numberAheadWomen = 0;
	
	public synchronized void enterMan() {
		numberWaitingMen++;
		while(numberWomen > 0 || numberMen >= CAPACITY ||numberAheadMen >= WAIT_TIME) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		numberWaitingMen--;
		numberMen++;
		if(numberWaitingWomen > 0) {
			numberAheadMen++;
		}
	}

	public synchronized void enterWoman() {
		numberWaitingWomen++;
		while(numberMen > 0 || numberWomen >= CAPACITY ||numberAheadWomen >= WAIT_TIME) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		numberWomen++;
		numberWaitingWomen--;
		if(numberWaitingMen > 0) {
			numberAheadWomen++;
		}
	}

	public synchronized void exitMan() {
		numberMen--;
		notifyAll();
		if(numberMen == 0) {
			numberAheadWomen = 0;
		}		
	}

	public synchronized void exitWoman() {
		numberWomen--;
		notifyAll();
		if(numberWomen == 0) {
			numberAheadMen = 0;
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
