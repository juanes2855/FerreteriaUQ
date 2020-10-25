package Ferreteria.app.Excepciones;

import java.io.FileNotFoundException;

public class ArchivoCorrupto extends FileNotFoundException{
	private static final long serialVersionUID = 1L;
	
	public ArchivoCorrupto(String mensaje){
		super(mensaje);
	}

}
