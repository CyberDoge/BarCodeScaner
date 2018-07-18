package com.pganin.barcodescaner;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class TableActivity  {
    private boolean mShrink;

    public TableLayout Create(Context context, Product product) {

        TableLayout tableLayout = new TableLayout(context);
        tableLayout.setLayoutParams(new TableLayout.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        tableLayout.setStretchAllColumns(true);

        TextView textView1 = new TextView(context);
        textView1.setText("Column 1");
        TextView textView2 = new TextView(context);
        textView2.setText("Column 2");
        TextView textView3 = new TextView(context);
        textView3.setText("Column 3");
        TableRow tableRow1 = new TableRow(context);
        tableRow1.addView(textView1);
        tableRow1.addView(textView2);
        tableRow1.addView(textView3);
        tableLayout.addView(tableRow1);
        return tableLayout;
    }
}
