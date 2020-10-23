package Ferreteria.app.Excepciones;

public class cantidadInvalida extends Exception {
	
	private static final long serialVersionUID = 1L;

	public cantidadInvalida (String mensaje) {
		super(mensaje);
	}

	public cantidadInvalida() {
	}

}
