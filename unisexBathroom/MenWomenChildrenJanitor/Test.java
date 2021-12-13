package kdp.unisexBathroom.MenWomenChildrenJanitor;

import kdp.unisexBathroom.Toilet;

public class Test {

	public static int NUM_MEN = 15;
	public static int NUM_WOMEN = 10;
	public static int NUM_CHILDREN = 17;
	public static int NUM_JANITORS = 2;
	
	public static void main(String[] args) {
		
		Toilet t = new ToiletMonitor();
		
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
		Children[] c = new Children[NUM_CHILDREN];
		for(int i = 0; i < NUM_CHILDREN; i++) {
			c[i] = new Children("C" + i, t);
			c[i].start();
		}
		Janitor[] j = new Janitor[NUM_JANITORS];
		for(int i = 0; i < NUM_JANITORS; i++) {
			j[i] = new Janitor("J" + i, t);
			j[i].start();
		}
	}
}
