package kdp.ProducerConsumer;

public class Test {

	public static void main(String[] args) {

		int B = 3;
		
		BoundedBuffer<Integer> b = new BlockingQueueBoundedBuffer<Integer>(B);

		Producer p1 = new Producer("P1", b); 
		p1.start();
		Producer p2 = new Producer("P2", b);
		p2.start();
		
		Consumer c1 = new Consumer("C1", b);
		c1.start();
		
		Consumer c2 = new Consumer("C2", b);
		c2.start();

		Consumer c3 = new Consumer("C3", b);
		c3.start();

		/*
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		p1.interrupt();
		p2.interrupt();
		c1.interrupt();
		c2.interrupt();
		c3.interrupt();
		*/
	}

}
