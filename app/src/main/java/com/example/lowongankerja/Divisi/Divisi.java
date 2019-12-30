package com.example.lowongankerja.Divisi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lowongankerja.Admin.Dashboard;
import com.example.lowongankerja.ApiHelper.BaseApiHelper;
import com.example.lowongankerja.ApiHelper.UtilsApi;
import com.example.lowongankerja.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.lowongankerja.Divisi.TambahDivisi.URL;

public class Divisi extends AppCompatActivity {
    private List<ResultDivisi> results = new ArrayList<>();
    RecyclerView recyclerView;
    BaseApiHelper mApiService;
    DivisiAdapter viewAdapter;
    FloatingActionButton add;
    ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_divisi);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_divisi);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Divisi Info");
        mApiService = UtilsApi.getAPIService();
        viewAdapter = new DivisiAdapter(this, results);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(viewAdapter);

        add = (FloatingActionButton) findViewById(R.id.tambah);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Divisi.this,TambahDivisi.class);
                startActivity(i);
            }
        });
        Log.e("m", "mantap");
        loadDataDiv();
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

    private void loadDataDiv() {
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
                Log.e("PROGRESSSS", "SUDAH SAMPAI SINI2");
                results = response.body().getResult();
                Log.e("anjay", "onResponse: "+results );
                Log.e("ERROR", "asa" + results.size());
                viewAdapter = new DivisiAdapter(Divisi.this, results);
                recyclerView.setAdapter(viewAdapter);
            }
            @Override
            public void onFailure(Call<GetDivisi> call, Throwable t) {
            }
        });
    }
}
