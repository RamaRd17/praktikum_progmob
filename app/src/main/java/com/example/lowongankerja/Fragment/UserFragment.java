package com.example.lowongankerja.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lowongankerja.ApiHelper.BaseApiHelper;
import com.example.lowongankerja.ApiHelper.UtilsApi;
import com.example.lowongankerja.LoginActivity;
import com.example.lowongankerja.R;
import com.example.lowongankerja.User.EditProfile;
import com.example.lowongankerja.User.ValueUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class UserFragment extends Fragment{
    public static final String URL = "https://lokerprogmob.herokuapp.com/api/";
    public static final String URL_BASE = "https://lokerprogmob.herokuapp.com/";
    BaseApiHelper mApiService;
    Button edit;
    TextView btnLogout;
    SharedPreferences sharedPreferences;
    boolean session = false;
    String token;
    Integer id_user;
    ImageView foto;
    Integer admin;
    final String SHARED_PREFERENCES_NAME = "shared_preferences";
    final String SESSION_STATUS = "session";
    public final static String TAG_TOKEN = "token";
    public final static String TAG_ID = "id";
    public final static String TAG_ADMIN = "admin";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_user, container, false);
        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        session = sharedPreferences.getBoolean(SESSION_STATUS, false);
        token = sharedPreferences.getString(TAG_TOKEN, null);
        id_user = sharedPreferences.getInt(String.valueOf(TAG_ID),0);
        final TextView nama = view.findViewById(R.id.nama);
        final TextView email = view.findViewById(R.id.email);
        final TextView namab = view.findViewById(R.id.nama_bawah);
        final TextView emailb = view.findViewById(R.id.email_bawah);

        Log.d(TAG, "onCreateView: "+id_user);
        edit = (Button) view.findViewById(R.id.edit_profil);
        btnLogout = (TextView) view.findViewById(R.id.button_logoutMain);
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
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        });

        edit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(view.getContext(), EditProfile.class);
                mIntent.putExtra("name", namab.getText().toString());
                mIntent.putExtra("email", emailb.getText().toString());
                mIntent.putExtra("id_user",id_user.toString());
                view.getContext().startActivity(mIntent);
            }
        });

        foto = (ImageView) view.findViewById(R.id.profil_image);
        Glide.with(requireContext()).load("https://guarded-woodland-53288.herokuapp.com/image/user_image.jpg")
                .into(foto);


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
                String email_user = response.body().getEmail();

                namab.setText(nama_user);
                emailb.setText(email_user);
                nama.setText(nama_user);
                email.setText(email_user);
                try{
                    Glide.with(getContext()).load(URL_BASE+response.body().getImage())
                            .into(foto);
                }
                catch (Exception e){
                    Log.e(TAG, "on error" + e.getMessage());
                }

                Log.e(TAG, "onResponse: Masuk" );
            }

            @Override
            public void onFailure(Call<ValueUser> call, Throwable t) {

            }
        });

        initComponents();

        return view;
    }

    private void initComponents() {

    }
}
