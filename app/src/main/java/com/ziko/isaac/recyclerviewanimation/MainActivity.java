package com.ziko.isaac.recyclerviewanimation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ziko.isaac.recyclerviewanimation.Adapters.RecyclerViewAdapter;
import com.ziko.isaac.recyclerviewanimation.Model.News;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView newsRecyclerView;
    private List<News> mData;
    private FloatingActionButton fab;
    private boolean isDark = false;
    RecyclerViewAdapter adapter;
    ConstraintLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //make this activity a full screen
        makeActivityFullScreen();

        setContentView(R.layout.activity_main);

        //hide the action bar
        getSupportActionBar().hide();

        //init Views, Recycler and List.
        fab = findViewById(R.id.fab_switcher);
        rootLayout = findViewById(R.id.rootlayout);

        newsRecyclerView = findViewById(R.id.news_recycler_view);
        mData = new ArrayList<>();

        //Load the Theme State and pass to the adapter parameters below
        isDark = getThemeStatePref();
        if(isDark){
            //dark mode theme is on
            rootLayout.setBackgroundColor(getResources().getColor(R.color.black));

        } else{
            //light mode theme is on
            rootLayout.setBackgroundColor(getResources().getColor(R.color.white));

        }

        //filling the list
        fillTheList();

        //Set the Adapter
        adapterSetup();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isDark = !isDark;
                if (isDark) {
                    rootLayout.setBackgroundColor(getResources().getColor(R.color.black));
                } else {
                    rootLayout.setBackgroundColor(getResources().getColor(R.color.white));
                }

                adapter = new RecyclerViewAdapter(getApplicationContext(), mData, isDark);
                newsRecyclerView.setAdapter(adapter);
                saveThemeStatePref(isDark);
            }
        });


    }

    private void saveThemeStatePref(boolean isDark) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("myPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isDark", isDark);
        //apply in background
        editor.apply();
    }

    private boolean getThemeStatePref(){
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
        mData.add(new News("Java SE 8", "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout", "4th July 2014", R.drawable.circle2));
        mData.add(new News("Xcode Device kdfv fkbjkdjhfdhfdshfljhsdjkfhdjkfhsdkfhdskjfhdsk", "It is a long established fact that a reader will be distracted at its layout", "4th July 2024", R.drawable.circle3));
        mData.add(new News("Android Studio", "It is a long established fact that a reader will be distracted by the readable content of a page when looking  layout", "4th June 2004", R.drawable.circle1));
        mData.add(new News("After Effect", "It is a long established fact that a reader will be distracted by the readable content of", "4th July 1984", R.drawable.circle4));
        mData.add(new News("Adobe Photoshop", "It is a long established fact that a reader will be distracted by the reada its layout", "4th July 1974", R.drawable.circle5));
        mData.add(new News("Adobe Illustrator", "It is a long established fact that a reader will ben looking at its layout", "4th July 1567", R.drawable.circle6));
        mData.add(new News("Adobe Premiere", "It is a long established fact that a reader will be distracted by the readable at its layout", "4th July 1098", R.drawable.circle6));
        mData.add(new News("Java SE 8", "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout", "4th July 2014", R.drawable.circle2));
        mData.add(new News("Xcode Device", "It is a long established fact that a reader will be distracted at its layout", "4th July 2024", R.drawable.circle3));
        mData.add(new News("Android Studio", "It is a long established fact that a reader will be distracted by the readable content of a page when looking  layout", "4th June 2004", R.drawable.circle1));
        mData.add(new News("After Effect", "It is a long established fact that a reader will be distracted by the readable content of", "4th July 1984", R.drawable.circle4));
        mData.add(new News("Adobe Photoshop", "It is a long established fact that a reader will be distracted by the reada its layout", "4th July 1974", R.drawable.circle5));
        mData.add(new News("Adobe Illustrator", "It is a long established fact that a reader will ben looking at its layout", "4th July 1567", R.drawable.circle6));
        mData.add(new News("Adobe Premiere", "It is a long established fact that a reader will be distracted by the readable at its layout", "4th July 1098", R.drawable.circle6));
        mData.add(new News("Java SE 8", "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout", "4th July 2014", R.drawable.circle2));
        mData.add(new News("Xcode Device", "It is a long established fact that a reader will be distracted at its layout", "4th July 2024", R.drawable.circle3));
        mData.add(new News("Android Studio", "It is a long established fact that a reader will be distracted by the readable content of a page when looking  layout", "4th June 2004", R.drawable.circle1));
        mData.add(new News("After Effect", "It is a long established fact that a reader will be distracted by the readable content of", "4th July 1984", R.drawable.circle4));
        mData.add(new News("Adobe Photoshop", "It is a long established fact that a reader will be distracted by the reada its layout", "4th July 1974", R.drawable.circle5));
        mData.add(new News("Adobe Illustrator", "It is a long established fact that a reader will ben looking at its layout", "4th July 1567", R.drawable.circle6));
        mData.add(new News("Adobe Premiere", "It is a long established fact that a reader will be distracted by the readable at its layout", "4th July 1098", R.drawable.circle6));
        mData.add(new News("Java SE 8", "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout", "4th July 2014", R.drawable.circle2));
        mData.add(new News("Xcode Device", "It is a long established fact that a reader will be distracted at its layout", "4th July 2024", R.drawable.circle3));
        mData.add(new News("Android Studio", "It is a long established fact that a reader will be distracted by the readable content of a page when looking  layout", "4th June 2004", R.drawable.circle1));
        mData.add(new News("After Effect", "It is a long established fact that a reader will be distracted by the readable content of", "4th July 1984", R.drawable.circle4));
        mData.add(new News("Adobe Photoshop", "It is a long established fact that a reader will be distracted by the reada its layout", "4th July 1974", R.drawable.circle5));
        mData.add(new News("Adobe Illustrator", "It is a long established fact that a reader will ben looking at its layout", "4th July 1567", R.drawable.circle6));
        mData.add(new News("Adobe Premiere", "It is a long established fact that a reader will be distracted by the readable at its layout", "4th July 1098", R.drawable.circle6));
        mData.add(new News("Java SE 8", "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout", "4th July 2014", R.drawable.circle2));
        mData.add(new News("Xcode Device", "It is a long established fact that a reader will be distracted at its layout", "4th July 2024", R.drawable.circle3));
        mData.add(new News("Android Studio", "It is a long established fact that a reader will be distracted by the readable content of a page when looking  layout", "4th June 2004", R.drawable.circle1));
        mData.add(new News("After Effect", "It is a long established fact that a reader will be distracted by the readable content of", "4th July 1984", R.drawable.circle4));
        mData.add(new News("Adobe Photoshop", "It is a long established fact that a reader will be distracted by the reada its layout", "4th July 1974", R.drawable.circle5));
        mData.add(new News("Adobe Illustrator", "It is a long established fact that a reader will ben looking at its layout", "4th July 1567", R.drawable.circle6));
        mData.add(new News("Adobe Premiere", "It is a long established fact that a reader will be distracted by the readable at its layout", "4th July 1098", R.drawable.circle6));
        mData.add(new News("Java SE 8", "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout", "4th July 2014", R.drawable.circle2));
        mData.add(new News("Xcode Device", "It is a long established fact that a reader will be distracted at its layout", "4th July 2024", R.drawable.circle3));
        mData.add(new News("Android Studio", "It is a long established fact that a reader will be distracted by the readable content of a page when looking  layout", "4th June 2004", R.drawable.circle1));
        mData.add(new News("After Effect", "It is a long established fact that a reader will be distracted by the readable content of", "4th July 1984", R.drawable.circle4));
        mData.add(new News("Adobe Photoshop", "It is a long established fact that a reader will be distracted by the reada its layout", "4th July 1974", R.drawable.circle5));
        mData.add(new News("Adobe Illustrator", "It is a long established fact that a reader will ben looking at its layout", "4th July 1567", R.drawable.circle6));
        mData.add(new News("Adobe Premiere", "It is a long established fact that a reader will be distracted by the readable at its layout", "4th July 1098", R.drawable.circle6));
        mData.add(new News("Java SE 8", "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout", "4th July 2014", R.drawable.circle2));
        mData.add(new News("Xcode Device", "It is a long established fact that a reader will be distracted at its layout", "4th July 2024", R.drawable.circle3));
        mData.add(new News("Android Studio", "It is a long established fact that a reader will be distracted by the readable content of a page when looking  layout", "4th June 2004", R.drawable.circle1));
        mData.add(new News("After Effect", "It is a long established fact that a reader will be distracted by the readable content of", "4th July 1984", R.drawable.circle4));
        mData.add(new News("Adobe Photoshop", "It is a long established fact that a reader will be distracted by the reada its layout", "4th July 1974", R.drawable.circle5));
        mData.add(new News("Adobe Illustrator", "It is a long established fact that a reader will ben looking at its layout", "4th July 1567", R.drawable.circle6));
        mData.add(new News("Adobe Premiere", "It is a long established fact that a reader will be distracted by the readable at its layout", "4th July 1098", R.drawable.circle6));
        mData.add(new News("Java SE 8", "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout", "4th July 2014", R.drawable.circle2));
        mData.add(new News("Xcode Device", "It is a long established fact that a reader will be distracted at its layout", "4th July 2024", R.drawable.circle3));
        mData.add(new News("Android Studio", "It is a long established fact that a reader will be distracted by the readable content of a page when looking  layout", "4th June 2004", R.drawable.circle1));
        mData.add(new News("After Effect", "It is a long established fact that a reader will be distracted by the readable content of", "4th July 1984", R.drawable.circle4));
        mData.add(new News("Adobe Photoshop", "It is a long established fact that a reader will be distracted by the reada its layout", "4th July 1974", R.drawable.circle5));
        mData.add(new News("Adobe Illustrator", "It is a long established fact that a reader will ben looking at its layout", "4th July 1567", R.drawable.circle6));
        mData.add(new News("Adobe Premiere", "It is a long established fact that a reader will be distracted by the readable at its layout", "4th July 1098", R.drawable.circle6));
    }
}