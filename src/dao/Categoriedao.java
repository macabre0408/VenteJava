/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import entities.Categorie;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dell
 */
public class Categoriedao {
    public static void saveCat(String laDesignation) throws SQLException{
        Connection con = Connexion.Connect();
        //formulation de la requête
        String query = "insert into categorie(designation) values(?)";
        //preparation de la requête
        
        PreparedStatement ps = null;
        ps = con.prepareStatement(query);
        ps.setString(1, laDesignation);
        ps.executeUpdate();
        
    }
    public static List<Categorie> ShowCat() throws SQLException{
        List<Categorie> cat = new ArrayList<>();
        Connection con = Connexion.Connect();
        String query = "select designation from categorie";
        PreparedStatement ps = null;
        ps = con.prepareStatement(query);
        ResultSet rs = null;
        rs = ps.executeQuery();
        while(rs.next()){
            Categorie c = new Categorie();
            c.setDesignation(rs.getString("designation"));
            cat.add(c);
        }
        return cat;
    }
    public static void getOldDesignation(String old) throws SQLException{
        Connection con = Connexion.Connect();
        String query = "update categorie set old_designation = ? where designation = ?";
        PreparedStatement ps = null;
        ps = con.prepareStatement(query);
        ps.setString(1, old);
        ps.setString(2, old);
        ps.executeUpdate();
    }
    public static void UpdateCat(String New) throws SQLException{
        Connection con = Connexion.Connect();
        String query = "update categorie set designation = ? where designation = old_designation";
        PreparedStatement ps = null;
        ps = con.prepareStatement(query);
        ps.setString(1, New);
        ps.executeUpdate();
    }
    public static boolean tryFindCat(String desi) throws SQLException{
        Connection con = Connexion.Connect();
        String query = "select * from categorie where designation = ?";
        PreparedStatement ps = null;
        ps = con.prepareStatement(query);
        ps.setString(1, desi);
        ResultSet rs = null;
        rs = ps.executeQuery();
        if(rs.next()){
            return true;
        }
        return false;
    }
    public static List<Categorie> SearchCat(String search) throws SQLException{
        Connection con = Connexion.Connect();
        String query = "select designation from categorie where designation like ? or designation like ? or designation like ? ";
        List<Categorie> cat = new ArrayList<>();
        PreparedStatement ps = null;
        ps = con.prepareStatement(query);
        ps.setString(1, "%"+search+"%");
        ps.setString(2, search+"%");
        ps.setString(3, "%"+search);
        ResultSet rs = null;
        rs = ps.executeQuery();
        while(rs.next()){
            Categorie c = new Categorie();
            c.setDesignation(rs.getString("designation"));
            cat.add(c);
        }
        return cat;
    }    
    public static void main(String[] args) throws SQLException{
        List<Categorie> cat = new ArrayList<>();
        cat = SearchCat("o");
        for(Categorie c:cat){
            System.out.println(c.getDesignation());
        }
    }
}
