package com.esante.consultation.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class AppointmentRequest {
    private UUID patientId;
    private UUID doctorId;
    private LocalDateTime appointmentDate;
    private String appointmentType;
    private String reason;
}
