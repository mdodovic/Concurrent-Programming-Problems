package kdp.busProblem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Bus extends Thread {


	private int capacity;

	public static Semaphore station = new Semaphore(1);
	public static Semaphore aboard = new Semaphore(0);
	public static Semaphore allAboarded = new Semaphore(0);

	public static int waitingPassengers = 0;
	public static int space;
	
	public static List<String> list = new ArrayList<String>();
	
	public Bus(String string, int k) {
		super(string);
		capacity = k;
		space = k;
	}
	
	
	@Override
	public void run() {
		while(true) {
			
			aboardPassengers();
			
			drive();
			
		}
	}


	private void aboardPassengers() {
		station.acquireUninterruptibly();
		if(waitingPassengers == 0) {
			station.release();
			return;
		}
		
		aboard.release();
		allAboarded.acquireUninterruptibly();
		space = capacity;
		
		station.release();
	}


	private void drive() {
		synchronized (Bus.station) {
			System.out.print("BUS DRIVE !");
			for(String s: list)
				System.out.print(s + " ; ");
			list.clear();
			System.out.println();
		}
		
		
		
		try {
			sleep((int)(Math.random() * 1000 + 1));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
	}
	
}
