package com.example.aplikasiku.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasiku.R;
import com.example.aplikasiku.model.Todo;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    private final ArrayList<Todo> todoList;
    private final DatabaseReference todoRef;

    public TodoAdapter(ArrayList<Todo> todoList, DatabaseReference todoRef) {
        this.todoList = todoList;
        this.todoRef = todoRef;
    }

    @NonNull
    @Override
    public TodoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_todo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoAdapter.ViewHolder holder, int position) {
        Todo todo = todoList.get(position);
        holder.cbTodo.setText(todo.isi);
        holder.cbTodo.setChecked(todo.selesai);

        holder.cbTodo.setOnCheckedChangeListener((buttonView, isChecked) -> {
            todo.selesai = isChecked;
            todoRef.child(todo.id).setValue(todo);
        });
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox cbTodo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cbTodo = itemView.findViewById(R.id.cbTodo);
        }
    }
}
