package com.pganin.barcodescaner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static DataBase DB  = new DataBase();
    public static ArrayList<Product> products = new ArrayList<>();
    public final int CUSTOMIZED_REQUEST_CODE = 0x0000ffff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        //DB
        DB.Init();
        //DB.execute();
    }
 /*   @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // добавляем пункты меню
        menu.add(0, 1, 0, "add");
        menu.add(0, 2, 0, "edit");
        menu.add(0, 3, 3, "delete");
        menu.add(1, 4, 1, "copy");
        menu.add(1, 5, 2, "paste");
        menu.add(1, 6, 4, "exit");
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // обновление меню
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        // пункты меню с ID группы = 1 видны, если в CheckBox стоит галка
        menu.setGroupVisible(1, true);
        return super.onPrepareOptionsMenu(menu);
    }

    // обработка нажатий
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        StringBuilder sb = new StringBuilder();

        // Выведем в TextView информацию о нажатом пункте меню
        sb.append("Item Menu");
        sb.append("\r\n groupId: " + String.valueOf(item.getGroupId()));
        sb.append("\r\n itemId: " + String.valueOf(item.getItemId()));
        sb.append("\r\n order: " + String.valueOf(item.getOrder()));
        sb.append("\r\n title: " + item.getTitle());
        //tv.setText(sb.toString());

        return super.onOptionsItemSelected(item);
    }*/
    public void scanMarginScanner(View view) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setOrientationLocked(false);
        integrator.setCaptureActivity(SmallCaptureActivity.class);
        integrator.initiateScan();
    }
    public  void ivennted(View view){
        products = DB.GetProducts();
        //DB.AddProduct(new Product("123", "test1", 1, .0f, 1.0f, 2.0f));
        //for( Product p:products){
        //    System.out.println("");
        //}
        Intent intent = new Intent(this, ProductPage.class);
        startService(intent);
        /*TableLayout tableLayout = new TableLayout(this);
        tableLayout.setLayoutParams(new TableLayout.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        tableLayout.setStretchAllColumns(true);

        TextView textView1 = new TextView(this);
        textView1.setText("Column 1");
        TextView textView2 = new TextView(this);
        textView2.setText("Column 2");
        TextView textView3 = new TextView(this);
        textView3.setText("Column 3");

        TextView textView4 = new TextView(this);
        textView4.setText("Column 4");
        TextView textView5 = new TextView(this);
        textView5.setText("Column 5");
        TextView textView6 = new TextView(this);
        textView6.setText("Column 6");

        TextView textView7 = new TextView(this);
        textView7.setText("Column 7");
        TextView textView8 = new TextView(this);
        textView8.setText("Column 8");
        TextView textView9 = new TextView(this);
        textView9.setText("Column 9");

        TableRow tableRow1 = new TableRow(this);
        TableRow tableRow2 = new TableRow(this);
        TableRow tableRow3 = new TableRow(this);

        tableRow1.addView(textView1);
        tableRow1.addView(textView2);
        tableRow1.addView(textView3);

        tableRow2.setBackgroundColor(0xffcccccc);
        tableRow2.addView(textView4);
        tableRow2.addView(textView5);
        tableRow2.addView(textView6);

        tableRow3.addView(textView7);
        tableRow3.addView(textView8);
        tableRow3.addView(textView9);

        tableLayout.addView(tableRow1);
        tableLayout.addView(tableRow2);
        tableLayout.addView(tableRow3);
        setContentView(tableLayout);*/
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
            String barcode = result.getContents();
            Toast.makeText(this, "Scanned : " + barcode, Toast.LENGTH_LONG).show();
            //if(!DB.FindProductByBarCode(barcode))
            {
                Intent intent = new Intent(this, ProductPage.class);
                startService(intent);
            }
        }

    }
}
