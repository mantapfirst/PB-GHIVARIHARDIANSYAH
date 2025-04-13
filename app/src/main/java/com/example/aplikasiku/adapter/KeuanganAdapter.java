package com.example.aplikasiku.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasiku.R;
import com.example.aplikasiku.model.Keuangan;

import java.util.ArrayList;

public class KeuanganAdapter extends RecyclerView.Adapter<KeuanganAdapter.ViewHolder> {

    private final ArrayList<Keuangan> listData;

    public KeuanganAdapter(ArrayList<Keuangan> listData) {
        this.listData = listData;
    }

    @NonNull
    @Override
    public KeuanganAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_keuangan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KeuanganAdapter.ViewHolder holder, int position) {
        Keuangan data = listData.get(position);
        holder.tvKeterangan.setText(data.keterangan);
        holder.tvJumlah.setText("Rp " + data.jumlah);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvKeterangan, tvJumlah;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvKeterangan = itemView.findViewById(R.id.tvKeterangan);
            tvJumlah = itemView.findViewById(R.id.tvJumlah);
        }
    }
}
