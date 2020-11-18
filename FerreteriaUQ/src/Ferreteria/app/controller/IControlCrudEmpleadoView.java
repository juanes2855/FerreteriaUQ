package Ferreteria.app.controller;

import Ferreteria.app.Excepciones.yaExiste;
import Ferreteria.app.model.Empleado;

public interface IControlCrudEmpleadoView {
	

	public void crearEmpleado(Empleado empleadoNuevo) throws yaExiste;
	public void actualizarEmpleado(String nombreEmpleado, String cargo, int codigoEmpleado, String direccion, double salario, int codigoAnterior) throws yaExiste;
	public Boolean eliminarEmpleado(int codigoEmpleado);
	public Empleado obtenerEmpleado(int codigoEmpleado);

}
