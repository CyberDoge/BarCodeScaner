package com.pganin.barcodescaner;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.zxing.integration.android.IntentIntegrator;

public class Sale2Activity extends AppCompatActivity {
    private ListView list;
    private StringAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Продажа");
        if(adapter == null)
            adapter = new StringAdapter(this);

        //if(footer == null)
        //    footer = getLayoutInflater().inflate(R.layout.listview_sale, null);
        //ListActivity listActivity = new ListActivity();
        //new Intent(MainActivity.this, ListActivity.class);
        //listActivity.onCreate(savedInstanceState, );
        list = (ListView)findViewById(R.id.list_sale);//listActivity.getListView();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long arg3) {
                view.setSelected(true);
                //startClick(position);
            }
        });
        //list.addFooterView(footer); // it's important to call 'addFooter' before 'setAdapter'
        list.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSearch();
            }
        });

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
