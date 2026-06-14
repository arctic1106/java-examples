import java.util.ArrayList;
import java.util.List;

public final class Knapsack {

	public static List<Item> knapsack(List<Item> items, int maxCapacity) {
		var table = new double[items.size() + 1][maxCapacity + 1];
		for (var i = 0; i < items.size(); i++) {
			var item = items.get(i);
			for (var capacity = 1; capacity <= maxCapacity; capacity++) {
				var prevItemValue = table[i][capacity];
				if (capacity >= item.weight) { // item fits in knapsack
					var valueFreeingWeightForItem = table[i][capacity - item.weight];
					table[i + 1][capacity] = Math.max(valueFreeingWeightForItem + item.value, prevItemValue);
				} else {
					table[i + 1][capacity] = prevItemValue;
				}
			}
		}
		var solution = new ArrayList<Item>();
		var capacity = maxCapacity;
		for (int i = items.size(); i > 0; i--) {
			if (table[i - 1][capacity] != table[i][capacity]) {
				solution.add(items.get(i - 1));
				capacity -= items.get(i - 1).weight;
			}
		}
		return solution;
	}

	public static void main(String[] args) {
		var items = new ArrayList<Item>();
		items.add(new Item("television", 50, 500));
		items.add(new Item("candlesticks", 2, 300));
		items.add(new Item("stereo", 35, 400));
		items.add(new Item("laptop", 3, 1000));
		items.add(new Item("food", 15, 50));
		items.add(new Item("clothing", 20, 800));
		items.add(new Item("jewelry", 1, 4000));
		items.add(new Item("books", 100, 300));
		items.add(new Item("printer", 18, 30));
		items.add(new Item("refrigerator", 200, 700));
		items.add(new Item("painting", 10, 1000));
		var toSteal = knapsack(items, 75);
		System.out.println("The best items for the thief to steal are:");
		System.out.printf("%-15.15s %-15.15s %-15.15s%n", "Name", "Weight", "Value");
		for (var item : toSteal) {
			System.out.printf("%-15.15s %-15.15s %-15.15s%n", item.name, item.weight, item.value);
		}
	}

	public static final class Item {
		public final String name;
		public final int weight;
		public final double value;

		public Item(String name, int weight, double value) {
			this.name = name;
			this.weight = weight;
			this.value = value;
		}
	}
}
