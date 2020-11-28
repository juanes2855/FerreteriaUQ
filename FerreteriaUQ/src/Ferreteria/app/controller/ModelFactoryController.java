package Ferreteria.app.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
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
	
	private Socket sockedComunicacion= null;
	private Socket sockedTransferenciaObjeto = null;
	private Socket socketInicio=null;
	private DataInputStream flujoEntradaComunicacion;
	private DataOutputStream flujoSalidaComunicacion = null;
	
	private String idInstanciaCliente="";
	byte[] receivedData;
	int in;
	BufferedInputStream flujoEntradaArchivo;
	BufferedOutputStream  flujoSalidaArchivo;
	
	String filename= "";
	String rutaArchivoLocal= "C:/td/server/";
	ObjectInputStream flujoEntradaObjeto;
	ObjectOutputStream flujosSalidaObjeto;
	
	
	
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

//	public ModelFactoryController() {
//        
//	 // cargarDatosDesdeArchivo();
//      //cargarResourceBinario();
//      cargarResourceXML();
//        if(ferreteria== null){
//        	inicializarDatos();
//        }
//   
//	}
	
	public ModelFactoryController(){
		try {
			crearConexion();
			solicitarInformacionPersistencia();
			leerObjetoPersistenciaTransferido();
			System.out.println("llamadoo");
		}catch(Exception e){
			System.out.println("Ha fallado el servidor");
		}
		
	}
	
	public Boolean iniciarSession(String usuario, String contrasenia){
		Boolean inicia=false;
		
		
		try {
			crearConexion2();
			solicitarInicioDeSession(usuario, contrasenia);
		  inicia=leerInicioSession();
		 // inicia=true;
		  if(inicia){
			  try {
					crearConexion();
					solicitarInformacionPersistencia();
					leerObjetoPersistenciaTransferido();
					System.out.println("llamadoo");
				}catch(Exception e){
					System.out.println("Ha fallado el servidor aqui");
				}
		  }else{
			  System.out.println("Dato erroneos");
		  }
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return inicia;
	}

	private void leerObjetoPersistenciaTransferido() throws IOException, ClassNotFoundException {	
		ferreteria= (Ferreteria) flujoEntradaObjeto.readObject();
		System.out.println("Objeto Recibido");
		flujoEntradaObjeto.close();
	}
	private Boolean leerInicioSession() throws IOException{
		Boolean ya=false;
		ya=flujoEntradaComunicacion.readBoolean();
		flujoEntradaComunicacion.close();
		return ya;
	}	

	private void solicitarInformacionPersistencia() throws IOException {
		flujoSalidaComunicacion.writeInt(1);
		flujoSalidaComunicacion.close();
		
	}
	private void solicitarInicioDeSession(String usuario, String contrasenia) throws IOException{
		flujoSalidaComunicacion.writeInt(3);
//		flujoSalidaComunicacion.writeUTF("admin");
//		flujoSalidaComunicacion.writeUTF("1234");
		flujoSalidaComunicacion.close();
	}

	private void crearConexion() throws IOException, Exception{
	    
		try{
			sockedComunicacion= new Socket("localhost", 8081);
			sockedTransferenciaObjeto= new Socket("localhost", 8082);
			
			flujoEntradaComunicacion = new DataInputStream(sockedComunicacion.getInputStream());
			flujoSalidaComunicacion = new DataOutputStream(sockedComunicacion.getOutputStream());
			
			flujoEntradaObjeto=  new ObjectInputStream(sockedTransferenciaObjeto.getInputStream());
			flujosSalidaObjeto=  new ObjectOutputStream(sockedTransferenciaObjeto.getOutputStream());
			
			System.out.println("Se conecto");
		}catch (IOException e){
			throw new Exception("\tError en el servidor");
		}
	
}
	
	private void crearConexion2(){
		try {
			socketInicio= new Socket("localhost", 8081);
			flujoEntradaComunicacion = new DataInputStream(socketInicio.getInputStream());
			flujoSalidaComunicacion = new DataOutputStream(socketInicio.getOutputStream());
		    System.out.println("Conexion2");
		} catch (IOException e) {
		 System.out.println("no se conecto en 2");
			e.printStackTrace();
		}
	}
	private void solicitarGuardarPersistencia() throws IOException{
		flujoSalidaComunicacion.writeInt(2);
		flujoSalidaComunicacion.close();
	}
	
	private void enviarObjetoPersistenciaTransferido() throws IOException, ClassNotFoundException{
		flujosSalidaObjeto.writeObject(getFerreteria());
		System.out.println("Objeto Enviado");
		flujosSalidaObjeto.close();
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

			try{
				crearConexion();
				solicitarGuardarPersistencia();
				enviarObjetoPersistenciaTransferido();
				//leerObjetoPersistenciaTransferido();
			}catch(IOException e){
				System.out.println("----");
			}catch(Exception e1){
				System.out.println("----1");
			}
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
