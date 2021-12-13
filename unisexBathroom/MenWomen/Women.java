package kdp.unisexBathroom.MenWomen;

import kdp.unisexBathroom.Toilet;

public class Women extends Thread {

	private Toilet t;
	
	public Women(String string, Toilet t) {
		super(string);
		this.t = t;
	}
	
	@Override
	public void run() {
		while(true) {
			t.enterWoman();
			
			System.out.println(getName() + " entered");
	
			try {
				sleep((int)(Math.random() * 1000));
			} catch(Exception e) {}
			
			System.out.println(getName() + " exited");
	
			t.exitWoman();
		}
	}

}
