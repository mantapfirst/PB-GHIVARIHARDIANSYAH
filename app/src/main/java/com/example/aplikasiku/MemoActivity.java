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

import com.example.aplikasiku.adapter.MemoAdapter;
import com.example.aplikasiku.model.Memo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MemoActivity extends AppCompatActivity {

    private EditText etMemo;
    private Button btnSimpanMemo;
    private RecyclerView recyclerViewMemo;
    private MemoAdapter adapter;
    private ArrayList<Memo> memoList;
    private DatabaseReference memoRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        etMemo = findViewById(R.id.etMemo);
        btnSimpanMemo = findViewById(R.id.btnSimpanMemo);
        recyclerViewMemo = findViewById(R.id.recyclerViewMemo);

        memoRef = FirebaseDatabase.getInstance().getReference("memo");

        recyclerViewMemo.setLayoutManager(new LinearLayoutManager(this));
        memoList = new ArrayList<>();
        adapter = new MemoAdapter(memoList);
        recyclerViewMemo.setAdapter(adapter);

        btnSimpanMemo.setOnClickListener(view -> {
            String isiMemo = etMemo.getText().toString().trim();

            if (TextUtils.isEmpty(isiMemo)) {
                Toast.makeText(this, "Memo tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return;
            }

            String id = memoRef.push().getKey();
            Memo memo = new Memo(id, isiMemo);
            memoRef.child(id).setValue(memo);
            Toast.makeText(this, "Memo disimpan", Toast.LENGTH_SHORT).show();

            etMemo.setText("");
        });

        memoRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                memoList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Memo memo = dataSnapshot.getValue(Memo.class);
                    memoList.add(memo);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MemoActivity.this, "Gagal mengambil data memo", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
