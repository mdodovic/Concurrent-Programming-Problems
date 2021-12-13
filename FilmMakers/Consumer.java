package kdp.lab.FilmMakers;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Consumer extends Thread {

	private Buffer<String> b;
	
	private List<String> listOfFilms = new ArrayList<>();
	private int currentMaximum = 0;
	
	private static CyclicBarrier barrier = null;
	private int numberOfConsumers;
	
	private Buffer<String> bufferFilmCounterInfo;
	
	@SuppressWarnings("unused")
	private int id;

	private Combiner combiner;
	
	
	public Consumer(String s, int id, Buffer<String> bufDirecctors, int numberOfConsumers, Buffer<String> bufferFilmInfo, Combiner c) {
		super(s);
		this.b = bufDirecctors;
		this.id = id;
		this.numberOfConsumers = numberOfConsumers;
		barrier = new CyclicBarrier(this.numberOfConsumers);
		this.bufferFilmCounterInfo = bufferFilmInfo;
		this.combiner = c;
	}


	@Override
	public void run() {
		
		int processes = getFilms();
		
		writeSync(processes);
		
		sentToCombiner();

	}


	private void sentToCombiner() {
		String s = getName() + "," + String.valueOf(currentMaximum) + "," + listOfFilms.size();
		bufferFilmCounterInfo.put(s);
		
		try { // Not need but it is easier to debug if all consumers are here
			barrier.await();
			//System.out.println("*** " + getName() + " ***");
			
		} catch (InterruptedException | BrokenBarrierException e1) {
			e1.printStackTrace();
		}

		combiner.waitForConsumers.release();
		combiner.waitForMe.acquireUninterruptibly();

		
		String announcement = bufferFilmCounterInfo.get();
		String[] announcements = announcement.split("#");
		
		boolean myStatus = true;
		
		for(String iter: announcements) {
			StringTokenizer st = new StringTokenizer(iter, ",");
			if(st.nextToken().equals(getName())) {
				if(st.nextToken().equals("NOTOK")) {
					myStatus = false;
				} 
				break;
			}
		}
		
		try {
			//System.out.println(getName() + " received status: " + myStatus);
			barrier.await();
			System.out.println(getName() + " FINISHED THIRD PHASE: send its films to the combiner or die");
			combiner.waitForConsumers.release();
			if(myStatus == false) {
				return;
			}
			
		} catch (InterruptedException | BrokenBarrierException e1) {
			e1.printStackTrace();
		}
		
		for(String l : listOfFilms) {
			bufferFilmCounterInfo.put(l);
		}
		bufferFilmCounterInfo.put(null);
		
	}


	private void writeSync(int processes) {
		synchronized (Test.N) {
			System.out.println("Consumer: " + getName() + " processes " + processes + " films");			
			for(String l : listOfFilms) {
				System.out.println(l);
			}
			System.out.println();
		}

		try {
			barrier.await();
		} catch (InterruptedException | BrokenBarrierException e1) {
			e1.printStackTrace();
		}
		
		System.out.println(getName() + " FINISHED SECOND PHASE: write its films");
		
		try {
			barrier.await();
		} catch (InterruptedException | BrokenBarrierException e1) {
			e1.printStackTrace();
		}
	}


	private int getFilms() {
		int processes = 0;
		while(true) {
			String s = b.get();
			if(s == null)
				break;
			StringTokenizer st = new StringTokenizer(s, ",");
			String filmTitle = st.nextToken();
			String filmDirector = st.nextToken();
			int directorsCounter = Integer.parseInt(st.nextToken());
			String otherInfo = st.nextToken();
			processes ++;
			
			System.out.println(getName() + " :->: " + filmTitle + " , " + filmDirector + " , " + directorsCounter + " , " + otherInfo);
			
			if(currentMaximum <= directorsCounter) {
				if(currentMaximum < directorsCounter) {
					listOfFilms.clear();
					currentMaximum = directorsCounter;
				} 				
				listOfFilms.add(filmTitle + " , " + filmDirector + " , " + directorsCounter + " , " + otherInfo);							
			}
			try {
				sleep((int)(500));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}


		try {
			barrier.await();
		} catch (InterruptedException | BrokenBarrierException e1) {
			e1.printStackTrace();
		}

		System.out.println(getName() + " FINISHED FIRST PHASE: get and process every films");
		
		try {
			barrier.await();
		} catch (InterruptedException | BrokenBarrierException e1) {
			e1.printStackTrace();
		}
		return processes;
	}
	
}
