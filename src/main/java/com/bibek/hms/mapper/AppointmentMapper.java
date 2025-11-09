package com.bibek.hms.mapper;

import com.bibek.hms.dto.CreateAppointmentRequestDTO;
import com.bibek.hms.dto.AppointmentResponseDTO;
import com.bibek.hms.enities.Appointment;

public class AppointmentMapper {
    public static Appointment toEntity(CreateAppointmentRequestDTO dto) {
        return Appointment.builder()
                .reason(dto.getReason())
                .appointmentDateTime(dto.getAppointmentDateTime())
                .build();
    }

    public static AppointmentResponseDTO toResponse(Appointment appointment) {
        return AppointmentResponseDTO.builder()
                .uuid(appointment.getUuid())
                .reason(appointment.getReason())
                .appointmentDateTime(appointment.getAppointmentDateTime())
                .patientId(appointment.getPatient() != null ? appointment.getPatient().getUuid() : null)
                .patientName(appointment.getPatient() != null ? appointment.getPatient().getName() : null)
                .doctorId(appointment.getDoctor() != null ? appointment.getDoctor().getUuid() : null)
                .doctorName(appointment.getDoctor() != null ? appointment.getDoctor().getName() : null)
                .build();
    }
}

