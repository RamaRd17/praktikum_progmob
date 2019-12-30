package com.example.lowongankerja.Perusahaan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lowongankerja.ApiHelper.BaseApiHelper;
import com.example.lowongankerja.ApiHelper.UtilsApi;
import com.example.lowongankerja.BottomActivity;
import com.example.lowongankerja.Database.AppDatabase;
import com.example.lowongankerja.Database.AppExecutors;
import com.example.lowongankerja.Model.DaftarPerusahaan;
import com.example.lowongankerja.Model.User;
import com.example.lowongankerja.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailRecyclerViewAdapter extends RecyclerView.Adapter<DetailRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<ResultDetail> results;
    BaseApiHelper Apihelper;
    int id_user;
    AppDatabase mDb;
    final String SHARED_PREFERENCES_NAME = "shared_preferences";
    public final static String TAG_ID = "id";
    SharedPreferences sharedPreferences;

    public DetailRecyclerViewAdapter(Context context,List<ResultDetail> results) {
        this.context = context;
        this.results = results;

    }
    @NonNull
    @Override
    public DetailRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_detail, viewGroup, false);
        DetailRecyclerViewAdapter.ViewHolder holder = new DetailRecyclerViewAdapter.ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DetailRecyclerViewAdapter.ViewHolder holder, final int position) {
        final ResultDetail result = results.get(position);
        holder.StafDivisi.setText(result.getNama_stafdivisi());
        holder.Deskripsi.setText(result.getTentang_stafdivisi());
        holder.Divisi.setText(result.getNama_divisi());
        holder.Gaji.setText(String.valueOf(result.getBiaya()));

        Apihelper = UtilsApi.getAPIService();
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        id_user = sharedPreferences.getInt(TAG_ID,0);
        final String nama_user = sharedPreferences.getString("nama_user","");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final SweetAlertDialog sDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
                sDialog.setTitle("Daftar via Loker");
                sDialog.setContentText("Ingin Daftar di " + results.get(position).getNama_stafdivisi() + " ?");
                sDialog.setConfirmButton("Ya", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        Apihelper.Daftar(results.get(position).getNama_perusahaan(),results.get(position).getBiaya(),id_user,
                                results.get(position).getId_stafdivisi()).
                                enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        if (response.isSuccessful()) {
                                            JSONObject jsonRESULTS = null;
                                            try {
                                                jsonRESULTS = new JSONObject(response.body().string());
                                                if (jsonRESULTS.getString("status").equals("success")) {

                                                    //insert into room (lokal storage)

                                                    mDb = AppDatabase.getDatabase(context);
                                                    final DaftarPerusahaan data = new DaftarPerusahaan(
                                                            String.valueOf(id_user),
                                                            nama_user,
                                                            results.get(position).getNama_stafdivisi(),
                                                            results.get(position).getNama_perusahaan(),
                                                            String.valueOf(results.get(position).getBiaya())
                                                    );
                                                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            mDb.daftarPerusahaan().insertDaftarPerusahaan(data);
                                                        }
                                                    });

                                                    Log.i("debug", "onResponse: BERHASIL");
                                                    Toast.makeText(context, "Berhasil Melamar Kerja", Toast.LENGTH_SHORT).show();
                                                    Intent mIntent = new Intent(context, BottomActivity.class);
                                                    context.startActivity(mIntent);
                                                }else if(jsonRESULTS.getString("status").equals("error")){
                                                    Toast.makeText(context, "Anda Sudah Terdaftar", Toast.LENGTH_SHORT).show();
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }

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
