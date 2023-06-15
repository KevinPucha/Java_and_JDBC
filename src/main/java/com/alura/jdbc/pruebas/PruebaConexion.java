package com.alura.jdbc.pruebas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.alura.jdbc.factory.ConnectionFactory;

public class PruebaConexion {

    public static void main(String[] args) throws SQLException {
    	
    	Connection con = new ConnectionFactory().recuperaConexion();
    	
//	Here we replace the way that we stablish a conexion, insted we have created one class for that    	
//    	String url = "jdbc:mysql://localhost/control_de_stock?useTimeZone=true&serverTimeZone=UTC"; 
//      String userName = "root";
//      String passWord = "KyD031014";
//    	
//    	Connection con = DriverManager.getConnection(url,userName,passWord);
                
        System.out.println("Cerrando la conexión");

        con.close();
    }

}
