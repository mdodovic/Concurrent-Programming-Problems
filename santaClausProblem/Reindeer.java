package kdp.santaClausProblem;

public class Reindeer extends Thread{

	private House h;
	
	public Reindeer(String string, House h) {
		super(string);
		this.h = h;
	}

	@Override
	public void run() {
		while(true) {
			synchronized (h) {	
				System.out.println(getName() + " rest");
			}
			try {
				sleep((int)(Math.random() * 1000 + 1));
			} catch (InterruptedException e) {}
			
			h.ReindeerwakeUpSanta();
			synchronized (h) {	
				System.out.println(getName() + " ride");
			}
			try {
				sleep((int)(Math.random() * 1000 + 1));
			} catch (InterruptedException e) {}

			h.ReindeerExitTheSleigh();

		}
	}


}
