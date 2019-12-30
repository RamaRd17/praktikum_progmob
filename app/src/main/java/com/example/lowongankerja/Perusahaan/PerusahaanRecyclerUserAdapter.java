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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.lowongankerja.R;

import java.util.List;

public class PerusahaanRecyclerUserAdapter extends RecyclerView.Adapter<PerusahaanRecyclerUserAdapter.ViewHolder>{
    private Context context;
    private List<ResultPerusahaan> results;

    public PerusahaanRecyclerUserAdapter(Context context,List<ResultPerusahaan> results) {
        this.context = context;
        this.results = results;

    }

    @NonNull
    @Override
    public PerusahaanRecyclerUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_perusahaan_user, viewGroup, false);
        PerusahaanRecyclerUserAdapter.ViewHolder holder = new PerusahaanRecyclerUserAdapter.ViewHolder(v);
        context = viewGroup.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PerusahaanRecyclerUserAdapter.ViewHolder holder, final int position) {
        ResultPerusahaan result = results.get(position);
        holder.textViewKategori.setText(result.getPerusahaan());
        holder.textViewAlamat.setText(result.getAlamat());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(view.getContext(), DetailActivity.class);
                mIntent.putExtra("id", results.get(position).getId());
                mIntent.putExtra("nama_perusahaan", results.get(position).getPerusahaan());
                mIntent.putExtra("tentang", results.get(position).getTentang());
                mIntent.putExtra("lokasi", results.get(position).getAlamat());
                view.getContext().startActivity(mIntent);
            }
        });
        RequestOptions myOptions = new RequestOptions()
                .override(100, 100);
        Glide.with(context)
                .asBitmap()
                .apply(myOptions)
//                .load("https://kampusjack.000webhostapp.com/image/unud.jpg")
                .load("https://pngimage.net/wp-content/uploads/2018/06/perusahaan-png-3.png")
                .into(holder.kampus);
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewKategori;
        TextView textViewAlamat;
        ImageView kampus;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewKategori = itemView.findViewById(R.id.perusahaan);
            textViewAlamat = itemView.findViewById(R.id.alamat);
            kampus = itemView.findViewById(R.id.gambar_perusahaan);
        }
    }
}
