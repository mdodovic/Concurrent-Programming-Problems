package kdp.DiningPhilosophers;

import java.util.concurrent.Semaphore;

public class SemaphorForks implements Forks {

	private Semaphore[] available;
		
	public SemaphorForks(int n) {
		available = new Semaphore[n];
		for(int i = 0; i < n; i++)
			available[i] = new Semaphore(1);
	}

	@Override
	public void pickup(int id) {
		int first; 
		int second;
		if(id % 2 == 1) {
			first = id;
			second = (id + 1) % available.length;
		} else {
			first = (id + 1) % available.length;
			second = id;
		}
		
		available[first].acquireUninterruptibly();
		available[second].acquireUninterruptibly();
		
	}

	@Override
	public void putdown(int id) {
		int first; 
		int second;
		if(id % 2 == 1) {
			first = id;
			second = (id + 1) % available.length;
		} else {
			first = (id + 1) % available.length;
			second = id;
		}
		
		available[first].release();
		available[second].release();
	
	}


}
