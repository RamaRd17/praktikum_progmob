package com.example.lowongankerja.Divisi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lowongankerja.ApiHelper.BaseApiHelper;
import com.example.lowongankerja.ApiHelper.UtilsApi;
import com.example.lowongankerja.R;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateDivisi extends AppCompatActivity {
    EditText divisi;
    Button btUpdate;
    Context mContext;
    ProgressDialog loading;
    BaseApiHelper mApiService;
    String intValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_divisi);

        divisi = (EditText) findViewById(R.id.fakultas);
        btUpdate = (Button) findViewById(R.id.update);
        Intent mIntent = getIntent();
        intValue = mIntent.getStringExtra("id");
        String test = String.valueOf(intValue);
        Log.d("jaja", "onCreate: "+test);
        divisi.setText(mIntent.getStringExtra("nama_divisi"));
        mContext = this;
        mApiService = UtilsApi.getAPIService();
        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
                uptDivisi();
            }
        });
    }

    private void uptDivisi() {
        mApiService.updateDivisi(String.valueOf(intValue),divisi.getText().toString()).
                enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            Log.i("debug", "onResponse: BERHASIL");
                            Toast.makeText(mContext, "BERHASIL UPDATE", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(mContext, Divisi.class));
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
