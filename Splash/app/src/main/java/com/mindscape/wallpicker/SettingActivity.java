package com.mindscape.wallpicker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//import com.android.wallpicker.R;
import com.firebase.ui.auth.AuthUI;

/**
 * Created by Hakimi on 20/3/2020.
 */
public class SettingActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        button = findViewById(R.id.buttonSignOut);

        TextView titlebar = findViewById(R.id.titleToolbar);
        titlebar.setText(R.string.settingToolbar);
    }

    public void signOut(View view) {
        AuthUI.getInstance().signOut(this);
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
