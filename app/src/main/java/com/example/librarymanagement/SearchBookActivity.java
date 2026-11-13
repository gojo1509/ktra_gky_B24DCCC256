package com.example.librarymanagement;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.librarymanagement.adapter.BookAdapter;
import com.example.librarymanagement.database.DatabaseHelper;
import com.example.librarymanagement.model.Book;
import java.util.List;

public class SearchBookActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private EditText edtKeyword;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_book);

        dbHelper = new DatabaseHelper(this);
        edtKeyword = findViewById(R.id.edtSearchKeyword);
        Button btnSearch = findViewById(R.id.btnSearch);
        listView = findViewById(R.id.listViewSearchResult);

        btnSearch.setOnClickListener(v -> {
            String keyword = edtKeyword.getText().toString().trim();
            List<Book> result = dbHelper.searchBooks(keyword);
            BookAdapter adapter = new BookAdapter(this, result);
            listView.setAdapter(adapter);
        });
    }
}