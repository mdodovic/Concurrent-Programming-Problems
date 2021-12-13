package kdp.savingAccounts;

public class User extends Thread{

	private Account a;
	
	public User(String name, Account a) {
		super(name);
		this.a = a;		
	}

	@Override
	public void run() {
		while(true) {
			try {
				sleep((int)(Math.random()*1000 + 1));
			} catch(Exception e) {}
			int acc = a.status();
			System.out.println(getName() + " --> " + "Currently: " + acc);
			
			int amount = (int)(Math.random() * 100 + 1);
			if(getName().equals("U0")) {
				a.putin(amount);
				System.out.println(getName() + " --> " + "Put: " + amount);
			} else {
				a.putout(amount);
				System.out.println(getName() + " --> " + "Get: " + amount);
			}
			try {
				sleep((int)(Math.random()*1000 + 1));
			} catch(Exception e) {}
			acc = a.status();
			System.out.println(getName() + " --> " + "Currently: " + acc);
		}
	}
	

	public static void main(String[] args) {
		Account a = new Account();
		for(int i = 0; i < 3; i++) {
			new User("U" + i, a).start();
		}
	}

}
