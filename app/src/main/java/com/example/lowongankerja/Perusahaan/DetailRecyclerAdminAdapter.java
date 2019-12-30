package com.example.lowongankerja.Perusahaan;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lowongankerja.Admin.UpdateStafActivity;
import com.example.lowongankerja.R;

import java.util.List;

public class DetailRecyclerAdminAdapter extends RecyclerView.Adapter<DetailRecyclerAdminAdapter.ViewHolder> {
    private Context context;
    private List<ResultDetail> results;

    public DetailRecyclerAdminAdapter(Context context, List<ResultDetail> results) {
        this.context = context;
        this.results = results;

    }
    @NonNull
    @Override
    public DetailRecyclerAdminAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_detail, viewGroup, false);
        DetailRecyclerAdminAdapter.ViewHolder holder = new DetailRecyclerAdminAdapter.ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DetailRecyclerAdminAdapter.ViewHolder holder, final int position) {

        final ResultDetail result = results.get(position);
        Log.e("ggwp",result.toString());
        holder.StafDivisi.setText(result.getNama_stafdivisi());
        holder.Deskripsi.setText(result.getTentang_stafdivisi());
        holder.Divisi.setText(result.getNama_divisi());
        holder.Gaji.setText(String.valueOf(result.getBiaya()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(view.getContext(), UpdateStafActivity.class);
                mIntent.putExtra("id", results.get(position).getId_stafdivisi());
                mIntent.putExtra("id_perusahaan", results.get(position).getId_perusahaan());
                mIntent.putExtra("nama_stafdivisi", results.get(position).getNama_stafdivisi());
                mIntent.putExtra("divisi", results.get(position).getNama_divisi());
                mIntent.putExtra("tentang", results.get(position).getTentang_stafdivisi());
                mIntent.putExtra("biaya",results.get(position).getBiaya());
                context.startActivity(mIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView StafDivisi;
        TextView Deskripsi;
        TextView Divisi;
        TextView Gaji;

        public ViewHolder(View itemView) {
            super(itemView);
            StafDivisi = itemView.findViewById(R.id.stafdivisi);
            Deskripsi = itemView.findViewById(R.id.tentang_stafdivisi);
            Divisi = itemView.findViewById(R.id.divisi);
            Gaji = itemView.findViewById(R.id.biaya);
        }
    }
}
