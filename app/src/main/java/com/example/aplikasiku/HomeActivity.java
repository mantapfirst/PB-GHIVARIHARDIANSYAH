package com.example.aplikasiku;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                return true;
            } else if (itemId == R.id.nav_profile) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                overridePendingTransition(0, 0);
                return true;
            } else if (itemId == R.id.nav_settings) {
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });


        CardView cardKeuangan = findViewById(R.id.cardKeuangan);
        CardView cardMemo = findViewById(R.id.cardMemo);
        CardView cardTodo = findViewById(R.id.cardTodo);

        cardKeuangan.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, KeuanganActivity.class);
            startActivity(intent);
        });

        cardMemo.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, MemoActivity.class);
            startActivity(intent);
        });

        cardTodo.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, TodoActivity.class);
            startActivity(intent);
        });
    }
}