package Ferreteria.app.Excepciones;

public class EstaVinculado extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public EstaVinculado (String mensaje){
		super(mensaje);
	}

}
