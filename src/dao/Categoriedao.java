/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
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
    
        
       
        
        
        
    
}
