package com.example.librarymanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.librarymanagement.R;
import com.example.librarymanagement.model.BorrowRecord;
import java.util.List;

public class BorrowAdapter extends BaseAdapter {
    private Context context;
    private List<BorrowRecord> list;

    public BorrowAdapter(Context context, List<BorrowRecord> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() { return list.size(); }
    @Override
    public Object getItem(int position) { return list.get(position); }
    @Override
    public long getItemId(int position) { return list.get(position).getId(); }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_borrow, parent, false);
        }
        BorrowRecord record = list.get(position);
        TextView tvInfo = convertView.findViewById(R.id.tvBorrowInfo);
        tvInfo.setText("Sách: " + record.getBookTitle() + "\nNgười mượn: " + record.getBorrowerName() +
                "\nSĐT: " + record.getPhoneNumber() + "\nNgày mượn: " + record.getBorrowDate() +
                "\nHạn trả: " + record.getDueDate());
        return convertView;
    }
}