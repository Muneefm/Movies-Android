package moviez.mnf.com.movie.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.net.URLEncoder;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import fr.castorflex.android.circularprogressbar.CircularProgressDrawable;
import info.hoang8f.android.segmented.SegmentedGroup;
import moviez.mnf.com.movie.Adapters.RecycleAdapterTv;
import moviez.mnf.com.movie.Adapters.RecylcleAdapter;
import moviez.mnf.com.movie.AppController;
import moviez.mnf.com.movie.DataSet.TV.list.TvDataList;
import moviez.mnf.com.movie.DataSet.first.DataMain;
import moviez.mnf.com.movie.R;

public class SearchActivity extends ActionBarActivity {


    Toolbar toolbar;
    CircularProgressBar smothCirSearch;
    public Gson gson = new Gson();
    RecycleAdapterTv adapterTv;
    EditText search;
    RecylcleAdapter adapter;
    ObservableRecyclerView lv;
    private LinearLayoutManager mLayoutManager;
    GridLayoutManager  tvlmanager;
    String BaseUrl,BaseUrltv;
    SegmentedGroup segmented2;
     String key;
    TextView noRes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent intent = getIntent();
         key = intent.getExtras().getString("key");
        lv = (ObservableRecyclerView) findViewById(R.id.searchResult);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        search = (EditText) findViewById(R.id.search);
        smothCirSearch= (CircularProgressBar) findViewById(R.id.smothCirSearch);
        noRes = (TextView) findViewById(R.id.noResSearch);
        setSupportActionBar(toolbar);
        lv.setHasFixedSize(false);
        Display display = this.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density  = getResources().getDisplayMetrics().density;
        float dpWidth  = outMetrics.widthPixels / density;
        int columns = Math.round(dpWidth/170);
         segmented2 = (SegmentedGroup) findViewById(R.id.segmented2);
        segmented2.setTintColor(getResources().getColor(R.color.teal));

        adapter = new RecylcleAdapter(getApplicationContext());
        adapterTv = new RecycleAdapterTv(getApplicationContext());
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        tvlmanager = new GridLayoutManager(getApplicationContext(),columns);
        BaseUrl = "http://api.themoviedb.org/3/search/movie?api_key=7cf008680165ec352b68dce08866495f&query=";
        BaseUrltv = "http://api.themoviedb.org/3/search/tv?api_key=7cf008680165ec352b68dce08866495f&query=";


        if(key.equals("1")){
            segmented2.check(R.id.button21);
            initiliaseMovie();
        }else if(key.equals("2")){
            segmented2.check(R.id.button22);
            initialiseTv();

        }

        segmented2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.button21:
                     //   Toast.makeText(getApplicationContext(), "movie Button", Toast.LENGTH_LONG).show();

                        if (lv.getAdapter() != adapter) {
                            Log.e("TAG", "  1st adapt");
                            key = "1";

                            initiliaseMovie();
                            if (!search.getText().equals("")) {
                                Log.e("TAG", "not null");
                                //Toast.makeText(getApplicationContext(),"this is search not null",Toast.LENGTH_LONG).show();
                               // ((CircularProgressDrawable) smothCirSearch.getIndeterminateDrawable()).start();

                                makeJsonCrewRequest(BaseUrl + Uri.encode(search.getText().toString()));
                            } else {
                                Log.e("TAG", "search null");
                            }
                        }
                        return;
// this is the bes tt sjsd fsdf


                    case R.id.button22:
                    //    Toast.makeText(getApplicationContext(), "tv Button", Toast.LENGTH_LONG).show();
                        if (lv.getAdapter() != adapterTv) {
                            key = "2";
                            initialiseTv();
                            if (!search.getText().equals("")) {
                               // Toast.makeText(getApplicationContext(), "this is search not null", Toast.LENGTH_LONG).show();
                                Log.e("TAG", "not null");

                                //((CircularProgressDrawable) smothCirSearch.getIndeterminateDrawable()).start();
                                makeTvSearch(BaseUrltv + Uri.encode(search.getText().toString()));

                            }
                        }
                        return;

                }

            }
        });


        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (key.equals("1")) {
                  //      Toast.makeText(getApplicationContext(),"search one = ys "+search.getText().toString(),Toast.LENGTH_LONG).show();
                        //String query = URLEncoder.encode("apples oranges", "utf-8");
                        makeJsonCrewRequest(BaseUrl + Uri.encode(search.getText().toString()));
                    } else if (key.equals("2")) {
                    //    Toast.makeText(getApplicationContext(),"search two = "+search.getText().toString(),Toast.LENGTH_LONG).show();
                    makeTvSearch(BaseUrltv+Uri.encode(search.getText().toString()));
                    }


                    return true;
                }
                return false;
            }
        });



search.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //makeJsonCrewRequest(BaseUrl+s);
        //Log.e("TAG","String is ="+s);

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
});


    }


 /*   public void initialiseAdapter(){
        if(key.equals("1")){
            lv.setLayoutManager(mLayoutManager);
            lv.setAdapter(adapter);
            segmented2.check(R.id.button21);
        }else if(key.equals("2")){
            segmented2.check(R.id.button22);
            lv.setLayoutManager(tvlmanager);
            lv.setAdapter(adapterTv);

        }

    }
    */
    public void initiliaseMovie(){

        lv.setAdapter(adapter);
        lv.setLayoutManager(mLayoutManager);

    }

    public void initialiseTv(){


        lv.setAdapter(adapterTv);
        lv.setLayoutManager(tvlmanager);

    }


    public void makeJsonCrewRequest(String uu){
        noRes.setVisibility(View.INVISIBLE);
        smothCirSearch.setVisibility(View.VISIBLE);
        ((CircularProgressDrawable)smothCirSearch.getIndeterminateDrawable()).start();
       // Toast.makeText(getApplicationContext(), "rqst inside the function  " , Toast.LENGTH_SHORT).show();

        AppController.getInstance().cancelPendingRequests("cen");
        JsonObjectRequest reqthree = new JsonObjectRequest(com.android.volley.Request.Method.GET, uu, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                 DataMain feedfirst;


                feedfirst = gson.fromJson(response.toString(), DataMain.class);
              //  Toast.makeText(getApplicationContext(), "onResponce  "+feedfirst.getTotalResults() , Toast.LENGTH_SHORT).show();

                ((CircularProgressDrawable)smothCirSearch.getIndeterminateDrawable()).progressiveStop();
                smothCirSearch.setVisibility(View.INVISIBLE);



                if(feedfirst.getTotalResults()>0) {
                    lv.setVisibility(View.VISIBLE);
                    adapter.reData(feedfirst.getResults());
                }else if(feedfirst.getTotalResults()==0){
                    noRes.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "No Results" , Toast.LENGTH_SHORT).show();

                    lv.setVisibility(View.INVISIBLE);
                }
                }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Error  " + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                ((CircularProgressDrawable)smothCirSearch.getIndeterminateDrawable()).progressiveStop();
                smothCirSearch.setVisibility(View.INVISIBLE);


            }
        });

        AppController.getInstance().addToRequestQueue(reqthree);

    }
    public void makeTvSearch(String uu){
        noRes.setVisibility(View.INVISIBLE);
        smothCirSearch.setVisibility(View.VISIBLE);
        ((CircularProgressDrawable)smothCirSearch.getIndeterminateDrawable()).start();
        AppController.getInstance().cancelPendingRequests("cen");

        JsonObjectRequest reqtv = new JsonObjectRequest(com.android.volley.Request.Method.GET, uu, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                TvDataList feedfirst;


                feedfirst = gson.fromJson(response.toString(), TvDataList.class);
                ((CircularProgressDrawable)smothCirSearch.getIndeterminateDrawable()).progressiveStop();
                smothCirSearch.setVisibility(View.INVISIBLE);
                if(feedfirst.getTotalResults()>0) {
                   // adapter.reData(feedfirst.getResults());
                    lv.setVisibility(View.VISIBLE);
                    adapterTv.reData(feedfirst.getResults());
                }else if(feedfirst.getTotalResults()==0){
                    noRes.setVisibility(View.VISIBLE);
                    lv.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Sorry no result  ", Toast.LENGTH_SHORT).show();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), "Error  " + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                ((CircularProgressDrawable)smothCirSearch.getIndeterminateDrawable()).progressiveStop();
                smothCirSearch.setVisibility(View.INVISIBLE);


            }
        });

        AppController.getInstance().addToRequestQueue(reqtv);

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
/*
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_searchid).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                makeJsonCrewRequest(BaseUrl + s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.e("TAG", "text changed this is " + s);

                return false;
            }
        });

*/


        return true;
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       // if (id == R.id.action_settings) {
         //   return true;
        //}

        return super.onOptionsItemSelected(item);
    }
}
