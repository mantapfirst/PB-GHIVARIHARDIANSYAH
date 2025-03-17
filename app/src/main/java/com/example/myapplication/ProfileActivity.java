package com.example.myapplication;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvNamaUser,  tvEmailUser,  tvNimUser;
    private Button btnLogout;
    private FirebaseAuth mAuth;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();

        tvNamaUser = findViewById(R.id.tvNamaUser);
        tvEmailUser = findViewById(R.id.tvEmailUser);
        tvNimUser = findViewById(R.id.tvNimUser);
        btnLogout = findViewById(R.id.btnLogout);

        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            tvEmailUser.setText(user.getEmail()); //menampilkan email pengguna

            //cek apakah pengguna memiliki displayName
            String displayName = user.getDisplayName();
            if (displayName != null && !displayName.isEmpty()) {
                tvNamaUser.setText(displayName);
            }else {
                tvNamaUser.setText("Nama pengguna idak ditemukan");
            }
            tvNimUser.setText("NIM belum tersedia");
        }else {
            Toast.makeText(this, "User tidak ditemukan", Toast.LENGTH_SHORT).show();

        }

        btnLogout.setOnClickListener(v -> {
            mAuth.signOut();
            Toast.makeText(this, "Logout berhasil", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.profile_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}