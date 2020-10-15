package Ferreteria.app.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Compra implements Serializable {

	private static final long serialVersionUID = 1L;
	private int codigoCompra;
	private String fechaCompra;
	private int cantidadCompra;
	private Factura_Compra factura_Compra;
	private Empleado empleadoAsociado;
	private Proveedor proveedorAsociado;
	
	private String nombreEmpleado;
	private int codigoEmpleado;
	private String nombreProveedor;
	private int codigoProveedor;
	private ArrayList<Producto>productosCompra;
	

	public Compra(int codigoCompra, String fechaCompra, int cantidadCompra,
			Factura_Compra factura_Compra) {
		super();
		this.codigoCompra = codigoCompra;
		this.fechaCompra = fechaCompra;
		this.cantidadCompra = cantidadCompra;
		this.factura_Compra = factura_Compra;
		productosCompra= new ArrayList<Producto>();
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

	public Factura_Compra getFactura_Compra() {
		return factura_Compra;
	}

	public void setFactura_Compra(Factura_Compra factura_Compra) {
		this.factura_Compra = factura_Compra;
	}

	public Empleado getEmpleadoAsociado() {
		return empleadoAsociado;
	}

	public void setEmpleadoAsociado(Empleado empleadoAsociado) {
		this.empleadoAsociado = empleadoAsociado;
	}

	public Proveedor getProveedorAsociado() {
		return proveedorAsociado;
	}

	public void setProveedorAsociado(Proveedor proveedorAsociado) {
		this.proveedorAsociado = proveedorAsociado;
	}

	public String getNombreEmpleado() {
		return nombreEmpleado;
	}

	public void setNombreEmpleado(String nombreEmpleado) {
		this.nombreEmpleado = nombreEmpleado;
	}

	public int getCodigoEmpleado() {
		return codigoEmpleado;
	}

	public void setCodigoEmpleado(int codigoEmpleado) {
		this.codigoEmpleado = codigoEmpleado;
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

	public ArrayList<Producto> getProductosCompra() {
		return productosCompra;
	}

	public void setProductosCompra(ArrayList<Producto> productosCompra) {
		this.productosCompra = productosCompra;
	}

}
