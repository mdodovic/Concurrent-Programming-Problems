package kdp.santaClausProblem;

public class Elf extends Thread{

	private House h;
	
	public Elf(String string, House h) {
		super(string);
		this.h = h;
	}
	
	@Override
	public void run() {
		while(true) {
			synchronized (h) {				
				System.out.println(getName() + " work");
			}
			try {
				sleep((int)(Math.random() * 1000 + 1));
			} catch (InterruptedException e) {}
			
			h.ElfwakeUpSanta();
			synchronized (h) {
				System.out.println(getName() + " talk with santa");
			}
			try {
				sleep((int)(Math.random() * 1000 + 1));
			} catch (InterruptedException e) {}

			h.ElfExitTheRoom();

		}
	}

}
