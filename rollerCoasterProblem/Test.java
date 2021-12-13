package kdp.rollerCoasterProblem;

import java.util.concurrent.Semaphore;

public class Test {

	public static final int NUM_PASSENGER = 20;
	public static final int COASTER_CAPACITY = 4;
	
	public static void main(String[] args) {

		Semaphore startBoarding = new Semaphore(0);
		Semaphore allAboard = new Semaphore(0);
		
		Semaphore endRiding = new Semaphore(0);
		Semaphore allExited = new Semaphore(0);
		
		Passenger[] passenger = new Passenger[NUM_PASSENGER];
		Coaster coaster = new Coaster("COASTER1", startBoarding, allAboard, endRiding, allExited);
		coaster.start();

		for(int i = 0; i < NUM_PASSENGER; i++) {
			passenger[i] = new Passenger("P" + i, COASTER_CAPACITY, startBoarding, allAboard, endRiding, allExited);
			passenger[i].start();
		}
		
		
	}
}
