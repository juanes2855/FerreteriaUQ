package Ferreteria.app.model;

import Ferreteria.app.Excepciones.yaExiste;

public interface IFerreteria {

	public void crearEmpleado(Empleado empleado) throws yaExiste;

	public void actualizarEmpleado(String nombreEmpleado, String cargo, int codigoEmpleado, String direccion, double salario);

	public Boolean eliminarEmpleado(int codigoEmpleado);

	public void crearProducto(Producto producto) throws yaExiste;

	public void modificarProducto(String nombreProducto, int codigoProducto, double precio, String categoria,
			String marca);

	public Boolean eliminarProducto(int codigoProducto);

	public void anadirProveedor(Proveedor proveedor) throws yaExiste;

	public void modificarProveedor(String nombreProveedor, int codigoProveedor, int telefonoProveedor,
			String direccionProveedor);

	public Boolean eliminarProveedor(int codigoProveedor);

	public void crearCompra(Compra compraNueva) throws yaExiste;

	public void modificarCompra(int codigoCompra, String fechaCompra, 
			int cantidadCompra, Factura_Compra facturaCompra);

	public Boolean eliminarCompra(int codigoCompra);

}
