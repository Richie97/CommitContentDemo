package com.ericrichardson.commitcontent;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((CommitEditText) findViewById(R.id.edit)).setCommitListener(new CommitEditText.CommitListener() {
            @Override
            public void onCommitContent(Uri uri) {
                ImageView imageView = (ImageView) findViewById(R.id.image);
                GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
                Glide.with(MainActivity.this).load(uri).into(imageViewTarget);
            }
        });
    }
}
