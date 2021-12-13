package kdp.busProblem;

public class Test {

	public static final int K = 4;
	public static final int NUM_OF_PASSENGER = 100;
	public static void main(String[] args) {
		
		Bus bus = new Bus("BUS", K);
		bus.start();

		
		
		Passenger[] passenger = new Passenger[NUM_OF_PASSENGER];
		for(int i = 0; i < NUM_OF_PASSENGER; i++) {
			passenger[i] = new Passenger("P" + i);
			passenger[i].start();
		}
		

	}

}
