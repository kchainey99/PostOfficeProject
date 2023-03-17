package postoffice;

import java.util.concurrent.Semaphore;
import java.util.Random;

class Customer implements Runnable{
	private int customer_num;
	
	/**Initializes a Customer & gives them a customer number*/
	public Customer(int customer_num) {
		this.customer_num = customer_num;
	}
	
	/** getter for customer number*/
	public int getNumber() {
		int temp = customer_num;
		return temp;
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
	public void run(){
		//waiting to enter office
		try {
			SharedResources.max_capacity.acquire();
		} catch(InterruptedException e) {e.printStackTrace();}
		enterOffice();
		// wait for next available worker
		try {
			SharedResources.post_worker.acquire();
		} catch(InterruptedException e) {e.printStackTrace();}
		
		try {
			SharedResources.queue_mutex.acquire();
		} catch(InterruptedException e) {e.printStackTrace();}
		//add customer number and task to Linked List
		SharedResources.offerList.addFirst(new Integer(customer_num));
		SharedResources.offerList.addFirst(new Integer(giveTask()));
		SharedResources.queue_mutex.release();
		// wait until customer has been served
		try {
			SharedResources.served[customer_num].acquire();
		} catch(InterruptedException e) {e.printStackTrace();}
		
	}

}
