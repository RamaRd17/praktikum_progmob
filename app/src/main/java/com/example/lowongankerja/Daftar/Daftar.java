package com.example.lowongankerja.Daftar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.lowongankerja.ApiHelper.BaseApiHelper;
import com.example.lowongankerja.ApiHelper.UtilsApi;
import com.example.lowongankerja.BottomActivity;
import com.example.lowongankerja.Database.AppDatabase;
import com.example.lowongankerja.Database.AppExecutors;
import com.example.lowongankerja.Model.DaftarPerusahaan;
import com.example.lowongankerja.Model.User;
import com.example.lowongankerja.Perusahaan.ResultPerusahaan;
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
    DaftarOfflineAdapter viewAdapterOffline;
    int id_user;
    final String SHARED_PREFERENCES_NAME = "shared_preferences";
    public final static String TAG_ID = "id";
    SharedPreferences sharedPreferences;

    AppDatabase mDb;
    List<DaftarPerusahaan> daftarPerusahaan;

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

        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
            loadDataDaftar();
        }
        else {
            connected = false;
            Toast.makeText(getApplicationContext(), "OFFLINE", Toast.LENGTH_SHORT).show();
            mDb = AppDatabase.getDatabase(getApplicationContext());
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    daftarPerusahaan = mDb.daftarPerusahaan().getDaftarPerusahaan(id_user);

                    viewAdapterOffline = new DaftarOfflineAdapter(Daftar.this, daftarPerusahaan);
                    recyclerView.setAdapter(viewAdapterOffline);

                }
            });
        }
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
                mDb = AppDatabase.getDatabase(getApplicationContext());

                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mDb.daftarPerusahaan().deleteDaftarPerusahaan();
                    }
                });
                for (ResultDaftar perusahaan:results){
                    final DaftarPerusahaan data = new DaftarPerusahaan(
                            String.valueOf(perusahaan.getId_user()),
                            perusahaan.getNama_user(),
                            perusahaan.getNama_stafdivisi(),
                            perusahaan.getNama_perusahaan(),
                            String.valueOf(perusahaan.getFee())
                    );
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            mDb.daftarPerusahaan().insertDaftarPerusahaan(data);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<GetDaftar> call, Throwable t) {

            }
        });

    }
}
