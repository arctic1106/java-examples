import java.util.function.Function;

import models.Meteo;

public class PlayWithFunctions {

	public static void main(String[] args) {
		var meteo = new Meteo(10);
		Function<Meteo, Integer> readCelsius = Meteo::getTemperature;
		Function<Integer, Double> celsiusToFahrenheit = t -> t * 9d / 5d + 32d;

		var readFahrenheit = readCelsius.andThen(celsiusToFahrenheit);
		System.out.println("Meteo is F " + readFahrenheit.apply(meteo));

		var readFahrenheit2 = celsiusToFahrenheit.compose(readCelsius);
		System.out.println("Meteo is F " + readFahrenheit2.apply(meteo));
	}
}