package com.ziko.isaac.recyclerviewanimation.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.ziko.isaac.recyclerviewanimation.Model.EasySaleModel;
import com.ziko.isaac.recyclerviewanimation.Model.News;
import com.ziko.isaac.recyclerviewanimation.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView newsRecyclerView;
    private List<News> mData;
    private boolean isDark = false;
    RecyclerViewAdapter adapter;
    ConstraintLayout rootLayout;
    private EditText searchInput;
    private ArrayList<EasySaleModel> eSList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //make this activity a full screen
        makeActivityFullScreen();

        setContentView(R.layout.activity_main);

        //hide the action bar
        getSupportActionBar().hide();

        //init Views, Recycler and List.
        FloatingActionButton fab = findViewById(R.id.fab_switcher);
        rootLayout = findViewById(R.id.rootlayout);
        newsRecyclerView = findViewById(R.id.news_recycler_view);
        mData = new ArrayList<>();
        searchInput = findViewById(R.id.et_search);

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

        //filling the list
        fillTheList();

//        getJson();


//        parseJSON();

        //Set the Adapter
        adapterSetup();

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
                newsRecyclerView.setAdapter(adapter);
                saveThemeStatePref(isDark);
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
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        String url = "https://pixabay.com/api/?key={ KEY }&q=yellow+flowers&image_type=photo\n";
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
                        System.out.println(imageURL);                    }
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
        mRequestQueue.add(request);

    }

    public String getJson() {
        String json = "";
        try {
            InputStream is = getAssets().open("easysale.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            json = new String(buffer, "utf-8");
            JSONObject jsonObject = new JSONObject(json);

            JSONObject dataObject = jsonObject.getJSONObject("data");
            JSONArray itemListArray = dataObject.getJSONArray("item_list");
            for (int i = 0; i < itemListArray.length(); i++) {
                JSONObject finalObject = itemListArray.getJSONObject(i);
                int item_id = finalObject.getInt("item_id");

            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return json;
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
        newsRecyclerView.setAdapter(adapter);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void fillTheList() {
        mData.add(new News("מלל חופשי", "לורם איפסום דולור סיט אמט, קונסקטורר אדיפיסינג אלית נולום ארווס סאפיאן - פוסיליס קוויס, אקווזמן קוואזי במר מודוף. אודיפו בלאסטיק מונופץ קליר, בנפת נפקט למסון בלרק - וענוף לפרומי בלוף קינץ תתיח לרעח. לת צשחמי הועניב היושבב שערש שמחויט - שלושע ותלברו חשלו שעותלשך וחאית נובש ערששף. זותה מנק הבקיץ אפאח דלאמת יבש, כאנה ניצאחו נמרגי שהכים תוק, הדש שנרא התידם הכייר וק", "4th July 2014", R.drawable.circle2));
        mData.add(new News("cat", "It is a long established fact that a reader will be distracted at its layout", "4th July 2024", R.drawable.circle3));
        mData.add(new News("מסופון ריווחית", "It is a long established fact that a reader will be distracted by the readable content of a page when looking  layout", "4th June 2004", R.drawable.circle1));
        mData.add(new News("פרח", "It is a long established fact that a reader will be distracted by the readable content of", "4th July 1984", R.drawable.circle4));
        mData.add(new News("גלובוס", "It is a long established fact that a reader will be distracted by the reada its layout", "4th July 1974", R.drawable.circle5));
        mData.add(new News("בראץ", "It is a long established fact that a reader will ben looking at its layout", "4th July 1567", R.drawable.circle6));
        mData.add(new News("מיני מאוס", "It is a long established fact that a reader will be distracted by the readable at its layout", "4th July 1098", R.drawable.circle6));
        mData.add(new News("מלל חופשי", "לורם איפסום דולור סיט אמט, קונסקטורר אדיפיסינג אלית נולום ארווס סאפיאן - פוסיליס קוויס, אקווזמן קוואזי במר מודוף. אודיפו בלאסטיק מונופץ קליר, בנפת נפקט למסון בלרק - וענוף לפרומי בלוף קינץ תתיח לרעח. לת צשחמי הועניב היושבב שערש שמחויט - שלושע ותלברו חשלו שעותלשך וחאית נובש ערששף. זותה מנק הבקיץ אפאח דלאמת יבש, כאנה ניצאחו נמרגי שהכים תוק, הדש שנרא התידם הכייר וק", "4th July 2014", R.drawable.circle2));
        mData.add(new News("חתול", "It is a long established fact that a reader will be distracted at its layout", "4th July 2024", R.drawable.circle3));
        mData.add(new News("מסופון ריווחית", "It is a long established fact that a reader will be distracted by the readable content of a page when looking  layout", "4th June 2004", R.drawable.circle1));
        mData.add(new News("פרח", "It is a long established fact that a reader will be distracted by the readable content of", "4th July 1984", R.drawable.circle4));
        mData.add(new News("גלובוס", "It is a long established fact that a reader will be distracted by the reada its layout", "4th July 1974", R.drawable.circle5));
        mData.add(new News("בראץ", "It is a long established fact that a reader will ben looking at its layout", "4th July 1567", R.drawable.circle6));
        mData.add(new News("מיני מאוס", "It is a long established fact that a reader will be distracted by the readable at its layout", "4th July 1098", R.drawable.circle6));
        mData.add(new News("מלל חופשי", "לורם איפסום דולור סיט אמט, קונסקטורר אדיפיסינג אלית נולום ארווס סאפיאן - פוסיליס קוויס, אקווזמן קוואזי במר מודוף. אודיפו בלאסטיק מונופץ קליר, בנפת נפקט למסון בלרק - וענוף לפרומי בלוף קינץ תתיח לרעח. לת צשחמי הועניב היושבב שערש שמחויט - שלושע ותלברו חשלו שעותלשך וחאית נובש ערששף. זותה מנק הבקיץ אפאח דלאמת יבש, כאנה ניצאחו נמרגי שהכים תוק, הדש שנרא התידם הכייר וק", "4th July 2014", R.drawable.circle2));
        mData.add(new News("חתול", "It is a long established fact that a reader will be distracted at its layout", "4th July 2024", R.drawable.circle3));
        mData.add(new News("מסופון ריווחית", "It is a long established fact that a reader will be distracted by the readable content of a page when looking  layout", "4th June 2004", R.drawable.circle1));
        mData.add(new News("פרח", "It is a long established fact that a reader will be distracted by the readable content of", "4th July 1984", R.drawable.circle4));
        mData.add(new News("גלובוס", "It is a long established fact that a reader will be distracted by the reada its layout", "4th July 1974", R.drawable.circle5));
        mData.add(new News("בראץ", "It is a long established fact that a reader will ben looking at its layout", "4th July 1567", R.drawable.circle6));
        mData.add(new News("מיני מאוס", "It is a long established fact that a reader will be distracted by the readable at its layout", "4th July 1098", R.drawable.circle6));
        mData.add(new News("מלל חופשי", "לורם איפסום דולור סיט אמט, קונסקטורר אדיפיסינג אלית נולום ארווס סאפיאן - פוסיליס קוויס, אקווזמן קוואזי במר מודוף. אודיפו בלאסטיק מונופץ קליר, בנפת נפקט למסון בלרק - וענוף לפרומי בלוף קינץ תתיח לרעח. לת צשחמי הועניב היושבב שערש שמחויט - שלושע ותלברו חשלו שעותלשך וחאית נובש ערששף. זותה מנק הבקיץ אפאח דלאמת יבש, כאנה ניצאחו נמרגי שהכים תוק, הדש שנרא התידם הכייר וק", "4th July 2014", R.drawable.circle2));
        mData.add(new News("חתול", "It is a long established fact that a reader will be distracted at its layout", "4th July 2024", R.drawable.circle3));
        mData.add(new News("מסופון ריווחית", "It is a long established fact that a reader will be distracted by the readable content of a page when looking  layout", "4th June 2004", R.drawable.circle1));
        mData.add(new News("פרח", "It is a long established fact that a reader will be distracted by the readable content of", "4th July 1984", R.drawable.circle4));
        mData.add(new News("גלובוס", "It is a long established fact that a reader will be distracted by the reada its layout", "4th July 1974", R.drawable.circle5));
        mData.add(new News("בראץ", "It is a long established fact that a reader will ben looking at its layout", "4th July 1567", R.drawable.circle6));
        mData.add(new News("מיני מאוס", "It is a long established fact that a reader will be distracted by the readable at its layout", "4th July 1098", R.drawable.circle6));

    }
}