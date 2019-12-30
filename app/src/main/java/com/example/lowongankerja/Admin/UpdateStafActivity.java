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

public class UpdateStafActivity extends AppCompatActivity {
    public static final String URL = "https://lokerprogmob.herokuapp.com/api/";
    EditText nStafDivisi,tTentang,gaji;
    Button update,delete;
    Spinner divisi;
    Button simpan;
    ProgressDialog loading;
    BaseApiHelper mApiService;
    String namaDivisi;
    Context mContext;
    int id_perusahaan,fee;
    int id_divisi;
    int id_stafdivisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_stafdivisi);

        nStafDivisi = (EditText) findViewById(R.id.stafdivisi);
        tTentang = (EditText) findViewById(R.id.tentang);
        gaji = (EditText) findViewById(R.id.biaya);
        Intent mIntent = getIntent();
        id_stafdivisi = mIntent.getIntExtra("id", 0);
        id_perusahaan = mIntent.getIntExtra("id_perusahaan", 0);
        fee = mIntent.getIntExtra("biaya",0);
        namaDivisi = mIntent.getStringExtra("divisi");
        String test = String.valueOf(id_perusahaan);
        Log.d("jaja", "onCreate: "+test);
        nStafDivisi.setText(mIntent.getStringExtra("nama_stafdivisi"));
        tTentang.setText(mIntent.getStringExtra("tentang"));
        gaji.setText(String.valueOf(fee));
        divisi = (Spinner) findViewById(R.id.spiner_divisi);
        mContext = this;
        mApiService = UtilsApi.getAPIService();

        initSpinnerDivisi();
        initComponents();
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
                    final List<ResultDivisi> divisiitem = response.body().getResult();
                    Log.e("masuk", "onResponse: 11");
                    List<String> listSpinner = new ArrayList<String>();

                    int counter = 0;
                    int position = 0;
                    Log.e("dvisiissdfaw",namaDivisi);
                    for (int i = 0; i < divisiitem.size(); i++){
                        listSpinner.add(divisiitem.get(i).getNama_divisi());
                        if (divisiitem.get(i).getNama_divisi().equals(namaDivisi)){
                            position = counter;
                        }
                        counter += 1;
                    }
                    Log.e("posisition", ""+position);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),
                            android.R.layout.simple_spinner_item, listSpinner);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    divisi.setAdapter(adapter);
                    divisi.setSelection(position);
                    id_divisi = divisiitem.get(position).getId();
                    divisi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String selectedName = parent.getItemAtPosition(position).toString();
                            id_divisi = divisiitem.get(position).getId();
                            Toast.makeText(getBaseContext(), "Kamu memilih divisi dengan id " + id_divisi, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

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

    private void initComponents() {
        update = (Button) findViewById(R.id.update_stafdivisi);
        delete = (Button) findViewById(R.id.hapus_stafdivisi);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStafDivisi();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteStafDivisi();
            }
        });

    }

    private void deleteStafDivisi() {
        mApiService.DeleteStafDivisi(id_stafdivisi).
                enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            Log.i("debug", "onResponse: BERHASIL");
                            Toast.makeText(mContext, "BERHASIL MENGHAPUS", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(mContext, AdminActivity.class));
                        } else {
                            Toast.makeText(mContext, "Gagal", Toast.LENGTH_SHORT).show();
                            Log.i("debug", "onResponse: GA BERHASIL");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private void updateStafDivisi() {
        mApiService.UpdateStafDivisi(id_stafdivisi,nStafDivisi.getText().toString(),
                tTentang.getText().toString(),
                Integer.parseInt(gaji.getText().toString()),id_divisi,id_perusahaan).
                enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){

                            Log.i("debug", "onResponse: BERHASIL");
                            Toast.makeText(mContext, "BERHASIL UPDATE", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(mContext, AdminActivity.class));
                        } else {
                            Toast.makeText(mContext, "Gagal", Toast.LENGTH_SHORT).show();
                            Log.i("debug", "onResponse: GA BERHASIL");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
