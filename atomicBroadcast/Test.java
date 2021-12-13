package kdp.atomicBroadcast;

public class Test {

	public static void main(String[] args) {

		int N = 3;
		int B = 2;
		
//		AtomicBroadcastBuffer<Integer> buffer = new SemaphoreAtomicBroadcastBuffer<Integer>(N, B);

//      AtomicBroadcastBuffer<Integer> buffer = new MonitorAtomicBroadcastBuffer<Integer>(N, B); // Need to be checcked!		
//		AtomicBroadcastBuffer<Integer> buffer = new MonitorLockAtomicBroadcastBuffer<Integer>(N, B); // Need to be checcked!

		AtomicBroadcastBuffer<Integer> buffer = new RegionAtomicBroadcastBuffer<Integer>(N, B); //------
		
		
		Producer p1 = new Producer("P0", buffer); 
		p1.start();
		Producer p2 = new Producer("P1", buffer);
		p2.start();
		
		Consumer c1 = new Consumer("C0", 0, buffer);
		c1.start();
		
		Consumer c2 = new Consumer("C1", 1, buffer);
		c2.start();

		Consumer c3 = new Consumer("C2", 2, buffer);
		c3.start();

	}

}
