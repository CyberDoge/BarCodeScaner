package com.pganin.barcodescaner;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SaleActivity extends AppCompatActivity {

    private ListView list;
    private StringAdapter adapter;
    private View footer;
    private LoadMoreAsyncTask loadingTask = new LoadMoreAsyncTask();
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        setTitle("Продажа");
        /*fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                LoadMainTask loadMainTask = new LoadMainTask();
                loadMainTask.execute(0);

            }
        });*/
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
        //list.setOnScrollListener(this);
        final FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.plus_btn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startScan();
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.sale_btn:
                Snackbar.make(item.getActionView(), "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                LoadMainTask loadMainTask = new LoadMainTask();
                loadMainTask.execute(0);
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
    private void startScan(){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setOrientationLocked(false);
        integrator.setCaptureActivity(SmallCaptureActivity.class);
        integrator.initiateScan();
    }
    public Collection<Product> generateSearch() {
        List<Product> l = new ArrayList<Product>(1);

                l.add( Repository.getProducts().get(1) );
        return l;
    }
    public final int CUSTOMIZED_REQUEST_CODE = 0x0000ffff;

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
            if(Repository.getDB().FindProductByBarCode(barcode))
            {
                List<Product> list = new ArrayList<Product>();
                list.add(Repository.getDB().getLast_Product());
                adapter.add(list);
                adapter.notifyDataSetChanged();
            }else {
                Intent intent = new Intent(this, ProductPage.class);
                intent.putExtra("is_created", false);
                intent.putExtra("barcode", barcode);
                startActivity(intent);
            }
        }

    }
    private class LoadMoreAsyncTask extends AsyncTask<Integer, Void, Collection<Product>> {
        @SuppressWarnings("unchecked")
        @Override
        protected Collection<Product> doInBackground(Integer... params) {
            try {
                //Thread.sleep(1000);
              //  Collection<Product> data = generate(params[0].intValue(), 20);
                //return data;
            } catch (Exception e) {
                //Log.e(TAG, "Loading data", e);
            }
            return Collections.EMPTY_LIST;
        }

        @Override
        protected void onPostExecute(Collection<Product> data) {
            if (data.isEmpty()) {
                //isEndList = true;
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.listview_footer);
                linearLayout.setVisibility(View.GONE);
                //footer.isShown()
            }else{
                //Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();


                adapter.add(data);
                adapter.notifyDataSetChanged();
                int index = list.getFirstVisiblePosition();
                int top = (list.getChildAt(0) == null) ? 0 : list.getChildAt(0).getTop();
                list.setSelectionFromTop(index, top);
            }
        }
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
