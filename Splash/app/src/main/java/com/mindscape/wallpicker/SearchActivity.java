package com.mindscape.wallpicker;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//import com.android.wallpicker.R;

/**
 * Created by Hakimi on 24/3/2020.
 */
public class SearchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        final EditText editText = findViewById(R.id.searchEditText);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    String searchText = editText.getText().toString().replace(" ", "+");
                    Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                    intent.putExtra("Query", searchText);
                    startActivity(intent);
                    finish();
                    return true;
                }
                return false;
            }
        });
    }
}
