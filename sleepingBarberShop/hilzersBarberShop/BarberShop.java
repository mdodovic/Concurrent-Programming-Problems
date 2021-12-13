package kdp.sleepingBarberShop.hilzersBarberShop;

public interface BarberShop {

	boolean enterCustomer(int id);

	void exitCustomer(int id);

	void workBarber(int id);

	void sleepBarber(int id);

}
