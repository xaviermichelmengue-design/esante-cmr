package com.esante.facturation.repository;

import com.esante.facturation.model.Facture;
import com.esante.facturation.model.StatutFacture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FactureRepository extends JpaRepository<Facture, Long> {
    Optional<Facture> findByNumeroFacture(String numeroFacture);
    List<Facture> findByPatientId(Long patientId);
    List<Facture> findByStatut(StatutFacture statut);
    boolean existsByNumeroFacture(String numeroFacture);
}
