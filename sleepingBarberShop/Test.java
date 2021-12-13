package kdp.sleepingBarberShop;

public class Test {

	public static final int NUM_SEATS = 5;
	public static final int NUM_CUST = 12;
	
	public static void main(String[] args) {
		
		Barbershop shop = new Barbershop(NUM_SEATS);
		
		new Barber("Barber1", shop).start();
		
		for(int i = 0; i < NUM_CUST; i++)
			new Customer("Customer " + i, i, shop).start();
	}

}
