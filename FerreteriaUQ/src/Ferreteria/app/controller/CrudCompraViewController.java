package Ferreteria.app.controller;

import Ferreteria.app.Excepciones.soloUnProveedor;
import Ferreteria.app.Excepciones.yaExiste;
import Ferreteria.app.model.Compra;
import Ferreteria.app.model.Detalle_Inventario;
import Ferreteria.app.model.Factura_Compra;
import Ferreteria.app.model.Ferreteria;
import Ferreteria.app.model.Producto;

public class CrudCompraViewController {

	ModelFactoryController modelFactoryController;
	Ferreteria ferreteria;

	public CrudCompraViewController() {
		modelFactoryController = ModelFactoryController.getInstance();
		ferreteria = modelFactoryController.getFerreteria();
	}

	public void crearCompra(Compra compraNueva) throws yaExiste {
		modelFactoryController.crearCompra(compraNueva);
	}

	public void modificarCompra(int codigoCompra, String fechaCompra, int cantidadCompra,
			Factura_Compra facturaCompra) {
		modelFactoryController.modificarCompra(codigoCompra, fechaCompra, cantidadCompra, facturaCompra);
	}
	
	public Boolean eliminarCompra(int codigoCompra){
		return modelFactoryController.eliminarCompra(codigoCompra);
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

	public void anadirDetalleALista(Producto productoSeleccionado, int cantidad) throws soloUnProveedor{
		modelFactoryController.anadirDetalleAlista(productoSeleccionado, cantidad);
	}

	public void quitarDetalleLista(Detalle_Inventario detalleSeleccionado) {
	    modelFactoryController.quitarDetalleLista(detalleSeleccionado);
	}

	public void limpiarLista() {
		modelFactoryController.limpiarLista();
		
	}

	public void salvarDatos() {
		modelFactoryController.salvarDatos();
	}

	public void guardarArchivoLog(String mensaje, int nivel, String accion) {
		modelFactoryController.guardarLog(mensaje, nivel, accion);	
	}

	public void guardarTextPlano() {
		modelFactoryController.guardardatos();
		
	}
	
}