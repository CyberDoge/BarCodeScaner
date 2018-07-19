package com.pganin.barcodescaner;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class TableActivity  {


    private TableRow Create(Context context, String text, int margin) {

        TextView textView1 = new TextView(context);
        textView1.setText(text);
        textView1.setGravity(0);
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        llp.setMargins(0, 0, 0, margin);
        textView1.setLayoutParams(llp);
        TableRow tableRow1 = new TableRow(context);
        tableRow1.addView(textView1);


        return tableRow1;
    }
    public TableLayout Create(Context context, ArrayList<Product> products){
       /* TableLayout tableLayout = new TableLayout(context);
        tableLayout.setLayoutParams(new TableLayout.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        tableLayout.setStretchAllColumns(true);
        for (Product p: products){
            tableLayout.addView(Create(context, p.getName(), 0));
            tableLayout.addView(Create(context, ""+p.getValueBuy(), 0));
            tableLayout.addView(Create(context, ""+p.getValueOpt(), 0));
            tableLayout.addView(Create(context, ""+p.getValueSale(), 50));
        }*/
        TableLayout tableLayout = new TableLayout(context);
        tableLayout.setLayoutParams(new TableLayout.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        tableLayout.setStretchAllColumns(true);



        for (Product p: products)
        //for(int i=0; i < 10; i++)
        {
            TextView textView1 = new TextView(context);
            textView1.setText(p.getName());

            TextView textView2 = new TextView(context);
            textView2.setText(""+p.getValueBuy());

            TextView textView3 = new TextView(context);
            textView3.setText(""+p.getValueOpt());

            TextView textView4 = new TextView(context);
            textView4.setText(""+p.getValueSale());

            TextView textView5 = new TextView(context);
            textView5.setText("");

            TableRow tableRow1 = new TableRow(context);
            TableRow tableRow2 = new TableRow(context);
            TableRow tableRow3 = new TableRow(context);
            TableRow tableRow4 = new TableRow(context);
            TableRow tableRow5 = new TableRow(context);

            tableRow1.setBackgroundColor(0x6161bccc);
            tableRow1.addView(textView1);

            tableRow2.setBackgroundColor(0xdcdcdcdd);
            tableRow2.addView(textView2);

            tableRow3.setBackgroundColor(0xdcdcdcdd);
            tableRow3.addView(textView3);

            tableRow4.setBackgroundColor(0xdcdcdcdd);
            tableRow4.addView(textView4);

            tableRow5.addView(textView5);

            tableLayout.addView(tableRow1);
            tableLayout.addView(tableRow2);
            tableLayout.addView(tableRow3);
            tableLayout.addView(tableRow4);
            tableLayout.addView(tableRow5);
        }
        return tableLayout;
    }
}
