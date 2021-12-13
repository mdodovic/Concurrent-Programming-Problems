package kdp.DiningPhilosophers;

public class Test {

	public static void main(String[] args) {

		int N = 5;
		
		Forks f = new SemaphorForks(N);
		Philosopher[] p = new Philosopher[N];
		for(int i = 0; i < N; i++) {
			p[i] = new Philosopher("P" + i, i, f);
			p[i].start();
		}
		
	}

}
