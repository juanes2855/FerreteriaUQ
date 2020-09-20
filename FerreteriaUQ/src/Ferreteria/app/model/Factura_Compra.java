package Ferreteria.app.model;

import java.io.Serializable;

public class Factura_Compra implements Serializable {

	private static final long serialVersionUID = 1L;
	private int numeroFactura;
	private int cantidadCompra;
	private double totalCompra;
	private double descuento;

	public Factura_Compra(int numeroFactura, int cantidadCompra, double totalCompra, double descuento) {
		super();
		this.numeroFactura = numeroFactura;
		this.cantidadCompra = cantidadCompra;
		this.totalCompra = totalCompra;
		this.descuento = descuento;
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

	public double getDescuento() {
		return descuento;
	}

	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}

}
