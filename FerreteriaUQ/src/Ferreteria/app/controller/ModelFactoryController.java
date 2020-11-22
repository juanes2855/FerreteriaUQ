package Ferreteria.app.controller;

import java.io.File;
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



public class ModelFactoryController implements Runnable {
	Ferreteria ferreteria;
	Thread hiloGuardarXml;
	Thread hiloGuardarLog;
	Thread hiloguardarArchivos;
	Control control = new Control(1);
	String mensajeLog="";
	int nivel=0; 
	String accion="";
	String usuario;

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
			hiloguardarArchivos= new Thread(this);
			hiloguardarArchivos.start();
			
//			Persistencia.guardarEmpleadox(getFerreteria().getListaEmpleados());
//			Persistencia.guardarProducto(getFerreteria().getListaProductos());
//			Persistencia.guardarProveedores(getFerreteria().getListaProveedor());
//			Persistencia.guardarCompra(getFerreteria().getListaCompras());
		
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
			String nombreEmpleado, int codigoAnterior)throws yaExiste {
		getFerreteria().actualizarEmpleado(nombreEmpleado, cargo, codigoEmpleado, direccion, salario, codigoAnterior);

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
			Proveedor proveedor, int codigoAnterior ) throws yaExiste{
		getFerreteria().modificarProducto(nombreProducto, codigoProducto, precio, categoria, proveedor, codigoAnterior);

	}

	public Boolean eliminarProducto(int codigoProducto) {
		return getFerreteria().eliminarProducto(codigoProducto);
	}

	public void crearProveedor(Proveedor proveedor) throws yaExiste {
		getFerreteria().anadirProveedor(proveedor);
	}

	public void actualizarProveedor(String nombreProveedor, int codigoProveedor, int telefonoProveedor,
			String direccionProveedor, int codigoAnterior) throws yaExiste {
		getFerreteria().modificarProveedor(nombreProveedor, codigoProveedor, telefonoProveedor, direccionProveedor, codigoAnterior);
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
			Factura_Compra facturaCompra)throws yaExiste {
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
    	this.usuario=usuario;
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
    	hiloGuardarXml= new Thread(this);
    	hiloGuardarXml.start();
    	//Persistencia.guardarRecursoFerreteriaXML(ferreteria);
    }

    public void salvarDatos() {
    	guardarResourceBinario();
    	guardarResourceXML();
    }
    
    public void guardarLog(String mensajeLog, int nivel, String accion){
    	this.accion=accion;
    	this.mensajeLog=mensajeLog;
    	this.nivel=nivel;
    	hiloGuardarLog= new Thread(this);
    	hiloGuardarLog.start();
    	//Persistencia.guardaRegistroLog(mensajeLog, nivel, accion);
    }

	public void guardarRespaldoBinario() {
		Persistencia.guardarRespaldoBinario(ferreteria);	
	}
	public void guardarRespaldoCompras() throws IOException{
		Persistencia.respaldoCompra(getFerreteria().getListaCompras());
	}

	public boolean consultarSiExite(Object object) {
		return getFerreteria().consultarPerteneceAtransaccion(object);
	}
	

	@Override
	public void run() {
	
		Thread hiloEjecucion= Thread.currentThread();
		try {
			control.ocupar();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		if(hiloEjecucion== hiloGuardarXml){
			Persistencia.guardarRecursoFerreteriaXML(ferreteria);
			try {
				control.liberar();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(hiloEjecucion== hiloGuardarLog){
			Persistencia.guardaRegistroLog(mensajeLog, nivel, accion);
			try {
				control.liberar();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(hiloEjecucion== hiloguardarArchivos){
			try{
			Persistencia.guardarEmpleadox(getFerreteria().getListaEmpleados());
			Persistencia.guardarProducto(getFerreteria().getListaProductos());
			Persistencia.guardarProveedores(getFerreteria().getListaProveedor());
			Persistencia.guardarCompra(getFerreteria().getListaCompras());
			try {
				control.liberar();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}catch (IOException e) {
				System.out.println("falla aqui");
			}
		}
	}

	public void generarReporte(int reporte, String seleccion) throws IOException {
		Persistencia.generarReporte(reporte, usuario, getFerreteria(), seleccion);
		
	}
	
   
}
