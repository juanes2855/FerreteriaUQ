package Ferreteria.app.controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import Ferreteria.app.Excepciones.UsuarioExcepcion;

public class CrudInicioSesion {
	
	ModelFactoryController modelFactoryController;
	
	public CrudInicioSesion(){
		modelFactoryController= ModelFactoryController.getInstance();
	}

	public ModelFactoryController getModelFactoryController() {
		return modelFactoryController;
	}

	public void setModelFactoryController(ModelFactoryController modelFactoryController) {
		this.modelFactoryController = modelFactoryController;
	}

	public boolean iniciarSesion(String usuario, String contrasenia) throws FileNotFoundException, IOException, UsuarioExcepcion {
		return modelFactoryController.iniciarSesion(usuario, contrasenia);
	}

	public void escribirEnLog(int nivel, String string) {
	  modelFactoryController.guardarLog("El usuario no existe"+ string, nivel, "UsuarioNoExiste");	
	}

	public void escribirEnLog(String mensaje, int nivel, String accion) {
	 modelFactoryController.guardarLog(mensaje, nivel, accion);
	}

	public void guardarRespaldoXml() {
		modelFactoryController.guardarRespaldoXml();
	}

	public void guardarRespaldoBinario() {
		modelFactoryController.guardarRespaldoBinario();
	}

	public void guardarRespaldoCompras() throws IOException {
		modelFactoryController.guardarRespaldoCompras();

	}

}
