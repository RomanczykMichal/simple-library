package com.example.Zadanie;

public class Book {
    public String isbn;
    public String title;
    public String autor;
    public String publisher;
    public int year;
    public float price;

    public Book(String isbn, String title, String autor, String publisher, int year, float price) {
        this.isbn = isbn;
        this.title = title;
        this.autor = autor;
        this.publisher = publisher;
        this.year = year;
        this.price = price;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAutor() {
        return autor;
    }

    public String getPublisher() {
        return publisher;
    }

    public int getYear() {
        return year;
    }

    public float getPrice() {
        return price;
    }
}
