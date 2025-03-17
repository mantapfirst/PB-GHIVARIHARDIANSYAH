package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.color.utilities.Cam16;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    private TextView tvCardTitle1, tvCardDescription1, tvCardTitle2, tvCardDescription2;
    private Button btnLogout;
    private FirebaseAuth mAuth;
    private static final String TAG = "HomeActivity";

    CardView cardView1, cardView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        //inisialisasi firebase auth
        mAuth = FirebaseAuth.getInstance();

        //ambil data pengguna yang sedang login
        FirebaseUser user = mAuth.getCurrentUser();

        //inisialisasi elemen UI
        tvCardTitle1 = findViewById(R.id.tvCardTitle1);
        tvCardDescription1 = findViewById(R.id.tvCardDescription1);
        tvCardTitle2 = findViewById(R.id.tvCardTitle2);
        tvCardDescription2 = findViewById(R.id.tvCardDescription2);

        if (user != null) {
            tvCardTitle1.setText("Selamat Datang, " + user.getDisplayName());
            tvCardDescription1.setText("Ini adalah halaman utama aplikasi");
        }else {
            tvCardTitle1.setText("Halo");
            tvCardDescription1.setText("Silahkan login untuk melihat lebih lanjut");

        }

        tvCardTitle1.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
            startActivity(intent);
            finish();
        });




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}