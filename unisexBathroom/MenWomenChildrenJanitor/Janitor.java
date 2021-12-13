package kdp.unisexBathroom.MenWomenChildrenJanitor;

import kdp.unisexBathroom.Toilet;

public class Janitor extends Thread {

	private Toilet t;
	
	public Janitor(String string, Toilet t) {
		super(string);
		this.t = t;
	}
	
	@Override
	public void run() {
		while(true) {
			t.enterJanitor();
			
			System.out.println(getName() + " entered");
	
			try {
				sleep((int)(Math.random() * 1000));
			} catch(Exception e) {}
			
			System.out.println(getName() + " exited");
	
			t.exitJanitor();
		}
	}

	
}


