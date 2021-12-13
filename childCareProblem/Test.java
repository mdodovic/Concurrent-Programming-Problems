package kdp.childCareProblem;

public class Test {

	public static int N = 3;
	
	public static void main(String[] args) {
		Kindergarden kg = new Kindergarden();
		
		Parent[] parents = new Parent[N];
		Nanny[] nannies = new Nanny[N];
		
		for(int i = 0; i < N; i++) {
			parents[i] = new Parent("P" + i, kg);
			nannies[i] = new Nanny("N" + i, kg);
			parents[i].start();
			nannies[i].start();
			
		}
		
	}
}
