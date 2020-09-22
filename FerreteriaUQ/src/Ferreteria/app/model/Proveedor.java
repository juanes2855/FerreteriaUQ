package Ferreteria.app.model;

import java.io.Serializable;

public class Proveedor implements Serializable {

	private static final long serialVersionUID = 1L;
	private String nombreProveedor;
	private int codigoProveedor;
	private int telefonoProveedor;
	private String direccionProveedor;

	public Proveedor(String nombreProveedor, int codigoProveedor, int telefonoProveedor, String direccionProveedor) {
		super();
		this.nombreProveedor = nombreProveedor;
		this.codigoProveedor = codigoProveedor;
		this.telefonoProveedor = telefonoProveedor;
		this.direccionProveedor = direccionProveedor;
	}

	public Proveedor() {
		// TODO Auto-generated constructor stub
	}

	public String getNombreProveedor() {
		return nombreProveedor;
	}

	public void setNombreProveedor(String nombreProveedor) {
		this.nombreProveedor = nombreProveedor;
	}

	public int getCodigoProveedor() {
		return codigoProveedor;
	}

	public void setCodigoProveedor(int codigoProveedor) {
		this.codigoProveedor = codigoProveedor;
	}

	public int getTelefonoProveedor() {
		return telefonoProveedor;
	}

	public void setTelefonoProveedor(int telefonoProveedor) {
		this.telefonoProveedor = telefonoProveedor;
	}

	public String getDireccionProveedor() {
		return direccionProveedor;
	}

	public void setDireccionProveedor(String direccionProveedor) {
		this.direccionProveedor = direccionProveedor;
	}

}
