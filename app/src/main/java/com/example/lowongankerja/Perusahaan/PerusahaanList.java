package com.example.lowongankerja.Perusahaan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lowongankerja.ApiHelper.BaseApiHelper;
import com.example.lowongankerja.ApiHelper.UtilsApi;
import com.example.lowongankerja.MainActivity;
import com.example.lowongankerja.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PerusahaanList extends AppCompatActivity {
    public static final String URL = "https://guarded-woodland-53288.herokuapp.com/api/";
    private List<ResultPerusahaan> results = new ArrayList<>();
    private PerusahaanRecyclerViewAdapter viewAdapter;
    BaseApiHelper mApiService;
    private static String EXTRA = "extra";
    String nama_perusahaan = "";
    RecyclerView recyclerView;
    ProgressBar progressBar;
    FloatingActionButton gaskan;
    NestedScrollView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        test = (NestedScrollView) findViewById(R.id.nestedScrollView);
        test.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {
                    gaskan.hide();
                } else {
                    gaskan.show();
                }
            }
        });

        gaskan = (FloatingActionButton) findViewById(R.id.tambah);
        gaskan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), TambahPerusahaan.class));
                finish();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Perusahaan");
        mApiService = UtilsApi.getAPIService();
        viewAdapter = new PerusahaanRecyclerViewAdapter(this, results);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(viewAdapter);
        Log.e("m", "mantap");
        loadDataKategori();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(getBaseContext(), MainActivity.class));
                finish();
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    private void loadDataKategori() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BaseApiHelper api = retrofit.create(BaseApiHelper.class);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        Call<GetPerusahaan> call = api.getPerusahaan();
        Log.e("PROGRESSSS", "SUDAH SAMPAI SINI");
        call.enqueue(new Callback<GetPerusahaan>() {
            @Override
            public void onResponse(Call<GetPerusahaan> call, Response<GetPerusahaan> response) {
                Log.e("PROGRESSSS", "SUDAH SAMPAI SINI2");
                progressBar.setVisibility(View.GONE);
                String value = response.body().getStatus();
                results = response.body().getResult();
                Log.e("ERROR", "asa" + results.size());
                viewAdapter = new PerusahaanRecyclerViewAdapter(PerusahaanList.this, results);
                recyclerView.setAdapter(viewAdapter);

            }

            @Override
            public void onFailure(Call<GetPerusahaan> call, Throwable t) {

            }
        });
    }
}
