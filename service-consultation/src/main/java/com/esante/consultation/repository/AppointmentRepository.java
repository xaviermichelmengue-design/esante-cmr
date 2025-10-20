package com.esante.consultation.repository;

import com.esante.consultation.model.Appointment;
import com.esante.consultation.model.Appointment.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    List<Appointment> findByPatientIdOrderByAppointmentDateDesc(UUID patientId);
    List<Appointment> findByDoctorIdOrderByAppointmentDateDesc(UUID doctorId);
    List<Appointment> findByDoctorIdAndAppointmentDateBetween(UUID doctorId, LocalDateTime start, LocalDateTime end);
    List<Appointment> findByStatusAndAppointmentDateBefore(AppointmentStatus status, LocalDateTime date);
    List<Appointment> findByPatientIdAndStatus(UUID patientId, AppointmentStatus status);
}
