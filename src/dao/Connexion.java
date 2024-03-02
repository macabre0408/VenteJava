package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {
    private static String url = "jdbc:mysql://localhost:3306/vente";

    public static Connection Connect() throws SQLException {
        Connection con = null;
        try {
            // Chargement du pilote JDBC
            Class.forName("com.mysql.jdbc.Driver");
            // Établissement de la connexion
            con = DriverManager.getConnection(url, "root", "");
        } catch (ClassNotFoundException ex) {
            // Gestion de l'exception de chargement de classe
            System.out.println("Erreur de chargement de pilote JDBC: " + ex.getMessage());
        } catch (SQLException ex) {
            // Gestion des exceptions SQL
            System.out.println("Erreur de connexion à la base de données: " + ex.getMessage());
        }
        return con; 
    }
    
}

