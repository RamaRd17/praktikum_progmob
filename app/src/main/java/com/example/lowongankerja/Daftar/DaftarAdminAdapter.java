package com.example.lowongankerja.Daftar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lowongankerja.R;

import java.util.List;

public class DaftarAdminAdapter extends RecyclerView.Adapter<DaftarAdminAdapter.ViewHolder>{
    private Context context;
    private List<ResultDaftar> results;
    int id_dafar;

    public DaftarAdminAdapter(Context context, List<ResultDaftar> results) {
        this.context = context;
        this.results = results;

    }

    @NonNull
    @Override
    public DaftarAdminAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_daftar_admin, viewGroup, false);
        DaftarAdminAdapter.ViewHolder holder = new DaftarAdminAdapter.ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DaftarAdminAdapter.ViewHolder holder, int position) {
        final ResultDaftar result = results.get(position);
        id_dafar = result.getId_daftar();
        holder.nama.setText(result.getNama_user());
        holder.Perusahaan.setText(result.getNama_perusahaan());
        holder.Fee.setText(String.valueOf(result.getFee()));
        holder.Stafdivisi.setText(result.getNama_stafdivisi());
//        holder.biaya.setText(String.valueOf(result.getBiaya()));
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Perusahaan;
        TextView Fee;
        TextView Stafdivisi;
        TextView biaya;
        TextView nama;

        public ViewHolder(View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.nama);
            Perusahaan = itemView.findViewById(R.id.perusahaan);
            Fee = itemView.findViewById(R.id.fee);
            Stafdivisi = itemView.findViewById(R.id.stafdivisi);
            biaya = itemView.findViewById(R.id.biaya);

        }
    }
}
