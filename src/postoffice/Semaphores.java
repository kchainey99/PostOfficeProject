package postoffice;

import java.util.concurrent.Semaphore;
/**Stores semaphores*/
class Semaphores {
	public static Semaphore max_capacity = new Semaphore(10, true); //max capacity of post office
	public static Semaphore[] served = new Semaphore[50];
	public static Semaphore scale = new Semaphore(1, true); //allows first post worker to use scale. Mutual Exclusion of scale usage
	public static Semaphore count_mutex = new Semaphore(1, true);
	public static Semaphore queue_mutex = new Semaphore(1, true);
	public static Semaphore cust_left_counter = new Semaphore(0, true);
	public static Semaphore cust_at_counter = new Semaphore(0, true);
}
