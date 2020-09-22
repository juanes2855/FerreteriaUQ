package Ferreteria.app.model;

import java.io.Serializable;
import java.util.ArrayList;

import Ferreteria.app.Excepciones.yaExiste;

public class Ferreteria implements Serializable, IFerreteria {

	private static final long serialVersionUID = 1L;
	private String nombreFerreteria;
	private String direccionFerreteria;
	private int telefonoFerreteria;
	private ArrayList<Empleado> listaEmpleados;
	private ArrayList<Producto> listaProductos;
	private ArrayList<Proveedor> listaProveedor;

	public Ferreteria(String nombreFerreteria, String direccionFerreteria, int telefonoFerreteria) {
		super();
		this.nombreFerreteria = nombreFerreteria;
		this.direccionFerreteria = direccionFerreteria;
		this.telefonoFerreteria = telefonoFerreteria;
		listaEmpleados = new ArrayList<Empleado>();
		listaProductos = new ArrayList<Producto>();
		listaProveedor = new ArrayList<Proveedor>();
	}

	public Ferreteria() {

	}

	@Override
	public void crearEmpleado(Empleado nuevoEmpleado) throws yaExiste {
		Empleado empleadoExistente = obtenerEmpleado(nuevoEmpleado.getCodigoEmpleado());

		if (empleadoExistente != null) {
			throw new yaExiste("Ya existe el empleado");
		} else {
			getListaEmpleados().add(nuevoEmpleado);
		}

	}

	public void actualizarEmpleado(String nombreEmpleado, String cargo, int codigoEmpleado, String direccion,
			double salario) {

		Empleado empleado = obtenerEmpleado(codigoEmpleado);
		if (empleado != null) {
			empleado.setCargo(cargo);
			empleado.setCodigoEmpleado(codigoEmpleado);
			empleado.setDireccion(direccion);
			empleado.setNombreEmpleado(nombreEmpleado);
			empleado.setSalario(salario);

		}

	}

	public Empleado obtenerEmpleado(int codigoEmpleado) {

		Empleado empleadoE = null;

		for (Empleado empleado : getListaEmpleados()) {
			if (empleado.getCodigoEmpleado() == codigoEmpleado) {
				empleadoE = empleado;
				break;
			}
		}
		return empleadoE;
	}

	@Override
	public Boolean eliminarEmpleado(int codigoEmpleado) {
		Boolean Eliminado = false;
		Empleado empleado = obtenerEmpleado(codigoEmpleado);

		if (empleado != null) {
			listaEmpleados.remove(empleado);
			Eliminado = true;
		}
		return Eliminado;
	}

	@Override
	public void crearProducto(Producto producto) throws yaExiste {
		Producto productoExistente = obtenerProducto(producto.getCodigoProducto());

		if (productoExistente != null) {
			throw new yaExiste("Ya existe el producto");
		} else {
			getListaProductos().add(producto);
		}

	}

	public Producto obtenerProducto(int codigoProducto) {
		Producto productoE = null;

		for (Producto producto : getListaProductos()) {
			if (producto.getCodigoProducto() == codigoProducto) {
				productoE = producto;
				break;
			}
		}
		return productoE;
	}

	@Override
	public void modificarProducto(String nombreProducto, int codigoProducto, double precio, String categoria,
			String marca) {

		Producto producto = obtenerProducto(codigoProducto);
		if (producto != null) {

			producto.setCategoria(categoria);
			producto.setMarca(marca);
			producto.setCodigoProducto(codigoProducto);
			producto.setNombreProducto(nombreProducto);
			producto.setPrecio(precio);
		}

	}

	@Override
	public Boolean eliminarProducto(int codigoProducto) {
		Boolean Eliminado = false;
		Producto producto = obtenerProducto(codigoProducto);

		if (producto != null) {
			listaProductos.remove(producto);
			Eliminado = true;
		}
		return Eliminado;
	}

	@Override
	public void anadirProveedor(Proveedor proveedor) throws yaExiste {
		Proveedor proveedorExistente = obtenerProveedor(proveedor.getCodigoProveedor());

		if (proveedorExistente != null) {
			throw new yaExiste("Ya existe el proveedor");
		} else {
			getListaProveedor().add(proveedor);
		}

	}

	@Override
	public void modificarProveedor(String nombreProveedor, int codigoProveedor, int telefonoProveedor,
			String direccionProveedor) {

		Proveedor proveedor = obtenerProveedor(codigoProveedor);

		if (proveedor != null) {
			proveedor.setCodigoProveedor(codigoProveedor);
			proveedor.setDireccionProveedor(direccionProveedor);
			proveedor.setNombreProveedor(nombreProveedor);
			proveedor.setTelefonoProveedor(telefonoProveedor);
		}

	}

	@Override
	public Boolean eliminarProveedor(int codigoProveedor) {

		Boolean Eliminado = false;
		Proveedor proveedor = obtenerProveedor(codigoProveedor);

		if (proveedor != null) {
			listaEmpleados.remove(proveedor);
			Eliminado = true;
		}
		return Eliminado;
	}

	public Proveedor obtenerProveedor(int codigoProveedor) {
		Proveedor proveedorE = null;

		for (Proveedor proveedor : getListaProveedor()) {
			if (proveedor.getCodigoProveedor() == codigoProveedor) {
				proveedorE = proveedor;
				break;
			}
		}
		return proveedorE;
	}

	public String getNombreFerreteria() {
		return nombreFerreteria;
	}

	public void setNombreFerreteria(String nombreFerreteria) {
		this.nombreFerreteria = nombreFerreteria;
	}

	public String getDireccionFerreteria() {
		return direccionFerreteria;
	}

	public void setDireccionFerreteria(String direccionFerreteria) {
		this.direccionFerreteria = direccionFerreteria;
	}

	public int getTelefonoFerreteria() {
		return telefonoFerreteria;
	}

	public void setTelefonoFerreteria(int telefonoFerreteria) {
		this.telefonoFerreteria = telefonoFerreteria;
	}

	public ArrayList<Empleado> getListaEmpleados() {
		return listaEmpleados;
	}

	public void setListaEmpleados(ArrayList<Empleado> listaEmpleados) {
		this.listaEmpleados = listaEmpleados;
	}

	public ArrayList<Producto> getListaProductos() {
		return listaProductos;
	}

	public void setListaProductos(ArrayList<Producto> listaProductos) {
		this.listaProductos = listaProductos;
	}

	public ArrayList<Proveedor> getListaProveedor() {
		return listaProveedor;
	}

	public void setListaProveedor(ArrayList<Proveedor> listaProveedor) {
		this.listaProveedor = listaProveedor;
	}

}
