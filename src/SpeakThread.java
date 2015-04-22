import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;


public class SpeakThread implements Runnable {

	//private Vector<String> messageQueue = new Vector<String>();
	private ArrayBlockingQueue<String> messageQueue = new ArrayBlockingQueue<String>(20);
	
	public SpeakThread() {
		
	}
	
	@Override
	public void run() {
		while (true) {
			
		}
	}
	
	public synchronized void queueMessage(String s) throws InterruptedException {
		messageQueue.put(s);
	}

}
