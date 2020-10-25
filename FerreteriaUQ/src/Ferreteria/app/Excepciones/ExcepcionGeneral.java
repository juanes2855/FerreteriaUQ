package Ferreteria.app.Excepciones;

public class ExcepcionGeneral extends Exception{
	private static final long serialVersionUID = 1L;
	
	public ExcepcionGeneral (String mensaje){
		super(mensaje);
	}
}
