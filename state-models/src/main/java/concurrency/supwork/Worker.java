package concurrency.supwork;

import concurrency.connector.TupleSpace;

public class Worker extends Thread {
	WorkerCanvas display;
	Function func;
	TupleSpace bag;
	int processingTime = (int) (6000 * Math.random());

	public Worker(WorkerCanvas d, TupleSpace b, Function f) {
		display = d;
		bag = b;
		func = f;
	}

	public void run() {
		double deltaX = 1.0 / SupervisorCanvas.N_SLICE;
		try {
			while (true) {
				// get new task from tuple space
				Integer task = (Integer) bag.in("task");
				int slice = task;
				if (slice < 0) {  // stop if negative
					bag.out("task", task);
					break;
				}
				display.setTask(slice);
				sleep(processingTime);
				double area = deltaX * func.fn(deltaX * slice + deltaX / 2);
				// output result to tuple space
				bag.out("result", new Function.Result(slice, area));
			}
		} catch (InterruptedException e) {
		}
	}
}