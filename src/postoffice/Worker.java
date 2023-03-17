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

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
}
