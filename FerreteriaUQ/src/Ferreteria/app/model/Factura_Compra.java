package Ferreteria.app.model;

import java.io.Serializable;

public class Factura_Compra implements Serializable {

	private static final long serialVersionUID = 1L;
	private int numeroFactura;
	private int cantidadCompra;
	private double totalCompra;

	public Factura_Compra(int numeroFactura, int cantidadCompra, double totalCompra) {
		super();
		this.numeroFactura = numeroFactura;
		this.cantidadCompra = cantidadCompra;
		this.totalCompra = totalCompra;
	}
	public Factura_Compra(){
	}

	public int getNumeroFactura() {
		return numeroFactura;
	}

	public void setNumeroFactura(int numeroFactura) {
		this.numeroFactura = numeroFactura;
	}

	public int getCantidadCompra() {
		return cantidadCompra;
	}

	public void setCantidadCompra(int cantidadCompra) {
		this.cantidadCompra = cantidadCompra;
	}

	public double getTotalCompra() {
		return totalCompra;
	}

	public void setTotalCompra(double totalCompra) {
		this.totalCompra = totalCompra;
	}

}
