package kdp.rollerCoasterProblem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Coaster extends Thread{

	public static int numAboarded = 0;
	
	private Semaphore startBoarding;
	private Semaphore allAboard;
	
	private Semaphore endRiding;
	private Semaphore allExited;
	
	public static List<String> l = new ArrayList<>();
	
	public Coaster(String name, 
			Semaphore startBoarding, Semaphore allAboard, Semaphore endRiding, Semaphore allExited) {
		super(name);
		this.startBoarding = startBoarding;
		this.allAboard = allAboard;
		this.endRiding = endRiding;
		this.allExited = allExited;
	}
	
	@Override
	public void run() {
		while(true) {
			aboardPassengers();
			
			drive();
			
			exitPassengers();
		}
	}

	private void aboardPassengers() {
		startBoarding.release();
		allAboard.acquireUninterruptibly();
	}

	private void exitPassengers() {
		endRiding.release();
		allExited.acquireUninterruptibly();		
	}

	private void drive() {
		synchronized (Coaster.l) {
			System.out.print(getName() + " DRIVING: ");
			for(String sI: l)
				System.out.print(sI + " ; ");
			l.clear();
			System.out.println();
		}
	}
	

	
	
}
