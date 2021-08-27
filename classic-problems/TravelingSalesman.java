import java.util.ArrayList;
import java.util.List;

public class TravelingSalesman {

	// Function to calculate the total distance of a given path
	public static int calculateDistance(int[][] distanceMatrix, List<Integer> path) {
		int totalDistance = 0;
		for (int i = 0; i < path.size(); i++) {
			int from = path.get(i);
			int to = path.get((i + 1) % path.size()); // Wrap around to the start
			totalDistance += distanceMatrix[from][to];
		}
		return totalDistance;
	}

	// Function to generate all permutations of the cities
	public static void generatePermutations(int[] cities, int start,
			List<List<Integer>> permutations) {
		if (start == cities.length - 1) {
			List<Integer> path = new ArrayList<>();
			for (int city : cities) {
				path.add(city);
			}
			permutations.add(path);
			return;
		}
		for (int i = start; i < cities.length; i++) {
			swap(cities, start, i);
			generatePermutations(cities, start + 1, permutations);
			swap(cities, start, i); // backtrack
		}
	}

	// Helper function to swap elements in an array
	private static void swap(int[] array, int i, int j) {
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}

	public static void main(String[] args) {
		// Distance matrix representing distances between cities
		int[][] distanceMatrix =
				{{0, 10, 15, 20}, {10, 0, 35, 25}, {15, 35, 0, 30}, {20, 25, 30, 0}};

		int numCities = distanceMatrix.length;
		int[] cities = new int[numCities];
		for (int i = 0; i < numCities; i++) {
			cities[i] = i; // Initialize city indices
		}

		List<List<Integer>> permutations = new ArrayList<>();
		generatePermutations(cities, 0, permutations);

		int minDistance = Integer.MAX_VALUE;
		List<Integer> bestPath = null;

		// Evaluate all permutations to find the shortest path
		for (List<Integer> path : permutations) {
			int distance = calculateDistance(distanceMatrix, path);
			if (distance < minDistance) {
				minDistance = distance;
				bestPath = path;
			}
		}

		// Output the best path and its distance
		System.out.println("Shortest path: " + bestPath);
		System.out.println("Minimum distance: " + minDistance);
	}
}
