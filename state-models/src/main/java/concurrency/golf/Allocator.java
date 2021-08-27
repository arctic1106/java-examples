package concurrency.golf;

public interface Allocator {
	void get(int n) throws InterruptedException;

	void put(int n);
}