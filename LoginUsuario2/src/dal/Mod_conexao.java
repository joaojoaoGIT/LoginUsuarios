package dal;

import java.sql.*;

public class Mod_conexao {

    public static Connection conector() {
        java.sql.Connection conexao = null;

        String url = "jdbc:mysql://localhost:3306/bancoJava";
        String user = "root";
        String password = ""; 

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;
        } catch (Exception e) {
            
            System.out.println("Erro na conexão: " + e.getMessage());
            return null;
        }
    }
}
