package com.example.librarymanagement.model;

public class BorrowRecord {
    private int id;
    private int bookId;
    private String bookTitle;
    private String borrowerName;
    private String phoneNumber;
    private String borrowDate;
    private String dueDate;

    public BorrowRecord(int id, int bookId, String bookTitle, String borrowerName, String phoneNumber, String borrowDate, String dueDate) {
        this.id = id;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.borrowerName = borrowerName;
        this.phoneNumber = phoneNumber;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
    }

    public int getId() { return id; }
    public int getBookId() { return bookId; }
    public String getBookTitle() { return bookTitle; }
    public String getBorrowerName() { return borrowerName; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getBorrowDate() { return borrowDate; }
    public String getDueDate() { return dueDate; }
}