package com.esante.facturation.service;

import com.esante.facturation.model.Facture;
import com.esante.facturation.model.StatutFacture;
import com.esante.facturation.repository.FactureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FactureService {
    
    @Autowired
    private FactureRepository factureRepository;
    
    public List<Facture> getAllFactures() {
        return factureRepository.findAll();
    }
    
    public Optional<Facture> getFactureById(Long id) {
        return factureRepository.findById(id);
    }
    
    public Optional<Facture> getFactureByNumero(String numeroFacture) {
        return factureRepository.findByNumeroFacture(numeroFacture);
    }
    
    public List<Facture> getFacturesByPatient(Long patientId) {
        return factureRepository.findByPatientId(patientId);
    }
    
    public List<Facture> getFacturesByStatut(StatutFacture statut) {
        return factureRepository.findByStatut(statut);
    }
    
    public Facture createFacture(Facture facture) {
        // Générer un numéro de facture unique si non fourni
        if (facture.getNumeroFacture() == null || facture.getNumeroFacture().isEmpty()) {
            String numero = "FACT-" + System.currentTimeMillis();
            facture.setNumeroFacture(numero);
        }
        
        // Vérifier l'unicité du numéro de facture
        if (factureRepository.existsByNumeroFacture(facture.getNumeroFacture())) {
            throw new RuntimeException("Le numéro de facture existe déjà");
        }
        
        return factureRepository.save(facture);
    }
    
    public Facture updateFacture(Long id, Facture factureDetails) {
        return factureRepository.findById(id)
            .map(facture -> {
                facture.setMontant(factureDetails.getMontant());
                facture.setStatut(factureDetails.getStatut());
                return factureRepository.save(facture);
            })
            .orElseThrow(() -> new RuntimeException("Facture non trouvée avec l'id: " + id));
    }
    
    public Facture payerFacture(Long id) {
        return factureRepository.findById(id)
            .map(facture -> {
                facture.setStatut(StatutFacture.PAYEE);
                facture.setDatePaiement(LocalDateTime.now());
                return factureRepository.save(facture);
            })
            .orElseThrow(() -> new RuntimeException("Facture non trouvée avec l'id: " + id));
    }
    
    public void deleteFacture(Long id) {
        factureRepository.deleteById(id);
    }
}
