package kdp.busProblem;

public class Passenger extends Thread {

	public Passenger(String string) {
		super(string);

	}
	
	
	@Override
	public void run() {

		comeToStation();
		
		enterToBus();
		
	}


	private void comeToStation() {
		try {
			sleep((int)(Math.random() * 5000 + 1));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println(getName() + " Arrived ");
		
	}


	private void enterToBus() {
		Bus.station.acquireUninterruptibly();
		Bus.waitingPassengers++;
		Bus.station.release();
		
		Bus.aboard.acquireUninterruptibly();
		Bus.list.add(getName());
		Bus.space--;
		Bus.waitingPassengers--;
		if(Bus.space == 0) {
			Bus.allAboarded.release();
		} else {
			if(Bus.waitingPassengers == 0) {
				Bus.allAboarded.release();
			} else {
				Bus.aboard.release();
			}			
		}
	}
	
	
}
