package com.pganin.barcodescaner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static DataBase DB ;
    public static ArrayList<Product> products = new ArrayList<>();
    public final int CUSTOMIZED_REQUEST_CODE = 0x0000ffff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        DB = new DataBase();
        DB.Init();
        //DB.execute();
    }
    public void scanMarginScanner(View view) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setOrientationLocked(false);
        integrator.setCaptureActivity(SmallCaptureActivity.class);
        integrator.initiateScan();
    }
    public  void ivennted(View view){
        products = DB.GetProducts();
        DB.AddProduct(new Product("123", "test1", 1, .0f, 1.0f, 2.0f));
        for( Product p:products){
            System.out.println("");
        }
        Intent intent = new Intent(this, ProductPage.class);
        startService(intent);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != CUSTOMIZED_REQUEST_CODE && requestCode != IntentIntegrator.REQUEST_CODE) {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
        switch (requestCode) {
            case CUSTOMIZED_REQUEST_CODE: {
                Toast.makeText(this, "REQUEST_CODE = " + requestCode, Toast.LENGTH_LONG).show();
                break;
            }
            default:
                break;
        }

        IntentResult result = IntentIntegrator.parseActivityResult(resultCode, data);

        if(result.getContents() == null) {
            Log.d("MainActivity", "Cancelled scan");
            Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
        } else {
            Log.d("MainActivity", "Scanned");
            Intent intent = new Intent(this, ProductPage.class);
            startService(intent);
            Toast.makeText(this, "Scanned : " + result.getContents(), Toast.LENGTH_LONG).show();

        }

    }
}
