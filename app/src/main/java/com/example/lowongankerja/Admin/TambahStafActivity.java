package com.example.lowongankerja.Admin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lowongankerja.ApiHelper.BaseApiHelper;
import com.example.lowongankerja.ApiHelper.UtilsApi;
import com.example.lowongankerja.Divisi.GetDivisi;
import com.example.lowongankerja.Divisi.ResultDivisi;
import com.example.lowongankerja.R;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TambahStafActivity extends AppCompatActivity {
    public static final String URL = "https://lokerprogmob.herokuapp.com/api/";
    List<String> sIds = new ArrayList<String>();
    EditText nStafDivisi,tTentang,gaji;
    Spinner divisi;
    Button simpan;
    ProgressDialog loading;
    BaseApiHelper mApiService;
    Context mContext;
    int id_perusahaan;
    int id_divisi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_stafdivisi);
        mContext = this;
        mApiService = UtilsApi.getAPIService();
        nStafDivisi = (EditText) findViewById(R.id.stafdivisi);
        tTentang = (EditText) findViewById(R.id.tentang);
        gaji = (EditText) findViewById(R.id.biaya);
        divisi = (Spinner) findViewById(R.id.spiner_divisi);
        simpan = (Button) findViewById(R.id.simpan_stafdivisi);
        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
                requestProdi();
            }
        });
        Intent mIntent = getIntent();
        id_perusahaan = mIntent.getIntExtra("id", 0);

        initSpinnerDivisi();

        divisi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedName = parent.getItemAtPosition(position).toString();
                id_divisi = divisi.getSelectedItemPosition() + 1;
                Toast.makeText(getBaseContext(), "Kamu memilih divisi dengan id " + id_divisi, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void requestProdi() {
        mApiService.InsertStafDivisi(nStafDivisi.getText().toString(),
                tTentang.getText().toString(), Integer.parseInt(gaji.getText().toString()),
                id_divisi,id_perusahaan).
                enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.e("debug", "onResponse: BERHASIL"+response.body());
                        if (response.isSuccessful()){

                            Log.i("debug", "onResponse: BERHASIL");
                            loading.dismiss();
                            Toast.makeText(mContext, "BERHASIL REGISTRASI", Toast.LENGTH_SHORT).show();

                            Intent mIntent = new Intent(getBaseContext(), StafDivisiActivity.class);
                            mIntent.putExtra("id", id_perusahaan);
                            startActivity(mIntent);
                        } else {
                            Toast.makeText(mContext, "Gagal", Toast.LENGTH_SHORT).show();
                            Log.i("debug", "onResponse: GA BERHASIL");
                            loading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initSpinnerDivisi() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BaseApiHelper api = retrofit.create(BaseApiHelper.class);
        Call<GetDivisi> call = api.getDivisi();
        Log.e("PROGRESSSS", "SUDAH SAMPAI SINI");
        call.enqueue(new Callback<GetDivisi>() {
            @Override
            public void onResponse(Call<GetDivisi> call, Response<GetDivisi> response) {
                if (response.isSuccessful()) {
                    List<ResultDivisi> divisiitem = response.body().getResult();
                    Log.e("masuk", "onResponse: 11");
                    List<String> listSpinner = new ArrayList<String>();
                    for (int i = 0; i < divisiitem.size(); i++){
                        listSpinner.add(divisiitem.get(i).getNama_divisi());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),
                            android.R.layout.simple_spinner_item, listSpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    divisi.setAdapter(adapter);


                } else {
                    Toast.makeText(getBaseContext(), "Gagal mengambil data divisi", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<GetDivisi> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Koneksi internet bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
