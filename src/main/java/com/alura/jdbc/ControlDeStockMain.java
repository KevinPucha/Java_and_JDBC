package com.alura.jdbc;

import javax.swing.JFrame;

import com.alura.jdbc.view.ControlDeStockFrame;

public class ControlDeStockMain {

	public static void main(String[] args) {
		// inicializo la pantalla
		ControlDeStockFrame productoCategoriaFrame = new ControlDeStockFrame();
		// stop the process when I click over the "x" button
		productoCategoriaFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
