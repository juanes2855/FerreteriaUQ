package Ferreteria.app.model;

import java.io.Serializable;

public class Producto implements Serializable {

	private static final long serialVersionUID = 1L;
	private String nombreProducto;
	private int codigoProducto;
	private double precio;
	private String categoria;
	private String marca;

	public Producto(String nombreProducto, int codigoProducto, double precio, String categoria, String marca) {
		super();
		this.nombreProducto = nombreProducto;
		this.codigoProducto = codigoProducto;
		this.precio = precio;
		this.categoria = categoria;
		this.marca = marca;
	}

	public Producto() {
		// TODO Auto-generated constructor stub
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public int getCodigoProducto() {
		return codigoProducto;
	}

	public void setCodigoProducto(int codigoProducto) {
		this.codigoProducto = codigoProducto;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

}
