package com.example.lowongankerja.ApiHelper;

import com.example.lowongankerja.Daftar.GetDaftar;
import com.example.lowongankerja.Divisi.GetDivisi;
import com.example.lowongankerja.Perusahaan.GetDetail;
import com.example.lowongankerja.Perusahaan.GetPerusahaan;
import com.example.lowongankerja.Perusahaan.PostPutDelPerusahaan;
import com.example.lowongankerja.Perusahaan.ResultPerusahaan;
import com.example.lowongankerja.StafDivisi.GetStafDivisi;
import com.example.lowongankerja.User.ValueUser;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BaseApiHelper {

    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> loginRequest(@Field("email") String email,
                                    @Field("password") String password);

    // Fungsi ini untuk memanggil API http://10.0.2.2/mahasiswa/register.php
    @FormUrlEncoded
    @POST("register")
    Call<ResponseBody> registerRequest(@Field("name") String nama,
                                       @Field("email") String email,
                                       @Field("password") String password,
                                       @Field("c_password") String c_password);

    @FormUrlEncoded
    @POST("edit-fcm-token")
    Call<ResponseBody> editFcmToken(@Field("id") String id,
                                       @Field("fcm_token") String fcm_token);

    @Multipart
    @POST("edituser")
    Call<ResponseBody> editUser(@Part("id") RequestBody id ,
                                @Part("name") RequestBody nama,
                                @Part("email") RequestBody email,
                                @Part MultipartBody.Part image );

    @GET("perusahaan")
    Call<GetPerusahaan> getPerusahaan();

    @GET("perusahaan/search/{nama_perusahaan}")
    Call<GetPerusahaan> getPerusahaan(@Path("nama_perusahaan") String nama_perusahaan);

    @FormUrlEncoded
    @POST("perusahaan")
    Call<ResponseBody> perusahaanRequest(@Field("nama_perusahaan") String nama_perusahaan,
                                     @Field("tentang") String tentang,
                                     @Field("lokasi") String lokasi);

    @GET("divisi")
    Call<GetDivisi> getDivisi();


    @FormUrlEncoded
    @PUT("perusahaan/{id}")
    Call<ResponseBody> perusahaanUpdate(@Path("id") int id ,
                                    @Field("nama_perusahaan") String nama_perusahaan,
                                    @Field("tentang") String tentang,
                                    @Field("lokasi") String lokasi);


    @DELETE("perusahaan/{id}")
    Call<ResponseBody> perusahaanDelete(@Path("id") int id);

    @GET("perusahaan/{id}")
    Call<GetDetail> getDetail(@Path("id") int id);

    @GET("getuser/{id}")
    Call<ValueUser> viewUser(@Path("id") int id);

    @FormUrlEncoded
    @POST("stafdivisi")
    Call<ResponseBody> InsertStafDivisi(@Field("nama_bagian") String nama_perusahaan,
                                   @Field("tentang") String tentang,
                                   @Field("biaya") int biaya,
                                   @Field("id_divisi") int id_divisi,
                                   @Field("id_perusahaan") int id_perusahaan);

    @FormUrlEncoded
    @POST("update-divisi")
    Call<ResponseBody> UpdateStafDivisi(@Field("id") int id ,
                                   @Field("nama_bagian") String nama_perusahaan,
                                   @Field("tentang") String tentang,
                                   @Field("biaya") int biaya,
                                   @Field("id_divisi") int id_divisi,
                                   @Field("id_perusahaan") int id_perusahaan);

    @GET("delete-divisi")
    Call<ResponseBody> DeleteStafDivisi(@Query("id") int id);

    @GET("getDaftar")
    Call<GetDaftar> GetDaftar(@Query("id") String id);

    @GET("daftar")
    Call<GetDaftar> daftar();


    @FormUrlEncoded
    @POST("daftar")
    Call<ResponseBody> Daftar(@Field("nama_perusahaan") String nama_perusahaan,
                              @Field("fee") int fee,
                              @Field("id_user") int id_user,
                              @Field("id_stafdivisi") int id_stafdivisi);

//    @DELETE("daftar/{id}")
//    Call<ResponseBody> DeleteDaftar(@Path("id") String id);

    @GET("delete-daftar")
    Call<ResponseBody> DeleteDaftar(@Query("id") String id);

    @Multipart
    @POST("gambar")
    Call<ResponseBody> uploadFile(@Part MultipartBody.Part file,
                                  @Part("id_perusahaan") int id_perusahaan);

    @FormUrlEncoded
    @POST("divisi")
    Call<ResponseBody> requestDivisi(@Field("nama_divisi") String nama_divisi);

    @FormUrlEncoded
    @POST("update_divisi")
    Call<ResponseBody> updateDivisi(  @Field("id") String id,
                                        @Field("nama_divisi") String nama_divisi);

//    @POST("delete_divisi/{id}")
//    Call<ResponseBody> deleteDivisi(@Path("id") int id);

    @GET("delete_divisi")
    Call<ResponseBody> deleteDivisi(@Query("id") int id);

    @GET("getPerusahaan")
    Call<GetPerusahaan> dataPerusahaan();
}
