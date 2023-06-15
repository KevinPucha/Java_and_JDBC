package com.alura.jdbc.pruebas;

import java.sql.Connection;
import java.sql.SQLException;

import com.alura.jdbc.factory.ConnectionFactory;

public class PruebaPoolDeConexiones {
	public static void main(String[] args) throws SQLException {
		ConnectionFactory connectionFactory = new ConnectionFactory();
		
		// Here we simulate creating multiple connection to the DB
		for (int i = 0; i < 20; i++) {
			Connection con = connectionFactory.recuperaConexion();
			System.out.println("Abriendo la conexion de número " + (i + 1));
			con.close();
			System.out.println("Conexion " + (i+1) + " cerrada");
		}
	}
}
