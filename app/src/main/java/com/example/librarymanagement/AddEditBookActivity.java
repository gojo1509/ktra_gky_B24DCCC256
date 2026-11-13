package com.example.librarymanagement;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.librarymanagement.database.DatabaseHelper;

public class AddEditBookActivity extends AppCompatActivity {
    private EditText edtCode, edtTitle, edtAuthor, edtCategory;
    private DatabaseHelper dbHelper;
    private int bookId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_book);

        dbHelper = new DatabaseHelper(this);
        edtCode = findViewById(R.id.edtBookCode);
        edtTitle = findViewById(R.id.edtBookTitle);
        edtAuthor = findViewById(R.id.edtBookAuthor);
        edtCategory = findViewById(R.id.edtBookCategory);
        Button btnSave = findViewById(R.id.btnSaveBook);

        if (getIntent().hasExtra("BOOK_ID")) {
            bookId = getIntent().getIntExtra("BOOK_ID", -1);
            edtCode.setText(getIntent().getStringExtra("BOOK_CODE"));
            edtCode.setEnabled(false);
            edtTitle.setText(getIntent().getStringExtra("TITLE"));
            edtAuthor.setText(getIntent().getStringExtra("AUTHOR"));
            edtCategory.setText(getIntent().getStringExtra("CATEGORY"));
        }

        btnSave.setOnClickListener(v -> {
            String code = edtCode.getText().toString().trim();
            String title = edtTitle.getText().toString().trim();
            String author = edtAuthor.getText().toString().trim();
            String category = edtCategory.getText().toString().trim();

            if (code.isEmpty() || title.isEmpty() || author.isEmpty() || category.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (bookId == -1) {
                boolean success = dbHelper.addBook(code, title, author, category);
                if (success) {
                    Toast.makeText(this, "Thêm sách thành công", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Mã sách bị trùng!", Toast.LENGTH_SHORT).show();
                }
            } else {
                boolean success = dbHelper.updateBook(bookId, title, author, category);
                if (success) {
                    Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}