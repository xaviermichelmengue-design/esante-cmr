package com.esante.facturation.controller;

import com.esante.facturation.model.Facture;
import com.esante.facturation.service.FactureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class WebController {
    
    @Autowired
    private FactureService factureService;
    
    @GetMapping("/")
    public String accueil(Model model) {
        List<Facture> factures = factureService.getAllFactures();
        model.addAttribute("factures", factures);
        model.addAttribute("nouvelleFacture", new Facture());
        return "accueil";
    }
    
    @PostMapping("/creer-facture")
    public String creerFacture(
            @RequestParam Long patientId,
            @RequestParam Long consultationId,
            @RequestParam Double montant) {
        
        Facture facture = new Facture();
        facture.setPatientId(patientId);
        facture.setConsultationId(consultationId);
        facture.setMontant(montant);
        
        factureService.createFacture(facture);
        
        return "redirect:/";
    }
    
    @PostMapping("/payer-facture")
    public String payerFacture(@RequestParam Long factureId) {
        factureService.payerFacture(factureId);
        return "redirect:/";
    }
}
