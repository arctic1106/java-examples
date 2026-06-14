void main() {
    var books = populateLibrary();
    books.stream()
            .filter(book -> book.author().startsWith("J"))
            .filter(book -> book.title().startsWith("E"))
            .forEach(System.out::println);
}

static ArrayList<Book> populateLibrary() {
    var books = new ArrayList<Book>();
    books.add(new Book("Alice Walker", "The Color Purple"));
    books.add(new Book("Alice Walker", "Meridian"));
    books.add(new Book("Toni Morrison", "Beloved"));
    books.add(new Book("Toni Morrison", "Jazz"));
    books.add(new Book("Toni Morrison", "Paradise"));
    books.add(new Book("John Steinbeck", "The Grapes of Wrath"));
    books.add(new Book("John Steinbeck", "East of Eden"));
    books.add(new Book("Kazuo Ishiguro", "The Remains of the Day"));
    books.add(new Book("Kazuo Ishiguro", "Never Let Me Go"));
    books.add(new Book("Kazuo Ishiguro", "The Buried Giant"));
    books.add(new Book("Jane Austen", "Pride and Prejudice"));
    books.add(new Book("Jane Austen", "Emma"));
    books.add(new Book("Jane Austen", "Persuasion"));
    books.add(new Book("Jane Austen", "Mansfield Park"));
    books.add(new Book("Chinua Achebe", "Things Fall Apart"));
    books.add(new Book("Chinua Achebe", "No Longer at Ease"));
    books.add(new Book("Chinua Achebe", "Home and Exile"));

    return books;
}

record Book(String author, String title) {
}
