package kdp.sleepingBarberShop;

public class Customer extends Thread {

	private int id;
	private Barbershop shop;
	
	public Customer(String string, int id, Barbershop shop) {
		super(string);
		this.id = id;
		this.shop = shop;
	}
	
	@Override
	public void run() {
		enterToShop();
		boolean result = shop.getHaircut(id);
		exit(result);
	}

	private void exit(boolean result) {
		System.out.println(getName() + " exited the shop " + (result == true ? "WITH haircut" : "WITHOUT haircut"));
		
	}

	private void enterToShop() {
		try {
			sleep(2000);
		} catch (InterruptedException e) {}
		System.out.println(getName() + " entered the shop!");
	}

}
