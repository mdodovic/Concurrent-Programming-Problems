package kdp.santaClausProblem;

public class Test {
	
	public static void main(String[] args) {
		
		House h = new House();
		
		new Santa("S", h).start();
		
		for(int i = 0; i < House.NUM_ELVES; i++)
			new Elf("E" + i, h).start(); 

		for(int i = 0; i < House.NUM_REINDEERS; i++)
			new Reindeer("R" + i, h).start(); 

	}

}
