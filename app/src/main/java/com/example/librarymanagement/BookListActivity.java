package com.example.librarymanagement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.librarymanagement.adapter.BookAdapter;
import com.example.librarymanagement.database.DatabaseHelper;
import com.example.librarymanagement.model.Book;
import java.util.List;

public class BookListActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private ListView listView;
    private BookAdapter adapter;
    private List<Book> bookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        dbHelper = new DatabaseHelper(this);
        listView = findViewById(R.id.listViewBooks);
        Button btnAdd = findViewById(R.id.btnAddBook);

        btnAdd.setOnClickListener(v -> startActivity(new Intent(this, AddEditBookActivity.class)));

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Book book = bookList.get(position);
            CharSequence[] options = {"Sửa thông tin", "Xóa sách"};
            new AlertDialog.Builder(this)
                    .setTitle("Chọn thao tác")
                    .setItems(options, (dialog, which) -> {
                        if (which == 0) {
                            Intent intent = new Intent(this, AddEditBookActivity.class);
                            intent.putExtra("BOOK_ID", book.getId());
                            intent.putExtra("BOOK_CODE", book.getBookCode());
                            intent.putExtra("TITLE", book.getTitle());
                            intent.putExtra("AUTHOR", book.getAuthor());
                            intent.putExtra("CATEGORY", book.getCategory());
                            startActivity(intent);
                        } else {
                            if(book.getStatus().equals("Đang được mượn")) {
                                Toast.makeText(this, "Không thể xóa sách đang được mượn!", Toast.LENGTH_SHORT).show();
                            } else {
                                dbHelper.deleteBook(book.getId());
                                loadData();
                            }
                        }
                    }).show();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        bookList = dbHelper.getAllBooks();
        adapter = new BookAdapter(this, bookList);
        listView.setAdapter(adapter);
    }
}