package com.example.aplikasiku;



import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



public class MainActivity2 extends AppCompatActivity {

    private TextInputEditText usernameSignUp;
    private TextInputEditText emailPengguna;
    private TextInputEditText passwordSignUp;
    private TextInputEditText nimPengguna;
    private Button SignUpBtn;
    private FirebaseAuth mAuth;
    private static final String TAG = "MainActivity2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);


        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("users");

        SignUpBtn = findViewById(R.id.btnsingUp);
        usernameSignUp = findViewById(R.id.nama_pengguna);
        emailPengguna = findViewById(R.id.email_toggle);
        passwordSignUp = findViewById(R.id.password_toggle);
        nimPengguna = findViewById(R.id.nim_toggle);

        SignUpBtn.setOnClickListener(view -> {
            String username = usernameSignUp.getText().toString().trim();
            String email = emailPengguna.getText().toString().trim();
            String password = passwordSignUp.getText().toString().trim();
            String nim = nimPengguna.getText().toString().trim();

            if (TextUtils.isEmpty(username)) {
                usernameSignUp.setError("Masukkan username");
                usernameSignUp.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(email)) {
                emailPengguna.setError("Masukkan email");
                emailPengguna.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                passwordSignUp.setError("Masukkan password");
                passwordSignUp.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(nim)) {
                nimPengguna.setError("Masukkan NIM");
                nimPengguna.requestFocus();
                return;
            }

            Toast.makeText(MainActivity2.this, "Sign Up berhasil", Toast.LENGTH_SHORT).show();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();

                            if (user != null) {
                                String userId = user.getUid();

                                User newUser = new User(username, email, nim); // Ganti dengan data yang sesuai
                                usersRef.child(userId).setValue(newUser);
                            }
                            Toast.makeText(MainActivity2.this, "Sign Up berhasil", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(MainActivity2.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(MainActivity2.this, "Sign Up gagal", Toast.LENGTH_SHORT).show();
                        }
                    });

            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });

        });




    }
}