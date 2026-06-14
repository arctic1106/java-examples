class LecturaAdapter {

    static Lectura fromTokens(String[] tokens) {
        return new Lectura(
                java.time.LocalDate.now(),
                (int) Double.parseDouble(tokens[1]),
                (int) Double.parseDouble(tokens[2]),
                Double.parseDouble(tokens[3]));
    }
}
