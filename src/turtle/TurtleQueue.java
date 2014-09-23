package turtle;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import edu.hendrix.earley.*;

public class TurtleQueue {
	Queue<Tree> queue;
	ManageCanvas manager;
	
	public TurtleQueue(ManageCanvas mc) { 
		queue = new ArrayBlockingQueue<Tree>(1000000);
		manager = mc;
	}
}
