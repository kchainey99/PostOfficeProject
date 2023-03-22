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
			System.out.println("Customer " + c_num + " asked post worker " + worker_num + " to mail a package.");
			SharedResources.scale.acquire();
			Thread.sleep(2000);
			SharedResources.scale.release();
			break;
		}
	}
	
	/**Prints out [X] post worker serving [Y] customer
	 * @param c_num - customer number*/
	void printServingCustomer(int c_num) {
		System.out.println("Postal Worker " + worker_num + " is serving customer " + c_num);
	}
	
	/** Gets customer info from Linked List in a FIFO manner.
	 * 
	 * @return A vector of size 2. <br>
	 * - vector[0] = customer number <br>
	 * - vector[1] = task
	 */
	int[] getCustomerInfo() {
		int[] custinfo = new int[2];
		custinfo[0] = SharedResources.offerList.removeLast();
		custinfo[1] = SharedResources.offerList.removeLast();
		return custinfo;
	}

	@Override
	public void run() {
		int[] custInfo; //customer info vector. custInfo[0] = name, custInfo[1] = task
		
		while(true) {
			//wait until customer is at the counter
			try {
				SharedResources.cust_at_counter.acquire();
			} catch (InterruptedException e) {e.printStackTrace();}
			try {
				SharedResources.queue_mutex.acquire();				
			} catch (InterruptedException e) {e.printStackTrace();}
			custInfo = getCustomerInfo();
			int customerNum = custInfo[0];
			int customerTask = custInfo[1];
			SharedResources.queue_mutex.release();
			printServingCustomer(customerNum);
			try {
				serve_cust(customerNum, customerTask);
			} catch (InterruptedException e) {e.printStackTrace();}
			SharedResources.served[customerNum].release(); //signal that customer has been served
			SharedResources.post_worker.release(); //make post worker available for next customer
		}
	}
	
}
