package concurrency.supwork;

import concurrency.connector.TupleSpace;

public class Supervisor extends Thread {
	SupervisorCanvas display;
	TupleSpace bag;
	Integer stop = -1;

	public Supervisor(SupervisorCanvas d, TupleSpace b) {
		display = d;
		bag = b;
	}

	public void run() {
		try {
			// output tasks to tuplespace
			for (int i = 0; i < SupervisorCanvas.N_SLICE; ++i)
				bag.out("task", i);
			// collect results
			for (int i = 0; i < SupervisorCanvas.N_SLICE; ++i) {
				Function.Result r = (Function.Result) bag.in("result");
				display.setSlice(r.task, r.area);
			}
			// output stop tuple
			bag.out("task", stop);
		} catch (InterruptedException e) {
		}
	}
}





