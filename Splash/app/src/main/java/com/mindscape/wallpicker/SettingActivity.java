package com.mindscape.wallpicker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//import com.android.wallpicker.R;
import com.firebase.ui.auth.AuthUI;

import java.io.File;

/**
 * Created by Hakimi on 20/3/2020.
 */
public class SettingActivity extends AppCompatActivity {

    private Button button, button2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        button = findViewById(R.id.buttonSignOut);
        button2 = findViewById(R.id.clearCacheButton);

        TextView titlebar = findViewById(R.id.titleToolbar);
        titlebar.setText(R.string.settingToolbar);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCache(SettingActivity.this);
                Toast.makeText(SettingActivity.this, "Cache cleared!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void signOut(View view) {
        AuthUI.getInstance().signOut(this);
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }


    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) { e.printStackTrace();}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }


}
