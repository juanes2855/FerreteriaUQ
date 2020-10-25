package Ferreteria.app.Excepciones;

import java.io.IOException;

public class entradaDedatosErronea extends IOException {
	private static final long serialVersionUID = 1L;
	
	public entradaDedatosErronea (String mensaje){
		super(mensaje);
	}
}
