package Ferreteria.app.controller;

import Ferreteria.app.Excepciones.yaExiste;
import Ferreteria.app.model.Compra;
import Ferreteria.app.model.Empleado;
import Ferreteria.app.model.Factura_Compra;
import Ferreteria.app.model.Ferreteria;
import Ferreteria.app.model.Producto;
import Ferreteria.app.model.Proveedor;

public class ModelFactoryController {
	Ferreteria ferreteria;

	private static class SingletonHolder {
		// El constructor de Singleton puede ser llamado desde aquí al ser
		// protected
		private final static ModelFactoryController eINSTANCE = new ModelFactoryController();
	
	}

	public static ModelFactoryController getInstance() {
		return SingletonHolder.eINSTANCE;
	}

	public ModelFactoryController() {
		ferreteria = new Ferreteria("JJ", "123", 312);
		
		inicializarDatos();
	}

	private void inicializarDatos() {

		Empleado empleado = new Empleado();
		empleado.setNombreEmpleado("Juan");
		empleado.setCargo("administrador");
		empleado.setCodigoEmpleado(123);
		empleado.setDireccion("Calarcá");
		empleado.setSalario(1000000);
		ferreteria.getListaEmpleados().add(empleado);

		Producto producto = new Producto();
		producto.setCategoria("De mano");
		producto.setCodigoProducto(213);
		producto.setMarca("Cat");
		producto.setNombreProducto("Hombre Solo");
		producto.setPrecio(47000);
		ferreteria.getListaProductos().add(producto);

		Proveedor proveedor = new Proveedor();
		proveedor.setCodigoProveedor(987);
		proveedor.setDireccionProveedor("Cali");
		proveedor.setNombreProveedor("Cat");
		proveedor.setTelefonoProveedor(7463333);
		ferreteria.getListaProveedor().add(proveedor);
		
		Producto producto2 = new Producto();
		producto2.setNombreProducto("Llave");
		producto2.setCategoria("De mano");
		producto2.setCodigoProducto(1123);
		producto2.setPrecio(47000);
		producto2.setMarca("cat");
		ferreteria.getListaProductos().add(producto2);
		
		Producto producto3 = new Producto();
		producto3.setNombreProducto("Tornillo");
		producto3.setCategoria("De mano");
		producto3.setCodigoProducto(12);
		producto3.setPrecio(4056);
		producto3.setMarca("cat");
		ferreteria.getProveedor().getProductosProveedor().add(producto3);
		
		/*Producto producto4 = new Producto();
		producto4.setNombreProducto("Destornillador");
		producto4.setCategoria("De mano");
		producto4.setCodigoProducto(4567);
		producto4.setPrecio(12000);
		producto4.setMarca("cat");
		ferreteria.getProveedor().getProductosProveedor().add(producto4);
		
		Producto producto5 = new Producto();
		producto5.setNombreProducto("Martillo");
		producto5.setCategoria("De mano");
		producto5.setCodigoProducto(1234);
		producto5.setPrecio(50000);
		producto5.setMarca("cat");
		ferreteria.getProveedor().getProductosProveedor().add(producto5);*/

	}

	public Ferreteria getFerreteria() {
		return ferreteria;
	}

	public void setFerreteria(Ferreteria ferreteria) {
		this.ferreteria = ferreteria;
	}

	public void crearEmpleado(Empleado empleadoNuevo) throws yaExiste {
		getFerreteria().crearEmpleado(empleadoNuevo);

	}

	public void actualizarEmpleado(double salario, String cargo, String direccion, int codigoEmpleado,
			String nombreEmpleado) {
		getFerreteria().actualizarEmpleado(nombreEmpleado, cargo, codigoEmpleado, direccion, salario);

	}

	public Boolean eliminarEmpleado(int codigoEmpleado) {
		return getFerreteria().eliminarEmpleado(codigoEmpleado);

	}

	public Empleado obtenerEmpleado(int codigoEmpleado) {
		return getFerreteria().obtenerEmpleado(codigoEmpleado);
	}

	public void crearProducto(Producto productoNuevo) throws yaExiste {
		getFerreteria().crearProducto(productoNuevo);

	}

	public void actualizarProducto(String nombreProducto, int codigoProducto, double precio, String categoria,
			String marca) {
		getFerreteria().modificarProducto(nombreProducto, codigoProducto, precio, categoria, marca);

	}

	public Boolean eliminarProducto(int codigoProducto) {
		return getFerreteria().eliminarProducto(codigoProducto);
	}

	public void crearProveedor(Proveedor proveedor) throws yaExiste {
		getFerreteria().anadirProveedor(proveedor);
	}

	public void actualizarProveedor(String nombreProveedor, int codigoProveedor, int telefonoProveedor,
			String direccionProveedor) {
		getFerreteria().modificarProveedor(nombreProveedor, codigoProveedor, telefonoProveedor, direccionProveedor);
	}
	
	public Boolean eliminarProveedor(int codigoProveedor){
		return getFerreteria().eliminarProveedor(codigoProveedor);
	}

	public Producto obtenerProducto(int codigoProducto) {
		return getFerreteria().obtenerProducto(codigoProducto);
	}
	
	public Proveedor obtenerProveedor(int codigoProveedor){
		return getFerreteria().obtenerProveedor(codigoProveedor);
	}
	public  void crearCompra(Compra compraNueva) throws yaExiste{
		getFerreteria().crearCompra(compraNueva);
	}
	public void modificarCompra(int codigoCompra, String fechaCompra, int cantidadCompra,
			Factura_Compra facturaCompra){
		getFerreteria().modificarCompra(codigoCompra, fechaCompra, cantidadCompra, facturaCompra);
	}
	public Boolean eliminarCompra(int codigoCompra){
		return getFerreteria().eliminarCompra(codigoCompra);
	}
}
