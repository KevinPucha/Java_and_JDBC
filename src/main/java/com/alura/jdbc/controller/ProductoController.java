package com.alura.jdbc.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.alura.jdbc.dao.ProductoDAO;
import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Producto;

public class ProductoController {
	
	private ProductoDAO productoDAO;
	
	public ProductoController() {
		var factory = new ConnectionFactory();
		this.productoDAO = new ProductoDAO(factory.recuperaConexion());
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

	public int eliminar(Integer id) {
		return productoDAO.eliminar(id);
	}
	
	public List<Producto> listar() {
		return productoDAO.listar(); 		
	}
	
    public void guardar (Producto producto){
    	productoDAO.guardar(producto);	
	}
}
