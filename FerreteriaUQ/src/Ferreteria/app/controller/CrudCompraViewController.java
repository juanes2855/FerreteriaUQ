package Ferreteria.app.controller;

import Ferreteria.app.Excepciones.yaExiste;
import Ferreteria.app.model.Compra;
import Ferreteria.app.model.Factura_Compra;
import Ferreteria.app.model.Ferreteria;

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
}