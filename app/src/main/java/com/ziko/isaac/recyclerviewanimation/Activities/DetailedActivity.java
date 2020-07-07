package com.ziko.isaac.recyclerviewanimation.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ziko.isaac.recyclerviewanimation.R;

import static com.ziko.isaac.recyclerviewanimation.Activities.MainActivity.EXTRA_CREATOR;
import static com.ziko.isaac.recyclerviewanimation.Activities.MainActivity.EXTRA_LIKES;
import static com.ziko.isaac.recyclerviewanimation.Activities.MainActivity.EXTRA_URL;

public class DetailedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(EXTRA_URL);
        String creatorName = intent.getStringExtra(EXTRA_CREATOR);
        int likes = intent.getIntExtra(EXTRA_LIKES, 0);

        ImageView imageURL = findViewById(R.id.image_view_detail);
        TextView textViewCreator = findViewById(R.id.text_view_creator_detail);
        TextView textViewlikes = findViewById(R.id.text_view_likes_detail);

        Picasso.get().load(imageUrl).fit().centerInside().into(imageURL);
        textViewCreator.setText(creatorName);
        textViewlikes.setText("Likes: " + likes);


    }
}