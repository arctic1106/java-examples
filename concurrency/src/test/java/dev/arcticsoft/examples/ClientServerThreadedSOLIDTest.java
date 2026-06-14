package dev.arcticsoft.examples;

import java.io.IOException;
import java.util.Arrays;


public class ClientServerThreadedSOLIDTest {
	private static final int PORT = 8010;
	private static final int TIMEOUT = 2000;
	private ServerThreadedSOLID serverThreadedSOLID;
	private Thread serverThread;

	public static Iterable<? extends Object> data() {
		return Arrays.asList(new ExecutorClientScheduler(5));
	}


	public void createServer() throws Exception {
		try {
			var clientScheduler = new ExecutorClientScheduler(5);
			this.serverThreadedSOLID = new ServerThreadedSOLID(new ConnectionManager(PORT, TIMEOUT), clientScheduler);
            this.serverThread = new Thread(this.serverThreadedSOLID);
            this.serverThread.start();
        } catch (final IOException e) {
            e.printStackTrace(System.err);
            throw e;
        }
	}

	public void shutdownServer() throws InterruptedException {
		if (this.serverThreadedSOLID != null) {
			this.serverThreadedSOLID.stopProcessing();
			this.serverThread.join();
		}
	}

	public void shouldRunInUnder10Seconds() throws Exception {
		final Thread[] threads = new Thread[10];
		for (int i = 0; i < 10; i++) {
			threads[i] = new Thread(new TrivialClient(i, PORT));
			threads[i].start();
		}

		for (int i = 0; i < 10; i++) {
			threads[i].join();
		}
	}
}
