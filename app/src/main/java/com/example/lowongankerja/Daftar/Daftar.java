package com.example.lowongankerja.Daftar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

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

import static com.example.lowongankerja.Perusahaan.DetailActivity.URL;

public class Daftar extends AppCompatActivity{
    private List<ResultDaftar> results = new ArrayList<>();
    RecyclerView recyclerView;
    BaseApiHelper mApiService;
    DaftarAdapter viewAdapter;
    int id_user;
    final String SHARED_PREFERENCES_NAME = "shared_preferences";
    public final static String TAG_ID = "id";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        sharedPreferences = getBaseContext().getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        id_user = sharedPreferences.getInt(String.valueOf(TAG_ID),0);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_daftar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Apply History");
        mApiService = UtilsApi.getAPIService();
//        viewAdapter = new DaftarAdapter(this, results);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(viewAdapter);
        Log.e("m", "mantap");
        loadDataDaftar();

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

    private void loadDataDaftar() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BaseApiHelper api = retrofit.create(BaseApiHelper.class);
        Log.e("id_user",String.valueOf(id_user));
        Call<GetDaftar> call = api.GetDaftar(String.valueOf(id_user));
        Log.e("PROGRESSSS", "SUDAH SAMPAI SINI");
        call.enqueue(new Callback<GetDaftar>() {
            @Override
            public void onResponse(Call<GetDaftar> call, Response<GetDaftar> response) {
                Log.e("PROGRESSSS", "SUDAH SAMPAI SINI2");
                String value = response.body().getValue();
                results = response.body().getResult();
                Log.e("anjay", "onResponse: "+results );
                Log.e("ERROR", "asa" + results.size());
                viewAdapter = new DaftarAdapter(Daftar.this, results);
                recyclerView.setAdapter(viewAdapter);
            }

            @Override
            public void onFailure(Call<GetDaftar> call, Throwable t) {

            }
        });

    }
}
