package kdp.readersWriters;

public class RegionRW implements ReadersWriters {

	private Counters counters = new Counters();
	
	@Override
	public void startRead() {
		synchronized (counters) {
			while(counters.w != 0)
				try {
					counters.wait();
				} catch (InterruptedException e) {}

			counters.r++;
		}

	}

	@Override
	public void endRead() {
		synchronized (counters) {
			counters.r--;
			counters.notifyAll();
		}
	}

	@Override
	public void startWrite() {
		synchronized (counters) {
			while(counters.w != 0)
				try {
					counters.wait();
				} catch (InterruptedException e) {}

			counters.w++;

			while(counters.r != 0)
				try {
					counters.wait();
				} catch (InterruptedException e) {}
		}
	}

	@Override
	public void endWrite() {
		synchronized (counters) {
			counters.w--;
			counters.notifyAll();
		}
	}

}


class Counters{
	public int r = 0;
	public int w = 0;
}