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
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
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
    public static List<Article> showOneArticle() throws SQLException{
        Connection con = Connexion.Connect();
        List<Article> art = new ArrayList<>();
        String query = "select libelle, designation, prix, quantite_en_stock, date_creation, quantite_seuille "
                + "from article inner join categorie on article.id_cat = categorie.id where article.id >=all(select id from article)";
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
public static void addArticle(String libelle, String prix, String quantite_en_stock, String date_creation, String quantite_seuille, String designation) {
    Connection con = null;
    PreparedStatement ps = null;
    PreparedStatement ps1 = null;
    ResultSet rs = null;
    
    try {
        con = Connexion.Connect();
        
        // Requête pour obtenir l'ID de la catégorie
        String query0 = "SELECT id FROM categorie WHERE designation = ?";
        ps1 = con.prepareStatement(query0);
        ps1.setString(1, designation);
        rs = ps1.executeQuery();
        String[] a = date_creation.split("-");
        int year = Integer.parseInt(a[0]);
            int month = Integer.parseInt(a[1]);
            int day = Integer.parseInt(a[2]);

            // Vérifier la validité de la date en utilisant LocalDate
            LocalDate date = LocalDate.of(year, month, day);
        if(Double.parseDouble(prix) < 0 || Integer.parseInt(quantite_en_stock) < 0 || Integer.parseInt(quantite_seuille)<0){
                JOptionPane.showMessageDialog(null, "Veuillez entrer des valeurs numériques positives pour le prix et la quantité seuille", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
        if (rs.next()) {
            // Requête pour insérer l'article
            String query = "INSERT INTO article(libelle, prix, quantite_en_stock, date_creation, quantite_seuille, id_cat) VALUES (?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(query);
            ps.setString(1, libelle);
            ps.setDouble(2, Double.parseDouble(prix));
            ps.setInt(3, Integer.parseInt(quantite_en_stock));
            ps.setString(4, date_creation);
            ps.setInt(5, Integer.parseInt(quantite_seuille));
            ps.setInt(6, rs.getInt("id"));
            ps.executeUpdate();
            
            // Afficher un message de succès à l'utilisateur
            JOptionPane.showMessageDialog(null, "Article enrégistré avec succès");
        } 
    } catch (SQLException e) {
        // Afficher un message d'erreur à l'utilisateur
        JOptionPane.showMessageDialog(null, "Une erreur est survenue lors de l'ajout de l'article.", "Erreur", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace(); // Imprime la trace de la pile pour le débogage
    } catch (NumberFormatException e) {
        // Gérer le cas où la conversion de chaîne en nombre échoue
        JOptionPane.showMessageDialog(null, "Veuillez entrer des valeurs numériques valides pour le prix, la quantité en stock,la quantité seuille et la date", "Erreur", JOptionPane.ERROR_MESSAGE);
    } catch (DateTimeException e) {
            JOptionPane.showMessageDialog(null, "Veuillez entrer une date valide", "date", JOptionPane.ERROR_MESSAGE);
        }
    finally {
        // Fermer les ressources (PreparedStatement, ResultSet, Connection)
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps1 != null) {
                ps1.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Imprime la trace de la pile pour le débogage
        }
    }
    
}
    public static void OldArticle(String old) throws SQLException{
        Connection con = Connexion.Connect();
        String query = "update article set old_libelle = ? where libelle= ? ";
        PreparedStatement ps = null;
        ps = con.prepareStatement(query);
        ps.setString(1, old);
        ps.setString(2, old);
        ps.executeUpdate();
    }
    public static void UpdateArticle(String libelle, String designation, String prix, String date_creation, String quantite_seuille) throws SQLException{
        try{
        String[] a = date_creation.split("-");
        int year = Integer.parseInt(a[0]);
            int month = Integer.parseInt(a[1]);
            int day = Integer.parseInt(a[2]);

            // Vérifier la validité de la date en utilisant LocalDate
            LocalDate date = LocalDate.of(year, month, day);
        
        Connection con = Connexion.Connect();
        String query0 = "select id from categorie where designation = ?";
        String query = "update article set libelle = ?, id_cat=?, prix=?, date_creation=? ,quantite_seuille = ?  where libelle=old_libelle ";
        PreparedStatement ps, ps1 = null;
        ps1 = con.prepareStatement(query0);
        ps1.setString(1, designation);
        ResultSet rs = null;
        rs = ps1.executeQuery();
        if (Integer.parseInt(quantite_seuille)<0 || Double.parseDouble(prix) < 0){
                JOptionPane.showMessageDialog(null, "Veuillez entrer des valeurs numériques positives pour le prix et la quantité seuille", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
        if(rs.next()){
            ps = con.prepareStatement(query);
        ps.setString(1, libelle);
        ps.setInt(2, rs.getInt("id"));
        ps.setDouble(3, Double.parseDouble(prix));
        ps.setString(4, date_creation);
        ps.setInt(5, Integer.parseInt(quantite_seuille));
        ps.executeUpdate();
        JOptionPane.showMessageDialog(null, "Article modifiée avec succès");
        }
        } catch (SQLException e) {
        // Afficher un message d'erreur à l'utilisateur
        JOptionPane.showMessageDialog(null, "Une erreur est survenue lors de l'ajout de l'article.", "Erreur", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace(); // Imprime la trace de la pile pour le débogage
    } catch (NumberFormatException e) {
        // Gérer le cas où la conversion de chaîne en nombre échoue
        JOptionPane.showMessageDialog(null, "Veuillez entrer des valeurs numériques valides pour le prix, la quantité en stock,la quantité seuille et la date", "Erreur", JOptionPane.ERROR_MESSAGE);
    } catch (DateTimeException e) {
            JOptionPane.showMessageDialog(null, "Veuillez entrer une date valide", "date", JOptionPane.ERROR_MESSAGE);
        }
}
    public static boolean TryFindArt(String libe) throws SQLException{
        Connection con = Connexion.Connect();
        String query = "select libelle from article where libelle = ? ";
        PreparedStatement ps = null;
        ps = con.prepareStatement(query);
        ps.setString(1, libe);
        ResultSet rs = null;
        rs = ps.executeQuery();
        if(rs.next()){
            return true;
        }
        return false;
    }
    public static void DeleteArticle(String libelle) throws SQLException{
        Connection con = Connexion.Connect();
        String query ="delete from article where libelle = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1,libelle);
        ps.executeUpdate();
    }
    public static void AppArticle(String libelle,String quantite_en_stock, String quantite_app) throws SQLException{
        try{
        Connection con = Connexion.Connect();
        PreparedStatement ps = con.prepareStatement("update article set quantite_en_stock = ? where libelle =?");
        PreparedStatement ps1 = con.prepareStatement("insert into app(id_art, quantite_app, date_app) values(?,?,?)");
        PreparedStatement ps2 = con.prepareStatement("select id from article where libelle = ?");
         if(Integer.parseInt(quantite_app) < 0){
                JOptionPane.showMessageDialog(null, "Veuillez entrer des valeurs numériques positives pour la quantité en stock", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
        ps.setInt(1, Integer.parseInt(quantite_en_stock) + Integer.parseInt(quantite_app));
        ps.setString(2, libelle);
        ps.executeUpdate();
        ps2.setString(1, libelle);
        ResultSet rs = ps2.executeQuery();
       
        if(rs.next()){
            ps1.setInt(1, rs.getInt("id"));
            ps1.setInt(2, Integer.parseInt(quantite_app));
           ps1.setString(3, String.valueOf(LocalDate.now()));
           ps1.executeUpdate();
            JOptionPane.showMessageDialog(null, "Approvisionnement réussi");

        }
        }catch (NumberFormatException e) {
        // Gérer le cas où la conversion de chaîne en nombre échoue
        JOptionPane.showMessageDialog(null, "Veuillez entrer des valeurs numériques valides la quantité d", "Erreur", JOptionPane.ERROR_MESSAGE);
    } 
        
     }
    public static List<Article> SearchArt(String search) throws SQLException{
        Connection con = Connexion.Connect();
        PreparedStatement ps = con.prepareStatement("select libelle, designation, prix, quantite_en_stock, date_creation, quantite_seuille from article inner join categorie on article.id_cat = categorie"
                + ".id where libelle like ? or libelle like ? or libelle like ?");
        ps.setString(1, "%"+search+"%");
        ps.setString(2, "%"+search);
        ps.setString(3, search+"%");
        ResultSet rs = ps.executeQuery();
        List<Article> art = new ArrayList<>();
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
       public static List<Article> VerifierBeforeUpdate(String s) throws SQLException{
        Connection con = Connexion.Connect();
        PreparedStatement ps = con.prepareStatement("select libelle from article where libelle<>?");
        ps.setString(1,s);
        ResultSet rs = ps.executeQuery();
        List<Article> art = new ArrayList<>();
        while(rs.next()){
            Article a = new Article();
            a.setLibelle(rs.getString("libelle"));
            art.add(a);
        }
        return art;
    }
       public static int CountArt() throws SQLException{
           Connection con = Connexion.Connect();
           PreparedStatement ps = con.prepareStatement("select count(*) as total from article");
           ResultSet rs = ps.executeQuery();
           int total=0;
           while(rs.next()){
               total = rs.getInt("total");
           }
           return total;
       }
    public static void main(String[] args) throws SQLException{
            List<Article> art = new ArrayList<>();
       art = SearchArt("Pain");
       System.out.println(art.get(0).getLibelle());
                    
        }
}
