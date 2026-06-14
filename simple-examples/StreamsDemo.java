record Car(int id, String marca, String modelo, LocalDate lanzamiento) {
}

void main() {
    var coche = new Car(1, "Ford", "Mustang", LocalDate.of(1980, 5, 19));
    var coche2 = new Car(2, "WV", "Golf", LocalDate.of(1988, 9, 19));
    var coche3 = new Car(3, "WV", "Golf GTI", LocalDate.of(1987, 9, 19));
    var carList = List.of(coche, coche2, coche3);
    carList = carList.stream().filter(x -> x.marca().equals("WV")).toList();

    Integer[] lista1 = { 1, 3, 5, 4, 2 };
    var l1 = Stream.of(lista1).map(x -> x * 2).sorted().filter(x -> x > 4).toList();

    var l2 = List.of(1, 2, 3, 4, 5);
    l2 = l2.stream().map(x -> x * 2).toList();

    IO.println(carList);
    IO.println(l1);
    IO.println(l2);
}
