package Ferreteria.app.controller;

import java.io.IOException;

import Ferreteria.app.Excepciones.yaExiste;
import Ferreteria.app.model.Ferreteria;
import Ferreteria.app.model.Producto;
import Ferreteria.app.model.Proveedor;

public class CrudProductoViewController {

	ModelFactoryController modelFactoryController;
	Ferreteria ferreteria;

	public CrudProductoViewController() {

		modelFactoryController = ModelFactoryController.getInstance();
		ferreteria = modelFactoryController.getFerreteria();
	}

	public void crearProducto(Producto productoNuevo) throws yaExiste {
		modelFactoryController.crearProducto(productoNuevo);
	}

	public void actualizarProducto(String nombreProducto, int codigoProducto, double precio, String categoria,
			Proveedor proveedor, int codigoAnterior) throws yaExiste {
		modelFactoryController.actualizarProducto(nombreProducto, codigoProducto, precio, categoria, proveedor, codigoAnterior);
	}
	
	public Boolean eliminarProducto(int codigoProducto){
		return modelFactoryController.eliminarProducto(codigoProducto);
	}
	
	public Producto obtenerProducto(int codigoProducto){
		return modelFactoryController.obtenerProducto(codigoProducto);
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

	public void guardaTextoPlano() {
	   modelFactoryController.guardardatos();
		
	}

	public boolean consultarSiExiste(Producto productoSeleccionado) {
		return modelFactoryController.consultarSiExite(productoSeleccionado);
	}

	public void guardarReporte(int producto, String seleccion) throws IOException {
		modelFactoryController.generarReporte(producto, seleccion);
	}
}
