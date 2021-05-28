package com.android.frontend.sns.appointments;
import java.util.ArrayList;

// 서버에서 받아오는 json 파일을 파싱하는 class
public class AppointmentResponse {
    private ArrayList<Appointments> appointmentList;

    public AppointmentResponse(ArrayList<Appointments> appointmentList) {
        this.appointmentList = appointmentList;
    }

    public ArrayList<Appointments> getAppointments() {
        return appointmentList;
    }
}
