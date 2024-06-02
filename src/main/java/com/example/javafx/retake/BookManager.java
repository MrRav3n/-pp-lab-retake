package com.example.javafx.retake;

import java.util.ArrayList;
import java.util.List;

public class BookManager implements BookOperations {
    private List<Book> books;

    public BookManager() {
        this.books = new ArrayList<>();
        // Dodanie początkowych książek
        books.add(new Book("Effective Java", "Joshua Bloch", "978-0134685991", 2017));
        books.add(new Book("Clean Code", "Robert C. Martin", "978-0132350884", 2008));
        books.add(new Book("Design Patterns", "Erich Gamma", "978-0201633610", 1994));
        books.add(new Book("Java Concurrency in Practice", "Brian Goetz", "978-0321349606", 2006));
        books.add(new Book("Head First Design Patterns", "Eric Freeman", "978-0596007126", 2004));

    }

    @Override
    public void addBook(Book book) {
        books.add(book);
    }

    @Override
    public void removeBook(Book book) {
        books.remove(book);
    }

    @Override
    public void updateBook(Book oldBook, Book newBook) {
        int index = books.indexOf(oldBook);
        if (index != -1) {
            books.set(index, newBook);
        }
    }

    @Override
    public List<Book> getBooks() {
        return books;
    }
}
