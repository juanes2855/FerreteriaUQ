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

	public Ferreteria(String nombreFerreteria, String direccionFerreteria, int telefonoFerreteria) {
		super();
		this.nombreFerreteria = nombreFerreteria;
		this.direccionFerreteria = direccionFerreteria;
		this.telefonoFerreteria = telefonoFerreteria;
		listaEmpleados = new ArrayList<Empleado>();
	}
	
	public Ferreteria() {
		
	}

	@Override
	public void crearEmpleado(Empleado nuevoEmpleado) throws yaExiste {
		Empleado empleadoExistente = obtenerEmpleado(nuevoEmpleado.getCodigoEmpleado());
		
		if(empleadoExistente != null){
			throw new yaExiste("Ya existe el empleado");			
		}
		else{
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
		
		if(empleado != null){
			listaEmpleados.remove(empleado);
			Eliminado = true;
		}
		return Eliminado;
	}

	/*@Override
	public void crearProducto(Producto producto) {
		// TODO Auto-generated method stub

	}

	@Override
	public void modificarProducto(String nombreProducto, int codigoProducto, double precio, String categoria,
			String marca) {
		// TODO Auto-generated method stub

	}

	@Override
	public Boolean eliminarProducto(int codigoProducto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void anadirProveedor(Proveedor proveedor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void modificarProveedor(String nombreProveedor, int codigoProveedor, int telefonoProveedor,
			String direccionProveedor) {
		// TODO Auto-generated method stub

	}

	@Override
	public Boolean eliminarProveedor(int codigoProveedor) {
		// TODO Auto-generated method stub
		return null;
	}*/

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

}
