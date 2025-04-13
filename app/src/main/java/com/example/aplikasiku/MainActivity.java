package com.example.aplikasiku;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText nama_pengguna;
    private TextInputEditText password;
    private CheckBox checkbox;
    private Button btnlogin;
    private TextView tvRegister;
    private TextView tvForgotPassword;
    private FirebaseAuth mAuth; 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        
        mAuth = FirebaseAuth.getInstance();
        
        nama_pengguna = findViewById(R.id.nama_pengguna);
        password = findViewById(R.id.password_toggle);
        checkbox = findViewById(R.id.checkbox);
        btnlogin = findViewById(R.id.btnlogin);
        tvRegister = findViewById(R.id.tv_register_clickable);
        tvForgotPassword = findViewById(R.id.tv_forgot_password);
        
        btnlogin.setOnClickListener(v -> {
            String email = nama_pengguna.getText().toString().trim();
            String pass = password.getText().toString().trim();
            
            if (email.isEmpty()) {
                nama_pengguna.setError("Masukkan email");
                nama_pengguna.requestFocus();
                return;
            }

            if (pass.isEmpty()) {
                password.setError("Masukkan password");
                password.requestFocus();
                return;
            }
            
            mAuth.signInWithEmailAndPassword(email,pass)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "Login berhasil", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();

                        }else{
                            Toast.makeText(MainActivity.this, "Login gagal", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(intent);
        });



    }
}