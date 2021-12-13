package kdp.DiningPhilosophers;

public class MonitorForks implements Forks {

	private boolean[] available;
	
	
	
	public MonitorForks(int n) {
		available = new boolean[n];
		for(int i = 0; i < n; i++)
			available[i] = true;
	}

	@Override
	public synchronized void pickup(int id) {
		int first; 
		int second;
		if(id % 2 == 1) {
			first = id;
			second = (id + 1) % available.length;
		} else {
			first = (id + 1) % available.length;
			second = id;
		}
		
		while(available[first] != true || available[second] != true) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		
		available[first] = false;
		available[second] = false;
		
	}

	@Override
	public synchronized void putdown(int id) {
		int first; 
		int second;
		if(id % 2 == 1) {
			first = id;
			second = (id + 1) % available.length;
		} else {
			first = (id + 1) % available.length;
			second = id;
		}
		
		available[first] = true;
		available[second] = true;
		notifyAll();
	}

}
