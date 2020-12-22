package com.lumadimusalia.su_cafeteria2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CafesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafes);
    }


    public void loadmenu(View view) {
        startActivity(new Intent(this, HomeActivity.class));
    }
}
