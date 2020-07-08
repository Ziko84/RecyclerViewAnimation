package com.ziko.isaac.recyclerviewanimation.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.JsonReader;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.onItemClickListener {

    private RecyclerView flowersRecyclerView;
    private List<YellowFlowerModel> mData;
    private boolean isDark = false;
    private RecyclerViewAdapter adapter;
    ConstraintLayout rootLayout;
    private EditText searchInput;
    private ArrayList<YellowFlowerModel> eSList = new ArrayList();
    private RequestQueue requestQueue;
    private SQLiteDatabase mDataBae;
    DataBaseHelper dBh;

    String creatorName = "";
    String imageURL = "";
    int likeCount = 0;

    ArrayList<String> creatorList, imgUrlList;
    ArrayList<Integer> likesList;

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

        //init Views, Recycler, List and DB.
        FloatingActionButton fab_saveToSQL = findViewById(R.id.saveToSQL_btn);
        FloatingActionButton fab = findViewById(R.id.fab_switcher);
        FloatingActionButton fab_parseJson = findViewById(R.id.parseJson_btn);
        FloatingActionButton difab_showInRV = findViewById(R.id.displayToRV_btn);
        rootLayout = findViewById(R.id.rootlayout);
        flowersRecyclerView = findViewById(R.id.news_recycler_view);
        flowersRecyclerView.setHasFixedSize(true);
        searchInput = findViewById(R.id.et_search);
        flowersRecyclerView.setVisibility(View.INVISIBLE);


        dBh = new DataBaseHelper(this);
        mDataBae = dBh.getReadableDatabase();

        //Load the Theme State and pass to the adapter parameters below
        isDark = getThemeStatePref();
        if (true) {
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


        //Set the Adapter
//        adapterSetup();

        fab_parseJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parseJSON();



            }
        });

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
//
//                adapter = new RecyclerViewAdapter(getApplicationContext(), mData, isDark);
//                flowersRecyclerView.setAdapter(adapter);
                saveThemeStatePref(isDark);
            }
        });

        fab_saveToSQL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Add Data To SQLite DB async
                AsyncTask aysnc_three = new AsyncTask(MainActivity.this);
                aysnc_three.execute(creatorName, imageURL, "" + likeCount);
                Toast.makeText(MainActivity.this, "Saved Data To SQLite DB", Toast.LENGTH_SHORT).show();

            }
        });

        difab_showInRV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flowersRecyclerView.setVisibility(View.VISIBLE);

                displayDataToRV();
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

    private void displayDataToRV() {
        //Store data in arrays
        creatorList = new ArrayList<>();
        imgUrlList = new ArrayList<>();
        likesList = new ArrayList<>();

        Cursor cursor = dBh.readAllDataFromDB();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data Available", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                creatorList.add(cursor.getString(0));
                imgUrlList.add(cursor.getString(1));
                likesList.add(cursor.getInt(2));
            }
        }
        //fill the list
//        mData.add(new YellowFlowerModel(imageURL, creatorName, likeCount));

        //set Adapter
        adapterSetup();

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
                        creatorName = hit.getString("user");
                        imageURL = hit.getString("webformatURL");
                        likeCount = hit.getInt("likes");
                    }
                    Toast.makeText(MainActivity.this, "JSON Parsed Successfully", Toast.LENGTH_SHORT).show();
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
//        adapter = new RecyclerViewAdapter(this, mData, isDark);
        adapter = new RecyclerViewAdapter(this, creatorList, imgUrlList, likesList, isDark);
        flowersRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(MainActivity.this);
        flowersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onItemclick(int position) {
        Intent detailIntent = new Intent(this, DetailedActivity.class);
//        YellowFlowerModel clickItem = mData.get(position);
        YellowFlowerModel yFM = new YellowFlowerModel(imgUrlList.get(position), creatorList.get(position), likesList.get(position));
        detailIntent.putExtra(EXTRA_CREATOR, yFM.getmCreator());
        detailIntent.putExtra(EXTRA_URL, yFM.getmImageUrl());
        detailIntent.putExtra(EXTRA_LIKES, yFM.getmLikes());
        startActivity(detailIntent);
    }

    private Cursor getAllItemsFromDB() {
        return mDataBae.query("flowers_table",
                null, null, null, null, null, null);
    }
}