package kdp.rollerCoasterProblem;

import java.util.concurrent.Semaphore;

public class Passenger extends Thread {

	private int capacity;

	private Semaphore startBoarding;
	private Semaphore allAboard;
	
	private Semaphore endRiding;
	private Semaphore allExited;

	public Passenger(String name, int capacity, Semaphore startBoarding, Semaphore allAboard, Semaphore endRiding,
			Semaphore allExited) {
		super(name);
		this.capacity = capacity;
		this.startBoarding = startBoarding;
		this.allAboard = allAboard;
		this.endRiding = endRiding;
		this.allExited = allExited;
	}

	
	@Override
	public void run() {
		walk();
		enterTheCoaster();
		ride();
		leaveTheCoaster();
	}


	private void enterTheCoaster() {
		startBoarding.acquireUninterruptibly();
		
		Coaster.numAboarded++;
		Coaster.l.add(getName());
		if(Coaster.numAboarded == capacity) {
			allAboard.release();
		} else {
			startBoarding.release();
		}

	}


	private void leaveTheCoaster() {
		endRiding.acquireUninterruptibly();
		
		Coaster.numAboarded--;
		if(Coaster.numAboarded == 0) {
			allExited.release();
		}
		endRiding.release();
	}


	private void ride() {
		synchronized (Coaster.l) {
			System.out.println(getName() + " RIDING");			
		}
		try {
			sleep((int)(Math.random() * 1000 + 1));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	private void walk() {
		synchronized(Coaster.l) {
			System.out.println(getName() + " WALKING");
		}
		try {
			sleep((int)(Math.random() * 1000 + 1));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}



	
}
