package kdp.oneLineBridge;

public abstract class Car extends Thread{

	protected int id;
	protected RegionBridge bridge;
	
	public Car(String name, int id, Bridge bridge) {
		super(name);
		this.id = id;
		this.bridge = (RegionBridge)bridge;
	}

	public void crossing() {
		System.out.println((id % 2 == 0 ? "SOUTH " : "NORTH ") + id + " crossing");
		try {
			sleep((int) (Math.random() * 1000 + 1));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void starting() {
		System.out.println((id % 2 == 0 ? "SOUTH " : "NORTH ") + id + " starting");
		try {
			sleep((int) (Math.random() * 1000 + 1));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}
	
}
