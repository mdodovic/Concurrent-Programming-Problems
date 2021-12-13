package kdp.readersWriters;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class SemaphoreFIFORW implements ReadersWriters {

	private Semaphore enter = new Semaphore(1);
	private Semaphore reader = new Semaphore(0);
	private Semaphore writer = new Semaphore(0);
	
	private int currentReader = 0;
	private int currentWriter = 0;
		
	private List<Character> list = new LinkedList<Character>();
	
	private void signal() {
		if(list.size() > 0 && list.get(0) == 'r' && currentWriter == 0) {
			reader.release();
		} else {
			if(list.size() > 0 && list.get(0) == 'w' && currentWriter == 0 && currentReader == 0) {
				writer.release();			
			} else {
				enter.release();
			}
		}
			
	}

	@Override
	public void startRead() {
		enter.acquireUninterruptibly();
		
		if(currentWriter > 0 || list.size() > 0) {
			list.add('r');
			enter.release();
			reader.acquireUninterruptibly();
			list.remove(0);
		}
		currentReader++;
		signal();
	}

	@Override
	public void endRead() {
		enter.acquireUninterruptibly();
		currentReader--;
		signal();
	}

	@Override
	public void startWrite() {
		enter.acquireUninterruptibly();
		
		if(currentWriter > 0 || currentReader > 0 || list.size() > 0) {
			list.add('w');
			enter.release();
			writer.acquireUninterruptibly();
			list.remove(0);
		}
		currentWriter++;
		signal();
	}

	@Override
	public void endWrite() {
		enter.acquireUninterruptibly();
		currentWriter--;
		signal();
	}

}
