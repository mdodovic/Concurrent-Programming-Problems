package kdp.oneLineBridge;

public class Test {

	public static void main(String[] args) {

		int N = 89;
		
		Bridge bridge = new RegionBridge();
		Car[] south = new Car[N];
		Car[] north = new Car[N];
		

		for(int i = 0; i < N; i++) {
			south[i] = new South("C" + 2*i, 2*i, bridge);
			north[i] = new North("C" + (2*i + 1), 2*i + 1, bridge);
			south[i].start();
			north[i].start();
		}


	}

}