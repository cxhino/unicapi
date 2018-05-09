package com.uca.unicapi.Api;

import com.uca.unicapi.models.UniversityModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Stephane on 04/25/2018.
 */

public interface ApiInterface {

    @GET("universities")
    Call<List<UniversityModel>> getUniversities();

    @POST("universities")
    Call<UniversityModel> createUniversity(@Body UniversityModel universityModel);

    @PUT("universities/{id}")
    Call<UniversityModel> updateUniversity(@Path("id") int id, @Body UniversityModel universityModel);

    @DELETE("universities/{id}")
    Call<UniversityModel> deleteUniversity(@Path("id") int id);
}
