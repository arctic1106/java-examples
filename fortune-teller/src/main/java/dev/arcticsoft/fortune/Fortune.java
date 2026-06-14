package dev.arcticsoft.fortune;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Fortune {
    private final ArrayList<String> fortunes = new ArrayList<>();

    public Fortune() throws IOException {
        loadJsonFortunes();
    }

    private void loadJsonFortunes() throws IOException {
        var inputStream = getClass().getClassLoader().getResourceAsStream("fortunes.json");
        new ObjectMapper().readTree(inputStream).get("data").elements()
                .forEachRemaining(node -> fortunes.add(node.get("quote").asText()));
    }

    public void printRandomFortune() throws InterruptedException {
        printWithTickerEffect(pickRandomFortune(), 100);
    }

    private String pickRandomFortune() {
        return fortunes.get(new Random().nextInt(fortunes.size()));
    }

    private void printWithTickerEffect(String fortune, int delayMillis) throws InterruptedException {
        for (var c : fortune.toCharArray()) {
            System.out.print(c);
            System.out.flush();
            Thread.sleep(delayMillis);
        }
        System.out.println();
    }
}