package com.android.frontend.appointments;
import java.util.ArrayList;

// 서버에서 받아오는 json 파일을 파싱하는 class
public class AppointmentResponse {
    private ArrayList<Appointment> appointmentList;

    public AppointmentResponse(ArrayList<Appointment> appointmentList){
        this.appointmentList = appointmentList;
    }

    public ArrayList<Appointment> getAppointments(){
        return appointmentList;
    }
