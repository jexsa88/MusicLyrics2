package com.example.musiclyrics2;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ShowActivity extends AppCompatActivity {
    private TextView tvName,tvSecName;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_layout);
        init();
        getIntentMain();
    }
    public void init() {
        tvName = findViewById(R.id.tvName);
        tvSecName = findViewById(R.id.tvSecName);
    }
    //Фунция для получения Интента
    private void getIntentMain() {
        Intent i = getIntent();
        if (i != null) {
            tvName.setText(i.getStringExtra("user_name"));
            tvSecName.setText(i.getStringExtra("user_sec_name"));
        }
    }
}
