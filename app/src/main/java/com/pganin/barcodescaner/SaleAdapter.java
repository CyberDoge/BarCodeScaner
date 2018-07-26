package com.pganin.barcodescaner;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SaleAdapter extends ArrayAdapter<Product> {
    private List<Product> data = new ArrayList<Product>();

    public SaleAdapter(Context context) {
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
        //ScrollView scrollView = (ScrollView) super.getView(position, convertView, parent);

        TextView view = (TextView) super.getView(position, convertView, parent);
        String text = data.get(position).getName() +
                "\n Закуп:      " + data.get(position).getValueBuy() +
                "\n Опт:          " + data.get(position).getValueOpt() +
                "\n Розница: " + data.get(position).getValueSale();
        view.setText(text);
        switch (data.get(position).getCategory()) {
            case 0:
                view.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_other, 0, 0, 0);
                break;
            case 1:
                view.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gloves, 0, 0, 0);
                break;
            case 2:
                view.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_bubl, 0, 0, 0);
                break;
            case 3:
                view.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_car, 0, 0, 0);
                break;
            case 4:
                view.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_tools, 0, 0, 0);
                break;
            case 5:
                view.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_all_incude, 0, 0, 0);
                break;
            default:
                view.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_launcher_foreground, 0, 0, 0);
                break;
        }

        return view;
    }
}
