package kdp.lab.FilmMakers;

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.Semaphore;

public class Combiner extends Thread {
	
	
	private Buffer<String> b;
	
	private int max = 0;
	
	private List<String> listOfMaxDirectorsThread = new LinkedList<String>();
	private List<String> listOfNotConsideredThread = new LinkedList<String>();
	
	private int numberOfConsumers;

	Semaphore waitForConsumers = new Semaphore(0);
	Semaphore waitForMe = new Semaphore(0);
	private Buffer<String> bufFilmsCombinerPriner;

	private int k;
	
	boolean ready = false;
	
	public Combiner(String s, Buffer<String> b, int n, Buffer<String> bufFilmsCombinerPriner, int k) {
		super(s);
		this.b = b;
		this.numberOfConsumers = n;
		this.bufFilmsCombinerPriner = bufFilmsCombinerPriner;
		this.k = k;
	}

	@Override
	public void run() {
		int processedFilms = 0;
		for(int i = 0; i < numberOfConsumers; i++) {
			String s = b.get();
			StringTokenizer st = new StringTokenizer(s, ",");
			String threadName = st.nextToken();
			int counter = Integer.parseInt(st.nextToken());
			String numberOfFilms = st.nextToken();
			processedFilms += Integer.parseInt(numberOfFilms);
			if(processedFilms >= k) {
				bufFilmsCombinerPriner.put(String.valueOf(processedFilms));
				processedFilms = 0;
			}
			System.out.println(threadName + " --> " + counter + " = " + numberOfFilms);

			if(max <= counter) {		
				if(max < counter) {
					for(String name: listOfMaxDirectorsThread)
						listOfNotConsideredThread.add(name);
					listOfMaxDirectorsThread.clear();
					max = counter;
				}
				listOfMaxDirectorsThread.add(threadName);
			} else {
				listOfNotConsideredThread.add(threadName);
			}
		}
			
		if(processedFilms > 0) {
			bufFilmsCombinerPriner.put(String.valueOf(processedFilms));
			processedFilms = 0;
		}

		//System.out.println("Receive Everything!");
		
		waitForConsumers.acquireUninterruptibly(numberOfConsumers);
		waitForMe.release(numberOfConsumers);	
		
		/*
		System.out.println("BAD:");
		for(String name: listOfNotConsideredThread) {
			System.out.print(name + " ; ");
		}
		System.out.println();
		System.out.println("GOOD:");
		for(String name: listOfMaxDirectorsThread) {
			System.out.print(name + " ; ");
		}
		System.out.println();
		*/
		
		String s = "";
		for(String name: listOfNotConsideredThread) {
			s += name + "," + "NOTOK"+"#";
		}
		for(String name: listOfMaxDirectorsThread) {
			s += name + "," + "OK"+"#";
		}
		for(int i = 0; i < numberOfConsumers; i++)
			b.put(s);

		//System.out.println("SendEverything!");
		
		waitForConsumers.acquireUninterruptibly(numberOfConsumers);

		int cnt = listOfMaxDirectorsThread.size();
		listOfMaxDirectorsThread.clear();
		for(int i = 0; i < cnt; i++) {
			while(true) {
				String maxFilms = b.get();
				if(maxFilms == null)
					break;
				listOfMaxDirectorsThread.add(maxFilms);
			}
		}
		

		for(String sI : listOfMaxDirectorsThread) { 
			bufFilmsCombinerPriner.put(sI);
		}
		bufFilmsCombinerPriner.put(null);

		
//		for(String sI : listOfMaxDirectorsThread) { 
//			System.out.println(sI);
//		}	
		
	}

}
