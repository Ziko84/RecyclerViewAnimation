package com.ziko.isaac.recyclerviewanimation.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.JsonReader;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ziko.isaac.recyclerviewanimation.Adapters.RecyclerViewAdapter;
import com.ziko.isaac.recyclerviewanimation.AsyncTasks.AsyncTask;
import com.ziko.isaac.recyclerviewanimation.DataBaseHelper;
import com.ziko.isaac.recyclerviewanimation.Model.EasySaleModel;
import com.ziko.isaac.recyclerviewanimation.Model.News;
import com.ziko.isaac.recyclerviewanimation.Model.YellowFlowerModel;
import com.ziko.isaac.recyclerviewanimation.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.onItemClickListener{

    private RecyclerView flowersRecyclerView;
    private List<YellowFlowerModel> mData;
    private boolean isDark = false;
    private RecyclerViewAdapter adapter;
    ConstraintLayout rootLayout;
    private EditText searchInput;
    private ArrayList<YellowFlowerModel> eSList = new ArrayList();
    private RequestQueue requestQueue;
    DataBaseHelper dBh;
    AsyncTask aSync;

    public static final String EXTRA_URL = "imageURL";
    public static final String EXTRA_CREATOR = "creatorName";
    public static final String EXTRA_LIKES = "likeCount";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //make this activity a full screen
        makeActivityFullScreen();

        setContentView(R.layout.activity_main);

        //hide the action bar
        getSupportActionBar().hide();

        //init Views, Recycler and List.
        FloatingActionButton fav_saveToSQL = findViewById(R.id.saveToSQL_btn);
        FloatingActionButton fab = findViewById(R.id.fab_switcher);
        rootLayout = findViewById(R.id.rootlayout);
        flowersRecyclerView = findViewById(R.id.news_recycler_view);
        flowersRecyclerView.setHasFixedSize(true);
        mData = new ArrayList<>();
        searchInput = findViewById(R.id.et_search);

        dBh = new DataBaseHelper(this);

        //Load the Theme State and pass to the adapter parameters below
        isDark = getThemeStatePref();
        if (isDark) {
            //dark mode theme is on
            searchInput.setBackgroundResource(R.drawable.card_bg_dark);
            rootLayout.setBackgroundColor(getResources().getColor(R.color.black));
        } else {
            //light mode theme is on
            searchInput.setBackgroundResource(R.drawable.card_bg);
            rootLayout.setBackgroundColor(getResources().getColor(R.color.white));
        }

        //init the list
        mData = new ArrayList<>();

//        getJson();

        requestQueue = Volley.newRequestQueue(this);

        parseJSON();

        //Set the Adapter
//        adapterSetup();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isDark = !isDark;
                if (isDark) {
                    searchInput.setBackgroundResource(R.drawable.card_bg_dark);
                    rootLayout.setBackgroundColor(getResources().getColor(R.color.black));
                } else {
                    searchInput.setBackgroundResource(R.drawable.card_bg);
                    rootLayout.setBackgroundColor(getResources().getColor(R.color.white));
                }

                adapter = new RecyclerViewAdapter(getApplicationContext(), mData, isDark);
                flowersRecyclerView.setAdapter(adapter);
                saveThemeStatePref(isDark);
            }
        });

        fav_saveToSQL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parseJSON();
            }
        });

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void parseJSON() {
        String url = "https://pixabay.com/api/?key=17376409-bfc9ecbbaae96da590445cc9e&q=yellow+flowers&image_type=photo&pretty=true";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("hits");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject hit = jsonArray.getJSONObject(i);
                        String creatorName = hit.getString("user");
                        String imageURL = hit.getString("webformatURL");
                        int likeCount = hit.getInt("likes");

                        //SQLite async part here

//                        new AsyncTask(MainActivity.this).execute(creatorName, imageURL, ""+likeCount);
//                        finish();
//                        AsyncTask aysnc_two = new AsyncTask(MainActivity.this);
//                        aysnc_two.execute(creatorName, imageURL, ""+likeCount);
                        AsyncTask aysnc_three = new AsyncTask(MainActivity.this);
                        aysnc_three.execute(creatorName, imageURL, ""+likeCount);


                        mData.add(new YellowFlowerModel(imageURL, creatorName, likeCount));


                    }

                    adapterSetup();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);

    }



    private void saveThemeStatePref(boolean isDark) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("myPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isDark", isDark);
        //apply in background
        editor.apply();
    }

    private boolean getThemeStatePref() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("myPref", MODE_PRIVATE);
        return sharedPreferences.getBoolean("isDark", false);
    }

    private void makeActivityFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void adapterSetup() {
        adapter = new RecyclerViewAdapter(this, mData, isDark);
        flowersRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(MainActivity.this);
        flowersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onItemclick(int position) {
        Intent detailIntent = new Intent(this, DetailedActivity.class);
        YellowFlowerModel clickItem = mData.get(position);
        detailIntent.putExtra(EXTRA_URL, clickItem.getmImageUrl());
        detailIntent.putExtra(EXTRA_CREATOR, clickItem.getmCreator());
        detailIntent.putExtra(EXTRA_LIKES, clickItem.getmLikes());
        startActivity(detailIntent);
    }
}