package com.pganin.barcodescaner;

import android.os.Bundle;

import com.journeyapps.barcodescanner.CaptureActivity;

public class ProductPage extends CaptureActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_page);
    }
}
