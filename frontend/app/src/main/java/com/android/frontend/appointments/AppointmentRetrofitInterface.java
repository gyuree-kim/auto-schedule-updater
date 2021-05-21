package com.android.frontend.appointments;

import com.google.gson.JsonObject;
import java.util.HashMap;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AppointmentRetrofitInterface {
    @GET("appointments/userId/{userId}")
    Call<AppointmentsResponse> executeInit (@Path("userId") String userId);

    @POST("appointments/")
    Call<NewAppointment> executeCreateAppointment (@Body HashMap<String, String> map);

    @GET("appointments/")
    Call<ResponseGet> executeGetAppointment (@Body HashMap<String, String> map);

    @GET("appointments/_id/{_id}")
    Call<ResponseGet> executeGetAppointmentById (@Path("_id") String _id);

    @PUT("appointments/_id/{_id}")
    Call<Void> executeUpdateAppointment (@Path("_id") String _id);

    @DELETE("appointments/_id/{_id}")
    Call<Void> executeDeleteAppointment (@Path("_id") String _id);
}