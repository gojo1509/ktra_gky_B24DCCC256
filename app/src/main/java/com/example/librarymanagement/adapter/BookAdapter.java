package com.example.librarymanagement.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.librarymanagement.R;
import com.example.librarymanagement.model.Book;
import java.util.List;

public class BookAdapter extends BaseAdapter {
    private Context context;
    private List<Book> list;

    public BookAdapter(Context context, List<Book> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_book, parent, false);
        }
        Book book = list.get(position);
        TextView tvInfo = convertView.findViewById(R.id.tvBookInfo);
        tvInfo.setText("Mã: " + book.getBookCode() + "\nTên: " + book.getTitle() +
                "\nTác giả: " + book.getAuthor() + "\nThể loại: " + book.getCategory() +
                "\nTrạng thái: " + book.getStatus());
        return convertView;
    }
}