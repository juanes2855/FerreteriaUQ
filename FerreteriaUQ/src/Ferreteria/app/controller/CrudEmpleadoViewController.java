package Ferreteria.app.controller;

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
			double salario) {
		modelFactoryController.actualizarEmpleado(salario, cargo, direccion, codigoEmpleado, nombreEmpleado);

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

}
