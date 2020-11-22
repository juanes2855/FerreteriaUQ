package Ferreteria.app.controller;

import java.io.File;
import java.io.IOException;

import Ferreteria.app.Excepciones.yaExiste;
import Ferreteria.app.model.Empleado;
import Ferreteria.app.model.Ferreteria;

public class CrudEmpleadoViewController implements IControlCrudEmpleadoView {

	ModelFactoryController modelFactoryController;
	Ferreteria ferreteria;

	public CrudEmpleadoViewController() {
		modelFactoryController = ModelFactoryController.getInstance();
		ferreteria = modelFactoryController.getFerreteria();
	}

	public Ferreteria getFerreteria() {
		return ferreteria;
	}

	public void setFerreteria(Ferreteria ferreteria) {
		this.ferreteria = ferreteria;
	}

	@Override
	public void crearEmpleado(Empleado empleadoNuevo) throws yaExiste {

		modelFactoryController.crearEmpleado(empleadoNuevo);

	}

	@Override
	public void actualizarEmpleado(String nombreEmpleado, String cargo, int codigoEmpleado, String direccion,
			double salario, int codigoAnterior) throws yaExiste {
		modelFactoryController.actualizarEmpleado(salario, cargo, direccion, codigoEmpleado, nombreEmpleado, codigoAnterior);

	}

	@Override
	public Boolean eliminarEmpleado(int codigoEmpleado) {
		return modelFactoryController.eliminarEmpleado(codigoEmpleado);
	}

	@Override
	public Empleado obtenerEmpleado(int codigoEmpleado) {
		return modelFactoryController.obtenerEmpleado(codigoEmpleado);
	}

	public void salvarDatos() {
		modelFactoryController.salvarDatos();
	}

	public void guardarArchivoLog(String mensaje, int nivel, String accion) {
		modelFactoryController.guardarLog(mensaje, nivel, accion);	
	}

	public void guardaTextoPlano() {
		modelFactoryController.guardardatos();
	}

	public boolean consultarSiExiste(Empleado empleadoSeleccionado) {
		return modelFactoryController.consultarSiExite(empleadoSeleccionado);
	}

	public void guardarReporte(int reporte, String seleccion) throws IOException {
		modelFactoryController.generarReporte(reporte, seleccion);
		
	}

}
