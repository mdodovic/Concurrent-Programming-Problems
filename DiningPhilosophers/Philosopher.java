package kdp.DiningPhilosophers;

public class Philosopher extends Thread {
	
	private int id;
	
	private Forks forks;
	
	public Philosopher(String name, int id, Forks forks) {
		super(name);
		this.id = id;
		this.forks = forks;
	}

	@Override
	public void run() {
		while(true) {
			think();
			forks.pickup(id);
			eat();
			forks.putdown(id);
		}
	}

	private void eat() {
		System.out.println("Philosopher " + id + " eats");
		try {
			sleep((int)(Math.random() * 1000 + 1));
		} catch (InterruptedException e) {} 
	}

	private void think() {
		System.out.println("Philosopher " + id + " thinks");
		try {
			sleep((int)(Math.random() * 2000 + 1));
		} catch (InterruptedException e) {} 		
	}

}
