package com.esante.facturation.controller;

import com.esante.facturation.model.Facture;
import com.esante.facturation.model.StatutFacture;
import com.esante.facturation.service.FactureService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/factures")
public class FactureController {
    
    @Autowired
    private FactureService factureService;
    
    @GetMapping
    public List<Facture> getAllFactures() {
        return factureService.getAllFactures();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Facture> getFactureById(@PathVariable Long id) {
        return factureService.getFactureById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/numero/{numero}")
    public ResponseEntity<Facture> getFactureByNumero(@PathVariable String numero) {
        return factureService.getFactureByNumero(numero)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/patient/{patientId}")
    public List<Facture> getFacturesByPatient(@PathVariable Long patientId) {
        return factureService.getFacturesByPatient(patientId);
    }
    
    @GetMapping("/statut/{statut}")
    public List<Facture> getFacturesByStatut(@PathVariable StatutFacture statut) {
        return factureService.getFacturesByStatut(statut);
    }
    
    @PostMapping
    public Facture createFacture(@Valid @RequestBody Facture facture) {
        return factureService.createFacture(facture);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Facture> updateFacture(@PathVariable Long id, @Valid @RequestBody Facture factureDetails) {
        try {
            Facture updatedFacture = factureService.updateFacture(id, factureDetails);
            return ResponseEntity.ok(updatedFacture);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{id}/paiement")
    public ResponseEntity<Facture> payerFacture(@PathVariable Long id) {
        try {
            Facture facturePayee = factureService.payerFacture(id);
            return ResponseEntity.ok(facturePayee);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFacture(@PathVariable Long id) {
        try {
            factureService.deleteFacture(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
