package Ferreteria.app.model;

import java.io.Serializable;

public class Producto implements Serializable {

	private static final long serialVersionUID = 1L;
	private String nombreProducto;
	private int codigoProducto;
	private double precio;
	private String categoria;
	private Proveedor proveedorAsociado;

	public Producto(String nombreProducto, int codigoProducto, double precio, String categoria,
			Proveedor proveedorAsociado) {
		super();
		this.nombreProducto = nombreProducto;
		this.codigoProducto = codigoProducto;
		this.precio = precio;
		this.categoria = categoria;
		this.proveedorAsociado = proveedorAsociado;
	}

	public Producto() {
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

	public Proveedor getProveedorAsociado() {
		return proveedorAsociado;
	}

	public void setProveedorAsociado(Proveedor proveedorAsociado) {
		this.proveedorAsociado = proveedorAsociado;
	}

}
