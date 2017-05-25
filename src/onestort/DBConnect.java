/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package onestort;

/**
 *
 * @author Henrique
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * 
 * @author Narayan
 */

public class DBConnect {

    private static Connection conn;
    private static final String url = "jdbc:mysql://mysql06.onestorsoftwares.hospedagemdesites.ws/onestorsoftwar4";
    private static final String user = "onestorsoftwar4";
    private static final String pass = "academico1";

    public static Connection connect() throws SQLException{
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        }catch(ClassNotFoundException | InstantiationException | IllegalAccessException cnfe){
            System.err.println("Error: "+cnfe.getMessage());
        }

        conn = DriverManager.getConnection(url,user,pass);
        Statement s = conn.createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS compromissos " +
                   "(Data TEXT, " +
                   " Turma TEXT, " + 
                   " HInicial TIME, " + 
                   " HFinal TIME, " + 
                   " Atividade TEXT, "
                + " IDProfessor TEXT)"; 
        int Result = s.executeUpdate(sql);
        return conn;
    }
    public static Connection getConnection() throws SQLException, ClassNotFoundException{
        if(conn !=null && !conn.isClosed())
            return conn;
        connect();
        return conn;

    }
}