package postoffice;

import java.util.concurrent.Semaphore;

class Post_Office {
	
	//For initializing threads
	final static int total_customers = 50; //50 customers
	final static int total_workers = 3; //3 workers
	
	public static void main(String[] args) {
		//customer threads
		Customer[] cust_arr = new Customer[total_customers];
		Thread[] cust_threads = new Thread[total_customers];
		
		//post worker threads
		Worker[] worker_arr = new Worker[total_workers];
		Thread[] worker_threads = new Thread[total_workers];
		
		/* Initializing worker threads*/
		for (int i = 0; i < total_workers; i++) {
			worker_arr[i] = new Worker(i);
			worker_threads[i] = new Thread(worker_arr[i]);
			worker_threads[i].setDaemon(true); //set worker threads as Daemon threads, so they terminate when the non-daemon (customer threads) finish
			worker_threads[i].start();
			System.out.println("Worker " + worker_arr[i].getNumber() + " created.");
		}
		
		/* Initializing customer threads & customer semaphores */
		for (int i = 0; i < total_customers; i++) {
			SharedResources.served[i] = new Semaphore(0, true); //all customers start not served, hence 0
			cust_arr[i] = new Customer(i);
			cust_threads[i] = new Thread(cust_arr[i]);
			cust_threads[i].setDaemon(false); //set customers as a non-daemon thread
			cust_threads[i].start();
			System.out.println("Customer " + cust_arr[i].getNumber() + " created.");
		}
		
		/* Join threads */
		for (int i = 0; i < total_customers; i++) {
			try {
				cust_threads[i].join();
				System.out.println("Joined customer " + cust_arr[i].getNumber());
			} catch (InterruptedException e) {e.printStackTrace();}
		}
	}

}
