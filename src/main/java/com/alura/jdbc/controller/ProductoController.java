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

import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Producto;

public class ProductoController {

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
		// the following method allows us to verify if something really was eliminated
		// Moreover, this return the numbers of rows that were affected after executed the statement
		int updateCount = statement.getUpdateCount();
		
		statement.close();
		con.close();
		return updateCount;
	// When we return directly the getUpdate we can't close the connection. Then we mus save this value 	
	// into a variable
		//return statement.getUpdateCount(); 
	}
	
	// This method just select the information of a table in the DB and restore it in a list
	public List<Map<String, String>> listar() throws SQLException {
		// in order to access to a data base, we always need to create a connection
		Connection con = new ConnectionFactory().recuperaConexion();
		// reserved words of SQL are called statements here in java
		// for example: Select, From, Where, etc.
		PreparedStatement statement = con.prepareStatement("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTO");
		statement.execute();
		// Here we save the table given by the statement
		ResultSet resultSet = statement.getResultSet();
		// Instantiation of List in order to save all the contents of the table
		List<Map<String,String>> resultado = new ArrayList<>();
			
		//	First treatment with statement 
		//		Statement statement = con.createStatement();
		//	Here the method .execute returns a boolean to tell us if the result of this execute is a list or not
		//		statement.execute("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM PRODUCTO");
		//		ResultSet resultSet = statement.getResultSet();
		//		List<Map<String,String>> resultado = new ArrayList<>();
		
		while(resultSet.next()) { // .next() move the pointer of the row in the resultSet
			Map<String, String> fila = new HashMap<>();
			fila.put("ID",String.valueOf(resultSet.getInt("ID")));
			fila.put("NOMBRE", resultSet.getString("NOMBRE"));
			fila.put("DESCRIPCION", resultSet.getString("DESCRIPCION"));
			fila.put("CANTIDAD",String.valueOf(resultSet.getInt("CANTIDAD")));
			
			resultado.add(fila);
		}
		// In java the commands used in SQL are called statements, for example: SELECT, DELETE, etc.
		statement.close();
		con.close();
		
		return resultado;
	}
	// Since we change of method, we need to put here Producto (the class) instead of the old Map<String,String> 
    public void guardar (Producto producto) throws SQLException {
		// extract the information required
//    	String nombre = producto.getNombre();
//		String descripcion = producto.getDescripcion();
//		Integer cantidad = producto.getCantidad();
//    	Integer maximoCantidad = 50;
		
		final Connection con = new ConnectionFactory().recuperaConexion();
		try (con){
			con.setAutoCommit(false);
			final PreparedStatement statement = con.prepareStatement("INSERT INTO PRODUCTO(nombre, descripcion,cantidad) VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS);
			try(statement) {  
//				do {
//					int cantidadParaGuardar = Math.min(cantidad, maximoCantidad);
					ejecutaRegistro(producto, statement);
//					cantidad -= maximoCantidad;
//				} while(cantidad > 0);
				con.commit();
			} catch(Exception e) {
				con.rollback();
			}
			
			statement.close();
		}
		// The AutoCommit, when it´s activated, saves automatically the transactions into the database.
		// While, if we turn off this function, then we need to tell to the machine we want to save the
		// transaction with a commit, and, if we want to undo the effects, you use rollback
		
		// Here we make a new rule of business

// 		This code is the simplest one, but with it can happen the SQL injection
//		PreparedStatement statement = con.prepareStatement("INSERT INTO PRODUCTO(nombre, descripcion,cantidad) VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS);
//		statement.setString(1, producto.get("NOMBRE"));
//		statement.setString(2, producto.get("DESCRIPCION"));
//		statement.setInt(3, Integer.valueOf(producto.get("CANTIDAD")));
//    
//    	statement.execute();
		
		// We know that statement.execute makes return a boolean value (execute statement generates a Resutset)
		// but wiht Statement.RETURN_GENERATED_KEYS this return the key generated
 
// 		With this, SQL injection is possible    
//		Statement statement = con.createStatement();	
//   	statement.execute("INSERT INTO PRODUCTO(nombre, descripcion,cantidad) VALUES('"
//				+ producto.get("NOMBRE") + "','"
//				+ producto.get("DESCRIPCION") + "',"
//				+ producto.get("CANTIDAD") + ")", Statement.RETURN_GENERATED_KEYS);
		
//	With this we get a list of all generated keys that we get thanks to the Statement.RETURN_GENERATED_KEYS
//		ResultSet resultSet = statement.getGeneratedKeys();
//		
//		while (resultSet.next()) {
//			System.out.println(
//					String.format(
//							"Fue insertado el producto de ID %d", resultSet.getInt(1)));
//		}
			
		con.close();
		
	}

	private void ejecutaRegistro(Producto producto, PreparedStatement statement)
			throws SQLException {
		// To generate an error
//		if(cantidad < 50) {
//			throw new RuntimeException("Ocurrió un error");
//		}
		statement.setString(1, producto.getNombre());
		statement.setString(2, producto.getDescripcion());
		statement.setInt(3, producto.getCantidad());
    
    	statement.execute();
		
    	// Get the generated key
		ResultSet resultSet = statement.getGeneratedKeys();
		
		while(resultSet.next()) {
			System.out.println(
					String.format(
					"Fue insertado el producto de ID %d",
					resultSet.getInt(1)));
		}
	}

}
