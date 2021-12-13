package kdp.lab.FilmMakers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Producer extends Thread {

	private Buffer<String> b;
	private FileReader fr;
	private int consumerCounter;
	
	public Producer(String s, Buffer<String> bufDirecctors, int consumerCounter, FileReader fr) {
		super(s);
		this.b = bufDirecctors;
		this.consumerCounter = consumerCounter;
		this.fr = fr;
	}



	@Override
	public void run() {
		String s;
		try (BufferedReader br = new BufferedReader(fr)){
			int i = 0;
			while((s = br.readLine()) != null) {
				b.put( i + "      " + s);
				i++;
			}
			for(i = 0; i < consumerCounter; i++) {
				b.put(null);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
}
