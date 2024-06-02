import retake.Book;
import retake.BookManager;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static BookManager bookManager = new BookManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            printMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    removeBook();
                    break;
                case 3:
                    updateBook();
                    break;
                case 4:
                    listBooks();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("Menu:");
        System.out.println("[1] Add book");
        System.out.println("[2] Remove book");
        System.out.println("[3] Update book");
        System.out.println("[4] List books");
        System.out.println("[5] Exit");
        System.out.print("Choose an option: ");
    }

    private static void addBook() {
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Enter year: ");
        int year = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        Book book = new Book(title, author, isbn, year);
        bookManager.addBook(book);
        System.out.println("Book added successfully.");
    }

    private static void removeBook() {
        System.out.print("Enter ISBN of the book to remove: ");
        String isbn = scanner.nextLine();
        Book bookToRemove = findBookByIsbn(isbn);
        if (bookToRemove != null) {
            bookManager.removeBook(bookToRemove);
            System.out.println("Book removed successfully.");
        } else {
            System.out.println("Book not found.");
        }
    }

    private static void updateBook() {
        System.out.print("Enter ISBN of the book to update: ");
        String isbn = scanner.nextLine();
        Book oldBook = findBookByIsbn(isbn);
        if (oldBook != null) {
            System.out.print("Enter new title: ");
            String newTitle = scanner.nextLine();
            System.out.print("Enter new author: ");
            String newAuthor = scanner.nextLine();
            System.out.print("Enter new year: ");
            int newYear = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            Book newBook = new Book(newTitle, newAuthor, oldBook.getIsbn(), newYear);
            bookManager.updateBook(oldBook, newBook);
            System.out.println("Book updated successfully.");
        } else {
            System.out.println("Book not found.");
        }
    }

    private static void listBooks() {
        List<Book> books = bookManager.getBooks();
        for (Book book : books) {
            System.out.println(book);
        }
    }

    private static Book findBookByIsbn(String isbn) {
        for (Book book : bookManager.getBooks()) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }
}
