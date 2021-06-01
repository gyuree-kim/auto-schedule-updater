package com.android.frontend.sns.appointments;

import java.util.HashMap;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AppointmentRetrofitInterface {
    @GET("appointments/userId/{userId}")
    Call<AppointmentResponse> executeInit (@Path("userId") String userId);

    @POST("appointments/")
    Call<AppointmentResponse> executeCreateAppointment (@Body HashMap<String, String> map);

    @GET("appointments/")
    Call<AppointmentResponse> executeGetAppointment (@Body HashMap<String, String> map);

    @GET("appointments/_id/{_id}")
    Call<AppointmentResponse> executeGetAppointmentById (@Path("_id") String _id);

    @PUT("appointments/_id/{_id}")
    Call<Void> executeUpdateAppointment (@Path("_id") String _id);

    @DELETE("appointments/_id/{_id}")
    Call<Void> executeDeleteAppointment (@Path("_id") String _id);
}