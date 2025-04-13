package com.example.aplikasiku;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasiku.model.Keuangan;
import com.example.aplikasiku.adapter.KeuanganAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class KeuanganActivity extends AppCompatActivity {

    private EditText etKeterangan, etJumlah;
    private Button btnSimpan;
    private RecyclerView recyclerView;
    private KeuanganAdapter adapter;
    private ArrayList<Keuangan> dataList;

    private DatabaseReference keuanganRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keuangan);

        // Inisialisasi Views
        etKeterangan = findViewById(R.id.etKeterangan);
        etJumlah = findViewById(R.id.etJumlah);
        btnSimpan = findViewById(R.id.btnSimpan);
        recyclerView = findViewById(R.id.recyclerViewKeuangan);

        // Inisialisasi Firebase Database Reference
        keuanganRef = FirebaseDatabase.getInstance().getReference("keuangan");

        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataList = new ArrayList<>();
        adapter = new KeuanganAdapter(dataList);
        recyclerView.setAdapter(adapter);

        // Menyimpan data ke Firebase saat tombol simpan diklik
        btnSimpan.setOnClickListener(view -> {
            String keterangan = etKeterangan.getText().toString().trim();
            String jumlah = etJumlah.getText().toString().trim();

            if (TextUtils.isEmpty(keterangan) || TextUtils.isEmpty(jumlah)) {
                Toast.makeText(this, "Lengkapi semua data", Toast.LENGTH_SHORT).show();
                return;
            }

            // Menambahkan data ke Firebase
            String id = keuanganRef.push().getKey();
            Keuangan data = new Keuangan(id, keterangan, jumlah);
            keuanganRef.child(id).setValue(data);
            Toast.makeText(this, "Data disimpan", Toast.LENGTH_SHORT).show();

            // Reset input fields
            etKeterangan.setText("");
            etJumlah.setText("");
        });

        // Mendengarkan perubahan data di Firebase dan memperbarui RecyclerView
        keuanganRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear(); // Hapus data sebelumnya
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Keuangan data = dataSnapshot.getValue(Keuangan.class); // Ambil data dari Firebase
                    dataList.add(data); // Masukkan data ke dalam list
                }
                adapter.notifyDataSetChanged(); // Memberitahukan adapter untuk memperbarui tampilan
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(KeuanganActivity.this, "Gagal ambil data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
