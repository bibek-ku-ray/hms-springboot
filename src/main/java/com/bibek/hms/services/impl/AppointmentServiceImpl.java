package com.bibek.hms.services.impl;

import com.bibek.hms.dto.CreateAppointmentRequestDTO;
import com.bibek.hms.dto.AppointmentResponseDTO;
import com.bibek.hms.enities.Appointment;
import com.bibek.hms.enities.Doctor;
import com.bibek.hms.enities.Patient;
import com.bibek.hms.mapper.AppointmentMapper;
import com.bibek.hms.repositories.AppointmentRepository;
import com.bibek.hms.repositories.DoctorRepository;
import com.bibek.hms.repositories.PatientRepository;
import com.bibek.hms.services.IAppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements IAppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    @Override
    @Transactional
    public AppointmentResponseDTO createAppointment(CreateAppointmentRequestDTO requestDTO) {
        Doctor doctor = doctorRepository.findById(requestDTO.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + requestDTO.getDoctorId()));

        Patient patient = patientRepository.findById(requestDTO.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + requestDTO.getPatientId()));

        Appointment appointment = AppointmentMapper.toEntity(requestDTO);
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);

        appointmentRepository.save(appointment);
        return AppointmentMapper.toResponse(appointment);
    }

    @Override
    public List<AppointmentResponseDTO> getAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();
        return appointments.stream().map(AppointmentMapper::toResponse).toList();
    }

    @Override
    public Page<AppointmentResponseDTO> getAppointments(Pageable pageable) {
        Page<Appointment> appointments = appointmentRepository.findAll(pageable);
        return appointments.map(AppointmentMapper::toResponse);
    }

    @Override
    public Optional<AppointmentResponseDTO> getAppointmentById(UUID id) {
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        return appointment.map(AppointmentMapper::toResponse);
    }

    @Override
    public List<AppointmentResponseDTO> getAppointmentsByDoctor(UUID doctorId) {
        List<Appointment> appointments = appointmentRepository.findByDoctorUuid(doctorId);
        return appointments.stream().map(AppointmentMapper::toResponse).toList();
    }

    @Override
    public List<AppointmentResponseDTO> getAppointmentsByPatient(UUID patientId) {
        List<Appointment> appointments = appointmentRepository.findByPatientUuid(patientId);
        return appointments.stream().map(AppointmentMapper::toResponse).toList();
    }

    @Override
    public Page<AppointmentResponseDTO> getAppointmentsByDateRange(LocalDateTime start, LocalDateTime end, Pageable pageable) {
        Page<Appointment> appointments = appointmentRepository.findByAppointmentDateTimeBetween(start, end, pageable);
        return appointments.map(AppointmentMapper::toResponse);
    }

    @Override
    @Transactional
    public AppointmentResponseDTO updateAppointment(UUID id, CreateAppointmentRequestDTO requestDTO) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));

        if (requestDTO.getDoctorId() != null) {
            Doctor doctor = doctorRepository.findById(requestDTO.getDoctorId())
                    .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + requestDTO.getDoctorId()));
            appointment.setDoctor(doctor);
        }

        if (requestDTO.getPatientId() != null) {
            Patient patient = patientRepository.findById(requestDTO.getPatientId())
                    .orElseThrow(() -> new RuntimeException("Patient not found with id: " + requestDTO.getPatientId()));
            appointment.setPatient(patient);
        }

        appointment.setReason(requestDTO.getReason());
        appointment.setAppointmentDateTime(requestDTO.getAppointmentDateTime());

        appointmentRepository.save(appointment);
        return AppointmentMapper.toResponse(appointment);
    }

    @Override
    @Transactional
    public void deleteAppointment(UUID id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + id));
        appointmentRepository.delete(appointment);
    }
}

