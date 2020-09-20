package Ferreteria.app.model;

import java.io.Serializable;

public class Empleado implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private double salario;
	private String cargo;
	private String direccion;
	private int codigoEmpleado;
	private String nombreEmpleado;
	
	
	public Empleado(double salario, String cargo, String direccion, int codigoEmpleado, String nombreEmpleado) {
		super();
		this.salario = salario;
		this.cargo = cargo;
		this.direccion = direccion;
		this.codigoEmpleado = codigoEmpleado;
		this.nombreEmpleado = nombreEmpleado;
	}
	

	public Empleado() {
		// TODO Auto-generated constructor stub
	}


	public double getSalario() {
		return salario;
	}


	public void setSalario(double salario) {
		this.salario = salario;
	}


	public String getCargo() {
		return cargo;
	}


	public void setCargo(String cargo) {
		this.cargo = cargo;
	}


	public String getDireccion() {
		return direccion;
	}


	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}


	public int getCodigoEmpleado() {
		return codigoEmpleado;
	}


	public void setCodigoEmpleado(int codigoEmpleado) {
		this.codigoEmpleado = codigoEmpleado;
	}


	public String getNombreEmpleado() {
		return nombreEmpleado;
	}


	public void setNombreEmpleado(String nombreEmpleado) {
		this.nombreEmpleado = nombreEmpleado;
	}
	

}
