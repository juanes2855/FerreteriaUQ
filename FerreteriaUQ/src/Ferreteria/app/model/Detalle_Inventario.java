package Ferreteria.app.model;

public class Detalle_Inventario {
	
	private int cantidadProducto;
	private Producto producto;
	
	public Detalle_Inventario(int cantidadProducto, Producto producto) {
		super();
		this.cantidadProducto = cantidadProducto;
		this.producto = producto;
	}

	public int getCantidadProducto() {
		return cantidadProducto;
	}

	public void setCantidadProducto(int cantidadProducto) {
		this.cantidadProducto = cantidadProducto;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}	

}
