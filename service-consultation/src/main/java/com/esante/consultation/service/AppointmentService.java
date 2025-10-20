package com.esante.consultation.service;

import com.esante.consultation.dto.AppointmentRequest;
import com.esante.consultation.model.Appointment;
import com.esante.consultation.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final RabbitTemplate rabbitTemplate;

    public Appointment createAppointment(AppointmentRequest request) {
        log.info("Création d'un rendez-vous pour le patient: {}", request.getPatientId());

        // Validation
        if (request.getAppointmentDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("La date du rendez-vous ne peut pas être dans le passé");
        }

        Appointment appointment = new Appointment();
        appointment.setPatientId(request.getPatientId());
        appointment.setDoctorId(request.getDoctorId());
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setAppointmentType(request.getAppointmentType());
        appointment.setReason(request.getReason());

        Appointment savedAppointment = appointmentRepository.save(appointment);

        // Envoi notification
        sendAppointmentNotification(savedAppointment);

        return savedAppointment;
    }

    public List<Appointment> getPatientAppointments(UUID patientId) {
        return appointmentRepository.findByPatientIdOrderByAppointmentDateDesc(patientId);
    }

    public List<Appointment> getDoctorAppointments(UUID doctorId) {
        return appointmentRepository.findByDoctorIdOrderByAppointmentDateDesc(doctorId);
    }

    public Appointment cancelAppointment(UUID appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
            .orElseThrow(() -> new RuntimeException("Rendez-vous non trouvé"));

        if (appointment.getStatus() == Appointment.AppointmentStatus.COMPLETED) {
            throw new IllegalStateException("Impossible d'annuler un rendez-vous déjà complété");
        }

        appointment.setStatus(Appointment.AppointmentStatus.CANCELLED);
        return appointmentRepository.save(appointment);
    }

    private void sendAppointmentNotification(Appointment appointment) {
        try {
            String message = String.format(
                "Rendez-vous créé - Patient: %s, Médecin: %s, Date: %s",
                appointment.getPatientId(), appointment.getDoctorId(), appointment.getAppointmentDate()
            );
            rabbitTemplate.convertAndSend("appointment.exchange", "appointment.created", message);
            log.info("Notification de rendez-vous envoyée");
        } catch (Exception e) {
            log.error("Erreur lors de l'envoi de la notification", e);
        }
    }
}
