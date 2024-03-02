/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.util.Date;

/**
 *
 * @author dell
 */
public class Article {
    private int id, quantite_en_stock, quantite_seuille, id_cat;
    private String libelle;
    private Date date_creation;

    public Article(int id,  String libelle, int quantite_en_stock, Date date_creation, int quantite_seuille, int id_cat) {
        this.id = id;
        this.quantite_en_stock = quantite_en_stock;
        this.quantite_seuille = quantite_seuille;
        this.libelle = libelle;
        this.date_creation = date_creation;
        this.id_cat = id_cat;
    }

    public Article() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantite_en_stock() {
        return quantite_en_stock;
    }

    public void setQuantite_en_stock(int quantite_en_stock) {
        this.quantite_en_stock = quantite_en_stock;
    }

    public int getQuantite_seuille() {
        return quantite_seuille;
    }

    public void setQuantite_seuille(int quantite_seuille) {
        this.quantite_seuille = quantite_seuille;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Date getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(Date date_creation) {
        this.date_creation = date_creation;
    }

    public int getId_cat() {
        return id_cat;
    }

    public void setId_cat(int id_cat) {
        this.id_cat = id_cat;
    }
    
    
}
