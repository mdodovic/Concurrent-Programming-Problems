package kdp.santaClausProblem;

import java.util.ArrayList;
import java.util.List;

public class House {

	public static final int NUM_REINDEERS = 9;
	public static final int NUM_ELVES = 10;
	public static final int MIN_ELVES = 3;
	public static final int MIN_REINDEERS = 9;
	
	public enum Type {ELF, REINDEER}

	private int elvesAtTheDoor = 0;
	private int elvesAtTheRoom = 0;
	private int reidneersAtTheDoor = 0;
	private int reindeersAtTheSleigh = 0;
	
	private boolean wakeUpByElves = false;
	private boolean wakeUpByReindeers = false;
	
	private boolean enterElves = false;
	private boolean exitElves = false;

	private boolean enterReindeers = false;
	private boolean exitReindeers = false;
	
	private boolean isSleeping = true;
	
	public static List<String> withWho = new ArrayList<String>();
	
	public synchronized void ReindeerwakeUpSanta() {
		while( isSleeping == false) {
			try {
				wait();
			} catch(Exception e) {}
		}
		
		reidneersAtTheDoor++;
		if(reidneersAtTheDoor == MIN_REINDEERS) {
			wakeUpByReindeers = true;
			notifyAll();
		}
		while(enterReindeers == false) {
			try {
				wait();
			} catch(Exception e) {}			
		}
		reidneersAtTheDoor--;
		reindeersAtTheSleigh++;
		withWho.add(Thread.currentThread().getName());
		if(reidneersAtTheDoor == 0) {
			notifyAll();
		}
	}

	public synchronized void ReindeerExitTheSleigh() {
		while(exitReindeers == false) {
			try {
				wait();
			} catch(Exception e) {}						
		}
		
		reindeersAtTheSleigh--;
		if(reindeersAtTheSleigh == 0)
			notifyAll();
	}

	public synchronized void ElfwakeUpSanta() {
		while(isSleeping == false) {
			try {
				wait();
			} catch(Exception e) {}			
		}

		elvesAtTheDoor++;
		if(elvesAtTheDoor == MIN_ELVES) {
			wakeUpByElves = true;
			notifyAll();
		}
		
		while(enterElves == false) {
			try {
				wait();
			} catch(Exception e) {}			
		}
		
		elvesAtTheDoor--;
		elvesAtTheRoom++;
		withWho.add(Thread.currentThread().getName());
		if(elvesAtTheDoor == 0) {
			notifyAll();
		}
	}

	public synchronized void ElfExitTheRoom() {
		while(exitElves == false) {
			try {
				wait();
			} catch(Exception e) {}			
		}
		elvesAtTheRoom--;
		if(elvesAtTheRoom == 0) {
			notifyAll();
		}
	}

	public synchronized void throwReindeers() {
		exitReindeers = true;
		notifyAll();
		while(reindeersAtTheSleigh != 0) {
			try {
				wait();
			} catch(Exception e) {}			
		}
		exitReindeers = false;		
	}

	public synchronized void throwElves() {
		exitElves = true;
		notifyAll();
		while(elvesAtTheRoom != 0) {
			try {
				wait();
			} catch(Exception e) {}			
		}
		exitElves = false;		
	}

	public synchronized Type sleeping() {
		isSleeping = true;
		notifyAll();
		while(wakeUpByElves == false && wakeUpByReindeers == false) {
			try {
				wait();
			} catch(Exception e) {}			
		}
		
		isSleeping = false;
		if(wakeUpByReindeers) {
			wakeUpByReindeers = false;
			enterReindeers = true;
			notifyAll();
			while (reidneersAtTheDoor != 0) {
				try {
					wait();
				} catch(Exception e) {}			
			}
			enterReindeers = false;
			return Type.REINDEER;
		} else {			
			wakeUpByElves = false;
			enterElves = true;
			notifyAll();
			while (elvesAtTheDoor != 0) {
				try {
					wait();
				} catch(Exception e) {}			
			}
			enterElves = false;
			return Type.ELF;
		}
		
	};
	
}
