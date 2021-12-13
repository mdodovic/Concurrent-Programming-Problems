package kdp.childCareProblem;

public class Nanny extends Thread {

	private Kindergarden kg;
	
	public Nanny(String string, Kindergarden kg) {
		super(string);
		this.kg = kg;
	}
	
	@Override
	public void run() {
		while(true) {
			System.out.println(getName() + " start with work");
			
			kg.nannyEnter();
			
			System.out.println(getName() + " WORK");
			try {
				sleep((int)(Math.random() * 2000));
			}catch(Exception e) {}
				
			kg.nannyExit();

			System.out.println(getName() + " LEFT");
			try {
				sleep((int)(Math.random() * 2000));
			}catch(Exception e) {}
		}
	}

}
