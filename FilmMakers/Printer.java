package kdp.lab.FilmMakers;

public class Printer extends Thread {

	private final int m;
	
	private Buffer<String> bufFilmsCombinerPriner;
	
	public Printer(String string, int m, Buffer<String> bufFilmsCombinerPriner) {
		super(string);
		this.m = m;
		
		this.bufFilmsCombinerPriner = bufFilmsCombinerPriner;
		
	}
	
	
	@Override
	public void run() {
		while(true) {
			String s = bufFilmsCombinerPriner.get();
			if(s == null)
				break;
			System.err.println("PRINTER: " + s);
			try {
				sleep((int)(m * 1000));
			} catch (InterruptedException e) {}

		}
	}
	

}
