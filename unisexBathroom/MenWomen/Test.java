package kdp.unisexBathroom.MenWomen;

import kdp.unisexBathroom.Toilet;

public class Test {

	public static int NUM_MEN = 15;
	public static int NUM_WOMEN = 10;
	
	public static void main(String[] args) {
		
		//Toilet t = new ToiletMonitor();
		//Toilet t = new ToiletLock();
		Toilet t = new ToiletRegionTicket();
		
		Women[] w = new Women[NUM_WOMEN];
		for(int i = 0; i < NUM_WOMEN; i++) {
			w[i] = new Women("W" + i, t);
			w[i].start();
		}
		Men[] m = new Men[NUM_MEN];
		for(int i = 0; i < NUM_MEN; i++) {
			m[i] = new Men("M" + i, t);
			m[i].start();
		}
	}
}
