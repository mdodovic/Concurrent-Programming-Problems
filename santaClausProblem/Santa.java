package kdp.santaClausProblem;

import kdp.santaClausProblem.House.Type;

public class Santa extends Thread{

	private House h;
	
	public Santa(String string, House h) {
		super(string);
		this.h = h;
	}
	
	@Override
	public void run() {
		while(true) {

			try {
				sleep((int)(Math.random() * 1000 + 1));
			} catch (InterruptedException e) {}

			House.Type who = h.sleeping();

			if(who.equals(Type.ELF)) {
				System.out.print(getName() + " talk with elves: ");
				synchronized (h) {
					for(String sI: House.withWho)
						System.out.print(sI + " ; ");
					House.withWho.clear();
					System.out.println();
				}
				try {
					sleep((int)(Math.random() * 1000 + 1));
				} catch (InterruptedException e) {}

				h.throwElves();				
			} else {
				System.out.print(getName() + " ride with reindeers: ");
				synchronized (h) {
					for(String sI: House.withWho)
						System.out.print(sI + " ; ");
					House.withWho.clear();
					System.out.println();
				}

				try {
					sleep((int)(Math.random() * 1000 + 1));
				} catch (InterruptedException e) {}

				h.throwReindeers();								
			}

		}
	}

}
