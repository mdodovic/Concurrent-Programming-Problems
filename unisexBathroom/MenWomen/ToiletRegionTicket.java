package kdp.unisexBathroom.MenWomen;

import kdp.unisexBathroom.Toilet;

public class ToiletRegionTicket implements Toilet {

	private static final int WAIT_TIME = 5;
	private RegionWC wc = new RegionWC();
	
	@Override
	public void enterWoman() {
		int myTicket = 0;
		synchronized (wc) {
			myTicket = wc.ticket;
			wc.ticket++;
			while(wc.cntMen > 0 || myTicket != wc.next || wc.cntWomen >= WAIT_TIME) {
				try {
					wc.wait();
				} catch (InterruptedException e) {}
			}
			wc.cntWomen++;
			wc.next++;
		}
	}

	@Override
	public void exitWoman() {

		synchronized (wc) {
			wc.cntWomen--;
			wc.notifyAll();
		}
	}

	@Override
	public void enterMan() {
		int myTicket = 0;
		synchronized (wc) {
			myTicket = wc.ticket;
			wc.ticket++;
			while(wc.cntWomen > 0 || myTicket != wc.next || wc.cntMen >= WAIT_TIME) {
				try {
					wc.wait();
				} catch (InterruptedException e) {}
			}
			wc.cntMen++;
			wc.next++;
		}
	}

	@Override
	public void exitMan() {
		synchronized (wc) {
			wc.cntMen--;
			wc.notifyAll();
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


class RegionWC{
	public int cntWomen = 0;
	public int cntMen = 0;
	public int ticket = 0;
	public int next = 0;
}