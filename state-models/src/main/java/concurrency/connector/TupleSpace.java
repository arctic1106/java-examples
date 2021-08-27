package concurrency.connector;

public interface TupleSpace {

	// deposits data in tuple space
	void out(String tag, Object data);

	// extracts object with tag from tuple space, blocks if not available
	Object in(String tag) throws InterruptedException;

	// reads object with tag from tuple space, blocks if not available
	Object rd(String tag) throws InterruptedException;


	// extracts object if available, return null if not available
	Object inp(String tag);

	//reads object if available, return null if not available
	Object rdp(String tag);

}