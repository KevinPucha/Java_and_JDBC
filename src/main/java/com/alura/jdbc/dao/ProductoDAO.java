package com.alura.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.alura.jdbc.modelo.Producto;

public class ProductoDAO {

	private Connection con;

	public ProductoDAO(Connection con) {
		this.con = con;
	}

	public void guardar(Producto producto) {
		try{
			PreparedStatement statement = con.prepareStatement("INSERT INTO PRODUCTO(nombre, descripcion,cantidad,categoria_id) VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			try(statement) { 
				statement.setString(1, producto.getNombre());
				statement.setString(2, producto.getDescripcion());
				statement.setInt(3, producto.getCantidad());
				statement.setInt(4, producto.getCategoriaId());
				statement.execute();

				ResultSet resultSet = statement.getGeneratedKeys();
				try(resultSet){
					while(resultSet.next()) {
						producto.setId(resultSet.getInt(1));
						System.out.println(String.format("Fue insertado el producto %s",producto));
					}
				}
			}
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Producto> listar() {
		try{
			PreparedStatement statement = con.prepareStatement("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTO");
			statement.execute();
			ResultSet resultSet = statement.getResultSet();

			List<Producto> resultado = new ArrayList<>();
			while(resultSet.next()) {
				Producto fila = new Producto(
						resultSet.getInt("ID"),
						resultSet.getString("NOMBRE"),
						resultSet.getString("DESCRIPCION"),
						resultSet.getInt("CANTIDAD"));
				resultado.add(fila);
			}
			return resultado;
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int eliminar(int id) {
		try{
			PreparedStatement statement = con.prepareStatement("DELETE FROM PRODUCTO WHERE ID = ?");
			statement.setInt(1,id);
			statement.execute();

			int updateCount = statement.getUpdateCount();
			return updateCount;
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public int modificar(Producto producto) {
		PreparedStatement statement;
		try {
			statement = con.prepareStatement("UPDATE PRODUCTO SET "
					+ " NOMBRE = ?" 
					+ ", DESCRIPCION = ?"
					+ ", CANTIDAD = ?"
					+ " WHERE ID = ?");
			statement.setString(1,producto.getNombre());
			statement.setString(2,producto.getDescripcion());
			statement.setInt(3,producto.getCantidad());
			statement.setInt(4,producto.getId());
			statement.execute();

			int updateCount = statement.getUpdateCount();
			return updateCount;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Producto> listar(Integer categoriaId) {
		try{
			var querySelect = "SELECT ID, NOMBRE,"
					+ " DESCRIPCION, CANTIDAD FROM PRODUCTO"
					+ " WHERE CATEGORIA_ID = ?";
			
			System.out.println(querySelect);
			
			PreparedStatement statement = con.prepareStatement(querySelect);
			statement.setInt(1, categoriaId);
			statement.execute();
			ResultSet resultSet = statement.getResultSet();

			List<Producto> resultado = new ArrayList<>();
			while(resultSet.next()) {
				Producto fila = new Producto(
						resultSet.getInt("ID"),
						resultSet.getString("NOMBRE"),
						resultSet.getString("DESCRIPCION"),
						resultSet.getInt("CANTIDAD"));
				resultado.add(fila);
			}
			return resultado;
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
