package com.pganin.barcodescaner;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SearchResultsActivity extends AppCompatActivity implements AbsListView.OnScrollListener {
    ArrayList<Product> basket = new ArrayList<>();
    private ListView list;
    private StringAdapter adapter;
    private View footer;
    private LoadMoreAsyncTask loadingTask = new LoadMoreAsyncTask();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        if(adapter == null)
            adapter = new StringAdapter(this);

        if(footer == null)
            footer = getLayoutInflater().inflate(R.layout.listview_footer, null);
        //ListActivity listActivity = new ListActivity();
        //new Intent(MainActivity.this, ListActivity.class);
        //listActivity.onCreate(savedInstanceState, );
        list = (ListView)findViewById(R.id.list);//listActivity.getListView();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long arg3) {
                view.setSelected(true);
                ((TextView) view).setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done, 0, 0, 0);
                basket.add(Repository.getProducts().get(position));
            }
        });
        list.addFooterView(footer); // it's important to call 'addFooter' before 'setAdapter'
        list.setAdapter(adapter);
        list.setOnScrollListener(this);
        loadingTask.execute(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.go_btn:
//                Snackbar.make(item.getActionView(), "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Toast.makeText(this, "Добавлено", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, Sale2Activity.class);
                Repository.setBasket(basket);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }


    @Override
    public void onScroll(AbsListView view, int firstVisible, int visibleCount, int totalCount) {
        boolean loadMore = firstVisible + visibleCount >= totalCount;
        if (loadMore && loadingTask.getStatus() == AsyncTask.Status.FINISHED) {
            loadingTask = new LoadMoreAsyncTask();
            loadingTask.execute(totalCount);
        }
    }

    public Collection<Product> generate(int startIndex, int count) {
        List<Product> l = new ArrayList<Product>(count);
        if(Repository.getProducts().size() < count)
            count = Repository.getProducts().size();
        if(Repository.getProducts().size() > startIndex)
            for (int i = 0; i < count; i++) {
                l.add(Repository.getProducts().get(startIndex+i));
            }
        return l;
    }
    private class LoadMoreAsyncTask extends AsyncTask<Integer, Void, Collection<Product>> {
        @SuppressWarnings("unchecked")
        @Override
        protected Collection<Product> doInBackground(Integer... params) {
            try {
                Thread.sleep(1000);
                Collection<Product> data = generate(params[0].intValue(), 20);
                return data;
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

}