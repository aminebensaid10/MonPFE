package com.mypfeproject.pfe.entities;

public enum SituationFamiliale {
    CELIBATAIRE("Célibataire"),
    MARIE("Marié"),
    DIVORCE("Divorcé"),
    VEUF("Veuf"),
    AUTRE("Autre");
    private final String libelle;

    SituationFamiliale(String libelle) {
        this.libelle = libelle;
}

    public String getLibelle() {
        return libelle;
    }}
