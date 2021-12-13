package kdp.readersWriters;

public class Test {

	public static void main(String[] args) {

		int numR = 50;
		int numW = 50;
		
//		ReadersWriters book = new SemaphorePassingTheButtonRW(); // starvation
//		ReadersWriters book = new SemaphoreTurnSideRW();
//		ReadersWriters book = new SemaphoreFIFORW(); // starvation need to check?

//		ReadersWriters book = new RegionRW();

//		ReadersWriters book = new MonitorRW(); // starvation 
//		ReadersWriters book = new Monitor2RW(); // starvation
//		ReadersWriters book = new MonitorLockRW(); // starvation

//		ReadersWriters book = new ReadWriteLockRW(true);

//		ReadersWriters book = new StampedLockRW();
		ReadersWriters book = new StampedLock2RW();


		Reader[] readers = new Reader[numR];
		Writer[] writers = new Writer[numW];
		
		for(int i = 0; i < numR; i++) {
			readers[i] = new Reader("R" + i, i, book);
		}
		for(int i = 0; i < numW; i++) {
			writers[i] = new Writer("W" + i, i, book);
		}

		for(int i = 0; i < Math.max(numR, numW); i++) {
			if(i < numR)
				readers[i].start();
			if(i < numW)
				writers[i].start();
		}
		
	}

}
