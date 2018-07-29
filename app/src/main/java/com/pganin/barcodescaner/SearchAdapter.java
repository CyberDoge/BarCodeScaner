package com.pganin.barcodescaner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SearchAdapter extends ArrayAdapter<Product> {
        private List<Product> data = new ArrayList<Product>();


        public SearchAdapter(Context context) {
            super(context, 0, new Product[0]);
        }

        public void add(Collection<Product> data) {
            this.data.addAll(data);
        }

        public void add(Product product){
            this.data.add(product);
        }
        public void remove(int pos){
            this.data.remove(pos);
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
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            }
            TextView Name = (TextView) convertView.findViewById(R.id.name);
            EditText quantity = (EditText) convertView.findViewById(R.id.quantity);
            // Populate the data into the template view using the data object
            Name.setText("qweqwe");
//            int q = Integer.parseInt(quantity.getText().toString());
           // System.out.println("q" + q);
            // Return the completed view to render on screen
            return convertView;
        }
    }
