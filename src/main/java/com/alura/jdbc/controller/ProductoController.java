package com.alura.jdbc.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alura.jdbc.dao.ProductoDAO;
import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Producto;

public class ProductoController {
	
	private ProductoDAO productoDAO;
	
	public ProductoController() {
		this.productoDAO = new ProductoDAO(new ConnectionFactory().recuperaConexion());
	}

	public int modificar(String nombre, String descripcion, Integer cantidad, Integer id) throws SQLException {
	    ConnectionFactory factory = new ConnectionFactory();
	    Connection con = factory.recuperaConexion();
	    
	    PreparedStatement statement = con.prepareStatement("UPDATE PRODUCTO SET "
	            + " NOMBRE = ?" 
	            + ", DESCRIPCION = ?"
	            + ", CANTIDAD = ?"
	            + " WHERE ID = ?");
	    
	    statement.setString(1,nombre);
	    statement.setString(2,descripcion);
	    statement.setInt(3,cantidad);
	    statement.setInt(4,id);
	    
	    statement.execute();

	    int updateCount = statement.getUpdateCount();

	    statement.close();
	    con.close();   

	    return updateCount;
	}

	public int eliminar(Integer id) throws SQLException {
		Connection con = new ConnectionFactory().recuperaConexion();
		
		PreparedStatement statement = con.prepareStatement("DELETE FROM PRODUCTO WHERE ID = ?");
		statement.setInt(1,id);
		statement.execute();
		int updateCount = statement.getUpdateCount();
		
		statement.close();
		con.close();
		return updateCount;
	}
	
	public List<Map<String, String>> listar() {
		Connection con = new ConnectionFactory().recuperaConexion();
		try(con){
			
			PreparedStatement statement = con.prepareStatement("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTO");
			statement.execute();
			
			ResultSet resultSet = statement.getResultSet();

			List<Map<String,String>> resultado = new ArrayList<>();
						
			while(resultSet.next()) {
				Map<String, String> fila = new HashMap<>();
				fila.put("ID",String.valueOf(resultSet.getInt("ID")));
				fila.put("NOMBRE", resultSet.getString("NOMBRE"));
				fila.put("DESCRIPCION", resultSet.getString("DESCRIPCION"));
				fila.put("CANTIDAD",String.valueOf(resultSet.getInt("CANTIDAD")));
				
				resultado.add(fila);
			}
			statement.close();
			con.close();
			
			return resultado;
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		
		
	}
	
    public void guardar (Producto producto){
		productoDAO.guardar(producto);
	}
}
