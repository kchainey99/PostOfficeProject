package postoffice;

import java.util.Random;

/**Simulates a customer at a post office. */
class Customer implements Runnable{
	private int customer_num;
	
	/**Initializes a Customer & gives them a customer number*/
	public Customer(int customer_num) {
		this.customer_num = customer_num;
	}
	
	/**  customer number*/
	public int getNumber() {
		int temp = customer_num;
		return temp;
	}
	
	/**Decides what task to tell the Office Worker to execute. Returns a random number between 0-2*/
	private int createTask() {
		return new Random().nextInt(3);
	}
	
	/**Prints message to say when this customer has entered the office*/
	void enterOffice() {
		System.out.println("Customer " + getNumber() + " has entered the office.");
	}
	
	void leaveOffice() {
		System.out.println("Customer " + getNumber() + " has left the office.");
	}
	
	/**Adds customer number and customer name to LinkedList. <br>
	 * Linked List is treated as a queue*/
	void giveTask() {
		SharedResources.offerList.addFirst(Integer.valueOf(customer_num));
		SharedResources.offerList.addFirst(Integer.valueOf(createTask()));
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
		//approach the counter and give the post worker their task
		try {
			SharedResources.queue_mutex.acquire();
		} catch(InterruptedException e) {e.printStackTrace();}
		giveTask();
		SharedResources.queue_mutex.release();
		SharedResources.cust_at_counter.release();
		try {
			SharedResources.served[customer_num].acquire(); // wait until customer has been served
		} catch(InterruptedException e) {e.printStackTrace();}
		leaveOffice(); //leave office
		SharedResources.max_capacity.release();
	}

}
