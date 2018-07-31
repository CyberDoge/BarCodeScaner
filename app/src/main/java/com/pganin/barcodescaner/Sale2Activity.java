package com.pganin.barcodescaner;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Sale2Activity extends AppCompatActivity {
    private ListView list;
    private static SaleAdapter adapter;
    public final int CUSTOMIZED_REQUEST_CODE = 0x0000ffff;
    private ArrayList<Product> products = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Продажа");
        if(adapter == null)
            adapter = new SaleAdapter(this);
        list = (ListView)findViewById(R.id.list_sale);
        list.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSearch();
            }
        });
        if(!Repository.getBasket().isEmpty()){
            for (Basket p: Repository.getBasket()){

                adapter.add(p);

            }
            adapter.notifyDataSetChanged();
            Repository.getBasket().clear();
            products.addAll(Repository.getBasket());
        }
    }
    public static void Refresh(){
        adapter.notifyDataSetChanged();
    }
    private void startSearch(){
        Intent intent = new Intent(this, SearchResultsActivity.class);
        startActivity(intent);
    }
    private void startScan(){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setOrientationLocked(false);
        integrator.setCaptureActivity(SmallCaptureActivity.class);
        integrator.initiateScan();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.sale_btn:
//                Snackbar.make(item.getActionView(), "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Repository.getBasket().clear();
                Toast.makeText(this, "Продано!!!", Toast.LENGTH_LONG).show();

                LoadMainTask loadMainTask = new LoadMainTask();
                loadMainTask.execute(0);
                return true;
            case R.id.scan_btn:
                startScan();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
            Log.d("Sale2Activity", "Scanned");
            String barcode = result.getContents();
            Toast.makeText(this, "Scanned : " + barcode, Toast.LENGTH_LONG).show();

            if(Repository.getDB().FindProductByBarCode(barcode))
            {
                if(Repository.getBasket().stream().filter( c -> c.getBarCode().equals(Repository.getDB().getLast_Product().getBarCode()))
                        .collect(Collectors.toList()).isEmpty()) {
                    Repository.getBasket().add(Repository.getDB().getLast_Product().toBasket());

                    adapter.add(Repository.getDB().getLast_Product().toBasket());
                    adapter.notifyDataSetChanged();
                }
            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.menu_sale, menu);
        return true;
    }
    private void startMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private class LoadMainTask extends AsyncTask<Integer, Void, String> {
        @SuppressWarnings("unchecked")
        @Override
        protected String doInBackground(Integer... params) {
            try {
                Thread.sleep(1000);
                startMain();
            } catch (Exception e) {
                //Log.e(TAG, "Loading data", e);
            }
            return "";
        }
    }

}
