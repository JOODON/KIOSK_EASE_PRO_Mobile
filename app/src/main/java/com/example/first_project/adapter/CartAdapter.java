package com.example.first_project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.first_project.R;
import com.example.first_project.entity.Menu;

import java.util.List;

public class CartAdapter extends BaseAdapter {
    private List<Menu> menuList;
    private Context context;

    public CartAdapter(Context context, List<Menu> menuList) {
        this.context = context;
        this.menuList = menuList;
    }

    @Override
    public int getCount() {
        return menuList.size();
    }

    @Override
    public Object getItem(int position) {
        return menuList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        }

        Menu menu = (Menu) getItem(position);

        TextView menuNameTextView = convertView.findViewById(R.id.menuNameTextView);
        TextView menuCountTextView = convertView.findViewById(R.id.menuCountTextView);
        TextView menuPriceTextView = convertView.findViewById(R.id.menuPriceTextView);

        menuNameTextView.setText(menu.getName());
        menuCountTextView.setText(context.getString(R.string.quantity, menu.getQuantity()));
        menuPriceTextView.setText(context.getString(R.string.price, String.valueOf(menu.getPrice())));

        return convertView;
    }
}
