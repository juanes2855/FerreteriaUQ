package Ferreteria.app.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Inventario implements Serializable {

	private static final long serialVersionUID = 1L;
	private int codigoInventario;
	private int cantidadMaxima;
	private int cantidadInventario;
	private String fechaEntrada;
	private Empleado empleado;
	private ArrayList<Detalle_Inventario>detallesInventario;
	

	public Inventario(int codigoInventario, int cantidadMaxima, int cantidadInventario, String fechaEntrada,
			Empleado empleado) {
		super();
		this.codigoInventario = codigoInventario;
		this.cantidadMaxima = cantidadMaxima;
		this.cantidadInventario = cantidadInventario;
		this.fechaEntrada = fechaEntrada;
		this.empleado = empleado;
		detallesInventario= new ArrayList<Detalle_Inventario>();
		
	}
	
	public void anadirDetalleInventario(int cantidad, Producto producto){
		Detalle_Inventario detalle= new Detalle_Inventario(cantidad, producto);
		detallesInventario.add(detalle);
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

	public ArrayList<Detalle_Inventario> getDetallesInventario() {
		return detallesInventario;
	}

	public void setDetallesInventario(ArrayList<Detalle_Inventario> detallesInventario) {
		this.detallesInventario = detallesInventario;
	}

}
