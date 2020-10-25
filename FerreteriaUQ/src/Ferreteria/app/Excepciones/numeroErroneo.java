package Ferreteria.app.Excepciones;

public class numeroErroneo extends NumberFormatException {
	private static final long serialVersionUID = 1L;

	public numeroErroneo(String mensaje){
		super(mensaje);
	}
	
}
