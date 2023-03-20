package postoffice;

/** Takes in and processes customer transactions */
class Worker implements Runnable{
	private int worker_num;
	
	public Worker(int num) {
		worker_num = num;
	}
	
	public int getNumber() {
		int temp = worker_num;
		return temp;
	}
	
	void serve_cust(int c_num, int req) throws InterruptedException {
		switch(req) {
		case 0: //buying stamps
			System.out.println("Customer " + c_num + " asked post worker " + worker_num + " to buy stamps.");
			Thread.sleep(1000);
			break;
		case 1: //mail a letter
			System.out.println("Customer " + c_num + " asked post worker " + worker_num + " to mail a letter.");
			Thread.sleep(1500);
			break;
		case 2:
			System.out.println("Customer " + c_num + " asked post worker" + worker_num + " to mail a package.");
			SharedResources.scale.acquire();
			Thread.sleep(2000);
			SharedResources.scale.release();
			break;
		}
	}

	@Override
	public void run() {
		int served_c, c_task; //served customer number and customer task, respectively
		
		while(true) {
			try {
				SharedResources.cust_at_counter.acquire();
			} catch (InterruptedException e) {e.printStackTrace();}
			try {
				SharedResources.queue_mutex.acquire();				
			} catch (InterruptedException e) {e.printStackTrace();}
			served_c = SharedResources.offerList.removeLast(); //remove the customer number in a FIFO manner
			c_task = SharedResources.offerList.removeLast(); //remove customer task
			SharedResources.queue_mutex.release();
			try {
				serve_cust(served_c, c_task);
			} catch (InterruptedException e) {e.printStackTrace();}
			SharedResources.served[served_c].release(); //signal that customer has been served
			try {
				SharedResources.cust_left_counter.acquire(); //wait until customer has left counter
			} catch (InterruptedException e) {e.printStackTrace();}
			SharedResources.post_worker.release(); //make post worker available for next customer
		}
	}
	
}
