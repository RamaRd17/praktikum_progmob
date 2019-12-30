package com.example.lowongankerja.Admin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.lowongankerja.Divisi.Divisi;
import com.example.lowongankerja.Image.GambarPerusahaan;
import com.example.lowongankerja.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Dashboard extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    boolean session = false;
    Button btnLogout;
    String token;
    Integer id_user;
    Integer admin;
    CardView perusahaan,divisi,gambar,daftar;
    final String SHARED_PREFERENCES_NAME = "shared_preferences";
    final String SESSION_STATUS = "session";
    public final static String TAG_TOKEN = "token";
    public final static String TAG_ID = "id";
    public final static String TAG_ADMIN = "admin";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);
        sharedPreferences = this.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        session = sharedPreferences.getBoolean(SESSION_STATUS, false);
        token = sharedPreferences.getString(TAG_TOKEN, null);
        id_user = sharedPreferences.getInt(String.valueOf(TAG_ID),0);
        admin = sharedPreferences.getInt(String.valueOf(TAG_ADMIN),0);
        Log.d("id user", "id: "+id_user);
        btnLogout = (Button) findViewById(R.id.logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Menghapus Status login dan kembali ke Login Activity
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(SESSION_STATUS);
                editor.remove(TAG_TOKEN);
                editor.remove(String.valueOf(TAG_ID));
                editor.remove(String.valueOf(TAG_ADMIN));
                editor.apply();
                startActivity(new Intent(getBaseContext(), AdminLoginActivity.class));
                finish();
            }
        });

        perusahaan = (CardView) findViewById(R.id.perusahaan_card);
        perusahaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), AdminActivity.class));
            }
        });

        gambar = (CardView) findViewById(R.id.gambar_card);
        gambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), GambarPerusahaan.class));
            }
        });

        daftar = (CardView) findViewById(R.id.list_card);
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), Pendaftar.class));
            }
        });
//
        divisi = (CardView) findViewById(R.id.divisi_card);
        divisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), Divisi.class));
            }
        });


    }
}
