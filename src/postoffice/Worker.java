package postoffice;

import java.util.concurrent.Semaphore;

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
		
		
	}
	
}
