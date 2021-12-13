package kdp.childCareProblem;

public class Parent extends Thread {

	private Kindergarden kg;
	
	public Parent(String string, Kindergarden kg) {
		super(string);
		this.kg = kg;
	}
	
	@Override
	public void run() {
		while(true) {
			int num = (int) (Math.random() * 3 + 1);
			System.out.println(getName() + " bring up " + num + " childrens");
			
			boolean success = kg.bringChildren(num);
			
			if(success) {
				System.out.println(getName() + " WORK");
				try {
					sleep((int)(Math.random() * 2000));
				}catch(Exception e) {}
				
				kg.returnChildren(num);
				System.out.println(getName() + " bring back " + num + " childrens");
			} else {
				System.out.println(getName() + " no space for " + num + " childrens");			
			}
		}
	}

}
