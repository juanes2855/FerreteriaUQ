package Ferreteria.app.model;

import java.io.Serializable;

public class Compra implements Serializable {

	private static final long serialVersionUID = 1L;
	private int codigoCompra;
	private String fechaCompra;
	private int cantidadCompra;
	private int tipoPago;
	private Factura_Compra factura_Compra;

	public Compra(int codigoCompra, String fechaCompra, int cantidadCompra, int tipoPago,
			Factura_Compra factura_Compra) {
		super();
		this.codigoCompra = codigoCompra;
		this.fechaCompra = fechaCompra;
		this.cantidadCompra = cantidadCompra;
		this.tipoPago = tipoPago;
		this.factura_Compra = factura_Compra;
	}

	public int getCodigoCompra() {
		return codigoCompra;
	}
    //no
	public void setCodigoCompra(int codigoCompra) {
		this.codigoCompra = codigoCompra;
	}

	public String getFechaCompra() {
		return fechaCompra;
	}

	public void setFechaCompra(String fechaCompra) {
		this.fechaCompra = fechaCompra;
	}

	public int getCantidadCompra() {
		return cantidadCompra;
	}

	public void setCantidadCompra(int cantidadCompra) {
		this.cantidadCompra = cantidadCompra;
	}

	public int getTipoPago() {
		return tipoPago;
	}

	public void setTipoPago(int tipoPago) {
		this.tipoPago = tipoPago;
	}

	public Factura_Compra getFactura_Compra() {
		return factura_Compra;
	}

	public void setFactura_Compra(Factura_Compra factura_Compra) {
		this.factura_Compra = factura_Compra;
	}

}
