package com.example.lowongankerja.Perusahaan;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lowongankerja.Admin.StafDivisiActivity;
import com.example.lowongankerja.R;

import java.util.List;

public class PerusahaanRecyclerViewAdapter extends RecyclerView.Adapter<PerusahaanRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<ResultPerusahaan> results;

    public PerusahaanRecyclerViewAdapter(Context context,List<ResultPerusahaan> results) {
        this.context = context;
        this.results = results;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_perusahaan, parent, false);
        ViewHolder holder = new ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ResultPerusahaan result = results.get(position);
        holder.textViewKategori.setText(result.getPerusahaan());
        holder.textViewAlamat.setText(result.getAlamat());
        holder.edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(view.getContext(), UpdatePerusahaan.class);
                mIntent.putExtra("id", results.get(position).getId());
                mIntent.putExtra("nama_perusahaan", results.get(position).getPerusahaan());
                mIntent.putExtra("tentang", results.get(position).getTentang());
                mIntent.putExtra("lokasi", results.get(position).getAlamat());
                view.getContext().startActivity(mIntent);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(view.getContext(), StafDivisiActivity.class);
                mIntent.putExtra("id", results.get(position).getId());
                mIntent.putExtra("nama_perusahaan", results.get(position).getPerusahaan());
                view.getContext().startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewKategori;
        TextView textViewAlamat;
        ImageView edit_btn;


        public ViewHolder(View itemView) {
            super(itemView);
            textViewKategori = itemView.findViewById(R.id.perusahaan);
            textViewAlamat = itemView.findViewById(R.id.alamat);
            edit_btn = itemView.findViewById(R.id.edit);
        }
    }
}
