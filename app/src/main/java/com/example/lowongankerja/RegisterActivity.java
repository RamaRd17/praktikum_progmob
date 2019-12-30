package com.example.lowongankerja;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lowongankerja.ApiHelper.BaseApiHelper;
import com.example.lowongankerja.ApiHelper.RetrofitClient;
import com.example.lowongankerja.ApiHelper.UtilsApi;
import com.example.lowongankerja.Database.AppDatabase;
import com.example.lowongankerja.Database.AppExecutors;
import com.example.lowongankerja.Model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText etNama;
    EditText etEmail;
    EditText etPassword;
    EditText etCPassword;
    TextView login;
    ProgressDialog loading;
    CircularProgressButton btnRegister;
    ImageView balik;
    Context mContext;
    BaseApiHelper mApiService;
    AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        mContext = this;
        mApiService = UtilsApi.getAPIService();

        initComponents();
    }

    private void initComponents() {
        etNama = (EditText) findViewById(R.id.et_name);
        etEmail = (EditText) findViewById(R.id.et_emailSignup);
        etPassword = (EditText) findViewById(R.id.et_passwordSignup);
        etCPassword = (EditText) findViewById(R.id.et_passwordSignup2);
        btnRegister = (CircularProgressButton) findViewById(R.id.button_signupSignup);
        balik = (ImageView) findViewById(R.id.balik);
        login = findViewById(R.id.backlogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, LoginActivity.class));
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
                requestRegister();
            }
        });

        balik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, LoginActivity.class));
            }
        });
    }

    private void requestRegister(){

        mApiService.registerRequest(etNama.getText().toString(),
                etEmail.getText().toString(),
                etPassword.getText().toString(),
                etCPassword.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.e("testing",response.toString());
                        if (response.isSuccessful()){
                            Log.i("debug", "onResponse: BERHASIL");
                            loading.dismiss();
                            Toast.makeText(mContext, "BERHASIL REGISTRASI", Toast.LENGTH_SHORT).show();
                                mDb = AppDatabase.getDatabase(getApplicationContext());
                                final User data = new User(
                                        etNama.getText().toString(),
                                        etEmail.getText().toString(),
                                        etPassword.getText().toString(),
                                        2
                                );
                                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        mDb.userDao().insertUser(data);
                                    }
                                });
                            startActivity(new Intent(mContext, LoginActivity.class));
                        } else {
                            Toast.makeText(mContext, "Gagal", Toast.LENGTH_SHORT).show();
                            Log.i("debug", "onResponse: GA BERHASIL"+response.errorBody()+response.body());
                            loading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
                        Toast.makeText(mContext, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}