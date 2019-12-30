package com.example.lowongankerja.Admin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lowongankerja.ApiHelper.BaseApiHelper;
import com.example.lowongankerja.ApiHelper.UtilsApi;
import com.example.lowongankerja.Perusahaan.GetPerusahaan;
import com.example.lowongankerja.Perusahaan.PerusahaanRecyclerViewAdapter;
import com.example.lowongankerja.Perusahaan.ResultPerusahaan;
import com.example.lowongankerja.Perusahaan.TambahPerusahaan;
import com.example.lowongankerja.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AdminActivity extends AppCompatActivity {
    public static final String URL = "https://lokerprogmob.herokuapp.com/api/";
    private List<ResultPerusahaan> results = new ArrayList<>();
    private PerusahaanRecyclerViewAdapter viewAdapter;
    BaseApiHelper mApiService;
    private static String EXTRA = "extra";
    String nama_perusahaan = "";
    RecyclerView recyclerView;
    ProgressBar progressBar;
    FloatingActionButton tambah;
    NestedScrollView test;
    SharedPreferences sharedPreferences;
    boolean session = false;
    Button btnLogout;
    String token;
    Integer id_user;
    Integer admin;
    final String SHARED_PREFERENCES_NAME = "shared_preferences";
    final String SESSION_STATUS = "session";
    public final static String TAG_TOKEN = "token";
    public final static String TAG_ID = "id";
    public final static String TAG_ADMIN = "admin";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        sharedPreferences = this.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        session = sharedPreferences.getBoolean(SESSION_STATUS, false);
        token = sharedPreferences.getString(TAG_TOKEN, null);
        id_user = sharedPreferences.getInt(String.valueOf(TAG_ID),0);
        admin = sharedPreferences.getInt(String.valueOf(TAG_ADMIN),0);
        Log.d("id user", "id: "+id_user);
//        btnLogout = (Button) findViewById(R.id.logout);
//        btnLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Menghapus Status login dan kembali ke Login Activity
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.remove(SESSION_STATUS);
//                editor.remove(TAG_TOKEN);
//                editor.remove(String.valueOf(TAG_ID));
//                editor.remove(String.valueOf(TAG_ADMIN));
//                editor.apply();
//                startActivity(new Intent(getBaseContext(), AdminLoginActivity.class));
//                finish();
//            }
//        });

        tambah = (FloatingActionButton) findViewById(R.id.tambah);
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), TambahPerusahaan.class));
                finish();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.rv_admin);
        mApiService = UtilsApi.getAPIService();
        viewAdapter = new PerusahaanRecyclerViewAdapter(this, results);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(viewAdapter);
        Log.e("m", "mantap");
        loadDataKategori();

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
                viewAdapter = new PerusahaanRecyclerViewAdapter(AdminActivity.this, results);
                recyclerView.setAdapter(viewAdapter);

            }

            @Override
            public void onFailure(Call<GetPerusahaan> call, Throwable t) {
                Log.e("ERROR", "ERROR" );
            }
        });
    }


}
