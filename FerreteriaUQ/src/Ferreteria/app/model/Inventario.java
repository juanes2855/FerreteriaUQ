package Ferreteria.app.model;

import java.io.Serializable;

public class Inventario implements Serializable {

	private static final long serialVersionUID = 1L;
	private int codigoInventario;
	private int cantidadMaxima;
	private int cantidadInventario;
	private String fechaEntrada;
	private Empleado empleado;
	private Detalle_Inventario detalleInventario;

	public Inventario(int codigoInventario, int cantidadMaxima, int cantidadInventario, String fechaEntrada,
			Empleado empleado, Detalle_Inventario detalleInventario) {
		super();
		this.codigoInventario = codigoInventario;
		this.cantidadMaxima = cantidadMaxima;
		this.cantidadInventario = cantidadInventario;
		this.fechaEntrada = fechaEntrada;
		this.empleado = empleado;
		this.detalleInventario = detalleInventario;
	}

	public int getCodigoInventario() {
		return codigoInventario;
	}

	public void setCodigoInventario(int codigoInventario) {
		this.codigoInventario = codigoInventario;
	}

	public int getCantidadMaxima() {
		return cantidadMaxima;
	}

	public void setCantidadMaxima(int cantidadMaxima) {
		this.cantidadMaxima = cantidadMaxima;
	}

	public int getCantidadInventario() {
		return cantidadInventario;
	}

	public void setCantidadInventario(int cantidadInventario) {
		this.cantidadInventario = cantidadInventario;
	}

	public String getFechaEntrada() {
		return fechaEntrada;
	}

	public void setFechaEntrada(String fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public Detalle_Inventario getDetalleInventario() {
		return detalleInventario;
	}

	public void setDetalleInventario(Detalle_Inventario detalleInventario) {
		this.detalleInventario = detalleInventario;
	}

}
