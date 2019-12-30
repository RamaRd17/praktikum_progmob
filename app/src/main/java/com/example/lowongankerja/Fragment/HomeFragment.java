package com.example.lowongankerja.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.lowongankerja.ApiHelper.BaseApiHelper;
import com.example.lowongankerja.ApiHelper.UtilsApi;
import com.example.lowongankerja.Daftar.Daftar;
import com.example.lowongankerja.Perusahaan.GetPerusahaan;
import com.example.lowongankerja.Perusahaan.PerusahaanRecyclerUserAdapter;
import com.example.lowongankerja.Perusahaan.ResultPerusahaan;
import com.example.lowongankerja.R;
import com.example.lowongankerja.User.ValueUser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment {
    public static final String URL = "https://lokerprogmob.herokuapp.com/api/";
    private List<ResultPerusahaan> results = new ArrayList<>();
    private PerusahaanRecyclerUserAdapter viewAdapter;
    BaseApiHelper mApiService;
    private static String EXTRA = "extra";
    String nama_perusahaan = "";
    RecyclerView recyclerView;
    ProgressBar progressBar;
    CardView pindah;
    TextView textView;
    final String SHARED_PREFERENCES_NAME = "shared_preferences";
    final String SESSION_STATUS = "session";
    public final static String TAG_TOKEN = "token";
    public final static String TAG_ID = "id";
    public final static String TAG_ADMIN = "admin";
    SharedPreferences sharedPreferences;
    boolean session = false;
    String token;
    Integer id_user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        session = sharedPreferences.getBoolean(SESSION_STATUS, false);
        token = sharedPreferences.getString(TAG_TOKEN, null);
        id_user = sharedPreferences.getInt(String.valueOf(TAG_ID),0);

        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view1);
        textView = (TextView) view.findViewById(R.id.namaku);
        pindah = (CardView) view.findViewById(R.id.card_daftar);

        pindah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(view.getContext(), Daftar.class);
                view.getContext().startActivity(mIntent);
            }
        });

        mApiService = UtilsApi.getAPIService();
        viewAdapter = new PerusahaanRecyclerUserAdapter(getActivity(), results);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(viewAdapter);
        Log.e("m", "mantap");
        loadDataKategori();
        loadNama();
        return view;
    }

    private void loadNama(){
        mApiService = UtilsApi.getAPIService();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BaseApiHelper api = retrofit.create(BaseApiHelper.class);
        Call<ValueUser> call = api.viewUser(id_user);
        call.enqueue(new Callback<ValueUser>() {
            @Override
            public void onResponse(Call<ValueUser> call, Response<ValueUser> response) {

                String nama_user = response.body().getName();
                textView.setText(" "+nama_user);

                Log.e(TAG, "onResponse: Masuk" );
            }

            @Override
            public void onFailure(Call<ValueUser> call, Throwable t) {

            }
        });
    }

    private void loadDataKategori() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        BaseApiHelper api = retrofit.create(BaseApiHelper.class);
        Call<GetPerusahaan> call = api.getPerusahaan();
        Log.e("PROGRESSSS", "SUDAH SAMPAI SINI");
        call.enqueue(new Callback<GetPerusahaan>() {
            @Override
            public void onResponse(Call<GetPerusahaan> call, Response<GetPerusahaan> response) {
                Log.e("PROGRESSSS", "SUDAH SAMPAI SINI2");
                progressBar.setVisibility(View.GONE);
                Log.e("PROGRESSSS", response.message().toString());
//                String value = response.body().getStatus();
                results = response.body().getResult();
                Log.e("ERROR", "asa" + results.size());
                viewAdapter = new PerusahaanRecyclerUserAdapter(getActivity(), results);
                recyclerView.setAdapter(viewAdapter);
            }

            @Override
            public void onFailure(Call<GetPerusahaan> call, Throwable t) {

            }
        });
    }
}
