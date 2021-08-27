package concurrency.supwork;

public interface Function {
	double fn(double x);

	class Result {
		int task;
		double area;

		Result(int s, double a) {
			task = s;
			area = a;
		}
	}
}

