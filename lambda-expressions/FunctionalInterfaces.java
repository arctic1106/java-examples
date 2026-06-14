import java.util.function.Function;

public class FunctionalInterfaces {

	public static void main(String[] args) throws Exception {

		// 1 - Lambda expression
		Runnable r1 = () -> System.out.println("Hello from runnable 1");
		var thread = new Thread(r1);
		thread.start();
		thread.join();

		// 2 - Anonymous class
		Runnable r2 = new Runnable() {
			@Override
			public void run() {
				System.out.println("Hello from runnable 2");
			}
		};
		thread = new Thread(r2);
		thread.start();
		thread.join();

		// 3 - Using an interface implementation class
		class RunnableImpl implements Runnable {
			@Override
			public void run() {
				System.out.println("Hello from runnable 3");
			}
		}
		Runnable r3 = new RunnableImpl();
		thread = new Thread(r3);
		thread.start();
		thread.join();

		// 4 - Function interface with lambda expression
		Function<String, String> f1 = s -> "Hello from function1: lengh = " + s.length();
		System.out.println(f1.apply("123"));

		// 5 - Function interface with anonymous class
		Function<String, String> f2 = new Function<>() {
			@Override
			public String apply(String s) {
				return "Hello from function1: lengh = " + s.length();
			}
		};
		System.out.println(f2.apply("1234"));
	}
}