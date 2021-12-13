package kdp.sleepingBarberShop.hilzersBarberShop;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.StampedLock;

public class BarberShopStamped implements BarberShop {
	private int numOfCustomers =0;
	private int numOfFilledSeats=0;
	private int numOfStanding=0;
	private int numOfAvailableBarbers=3;
	private int waitingCustomers =0;
	private StampedLock lock= new StampedLock();
	private Queue<Long> longQ1 = new PriorityQueue<Long>(10);
	private Queue<Long> longQ2 = new PriorityQueue<Long>(10);
	private Queue<Long> longQ3 = new PriorityQueue<Long>(3);
	private long[] workId = new long[3];
	//bez niz conditionA
	private Condition waitingForSeat = lock.asWriteLock().newCondition();
	private Condition waitingForBarber = lock.asWriteLock().newCondition();
	private Condition waitingForHairCut = lock.asWriteLock().newCondition();
	private Condition waitingForPay = lock.asWriteLock().newCondition();
	private Condition waitingForRegister = lock.asWriteLock().newCondition();
	private Condition sleeping = lock.asWriteLock().newCondition();
	
	
	private long payingCustomer=0;
	private boolean cashRegisterBusy =false;
	
	@Override
	public boolean enterCustomer(int id) {
		long stp = lock.writeLock();
		if(numOfCustomers==20) {
			lock.unlockRead(stp);
			return false;
		}
		numOfCustomers++;
		if(numOfFilledSeats==10||numOfStanding>0) {
			longQ1.add(stp);
			boolean been = false;
			numOfStanding++;
			while(!been||(longQ1.element()!=stp&&numOfFilledSeats==10)) {
					try {
						if(!been) {
							waitingForSeat.await();
						}
						been=true;
						if(longQ1.element()!=stp) {
							waitingForSeat.signal();
						}
						waitingForSeat.await();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
			}
			
			numOfStanding--;
			longQ2.remove();
		}
		
		if(numOfAvailableBarbers==0||numOfFilledSeats>0) {
			numOfFilledSeats++;
			longQ2.add(stp);
			boolean been = false;
			while(!been||(longQ2.element()!=stp&&numOfAvailableBarbers==0)) {
				try {
					if(!been) {
						waitingForBarber.await();
					}
					been=true;
					if(longQ2.element()!=stp) {
						waitingForBarber.signal();
					}
					waitingForBarber.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
			numOfFilledSeats--;
			if (numOfStanding>0) {
				longQ2.remove();
				waitingForSeat.signal();
			}
		}
		waitingCustomers++;
		longQ3.add(stp);
		while(stp!=payingCustomer)
		try {
			waitingForHairCut.await();
			if(stp!=payingCustomer) {
				waitingForHairCut.signal();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		waitingCustomers--;
		//numOfCustomers--;
		lock.unlockWrite(stp);
		return true;
	}

	@Override
	public void exitCustomer(int id) {
		long stpexit = lock.writeLock();
		waitingForPay.signal();
		numOfCustomers--;
		lock.unlockWrite(stpexit);
		
	}

	@Override
	public void sleepBarber(int id) {
		long stpSleep = lock.writeLock();
		if(waitingCustomers<=0) {
			numOfAvailableBarbers++;
			try {
				sleeping.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			numOfAvailableBarbers--;
		}
		workId[id]=longQ3.remove();
		lock.unlock(stpSleep);
	}

	@Override
	public void workBarber(int id) {
		/*long stpWork = */lock.writeLock();
		while(cashRegisterBusy) {
			try {
				waitingForRegister.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		cashRegisterBusy=true;
		payingCustomer = workId[id];
		waitingForHairCut.signal();
		try {
			waitingForPay.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cashRegisterBusy=false;
	}

}
