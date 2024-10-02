package com.lph.selfcareapp.menu.account;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lph.selfcareapp.R;

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private String[] itemNames;
    private int[] itemIcons;
    private LayoutInflater inflater;

    public CustomAdapter(Context context, String[] itemNames, int[] itemIcons) {
        this.context = context;
        this.itemNames = itemNames;
        this.itemIcons = itemIcons;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return itemNames.length;
    }

    @Override
    public Object getItem(int position) {
        return itemNames[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }

        ImageView icon = convertView.findViewById(R.id.item_icon);
        TextView text = convertView.findViewById(R.id.item_text);

        icon.setImageResource(itemIcons[position]);
        text.setText(itemNames[position]);

        return convertView;
    }
}
