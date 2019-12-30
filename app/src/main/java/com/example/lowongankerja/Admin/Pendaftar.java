package com.example.lowongankerja.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lowongankerja.ApiHelper.BaseApiHelper;
import com.example.lowongankerja.ApiHelper.UtilsApi;
import com.example.lowongankerja.Daftar.DaftarAdminAdapter;
import com.example.lowongankerja.Daftar.GetDaftar;
import com.example.lowongankerja.Daftar.ResultDaftar;
import com.example.lowongankerja.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.lowongankerja.Admin.UpdateStafActivity.URL;

public class Pendaftar extends AppCompatActivity {
    private List<ResultDaftar> results = new ArrayList<>();
    RecyclerView recyclerView;
    BaseApiHelper mApiService;
    DaftarAdminAdapter viewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendaftar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_daftar_admin);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("List Pendaftar Info");
        mApiService = UtilsApi.getAPIService();
        viewAdapter = new DaftarAdminAdapter(this, results);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(viewAdapter);
        Log.e("m", "mantap");
        loadDataDaftar();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(getBaseContext(), Dashboard.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadDataDaftar() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BaseApiHelper api = retrofit.create(BaseApiHelper.class);
        Call<GetDaftar> call = api.daftar();
        Log.e("PROGRESSSS", "SUDAH SAMPAI SINI");
        call.enqueue(new Callback<GetDaftar>() {
            @Override
            public void onResponse(Call<GetDaftar> call, Response<GetDaftar> response) {
                Log.e("PROGRESSSS", "SUDAH SAMPAI SINI2");
                String value = response.body().getValue();
                results = response.body().getResult();
                Log.e("anjay", "onResponse: "+results );
                Log.e("ERROR", "asa" + results.size());
                viewAdapter = new DaftarAdminAdapter(Pendaftar.this, results);
                recyclerView.setAdapter(viewAdapter);
            }

            @Override
            public void onFailure(Call<GetDaftar> call, Throwable t) {

            }
        });
    }
}
