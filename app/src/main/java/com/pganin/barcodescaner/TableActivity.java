package com.pganin.barcodescaner;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;

public class TableActivity extends Activity {
    private boolean mShrink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_activity);

        final TableLayout table = (TableLayout) findViewById(R.id.tablelayout);
        Button button = (Button) findViewById(R.id.toggle);
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                mShrink = !mShrink;
                table.setColumnShrinkable(0, mShrink);
            }
        });
        mShrink = table.isColumnShrinkable(0);
    }
}
