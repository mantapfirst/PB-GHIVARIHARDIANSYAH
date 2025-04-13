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

import com.example.aplikasiku.adapter.TodoAdapter;
import com.example.aplikasiku.model.Todo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TodoActivity extends AppCompatActivity {

    private EditText etTodo;
    private Button btnTambah;
    private RecyclerView recyclerView;
    private TodoAdapter adapter;
    private ArrayList<Todo> todoList;
    private DatabaseReference todoRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        etTodo = findViewById(R.id.etTodo);
        btnTambah = findViewById(R.id.btnTambahTodo);
        recyclerView = findViewById(R.id.recyclerViewTodo);

        todoRef = FirebaseDatabase.getInstance().getReference("todo");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        todoList = new ArrayList<>();
        adapter = new TodoAdapter(todoList, todoRef);
        recyclerView.setAdapter(adapter);

        btnTambah.setOnClickListener(view -> {
            String isi = etTodo.getText().toString().trim();
            if (TextUtils.isEmpty(isi)) {
                Toast.makeText(this, "Tugas tidak boleh kosong", Toast.LENGTH_SHORT).show();
                return;
            }

            String id = todoRef.push().getKey();
            Todo todo = new Todo(id, isi, false);
            todoRef.child(id).setValue(todo);
            Toast.makeText(this, "Tugas ditambahkan", Toast.LENGTH_SHORT).show();
            etTodo.setText("");
        });

        // Ambil data dari Firebase
        todoRef.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                todoList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Todo todo = dataSnapshot.getValue(Todo.class);
                    todoList.add(todo);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TodoActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
