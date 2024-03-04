/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entities.Article;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author dell
 */
public class Articledao {
    public static List<Article> showArticle() throws SQLException{
        Connection con = Connexion.Connect();
        List<Article> art = new ArrayList<>();
        String query = "select libelle, designation, prix, quantite_en_stock, date_creation, quantite_seuille "
                + "from article inner join categorie on article.id_cat = categorie.id";
        PreparedStatement ps = null;
        ps = con.prepareStatement(query);
        ResultSet rs;
        rs  = ps.executeQuery();
        while(rs.next()){
            Article a = new Article();
            a.setLibelle(rs.getString("libelle"));
            a.setDesignation(rs.getString("designation"));
            a.setPrix(rs.getDouble("prix"));
            a.setQuantite_en_stock(rs.getInt("quantite_en_stock"));
            String dat = String.valueOf(rs.getDate("date_creation"));
            a.setDate_creation(LocalDate.parse(dat));
            a.setQuantite_seuille(rs.getInt("quantite_seuille"));
            art.add(a);
        }
        return art;
         }
    public static void addArticle(String libelle, String prix, String quantite_en_stock, String date_creation, String quantite_seuille, String designation) throws SQLException{
        Connection con = Connexion.Connect();
        String query0 = "select id from categorie where designation = ?";
        String query = "insert into article(libelle, prix, quantite_en_stock, date_creation, quantite_seuille, id_cat ) values(?, ?, ?, ?, ?, ?);";
        PreparedStatement ps, ps1 = null;
        ps1 = con.prepareStatement(query0);
        ps1.setString(1, designation);
        ResultSet rs = null;
        rs = ps1.executeQuery();
        
        if(rs.next()){
        ps = con.prepareStatement(query);
        ps.setString(1, libelle);
        ps.setDouble(2, Double.parseDouble(prix));
        ps.setInt(3, Integer.parseInt(quantite_en_stock));
        ps.setString(4, date_creation);
        ps.setInt(5, Integer.parseInt(quantite_seuille));
        ps.setInt(6, rs.getInt("id"));
        ps.executeUpdate();
        }
    }
    public static void main(String[] args){
            List<Article> art = new ArrayList<>();
        try {
            art = showArticle();
            addArticle("Mac", "50", "4", "2024-03-04", "3", "Informatique"); 
        } catch (SQLException ex) {
            Logger.getLogger(Articledao.class.getName()).log(Level.SEVERE, null, ex);
        }
            System.out.println(art.get(0).getDate_creation());
                    
        }
}
