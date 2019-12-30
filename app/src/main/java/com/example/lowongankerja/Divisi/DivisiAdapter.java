package com.example.lowongankerja.Divisi;

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
import com.example.lowongankerja.R;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DivisiAdapter extends RecyclerView.Adapter<DivisiAdapter.ViewHolder> {
    private Context context;
    private List<ResultDivisi> results;
    BaseApiHelper mApiService;
    int id_divisi;
    public DivisiAdapter(Context context, List<ResultDivisi> results) {
        this.context = context;
        this.results = results;

    }
    @NonNull
    @Override
    public DivisiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_divisi, viewGroup, false);
        DivisiAdapter.ViewHolder holder = new DivisiAdapter.ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DivisiAdapter.ViewHolder holder, final int position) {
        final ResultDivisi result = results.get(position);
        id_divisi = result.getId();
        holder.divisi.setText(result.getNama_divisi());
        holder.mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(view.getContext(), UpdateDivisi.class);
                mIntent.putExtra("id", String.valueOf(results.get(position).getId()));
                mIntent.putExtra("nama_divisi", results.get(position).getNama_divisi());
                view.getContext().startActivity(mIntent);
            }
        });

        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SweetAlertDialog sDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
                sDialog.setTitle("Hapus Data");
                sDialog.setContentText("Ingin menghapus data ?");
                sDialog.setConfirmButton("Ya", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        mApiService.deleteDivisi(id_divisi).
                                enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        if (response.isSuccessful()){
                                            Log.i("debug", "onResponse: BERHASIL");
                                            Toast.makeText(context, "BERHASIL MENGHAPUS", Toast.LENGTH_SHORT).show();
                                            Intent mIntent = new Intent(context, Divisi.class);
                                            context.startActivity(mIntent);
                                        } else {
                                            Toast.makeText(context, "Gagal", Toast.LENGTH_SHORT).show();
                                            Log.i("debug", "onResponse: GA BERHASIL");
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                                        Toast.makeText(context, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                                    }
                                });
                        sDialog.dismissWithAnimation();
                    }
                });
                sDialog.setCancelButton("Tidak", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sDialog.dismissWithAnimation();
                    }
                });
                sDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView divisi;
        ImageView mDelete;
        ImageView mEdit;

        public ViewHolder(View itemView) {
            super(itemView);
            divisi = itemView.findViewById(R.id.divisi);
            mDelete = itemView.findViewById(R.id.delete);
            mEdit = itemView.findViewById(R.id.edit);

            mApiService = UtilsApi.getAPIService();

        }
    }
}
