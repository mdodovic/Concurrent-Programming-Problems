package kdp.savingAccounts;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Account {

	int money = 0;
	
	ReadWriteLock rw = new ReentrantReadWriteLock(true);
	
	Map<Thread, Integer> waiting = new HashMap<Thread, Integer>();
	List<Thread> blocked = new LinkedList<Thread>();
	List<Thread> toBeUnlock = new LinkedList<Thread>();

	Lock l = new ReentrantLock();
	Condition c = l.newCondition();
	
	public void putin(int amount) {
		rw.writeLock().lock();
		money += amount;
		if(blocked.size() > 0) {
			for(Thread t: blocked) {
				int m = waiting.get(t);
				if(money > m) {
					money -= m;
					waiting.put(t, -1);
					toBeUnlock.add(t);
					synchronized (this) {						
						notifyAll();
					}
				}
			}
			for(Thread t: toBeUnlock) {
				blocked.remove(t);
			}
			toBeUnlock.clear();
		}
		rw.writeLock().unlock();		
	}

	public void putout(int amount) {
		rw.writeLock().lock();
		
		if(money - amount >= 0) {
			money-= amount;
			rw.writeLock().unlock();				
		} else {
			waiting.put(Thread.currentThread(), amount);
			blocked.add(0,Thread.currentThread());
			rw.writeLock().unlock();
			synchronized (this) {
				while(waiting.get(Thread.currentThread()) != -1) {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}				
			}
		}

	}

	public int status() {
		int money = 0;
		rw.readLock().lock();
		money = this.money;
		rw.readLock().unlock();
		return money;
	}

}
