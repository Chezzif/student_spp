package com.example.student.network;

import com.example.student.model.LoginResponse;
import com.example.student.model.PengumumanUnResponse;
import com.example.student.model.SppResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServiceClient {

    @POST("exec")
    @FormUrlEncoded
    Call<LoginResponse> loginSiswa(@Field(value = "sheetname",encoded = true) String sheetname,
                                   @Field(value = "action",encoded = true) String action,
                                   @Field(value = "tingkatan",encoded = true) String tingkatan,
                                   @Field(value = "tahunAjaran",encoded = true) String tahunAjaran,
                                   @Field(value = "nis",encoded = true) String nis,
                                   @Field(value = "password",encoded = true) String password);
    @GET("exec")
    Call<SppResponse> readSpp(@Query("sheetname")String sheetname,
                              @Query("action")String action,
                              @Query("tingkatan")String tingkatan,
                              @Query("tahunAjaran")String tahunAjaran,
                              @Query("kelas")String kelas,
                              @Query("nis")String nis);

    @GET("exec")
    Call<PengumumanUnResponse> requestHasilUn(@Query("sheetname")String sheetname,
                                              @Query("action")String action,
                                              @Query("nis")String nis);

}
