package com.pganin.barcodescaner;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StringAdapter extends ArrayAdapter<Product> {
    private List<Product> data = new ArrayList<Product>();

    public StringAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_1, new Product[0]);
    }

    public void add(Collection<Product> data) {
        this.data.addAll(data);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public int getPosition(Product item) {
        return data.indexOf(item);
    }

    @Override
    public Product getItem(int position) {
        return data.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView view = (TextView) super.getView(position, convertView, parent);
        String text = data.get(position).getName()+
                "\n Закуп: "+data.get(position).getValueBuy()+
                "\n Опт: "+data.get(position).getValueOpt()+
                "\n Розница: "+data.get(position).getValueSale();
        view.setText(text);
        view.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_launcher_foreground, 0, 0, 0);
        return view;
    }
}
