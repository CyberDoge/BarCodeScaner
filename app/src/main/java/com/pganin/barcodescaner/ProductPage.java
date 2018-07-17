package com.pganin.barcodescaner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class ProductPage extends CaptureActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_page);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
    public void test(View view){
        System.out.println("qwe");
    }
}
