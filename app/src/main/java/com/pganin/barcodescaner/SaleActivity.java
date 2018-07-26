package com.pganin.barcodescaner;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SaleActivity extends AppCompatActivity {

    private ListView list;
    private StringAdapter adapter;
    private View footer;
    private LoadMoreAsyncTask loadingTask = new LoadMoreAsyncTask();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Продажа");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                LoadMainTask loadMainTask = new LoadMainTask();
                loadMainTask.execute(0);

            }
        });
        if(adapter == null)
            adapter = new StringAdapter(this);

        if(footer == null)
            footer = getLayoutInflater().inflate(R.layout.listview_sale, null);
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
        list.addFooterView(footer); // it's important to call 'addFooter' before 'setAdapter'
        list.setAdapter(adapter);
        //list.setOnScrollListener(this);
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.plus_btn);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collection<Product> data = generateSearch();
                adapter.add(data);
                adapter.notifyDataSetChanged();
                int index = list.getFirstVisiblePosition();
                int top = (list.getChildAt(0) == null) ? 0 : list.getChildAt(0).getTop();
                list.setSelectionFromTop(index, top);
            }
        });
    }
    private void startMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public Collection<Product> generateSearch() {
        List<Product> l = new ArrayList<Product>(1);

                l.add( Repository.getProducts().get(1) );
        return l;
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
