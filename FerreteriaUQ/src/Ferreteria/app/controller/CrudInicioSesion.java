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

}
