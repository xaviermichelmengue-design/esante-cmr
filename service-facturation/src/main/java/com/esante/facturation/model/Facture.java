package com.esante.facturation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "factures")
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // SUPPRIMER @NotBlank - le numéro sera généré automatiquement
    @Column(unique = true)
    private String numeroFacture;
    
    @NotNull(message = "L'ID patient est obligatoire")
    private Long patientId;
    
    @NotNull(message = "L'ID consultation est obligatoire")
    private Long consultationId;
    
    @Positive(message = "Le montant doit être positif")
    private Double montant;
    
    @Enumerated(EnumType.STRING)
    private StatutFacture statut = StatutFacture.EN_ATTENTE;
    
    private LocalDateTime dateCreation;
    private LocalDateTime datePaiement;
    
    public Facture() {
        this.dateCreation = LocalDateTime.now();
    }
    
    public Facture(String numeroFacture, Long patientId, Long consultationId, Double montant) {
        this();
        this.numeroFacture = numeroFacture;
        this.patientId = patientId;
        this.consultationId = consultationId;
        this.montant = montant;
    }
    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNumeroFacture() { return numeroFacture; }
    public void setNumeroFacture(String numeroFacture) { this.numeroFacture = numeroFacture; }
    
    public Long getPatientId() { return patientId; }
    public void setPatientId(Long patientId) { this.patientId = patientId; }
    
    public Long getConsultationId() { return consultationId; }
    public void setConsultationId(Long consultationId) { this.consultationId = consultationId; }
    
    public Double getMontant() { return montant; }
    public void setMontant(Double montant) { this.montant = montant; }
    
    public StatutFacture getStatut() { return statut; }
    public void setStatut(StatutFacture statut) { this.statut = statut; }
    
    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }
    
    public LocalDateTime getDatePaiement() { return datePaiement; }
    public void setDatePaiement(LocalDateTime datePaiement) { this.datePaiement = datePaiement; }
}
