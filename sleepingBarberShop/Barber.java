package kdp.sleepingBarberShop;

public class Barber extends Thread {

	private Barbershop shop;
	
	public Barber(String name, Barbershop shop) {
		super(name);
		this.shop = shop;
	}
	
	@Override
	public void run() {
		while(true) {
			int customer = shop.getNextCustomer();

			shavingCustomer(customer);
			
			shop.finishHaircut();			
		}
	}

	private void shavingCustomer(int customer) {
		System.out.println("Shaving " + customer);

	}

}
