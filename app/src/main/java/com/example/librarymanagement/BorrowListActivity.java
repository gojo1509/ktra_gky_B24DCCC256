package com.example.librarymanagement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.librarymanagement.adapter.BorrowAdapter;
import com.example.librarymanagement.database.DatabaseHelper;
import com.example.librarymanagement.model.BorrowRecord;
import java.util.List;

public class BorrowListActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private ListView listView;
    private BorrowAdapter adapter;
    private List<BorrowRecord> borrowList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_list);

        dbHelper = new DatabaseHelper(this);
        listView = findViewById(R.id.listViewBorrow);
        Button btnAddBorrow = findViewById(R.id.btnAddBorrow);

        btnAddBorrow.setOnClickListener(v -> startActivity(new Intent(this, AddBorrowActivity.class)));

        listView.setOnItemClickListener((parent, view, position, id) -> {
            BorrowRecord record = borrowList.get(position);
            new AlertDialog.Builder(this)
                    .setTitle("Xác nhận trả sách")
                    .setMessage("Bạn có muốn thực hiện trả cuốn sách này?")
                    .setPositiveButton("Trả sách", (dialog, which) -> {
                        dbHelper.returnBook(record.getId(), record.getBookId());
                        loadData();
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        borrowList = dbHelper.getAllBorrowRecords();
        adapter = new BorrowAdapter(this, borrowList);
        listView.setAdapter(adapter);
    }
}