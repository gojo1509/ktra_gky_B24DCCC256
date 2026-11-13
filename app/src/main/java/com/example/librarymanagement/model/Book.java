package com.example.librarymanagement.model;

public class Book {
    private int id;
    private String bookCode;
    private String title;
    private String author;
    private String category;
    private String status;
    public Book(int id, String bookCode, String title, String author, String category, String status) {
        this.id = id;
        this.bookCode = bookCode;
        this.title = title;
        this.author = author;
        this.category = category;
        this.status = status;
    }

    public int getId() { return id; }
    public String getBookCode() { return bookCode; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getCategory() { return category; }
    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
}