package postoffice;

import java.util.concurrent.Semaphore;
import java.util.LinkedList;
/**Stores shared resources that are used in the entire program <br>
 * Resources include: <br>
 * - Semaphores <br>
 * - Queue for exchanging data between customer and worker*/
class SharedResources {
	public static Semaphore max_capacity = new Semaphore(10, true); //max capacity of post office
	public static Semaphore post_worker = new Semaphore(3, true); //so customers can wait on the next available worker
	public static Semaphore[] served = new Semaphore[50];
	public static Semaphore scale = new Semaphore(1, true); //allows first post worker to use scale. Mutual Exclusion of scale usage
	public static Semaphore count_mutex = new Semaphore(1, true);
	public static Semaphore queue_mutex = new Semaphore(1, true);
	public static Semaphore cust_left_counter = new Semaphore(0, true);
	public static Semaphore cust_at_counter = new Semaphore(0, true);
	
	public static LinkedList<Integer> offerList = new LinkedList<Integer>(); //for passing information between customer and post worker
}
