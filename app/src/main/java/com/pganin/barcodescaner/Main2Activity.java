package com.pganin.barcodescaner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends ListActivity implements OnScrollListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ListView list;
    private StringAdapter adapter;
    private View footer;
    private LoadMoreAsyncTask loadingTask = new LoadMoreAsyncTask();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        adapter = new StringAdapter(this);

        footer = getLayoutInflater().inflate(R.layout.listview_footer, null);

        list = getListView();
        list.addFooterView(footer); // it's important to call 'addFooter' before 'setAdapter'
        list.setAdapter(adapter);
        list.setOnScrollListener(this);

        loadingTask.execute(0);
    }

    public Collection<Product> generate(int startIndex, int count) {
        List<Product> l = new ArrayList<Product>(count);
        for (int i = 0; i < count; i++) {
            l.add(MainActivity.products.get(startIndex+i));
        }
        return l;
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



    private class LoadMoreAsyncTask extends AsyncTask<Integer, Void, Collection<Product>> {
        @SuppressWarnings("unchecked")
        @Override
        protected Collection<Product> doInBackground(Integer... params) {
            try {
                Thread.sleep(1000);
                Collection<Product> data = generate(params[0].intValue(), 20);
                return data;
            } catch (Exception e) {
                Log.e(TAG, "Loading data", e);
            }
            return Collections.EMPTY_LIST;
        }

        @Override
        protected void onPostExecute(Collection<Product> data) {
            if (data.isEmpty()) {
                Toast.makeText(Main2Activity.this, "error", Toast.LENGTH_SHORT).show();
                return;
            }

            adapter.add(data);
            adapter.notifyDataSetChanged();
            int index = list.getFirstVisiblePosition();
            int top = (list.getChildAt(0) == null) ? 0 : list.getChildAt(0).getTop();
            list.setSelectionFromTop(index, top);
        }
    }
}
