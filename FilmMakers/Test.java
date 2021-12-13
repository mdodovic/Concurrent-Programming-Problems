package kdp.lab.FilmMakers;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Test {
	
	public static final Integer N = 10; // number of Consumer
	public static final int B = 3; // buffer size
	public static final int K = 3; // number of films when Combiner send a notification to Printer
	public static final int M = 1; // Printer waits for M seconds and then prints number of films processed by combiner
	
	public static void main(String[] args) {

		FileReader fr = null;
		try {
			fr = new FileReader("C:\\Users\\Matija\\Desktop\\Fakultet\\trecaGodina\\KDP\\lab\\" 
		+ "ConcurrentProgramming\\src\\kdp\\lab\\FilmMakers\\films-directors.txt");
		
			
			/*
			 * // Buffers - semaphores Buffer<String> bufFilmsPC = new
			 * BufferDataBySemaphore<String>(B); Buffer<String> bufFilmsCC = new
			 * BufferDataBySemaphore<String>(B); Buffer<String> bufFilmsCombinerPriner = new
			 * BufferDataBySemaphore<String>(1);
			 */
			 
			
			/*
			 * // Buffers - regions Buffer<String> bufFilmsPC = new
			 * BufferDataByRegions<String>(B); Buffer<String> bufFilmsCC = new
			 * BufferDataByRegions<String>(B); Buffer<String> bufFilmsCombinerPriner = new
			 * BufferDataByRegions<String>(1);
			 */
			  
			/*
			 * // Buffers - monitors Buffer<String> bufFilmsPC = new
			 * BufferDataByMonitors<String>(B); Buffer<String> bufFilmsCC = new
			 * BufferDataByMonitors<String>(B); Buffer<String> bufFilmsCombinerPriner = new
			 * BufferDataByMonitors<String>(1);
			 */			  
			 
			
			// Buffers - locks
			Buffer<String> bufFilmsPC = new BufferDataByLocks<String>(B); 
			Buffer<String> bufFilmsCC = new BufferDataByLocks<String>(B); 
			Buffer<String> bufFilmsCombinerPriner = new BufferDataByLocks<String>(1);
			 
			 
			
			
			Producer p = new Producer("P1", bufFilmsPC, N, fr);
			Combiner c = new Combiner("COMBINER", bufFilmsCC, N, bufFilmsCombinerPriner, K);
			Printer printer = new Printer("PRINTER", M, bufFilmsCombinerPriner);
			for(int i = 0; i < N; i++)
				new Consumer("C" + (i+1), i, bufFilmsPC, N, bufFilmsCC, c).start();

			p.start();
			printer.start();
			c.start();
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

}
