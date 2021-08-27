package concurrency.message;

import java.util.ArrayList;
import java.util.List;

class Select {
	List<Selectable> list = new ArrayList<>(2);

	public void add(Selectable s) {
		list.add(s);
		s.setSelect(this);
	}

	private void clearAll() {
		for (var s : list) {
			s.clearOpen();
		}
	}

	private void openAll() {
		for (var s : list) {
			if (s.testGuard()) s.setOpen();
		}
	}

	private int testAll() {
		int i = 1;
		for (var s : list) {
			if (s.testReady() && s.testGuard()) return i;
			++i;
		}
		return 0;
	}

	public synchronized int choose() throws InterruptedException {
		int readyIndex = 0;
		while (readyIndex == 0) {
			readyIndex = testAll();
			if (readyIndex == 0) {
				openAll();
				wait();
				clearAll();
			}
		}
		return readyIndex;
	}
}