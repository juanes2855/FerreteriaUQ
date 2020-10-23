package Ferreteria.app.Excepciones;

public class soloUnProveedor extends Exception {
	private static final long serialVersionUID = 1L;
	
	public soloUnProveedor(String mensaje){
		super(mensaje);
	}

}
