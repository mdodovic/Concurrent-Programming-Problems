package kdp.oneLineBridge;

public class North extends Car {

	public North(String name, int id, Bridge bridge) {
		super(name, id, bridge);
	}

	@Override
	public void run() {
		starting();
		
		synchronized (bridge) {
			bridge.waitingNorth++;
			while(bridge.crossingSouth != 0 || bridge.aheadNorth > Bridge.CAPACITY)
				try {
					bridge.wait();
				} catch (InterruptedException e) {}
			bridge.waitingNorth--;
			bridge.crossingNorth++;
			if(bridge.waitingSouth > 0)
				bridge.aheadNorth++;
		}
		
		crossing();
		
		synchronized (bridge) {
			bridge.crossingNorth--;
			if(bridge.crossingNorth == 0) {
				bridge.aheadSouth = 0;
				bridge.notifyAll();
			}
		}
	
	}	
	
}
