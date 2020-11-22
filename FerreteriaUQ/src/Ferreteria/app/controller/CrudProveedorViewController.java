package Ferreteria.app.controller;

import java.io.IOException;

import Ferreteria.app.Excepciones.yaExiste;
import Ferreteria.app.model.Ferreteria;
import Ferreteria.app.model.Proveedor;

public class CrudProveedorViewController {

	ModelFactoryController modelFactoryController;
	Ferreteria ferreteria;

	public CrudProveedorViewController() {

		modelFactoryController = ModelFactoryController.getInstance();
		ferreteria = modelFactoryController.getFerreteria();
	}

	public void crearProveedor(Proveedor proveedor) throws yaExiste {
		modelFactoryController.crearProveedor(proveedor);
	}

	public void actualizarProveedor(String nombreProveedor, int codigoProveedor, int telefonoProveedor,
			String direccionProveedor, int codigoAnterior) throws yaExiste {
		modelFactoryController.actualizarProveedor(nombreProveedor, codigoProveedor, telefonoProveedor,
				direccionProveedor, codigoAnterior);
	}
	
	public Boolean eliminarProveedor(int codigoProveedor){
		return modelFactoryController.eliminarProveedor(codigoProveedor);
	}
	
	public Proveedor obtenerProveedor(int codigoProveedor){
		return modelFactoryController.obtenerProveedor(codigoProveedor);
	}

	public ModelFactoryController getModelFactoryController() {
		return modelFactoryController;
	}

	public void setModelFactoryController(ModelFactoryController modelFactoryController) {
		this.modelFactoryController = modelFactoryController;
	}

	public Ferreteria getFerreteria() {
		return ferreteria;
	}

	public void setFerreteria(Ferreteria ferreteria) {
		this.ferreteria = ferreteria;
	}
	public void salvarDatos() {
		modelFactoryController.salvarDatos();
	}

	public void guardarArchivoLog(String mensaje, int nivel, String accion) {
		modelFactoryController.guardarLog(mensaje, nivel, accion);	
	}

	public void guardaDatosTextoPlano() {
	modelFactoryController.guardardatos();
		
	}

	public boolean consultarSiExiste(Proveedor proveedorSeleccionado) {
		return modelFactoryController.consultarSiExite(proveedorSeleccionado);
	}

	public void guardaReporte(int proveedor, String seleccion) throws IOException {
		modelFactoryController.generarReporte(proveedor, seleccion);
		
	}

}
