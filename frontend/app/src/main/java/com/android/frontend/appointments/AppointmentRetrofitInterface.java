package com.android.frontend.appointments;

import com.google.gson.JsonObject;
import java.util.HashMap;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AppointmentRetrofitInterface {
    @POST("appointments/")
    Call<AppointmentsResponse> executeInit (@Body HashMap<String, String> map);

    @POST("appointments/")
    Call<NewAppointment> executeCreateAppointment (@Body HashMap<String, String> map);

    @get("appointments/")
    Call<Void> executeGetAppointment (@Body HashMap<String, String> map);

    @get("appointments/_id")
    Call<Void> executeGetAppointmentById (@Body HashMap<String, String> map);

    @get("appointments/userId")
    Call<Void> executeGetAppointmentByUserId (@Body HashMap<String, String> map);

    @put("appointments/_id")
    Call<Void> executeUpdateAppointment (@Body HashMap<String, String> map);

    @delete("appointments/_id")
    Call<Void> executeDeleteAppointment (@Body HashMap<String, String> map);
}