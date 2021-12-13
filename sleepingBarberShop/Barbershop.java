package kdp.sleepingBarberShop;

import java.util.ArrayList;
import java.util.List;

public class Barbershop {
	
	private int numOfChairs;
	private int numberOfCustomers = 0;
	private List<Integer> chairs;
	private boolean done = false;
		
	public Barbershop(int numSeats) {
		this.numOfChairs = numSeats;
		this.chairs = new ArrayList<>(numOfChairs);

	}

	public synchronized boolean getHaircut(int id) {
		// customer
		// entrance protocol 		
		notifyAll(); // wake the barber!
		while(numberOfCustomers == numOfChairs) {
			return false; // Leave the barbershop
		}
		
		numberOfCustomers++;
		chairs.add(id); // sit down
		
		while(chairs.contains(id)) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		notifyAll(); // wake the barber!
		// exit protocol	
		while(done == false) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		done = false;
		notifyAll();
		return true;
	}


	public synchronized int getNextCustomer() {
		// barber
		while(numberOfCustomers  == 0) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		notifyAll();
		return chairs.remove(0);
	}

	public synchronized void finishHaircut() {
		// barber
		done  = true;
		notifyAll();
		while(done) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		numberOfCustomers--;
		notifyAll();
	}

}
