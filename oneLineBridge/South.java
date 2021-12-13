package kdp.oneLineBridge;

public class South extends Car {

	public South(String name, int id, Bridge bridge) {
		super(name, id, bridge);
	}

	@Override
	public void run() {
		starting();
		
		synchronized (bridge) {
			bridge.waitingSouth++;
			while(bridge.crossingNorth != 0 || bridge.aheadSouth > Bridge.CAPACITY) {
				try {
					bridge.wait();
				} catch (InterruptedException e) {}
			}
			bridge.waitingSouth--;
			bridge.crossingSouth++;
			if (bridge.waitingNorth > 0)
				bridge.aheadSouth++;
		}
		
		
		crossing();
		
		synchronized (bridge) {
			bridge.crossingSouth--;
			if(bridge.crossingSouth == 0) {
				bridge.aheadNorth = 0;
				bridge.notifyAll();
			}
		}

	}
}
