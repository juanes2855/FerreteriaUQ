package Ferreteria.app.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import Ferreteria.app.Excepciones.UsuarioExcepcion;
import Ferreteria.app.Excepciones.entradaDedatosErronea;
import Ferreteria.app.Excepciones.soloUnProveedor;
import Ferreteria.app.Excepciones.yaExiste;
import Ferreteria.app.Persistencia.ArchivoUtil;
import Ferreteria.app.Persistencia.Persistencia;
import Ferreteria.app.model.Compra;
import Ferreteria.app.model.Detalle_Inventario;
import Ferreteria.app.model.Empleado;
import Ferreteria.app.model.Factura_Compra;
import Ferreteria.app.model.Ferreteria;
import Ferreteria.app.model.Producto;
import Ferreteria.app.model.Proveedor;
import Ferreteria.app.model.Usuario;
import Ferreteria.app.view.InicioSesion;


public class ModelFactoryController {
	Ferreteria ferreteria;

	private static class SingletonHolder {
		private final static ModelFactoryController eINSTANCE = new ModelFactoryController();

	}

	public static ModelFactoryController getInstance() {
		return SingletonHolder.eINSTANCE;
	}

	public ModelFactoryController() {
        
	 // cargarDatosDesdeArchivo();
      //cargarResourceBinario();
      cargarResourceXML();
        if(ferreteria== null){
        	inicializarDatos();
        }
   
	}

	private void inicializarDatos() {
		ferreteria = new Ferreteria("JJ", "123", 312);
		//ferreteria= new Ferreteria();

		Empleado empleado = new Empleado();
		empleado.setNombreEmpleado("Juan");
		empleado.setCargo("administrador");
		empleado.setCodigoEmpleado(123);
		empleado.setDireccion("Calarcá");
		empleado.setSalario(1000000);
		ferreteria.getListaEmpleados().add(empleado);


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
		
	
		 
	}
	
	public void guardardatos(){
		
		try{
			Persistencia.guardarEmpleadox(getFerreteria().getListaEmpleados());
			Persistencia.guardarProducto(getFerreteria().getListaProductos());
			Persistencia.guardarProveedores(getFerreteria().getListaProveedor());
			Persistencia.guardarCompra(getFerreteria().getListaCompras());
		}catch(IOException e){
			System.out.println(e);
		}
		
		
	}
	public void cargarDatosDesdeArchivo() {
		ferreteria = new Ferreteria("Ferreteria", "Calarcá", 7433333);
		try {
			Persistencia.cargarDatosArchivos(getFerreteria());
		} catch (IOException e) {
			System.out.println(e);
		}
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

    public boolean iniciarSesion(String usuario, String contrasenia) throws entradaDedatosErronea, IOException, UsuarioExcepcion {
		
		if(validarUsuario(usuario,contrasenia)) {
			return true;
		}else {
			throw new UsuarioExcepcion("Usuario no existe");
		}
		
	}
    private boolean validarUsuario(String usuario, String contrasenia) throws FileNotFoundException, IOException{
    	return Persistencia.validarUsuario(usuario, contrasenia);
    }
    
    
    public void cargarResourceBinario(){
    	ferreteria = Persistencia.cargarRecursoFerreteriaBinario();
    }
    public void guardarRespaldoXml(){
    	Persistencia.guardarRespaldoXML(ferreteria);
    }
    
    public void guardarResourceBinario(){
    	Persistencia.guardarRecursoFerreteriaBinario(ferreteria);
    }
    
    public void cargarResourceXML(){
    	ferreteria = Persistencia.cargarRecursoFerreteriaXML();
    }
    
    public void guardarResourceXML(){
    	Persistencia.guardarRecursoFerreteriaXML(ferreteria);
    }

    public void salvarDatos() {
    	guardarResourceBinario();
    	guardarResourceXML();
    }
    
    public void guardarLog(String mensajeLog, int nivel, String accion){
    	Persistencia.guardaRegistroLog(mensajeLog, nivel, accion);
    }

	public void guardarRespaldoBinario() {
		Persistencia.guardarRespaldoBinario(ferreteria);	
	}
	public void guardarRespaldoCompras() throws IOException{
		Persistencia.respaldoCompra(getFerreteria().getListaCompras());
	}
	
    

}
