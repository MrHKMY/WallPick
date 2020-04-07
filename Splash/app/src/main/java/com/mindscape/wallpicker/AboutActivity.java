package com.mindscape.wallpicker;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mindscape.wallpicker.R;

/**
 * Created by Hakimi on 20/3/2020.
 */
public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);

        TextView titlebar = findViewById(R.id.titleToolbar);
        titlebar.setText("About");

        LinearLayout gitLayout = findViewById(R.id.githubLinearLayout);
        gitLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://github.com/MrHKMY";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        LinearLayout tweetLayout = findViewById(R.id.twitterLinearLayout);
        tweetLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://twitter.com/MrHKMY";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }
}
