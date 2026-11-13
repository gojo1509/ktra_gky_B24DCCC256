package com.example.librarymanagement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnManageBooks = findViewById(R.id.btnManageBooks);
        Button btnManageBorrow = findViewById(R.id.btnManageBorrow);
        Button btnSearchBooks = findViewById(R.id.btnSearchBooks);

        btnManageBooks.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, BookListActivity.class)));
        btnManageBorrow.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, BorrowListActivity.class)));
        btnSearchBooks.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SearchBookActivity.class)));
    }
}