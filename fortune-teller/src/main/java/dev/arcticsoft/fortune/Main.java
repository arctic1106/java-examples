package dev.arcticsoft.fortune;

import java.io.IOException;

public class Main {
    
    public static void main(String[] args) throws IOException, InterruptedException {
        new Fortune().printRandomFortune();
    }
}