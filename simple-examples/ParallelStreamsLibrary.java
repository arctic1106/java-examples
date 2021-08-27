record Book(String author, String title) {
}

private static List<Book> bookCollection = List.of(
        new Book("Alice Walker", "The Color Purple"),
        new Book("Alice Walker", "Meridian"),
        new Book("Toni Morrison", "Jazz"),
        new Book("Toni Morrison", "Paradise"),
        new Book("John Steinbeck", "East of Eden"),
        new Book("Kazuo Ishiguro", "The Remains of the Day"),
        new Book("Kazuo Ishiguro", "Never Let Me Go"));

void main(String[] args) {
    bookCollection.parallelStream()
            .filter(book -> book.author().startsWith("T"))
            .forEach(System.out::println);
}
