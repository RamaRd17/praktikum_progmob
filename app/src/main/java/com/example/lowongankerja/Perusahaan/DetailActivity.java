package com.example.lowongankerja.Perusahaan;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.lowongankerja.ApiHelper.BaseApiHelper;
import com.example.lowongankerja.ApiHelper.UtilsApi;
import com.example.lowongankerja.BottomActivity;
import com.example.lowongankerja.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends AppCompatActivity {
    public static final String URL = "https://lokerprogmob.herokuapp.com/api/";
    private List<ResultDetail> results = new ArrayList<>();
    private DetailRecyclerViewAdapter viewAdapter;
    BaseApiHelper mApiService;
    private static String EXTRA = "extra";
    RecyclerView recyclerView;
    TextView edtTentang, edtLokasi;
    int intValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        edtTentang = (TextView) findViewById(R.id.deskripsi);
        edtLokasi = (TextView) findViewById(R.id.lokasi_detail);
        Intent mIntent = getIntent();
        intValue = mIntent.getIntExtra("id", 0);
        String gas = mIntent.getStringExtra("nama_perusahaan");
        String test = String.valueOf(intValue);
        Log.d("jaja", "onCreate: "+test);
        edtTentang.setText(mIntent.getStringExtra("tentang"));
        edtLokasi.setText(mIntent.getStringExtra("lokasi"));
        recyclerView = (RecyclerView) findViewById(R.id.recycler_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(gas);
        mApiService = UtilsApi.getAPIService();
        viewAdapter = new DetailRecyclerViewAdapter (this, results);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(viewAdapter);
        Log.e("m", "mantap");
        loadDataDetail();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(getBaseContext(), BottomActivity.class));
                finish();
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    private void loadDataDetail() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BaseApiHelper api = retrofit.create(BaseApiHelper.class);
        Call<GetDetail> call = api.getDetail(intValue);
        Log.e("PROGRESSSS", "SUDAH SAMPAI SINI");
        call.enqueue(new Callback<GetDetail>() {
            @Override
            public void onResponse(Call<GetDetail> call, Response<GetDetail> response) {
                Log.e("PROGRESSSS", "SUDAH SAMPAI SINI2");
                String value = response.body().getStatus();
                results = response.body().getResult();
                Log.e("anjay", "onResponse: "+results );
                Log.e("ERROR", "asa" + results.size());
                viewAdapter = new DetailRecyclerViewAdapter(DetailActivity.this, results);
                recyclerView.setAdapter(viewAdapter);
            }

            @Override
            public void onFailure(Call<GetDetail> call, Throwable t) {

            }
        });
    }
}
