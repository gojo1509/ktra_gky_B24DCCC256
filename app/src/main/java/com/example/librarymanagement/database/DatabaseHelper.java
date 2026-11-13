package com.example.librarymanagement.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.librarymanagement.model.Book;
import com.example.librarymanagement.model.BorrowRecord;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "library.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BOOKS_TABLE = "CREATE TABLE books (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "book_code TEXT UNIQUE, " +
                "title TEXT, " +
                "author TEXT, " +
                "category TEXT, " +
                "status TEXT)";
        db.execSQL(CREATE_BOOKS_TABLE);

        String CREATE_BORROW_TABLE = "CREATE TABLE borrow_records (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "book_id INTEGER, " +
                "borrower_name TEXT, " +
                "phone_number TEXT, " +
                "borrow_date TEXT, " +
                "due_date TEXT, " +
                "FOREIGN KEY(book_id) REFERENCES books(id))";
        db.execSQL(CREATE_BORROW_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS borrow_records");
        db.execSQL("DROP TABLE IF EXISTS books");
        onCreate(db);
    }

    public boolean addBook(String code, String title, String author, String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("book_code", code);
        values.put("title", title);
        values.put("author", author);
        values.put("category", category);
        values.put("status", "Có sẵn");
        long result = db.insert("books", null, values);
        return result != -1;
    }

    public boolean updateBook(int id, String title, String author, String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("author", author);
        values.put("category", category);
        int rows = db.update("books", values, "id = ?", new String[]{String.valueOf(id)});
        return rows > 0;
    }

    public boolean deleteBook(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete("books", "id = ?", new String[]{String.valueOf(id)});
        return rows > 0;
    }

    public List<Book> getAllBooks() {
        List<Book> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM books", null);
        if (cursor.moveToFirst()) {
            do {
                list.add(new Book(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4), cursor.getString(5)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public List<Book> searchBooks(String keyword) {
        List<Book> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM books WHERE title LIKE ? OR author LIKE ?",
                new String[]{"%" + keyword + "%", "%" + keyword + "%"});
        if (cursor.moveToFirst()) {
            do {
                list.add(new Book(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4), cursor.getString(5)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public boolean addBorrowRecord(int bookId, String name, String phone, String borrowDate, String dueDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put("book_id", bookId);
            values.put("borrower_name", name);
            values.put("phone_number", phone);
            values.put("borrow_date", borrowDate);
            values.put("due_date", dueDate);
            long res = db.insert("borrow_records", null, values);
            if (res == -1) return false;

            ContentValues statusValues = new ContentValues();
            statusValues.put("status", "Đang được mượn");
            db.update("books", statusValues, "id = ?", new String[]{String.valueOf(bookId)});

            db.setTransactionSuccessful();
            return true;
        } finally {
            db.endTransaction();
        }
    }

    public List<BorrowRecord> getAllBorrowRecords() {
        List<BorrowRecord> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT br.id, br.book_id, b.title, br.borrower_name, br.phone_number, br.borrow_date, br.due_date " +
                "FROM borrow_records br JOIN books b ON br.book_id = b.id";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                list.add(new BorrowRecord(cursor.getInt(0), cursor.getInt(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public boolean returnBook(int recordId, int bookId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete("borrow_records", "id = ?", new String[]{String.valueOf(recordId)});
            ContentValues values = new ContentValues();
            values.put("status", "Có sẵn");
            db.update("books", values, "id = ?", new String[]{String.valueOf(bookId)});
            db.setTransactionSuccessful();
            return true;
        } finally {
            db.endTransaction();
        }
    }
}