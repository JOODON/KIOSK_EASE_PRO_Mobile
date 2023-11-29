package com.example.first_project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.first_project.R;
import com.example.first_project.entity.Menu;


import java.util.List;

public class MenuAdapter extends BaseAdapter {

    private Context context;
    private List<Menu> menuList;
    private OnItemClickListener onItemClickListener;

    public MenuAdapter(Context context, List<Menu> menuList) {
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

    public interface OnItemClickListener {
        void onItemClick(Menu menu);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.menu_item, null);

            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Menu menu = menuList.get(position);
        viewHolder.nameTextView.setText(menu.getName());
        viewHolder.priceTextView.setText(String.valueOf(menu.getPrice()));
        setImageByCategory(viewHolder, menu.getCategory());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(menuList.get(position));
                }
            }
        });

        return view;
    }
    private void setImageByCategory(ViewHolder viewHolder, String category) {

        int imageResId;

        viewHolder.imageView.setMaxHeight(15);
        viewHolder.imageView.setMaxHeight(15);

        // 카테고리에 따라 이미지 리소스 설정
        switch (category) {
            case "음식":
                imageResId = R.drawable.food;
                break;
            case "음료":
                imageResId = R.drawable.drink;
                break;
            case "디저트":
                imageResId = R.drawable.dessert;
                break;
            default:
                // 기본 이미지 설정
                imageResId = R.drawable.default_image;
                break;
        }

        viewHolder.imageView.setImageResource(imageResId);
    }
    private static class ViewHolder {
        ImageView imageView;
        TextView nameTextView;
        TextView priceTextView;
        View view; // 이 줄을 추가합니다

        ViewHolder(View view) {
            this.view = view; // 이 줄을 추가합니다
            imageView = view.findViewById(R.id.menu_item_imageView);
            nameTextView = view.findViewById(R.id.menu_item_nameTextView);
            priceTextView = view.findViewById(R.id.menu_item_priceTextView);
        }
    }
}
