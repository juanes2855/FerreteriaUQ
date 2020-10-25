package Ferreteria.app.Excepciones;

public class DatoVacio extends NullPointerException {
 
	private static final long serialVersionUID = 1L;
	
	public DatoVacio(String mensaje){
		super(mensaje);
	}
}
