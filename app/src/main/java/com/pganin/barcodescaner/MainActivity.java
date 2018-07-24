package com.pganin.barcodescaner;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import android.app.SearchManager;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnScrollListener { //extends ListActivity implements OnScrollListener {//

    public final int CUSTOMIZED_REQUEST_CODE = 0x0000ffff;
    private static final String TAG = MainActivity.class.getSimpleName();
//
    private ListView list;
    private StringAdapter adapter;
    private View footer;
    private LoadMoreAsyncTask loadingTask = new LoadMoreAsyncTask();
    private boolean isEndList = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        //DB
        if(Repository.getDB() == null){
            Repository.setDB( new DataBase() );
            Repository.getDB().Init();
            /*for(int i=250; i <1250; i++){
                DB.AddTests(i);
            }*/
        }else if(!Repository.getDB().isCreated()){
            Repository.getDB().Init();
        }

        Repository.setProducts( Repository.getDB().GetProducts());

      /*  TableActivity tableActivity = new TableActivity();
        ScrollView scrollView = new ScrollView(getApplicationContext());
        scrollView.addView(tableActivity.Create(getApplicationContext(), products));
        setContentView( scrollView );
*/
        if(adapter == null)
            adapter = new StringAdapter(this);

        if(footer == null)
        footer = getLayoutInflater().inflate(R.layout.listview_footer, null);
        //ListActivity listActivity = new ListActivity();
        //new Intent(MainActivity.this, ListActivity.class);
        //listActivity.onCreate(savedInstanceState, );
        list = (ListView)findViewById(R.id.list);//listActivity.getListView();
        list.addFooterView(footer); // it's important to call 'addFooter' before 'setAdapter'
        list.setAdapter(adapter);
        list.setOnScrollListener(this);

        loadingTask.execute(0);

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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.scan:
                IntentIntegrator integrator = new IntentIntegrator(this);
                integrator.setOrientationLocked(false);
                integrator.setCaptureActivity(SmallCaptureActivity.class);
                integrator.initiateScan();
                return true;
            case R.id.action_search:
                //Intent intent = new Intent(this, SearchResultsActivity.class);
                //startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    android.support.v7.widget.SearchView searchView;
    private void runtest(){
        Intent intent = new Intent(this, SearchResultsActivity.class);
        startActivity(intent);
    }
    private void onCloseSearch(){
        list = null;
        list = (ListView)findViewById(R.id.list);//listActivity.getListView();
        list.addFooterView(footer); // it's important to call 'addFooter' before 'setAdapter'
        list.setAdapter(adapter);
        list.setOnScrollListener(this);
        loadingTask = new LoadMoreAsyncTask();
        loadingTask.execute(0);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.main_menu, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
         searchView =
                (android.support.v7.widget.SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //runtest();
            }
        });
        searchView.setOnCloseListener(new android.support.v7.widget.SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                onCloseSearch();
                return true;
            }
        });
        searchView.setOnSuggestionListener(new android.support.v7.widget.SearchView.OnSuggestionListener(){
            @Override
            public boolean onSuggestionClick(int position){
                System.out.println("onSuggestionClick");
                return false;
            }
            public boolean onSuggestionSelect(int position){
                System.out.println("onSuggestionSelect");
                return false;
            }
        });
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchActivityWindow(query);
                System.out.println(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(newText == null || newText.equals("")){
                    onCloseSearch();
                }
                return false;
            }
        });
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        //searchView.

        return true;
        //return super.onCreateOptionsMenu(menu);
    }

    private void SearchActivityWindow(String text){

        ArrayList<Product> products = Repository.getDB().FindProducts(text);
        if(products.size() != 0) {
            list.setAdapter(null);
            list = (ListView)findViewById(R.id.list);//listActivity.getListView();

            list.addFooterView(footer); // it's important to call 'addFooter' before 'setAdapter'
            list.setAdapter(null);
            adapter = new StringAdapter(this);
            list.setAdapter(adapter);
            list.setOnScrollListener(this);
            loadingTask = new LoadMoreAsyncTask();
            loadingTask.execute(0);
        }else {

            Toast.makeText(this, "Ничего не найдено", Toast.LENGTH_LONG).show();
        }
        //Intent intent = new Intent(this, SearchResultsActivity.class);
       // startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            //findViewById(R.id.).setVisibility(View.VISIBLE);

        } else {
            super.onBackPressed();
        }
    }
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }*/

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
                Intent intent = new Intent(this, ProductPage.class);
                intent.putExtra("is_created", true);//
                intent.putExtra("name", Repository.getDB().getLast_Product().getName());
                intent.putExtra("barcode", barcode);
                intent.putExtra("quantity", Repository.getDB().getLast_Product().getQuantity());
                intent.putExtra("valueBuy", Repository.getDB().getLast_Product().getValueBuy());
                intent.putExtra("valueOpt", Repository.getDB().getLast_Product().getValueOpt());
                intent.putExtra("valueSale", Repository.getDB().getLast_Product().getValueSale());
                intent.putExtra("category", Repository.getDB().getLast_Product().getCategory());
                startActivity(intent);
            }else {
                Intent intent = new Intent(this, ProductPage.class);
                intent.putExtra("is_created", false);
                intent.putExtra("barcode", barcode);
                startActivity(intent);
            }
        }

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisible, int visibleCount, int totalCount) {
        boolean loadMore = firstVisible + visibleCount >= totalCount;

        if (loadMore && loadingTask.getStatus() == AsyncTask.Status.FINISHED ) {
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
