package com.alura.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.alura.jdbc.modelo.Categoria;

public class CategoriaDAO {
	
	private Connection con;
	
	public CategoriaDAO(Connection con) {
		this.con = con;
	}

	public List<Categoria> listar() {
		List<Categoria> resultado = new ArrayList<>();
		
		try {
			var querySelect = "SELECT ID, NOMBRE FROM CATEGORIA";
			System.out.println(querySelect);
			PreparedStatement statement = con.prepareStatement(querySelect);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				resultado.add(new Categoria(resultSet.getInt("ID"),resultSet.getString("NOMBRE")));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return resultado;
	}

	
	
}
