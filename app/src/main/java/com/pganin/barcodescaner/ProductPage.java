package com.pganin.barcodescaner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.journeyapps.barcodescanner.CaptureActivity;


public class ProductPage extends Activity {

    String barcode_text = "";
    boolean isCreated = false;
    int Categore_chose = 0;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_page);
        TextView barcode = (TextView) findViewById(R.id.textBarCode);
        Intent intent = getIntent();
        barcode_text = intent.getStringExtra("barcode");
        isCreated = intent.getBooleanExtra("is_created", false);
        barcode.setText("BarCode: " + barcode_text);
        spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(this, R.array.planets_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinner.getSelectedItem() != null) {
                    Categore_chose=i;
                    System.out.println(spinner.getSelectedItem() + " " + i);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
            Categore_chose = intent.getIntExtra("category", 0);
            spinner.setSelection(Categore_chose);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    public void saveButton(View view){
        if(!isCreated) {
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
                    Float.parseFloat(valueSaleText.getText().toString()),
                    Categore_chose);
            Repository.getDB().AddProduct(product);
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
                    Float.parseFloat(valueSaleText.getText().toString()),
                    Categore_chose);
            Repository.getDB().EditProduct(product);
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
