package postoffice;

import java.util.concurrent.Semaphore;
import java.util.Random;

class Customer extends Semaphores implements Runnable{
	private int customer_num;
	public Customer(int customer_num) {
		this.customer_num = customer_num;
	}
	
	/** setter for customer number*/
	public void setCustNum(int num) {
		customer_num = num;
	}
	
	/**Decides what task to tell the Office Worker to execute. Returns a random number between 0-2*/
	private int giveTask() {
		return new Random().nextInt(3);
	}
	
	/**Prints message to say when this customer has entered the office*/
	void enterOffice() {
		System.out.println("Customer " + customer_num + " has entered the office.");
	}
	
	void leaveOffice() {
		System.out.println("Customer " + customer_num + "has left the office.");
	}
	
	@Override
	public void run() {
		//waiting to enter office
		try {
			super.max_capacity.acquire();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

}
