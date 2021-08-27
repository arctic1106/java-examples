package concurrency.primes;

public interface Pipe<T> {

	void put(T o) throws InterruptedException; //put object into buffer

	T get() throws InterruptedException;//get an object from buffer
}