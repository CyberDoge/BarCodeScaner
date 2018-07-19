package com.pganin.barcodescaner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.journeyapps.barcodescanner.CaptureActivity;


public class ProductPage extends Activity {

    String barcode_text = "";
    boolean isCreated = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_page);
        TextView barcode = (TextView) findViewById(R.id.textBarCode);
        Intent intent = getIntent();
        barcode_text = intent.getStringExtra("barcode");
        isCreated = intent.getBooleanExtra("is_created", false);
        barcode.setText("BarCode: " + barcode_text);
        if(isCreated){
            String name = intent.getStringExtra("name");
            EditText nameText = (EditText)findViewById(R.id.editText);
            nameText.setText(name);

            int quantity = intent.getIntExtra("quantity",0);
            EditText quantityText = (EditText)findViewById(R.id.editText2);
            quantityText.setText(quantity+"");

            float valueBuy = intent.getFloatExtra("valueBuy", 0);
            EditText valueBuyText = (EditText)findViewById(R.id.editText3);
            valueBuyText.setText(valueBuy+"");

            float valueOpt = intent.getFloatExtra("valueOpt", 0);
            EditText valueOptText = (EditText)findViewById(R.id.editText4);
            valueOptText.setText(valueOpt+"");

            float valueSale = intent.getFloatExtra("valueSale", 0);
            EditText valueSaleText = (EditText)findViewById(R.id.editText5);
            valueSaleText.setText(valueSale+"");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    public void saveButton(View view){
        if(isCreated) {
            EditText nameText = (EditText) findViewById(R.id.editText);
            EditText quantityText = (EditText) findViewById(R.id.editText2);
            EditText valueBuyText = (EditText) findViewById(R.id.editText3);
            EditText valueOptText = (EditText) findViewById(R.id.editText4);
            EditText valueSaleText = (EditText) findViewById(R.id.editText5);
            Product product = new Product(barcode_text,
                    nameText.getText().toString(),
                    Integer.parseInt(quantityText.getText().toString()),
                    Float.parseFloat(valueBuyText.getText().toString()),
                    Float.parseFloat(valueOptText.getText().toString()),
                    Float.parseFloat(valueSaleText.getText().toString()));
            MainActivity.DB.AddProduct(product);
        }else {
            EditText nameText = (EditText) findViewById(R.id.editText);
            EditText quantityText = (EditText) findViewById(R.id.editText2);
            EditText valueBuyText = (EditText) findViewById(R.id.editText3);
            EditText valueOptText = (EditText) findViewById(R.id.editText4);
            EditText valueSaleText = (EditText) findViewById(R.id.editText5);
            Product product = new Product(barcode_text,
                    nameText.getText().toString(),
                    Integer.parseInt(quantityText.getText().toString()),
                    Float.parseFloat(valueBuyText.getText().toString()),
                    Float.parseFloat(valueOptText.getText().toString()),
                    Float.parseFloat(valueSaleText.getText().toString()));
            MainActivity.DB.EditProduct(product);
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
