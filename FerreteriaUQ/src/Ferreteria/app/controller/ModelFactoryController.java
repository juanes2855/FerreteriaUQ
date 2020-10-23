package Ferreteria.app.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import Ferreteria.app.Excepciones.UsuarioExcepcion;
import Ferreteria.app.Excepciones.soloUnProveedor;
import Ferreteria.app.Excepciones.yaExiste;
import Ferreteria.app.Persistencia.Persistencia;
import Ferreteria.app.model.Compra;
import Ferreteria.app.model.Detalle_Inventario;
import Ferreteria.app.model.Empleado;
import Ferreteria.app.model.Factura_Compra;
import Ferreteria.app.model.Ferreteria;
import Ferreteria.app.model.Producto;
import Ferreteria.app.model.Proveedor;
import Ferreteria.app.model.Usuario;


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

		Proveedor proveedor2 = new Proveedor();
		proveedor2.setCodigoProveedor(2345);
		proveedor2.setDireccionProveedor("Bogotá");
		proveedor2.setNombreProveedor("Ferreteria reina");
		proveedor2.setTelefonoProveedor(7440011);
		ferreteria.getListaProveedor().add(proveedor2);

		Proveedor proveedor = new Proveedor();
		proveedor.setCodigoProveedor(987);
		proveedor.setDireccionProveedor("Cali");
		proveedor.setNombreProveedor("Cat");
		proveedor.setTelefonoProveedor(7463333);
		ferreteria.getListaProveedor().add(proveedor);

		Proveedor proveedo3 = new Proveedor();
		proveedo3.setCodigoProveedor(104);
		proveedo3.setDireccionProveedor("Bogota");
		proveedo3.setNombreProveedor("Uyustools");
		proveedo3.setTelefonoProveedor(7463333);
		ferreteria.getListaProveedor().add(proveedo3);

		 Producto producto2 = new Producto();
		 producto2.setNombreProducto("Llave");
		 producto2.setCategoria("De mano");
		 producto2.setCodigoProducto(1123);
		 producto2.setPrecio(47000);
		 producto2.setProveedorAsociado(proveedo3);
		 ferreteria.getListaProductos().add(producto2);
		
		 Producto producto = new Producto();
		 producto.setCategoria("De mano");
		 producto.setCodigoProducto(213);
		 producto.setProveedorAsociado(proveedor);
		 producto.setNombreProducto("Hombre Solo");
		 producto.setPrecio(47000);
		 ferreteria.getListaProductos().add(producto);
		 
		 Factura_Compra factura_Compra= new Factura_Compra(1, 2, 94000);
		 ferreteria.getListaFacturas().add(factura_Compra);
		 
		 Compra compra = new Compra();
		 compra.setCantidadCompra(2);
		 compra.setCodigoCompra(1);
		 compra.setEmpleadoAsociado(empleado);
		 compra.setFactura_Compra(factura_Compra);
		 compra.setFechaCompra("21/10/2020");
		 compra.setProveedorAsociado(proveedor);
		 ferreteria.getListaCompras().add(compra);
		 
		 
		 //proveedo3.getProductosProveedor().add(producto);
		// ferreteria.obtenerListadeProductosProveedor(proveedo3).add(producto);

//		Producto producto3 = new Producto();
//		producto3.setNombreProducto("Tornillo");
//		producto3.setCategoria("De mano");
//		producto3.setCodigoProducto(12);
//		producto3.setPrecio(4056);
//		producto3.setProveedorAsociado(proveedor);
//		ferreteria.getProveedor().getProductosProveedor().add(producto3);

		/*
		 * Producto producto4 = new Producto();
		 * producto4.setNombreProducto("Destornillador");
		 * producto4.setCategoria("De mano"); producto4.setCodigoProducto(4567);
		 * producto4.setPrecio(12000); producto4.setMarca("cat");
		 * ferreteria.getProveedor().getProductosProveedor().add(producto4);
		 * 
		 * Producto producto5 = new Producto();
		 * producto5.setNombreProducto("Martillo");
		 * producto5.setCategoria("De mano"); producto5.setCodigoProducto(1234);
		 * producto5.setPrecio(50000); producto5.setMarca("cat");
		 * ferreteria.getProveedor().getProductosProveedor().add(producto5);
		 */

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
			Proveedor proveedor) {
		getFerreteria().modificarProducto(nombreProducto, codigoProducto, precio, categoria, proveedor);

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

	public Boolean eliminarProveedor(int codigoProveedor) {
		return getFerreteria().eliminarProveedor(codigoProveedor);
	}

	public Producto obtenerProducto(int codigoProducto) {
		return getFerreteria().obtenerProducto(codigoProducto);
	}

	public Proveedor obtenerProveedor(int codigoProveedor) {
		return getFerreteria().obtenerProveedor(codigoProveedor);
	}

	public void crearCompra(Compra compraNueva) throws yaExiste {
		getFerreteria().crearCompra(compraNueva);
	}

	public void modificarCompra(int codigoCompra, String fechaCompra, int cantidadCompra,
			Factura_Compra facturaCompra) {
		getFerreteria().modificarCompra(codigoCompra, fechaCompra, cantidadCompra, facturaCompra);
	}

	public Boolean eliminarCompra(int codigoCompra) {
		return getFerreteria().eliminarCompra(codigoCompra);
	}

	public void anadirDetalleAlista(Producto productoSeleccionado, int cantidad) throws soloUnProveedor {
		getFerreteria().anadirDetalleAlista(productoSeleccionado, cantidad);
	}

	public void quitarDetalleLista(Detalle_Inventario detalleSeleccionado) {
		getFerreteria().quitarDetalleLista(detalleSeleccionado);
	}

	public void limpiarLista() {
		getFerreteria().limpiarLista();
		
	}

    public boolean iniciarSesion(String usuario, String contrasenia) throws FileNotFoundException, IOException, UsuarioExcepcion {
		
		if(validarUsuario(usuario,contrasenia)) {
			return true;
		}else {
			throw new UsuarioExcepcion("Usuario no existe");
		}
		
	}
    private boolean validarUsuario(String usuario, String contrasenia) throws FileNotFoundException, IOException {
    	return true;
    }
//		ArrayList<Usuario> usuarios = Persistencia.cargarUsuarios(Persistencia.RUTA_ARCHIVO_USUARIOS);
//		
//		for (int indiceUsuario = 0; indiceUsuario < usuarios.size(); indiceUsuario++) {
//			Usuario usuarioAux = usuarios.get(indiceUsuario);
//			if(usuarioAux.getUsuario().equalsIgnoreCase(usuario) && usuarioAux.getContrasenia().equalsIgnoreCase(contrasenia)) {
//				return true;
//			}
//		}
//		return false;
//	}
	
}
