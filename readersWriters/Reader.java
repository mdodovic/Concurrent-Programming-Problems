package kdp.readersWriters;

public class Reader extends Thread {


	private int id;
	private ReadersWriters book;

	public Reader(String string, int i, ReadersWriters book) {
		super(string);
		this.id = i;
		this.book = book;
	}

	@Override
	public void run() {

		try {
			sleep((int)(Math.random())*100 + 1);
		} catch (InterruptedException e) {}
		
		book.startRead();

		System.out.println("R" + id + " reading");
		try {
			sleep((int)(Math.random() * 1000 + 1));
		} catch (InterruptedException e) {}

		System.out.println("R" + id + " finished");
		book.endRead();

	}
	
	
	
	
}
