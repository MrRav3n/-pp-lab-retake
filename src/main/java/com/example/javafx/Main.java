package com.example.javafx;

import com.example.javafx.retake.Book;
import com.example.javafx.retake.BookManager;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    private static BookManager bookManager = new BookManager();
    private static ObservableList<Book> bookList;

    public static void main(String[] args) {
        bookList = FXCollections.observableArrayList(bookManager.getBooks());
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Book Manager");

        // Tabela książek
        TableView<Book> tableView = new TableView<>(bookList);
        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTitle()));
        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getAuthor()));
        TableColumn<Book, String> isbnColumn = new TableColumn<>("ISBN");
        isbnColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getIsbn()));
        TableColumn<Book, Integer> yearColumn = new TableColumn<>("Year");
        yearColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getYear()).asObject());
        tableView.getColumns().addAll(titleColumn, authorColumn, isbnColumn, yearColumn);

        // Formularz dodawania książki
        TextField titleField = new TextField();
        titleField.setPromptText("Title");
        TextField authorField = new TextField();
        authorField.setPromptText("Author");
        TextField isbnField = new TextField();
        isbnField.setPromptText("ISBN");
        TextField yearField = new TextField();
        yearField.setPromptText("Year");
        Button addButton = new Button("Add Book");
        addButton.setOnAction(e -> {
            String title = titleField.getText();
            String author = authorField.getText();
            String isbn = isbnField.getText();
            int year = Integer.parseInt(yearField.getText());
            Book newBook = new Book(title, author, isbn, year);
            bookManager.addBook(newBook);
            bookList.setAll(bookManager.getBooks());
            titleField.clear();
            authorField.clear();
            isbnField.clear();
            yearField.clear();
        });


        TextField removeIsbnField = new TextField();
        removeIsbnField.setPromptText("ISBN to remove");
        Button removeButton = new Button("Remove Book");
        removeButton.setOnAction(e -> {
            String isbn = removeIsbnField.getText();
            Book bookToRemove = findBookByIsbn(isbn);
            if (bookToRemove != null) {
                bookManager.removeBook(bookToRemove);
                bookList.setAll(bookManager.getBooks());
                removeIsbnField.clear();
            } else {
                showAlert("Book not found", "No book with ISBN " + isbn + " found.");
            }
        });

        TextField updateIsbnField = new TextField();
        updateIsbnField.setPromptText("ISBN to update");
        TextField newTitleField = new TextField();
        newTitleField.setPromptText("New Title");
        TextField newAuthorField = new TextField();
        newAuthorField.setPromptText("New Author");
        TextField newYearField = new TextField();
        newYearField.setPromptText("New Year");
        Button updateButton = new Button("Update Book");
        updateButton.setOnAction(e -> {
            String isbn = updateIsbnField.getText();
            Book oldBook = findBookByIsbn(isbn);
            if (oldBook != null) {
                String newTitle = newTitleField.getText();
                String newAuthor = newAuthorField.getText();
                int newYear = Integer.parseInt(newYearField.getText());
                Book newBook = new Book(newTitle, newAuthor, oldBook.getIsbn(), newYear);
                bookManager.updateBook(oldBook, newBook);
                bookList.setAll(bookManager.getBooks());
                updateIsbnField.clear();
                newTitleField.clear();
                newAuthorField.clear();
                newYearField.clear();
            } else {
                showAlert("Book not found", "No book with ISBN " + isbn + " found.");
            }
        });

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(5);
        vbox.getChildren().addAll(tableView, new Label("Add Book"), titleField, authorField, isbnField, yearField, addButton,
                new Label("Remove Book"), removeIsbnField, removeButton,
                new Label("Update Book"), updateIsbnField, newTitleField, newAuthorField, newYearField, updateButton);

        Scene scene = new Scene(vbox, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static Book findBookByIsbn(String isbn) {
        for (Book book : bookManager.getBooks()) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }

    private static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
