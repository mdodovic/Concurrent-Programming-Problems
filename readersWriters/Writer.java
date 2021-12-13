package kdp.readersWriters;

public class Writer extends Thread {

	private int id;
	private ReadersWriters book;

	public Writer(String string, int i, ReadersWriters book) {
		super(string);
		this.id = i;
		this.book = book;
	}

	
	@Override
	public void run() {
		
		try {
			sleep((int)(Math.random())*100 + 1);
		} catch (InterruptedException e) {}

		book.startWrite();

		System.out.println("W" + id + " writing");
		try {
			sleep((int)(Math.random() * 1000 + 1));
		} catch (InterruptedException e) {}

		System.out.println("W" + id + " finished");
		book.endWrite();

	}

}
