package com.pganin.barcodescaner;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class SaleAdapter extends ArrayAdapter<Basket> {
    private List<Basket> data = new ArrayList<Basket>();

    public SaleAdapter(Context context) {
        super(context, 0, new Basket[0]);
    }
    public void add(Basket product){
        this.data.add(product);
    }
    public void add(Collection<Basket> data) {
        this.data.addAll(data);
    }

    public void remove(int pos){
        this.data.remove(pos);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public int getPosition(Basket item) {
        return data.indexOf(item);
    }

    @Override
    public Basket getItem(int position) {
        return data.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_sale, parent, false);
        }
        final int positionIn = position;
        final TextView Name = (TextView) convertView.findViewById(R.id.name);
        EditText quantity = (EditText) convertView.findViewById(R.id.quantity);
        Button button = (Button) convertView.findViewById(R.id.btn_add);
        //if(data.get(position).getQuatityInBasket() != 0)
        //    quantityIn = data.get(position).getQuatityInBasket();
        if(data.get(positionIn).getQuatityInBasket() != 0)
            quantity.setText(data.get(positionIn).getQuatityInBasket()+"", TextView.BufferType.EDITABLE);

        quantity.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    //quantityIn = Integer.parseInt(s.toString());
                    data.get(positionIn).setQuatityInBasket(Integer.parseInt(s.toString()));
                    //Repository.getProducts().get(positionIn).setQuantityInBasket(Integer.parseInt(s.toString()));
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        String text = data.get(position).getName()+
                "\n Закуп:      "+data.get(position).getValueBuy()+
                "\n Опт:          "+data.get(position).getValueOpt()+
                "\n Розница: "+data.get(position).getValueSale();
        Name.setText(text);
        switch (data.get(position).getCategory()){
            case 0:
                Name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_other, 0, 0, 0);
                break;
            case 1:
                Name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_gloves, 0, 0, 0);
                break;
            case 2:
                Name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_bubl, 0, 0, 0);
                break;
            case 3:
                Name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_car, 0, 0, 0);
                break;
            case 4:
                Name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_tools, 0, 0, 0);
                break;
            case 5:
                Name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_all_incude, 0, 0, 0);
                break;
            default:
                Name.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_launcher_foreground, 0, 0, 0);
                break;
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    remove(positionIn);
                    Repository.getBasket().remove(positionIn);
                    Sale2Activity.Refresh();
                }catch (Exception e){
                    System.out.println(e);
                }

            }
        });
        return convertView;
    }
}
