package com.example.lowongankerja;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.example.lowongankerja.Admin.Dashboard;
import com.example.lowongankerja.Admin.AdminLoginActivity;
import com.example.lowongankerja.ApiHelper.AccessToken;
import com.example.lowongankerja.ApiHelper.BaseApiHelper;
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

import static java.sql.Types.NULL;

public class LoginActivity extends AppCompatActivity {
    TextView regis,to_admin;
    EditText etEmail;
    EditText etPassword;
    CircularProgressButton btnLogin;
    ProgressDialog loading;
    ImageView regis2;
    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferences2;
    Context mContext;
    BaseApiHelper mApiService;
    boolean session = false;
    TokenManager tokenManager;
    String token;
    Integer id_user;
    Integer admin;
    final String SHARED_PREFERENCES_NAME = "shared_preferences";
    final String SHARED_PREFERENCES_test = "shared_preferences";
    final String SESSION_STATUS = "session";
    public final static String TAG_TOKEN = "token";
    public final static String TAG_ID = "id";
    public final static String TAG_ADMIN = "admin";
    AppDatabase mDb;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mContext = this;
        mApiService = UtilsApi.getAPIService(); // meng-init yang ada di package apihelper
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        sharedPreferences2 = getSharedPreferences(SHARED_PREFERENCES_test, Context.MODE_PRIVATE);
        session = sharedPreferences.getBoolean(SESSION_STATUS, false);
        token = sharedPreferences.getString(TAG_TOKEN, null);
        id_user = sharedPreferences.getInt(String.valueOf(TAG_ID),0);
        admin = sharedPreferences.getInt(String.valueOf(TAG_ADMIN),0);
        Log.d("asasa", "onCreate: admin  "+admin);

        if (admin==2){
            Intent intent = new Intent(LoginActivity.this, BottomActivity.class);
            intent.putExtra(TAG_TOKEN, token);
            intent.putExtra(String.valueOf(TAG_ADMIN),admin);
            intent.putExtra(String.valueOf(TAG_ID),id_user);
            finish();
            startActivity(intent);
        }else if(admin==1){
            Intent intent = new Intent(LoginActivity.this, Dashboard.class);
            intent.putExtra(TAG_TOKEN, token);
            intent.putExtra(String.valueOf(TAG_ID),id_user);
            intent.putExtra(String.valueOf(TAG_ADMIN),admin);
            finish();
            startActivity(intent);
        }

        initComponents();
    }
    private void initComponents() {
        etEmail = (EditText) findViewById(R.id.iemail);
        etPassword = (EditText) findViewById(R.id.ipassword);
        btnLogin = (CircularProgressButton) findViewById(R.id.blogin);
        regis = (TextView) findViewById(R.id.regis);
        to_admin = (TextView) findViewById(R.id.pindah);
        regis2 = findViewById(R.id.register_2);

        regis2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, RegisterActivity.class));
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(mContext, null, "Harap Tunggu...", true, false);
                Login();
            }
        });

        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, RegisterActivity.class));
            }
        });

        to_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, AdminLoginActivity.class));
            }
        });
    }

    private void Login() {
        mApiService.loginRequest(etEmail.getText().toString(), etPassword.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            loading.dismiss();
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                if (jsonRESULTS.getString("status").equals("success")) {
                                    // Jika login berhasil maka data nama yang ada di response API
                                    // akan diparsing ke activity selanjutnya.

                                    String sukses = jsonRESULTS.getJSONObject("data").getString("token");
                                    Log.d("wanjay", "onResponse: "+sukses);
                                    String name = jsonRESULTS.getJSONObject("user").getString("name");
                                    Integer id = jsonRESULTS.getJSONObject("user").getInt("id");
                                    Log.d("id_user", "onResponse: "+id);
                                    Integer is_admin = jsonRESULTS.getJSONObject("user").getInt("is_admin");
                                    Log.d("admin", "onResponse: "+is_admin);
                                    token = sukses;
                                    if (is_admin==2){
                                        admin = is_admin;
                                        Log.d("admin", "onanjay "+admin);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putBoolean(SESSION_STATUS, true);
                                        editor.putString(TAG_TOKEN, token);
                                        editor.putInt(String.valueOf(TAG_ADMIN),admin);
                                        editor.putString("nama_user",name);
                                        editor.putInt(String.valueOf(TAG_ID), jsonRESULTS.getJSONObject("user").getInt("id"));
                                        editor.apply();
                                        Toast.makeText(mContext, "ID ANDA " + id, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(mContext, BottomActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }else {
                                        Toast.makeText(mContext, "Mungkin anda admin" , Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    // Jika login gagal
                                    Toast.makeText(mContext, "EEROOR", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            loading.dismiss();
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        Toast.makeText(mContext, "OFFLINE", Toast.LENGTH_SHORT).show();
                        mDb = AppDatabase.getDatabase(getApplicationContext());
//                        Log.e("ggwp","sdkfl");
                        AppExecutors.getInstance().diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                user = mDb.userDao().loginUser(etEmail.getText().toString(), etPassword.getText().toString());
                                int id_user = user.getId();
                                Log.e("test", "run: "+id_user);
                                if (id_user > 0 ){
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putBoolean(SESSION_STATUS, true);
                                    editor.putInt(String.valueOf(TAG_ID), user.getId());
                                    editor.putInt(String.valueOf(TAG_ADMIN),user.getIs_admin());
                                    editor.putString("nama_user",user.getName());
                                    editor.commit();
                                    Intent i = new Intent(getApplicationContext(), BottomActivity.class);
                                    startActivity(i);
                                }
                            }
                        });
                        loading.dismiss();
                    }

                });
    }

}
