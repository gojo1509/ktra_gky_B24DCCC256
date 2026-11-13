package com.example.librarymanagement;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.librarymanagement.database.DatabaseHelper;
import com.example.librarymanagement.model.Book;
import java.util.ArrayList;
import java.util.List;

public class AddBorrowActivity extends AppCompatActivity {
    private Spinner spinnerBooks;
    private EditText edtName, edtPhone, edtBorrowDate, edtDueDate;
    private DatabaseHelper dbHelper;
    private List<Book> availableBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_borrow);

        dbHelper = new DatabaseHelper(this);
        spinnerBooks = findViewById(R.id.spinnerBooks);
        edtName = findViewById(R.id.edtBorrowerName);
        edtPhone = findViewById(R.id.edtBorrowerPhone);
        edtBorrowDate = findViewById(R.id.edtBorrowDate);
        edtDueDate = findViewById(R.id.edtDueDate);
        Button btnSave = findViewById(R.id.btnSaveBorrow);

        List<Book> allBooks = dbHelper.getAllBooks();
        availableBooks = new ArrayList<>();
        List<String> bookTitles = new ArrayList<>();
        for (Book b : allBooks) {
            if (b.getStatus().equals("Có sẵn")) {
                availableBooks.add(b);
                bookTitles.add(b.getTitle() + " (" + b.getBookCode() + ")");
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, bookTitles);
        spinnerBooks.setAdapter(adapter);

        btnSave.setOnClickListener(v -> {
            if (availableBooks.isEmpty()) {
                Toast.makeText(this, "Không có sách nào có sẵn để mượn!", Toast.LENGTH_SHORT).show();
                return;
            }

            String name = edtName.getText().toString().trim();
            String phone = edtPhone.getText().toString().trim();
            String borrowDate = edtBorrowDate.getText().toString().trim();
            String dueDate = edtDueDate.getText().toString().trim();

            if (name.isEmpty() || phone.isEmpty() || borrowDate.isEmpty() || dueDate.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            int selectedIndex = spinnerBooks.getSelectedItemPosition();
            Book selectedBook = availableBooks.get(selectedIndex);

            boolean success = dbHelper.addBorrowRecord(selectedBook.getId(), name, phone, borrowDate, dueDate);
            if (success) {
                Toast.makeText(this, "Lập phiếu mượn thành công", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Lỗi khi lập phiếu mượn", Toast.LENGTH_SHORT).show();
            }
        });
    }
}