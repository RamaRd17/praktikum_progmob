package com.example.lowongankerja.Daftar;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lowongankerja.ApiHelper.BaseApiHelper;
import com.example.lowongankerja.ApiHelper.UtilsApi;
import com.example.lowongankerja.Model.DaftarPerusahaan;
import com.example.lowongankerja.R;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarOfflineAdapter extends RecyclerView.Adapter<DaftarOfflineAdapter.ViewHolder>{
    private Context context;
    private List<DaftarPerusahaan> results;
    BaseApiHelper mApiService;
    String id_dafar;
    public DaftarOfflineAdapter(Context context, List<DaftarPerusahaan> results) {
        this.context = context;
        this.results = results;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_daftar, viewGroup, false);
        DaftarOfflineAdapter.ViewHolder holder = new DaftarOfflineAdapter.ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final DaftarPerusahaan result = results.get(position);

        holder.Perusahaan.setText(result.getNama_perusahaan());
        holder.Fee.setText(String.valueOf(result.getFee()));
        holder.Divisi.setText(result.getNama_stafdivisi());
//        holder.biaya.setText(String.valueOf(result.getBiaya()));
        mApiService = UtilsApi.getAPIService();
        holder.mDelete.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Perusahaan;
        TextView Fee;
        TextView Divisi;
        TextView biaya;
        ImageView mDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            Perusahaan = itemView.findViewById(R.id.perusahaan_daftar);
            Fee = itemView.findViewById(R.id.fee);
            Divisi = itemView.findViewById(R.id.stafdivisi);
            biaya = itemView.findViewById(R.id.biaya);
            mDelete = itemView.findViewById(R.id.del_daftar);


        }
    }
}
