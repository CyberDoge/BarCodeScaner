package com.pganin.barcodescaner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.journeyapps.barcodescanner.CaptureActivity;


public class ProductPage extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_page);
        TextView barcode = (TextView) findViewById(R.id.textBarCode);
        Intent intent = getIntent();
        String fName = intent.getStringExtra("barcode");
        barcode.setText("BarCode: " + fName);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
    public void test(View view){
        System.out.println("qwe");
    }
}
