package Ferreteria.app.Persistencia;

import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import Ferreteria.app.Excepciones.UsuarioExcepcion;
import Ferreteria.app.model.Ferreteria;
import Ferreteria.app.model.Producto;
import Ferreteria.app.model.Proveedor;
import Ferreteria.app.model.Usuario;


public class Persistencia {

	public static final String RUTA_ARCHIVO_PROVEEDORES = "src/resources/archivoClientes.txt";
	public static final String RUTA_ARCHIVO_COMPRAS = "src/resources/archivoUsuarios.txt";
	public static final String RUTA_ARCHIVO_LOG = "src/resources/BancoLog.txt";
	public static final String RUTA_ARCHIVO_USUARIOS = "src/resources/archivoEmpleados.txt";
	public static final String RUTA_ARCHIVO_PRODUCTOS= "src/resources/archivoProductos.txt";
	public static final String RUTA_ARCHIVO_MODELO_FERRETERIA_BINARIO = "src/resources/model.dat";
	public static final String RUTA_ARCHIVO_MODELO_FERRETERIA_XML = "src/resources/model.xml";


	
	
	public static void cargarDatosArchivos(Ferreteria ferreteria) throws FileNotFoundException, IOException {
		
		
		//cargar archivo de proveedores
		ArrayList<Proveedor> proveedoresCargados = cargarProveedores();
		
		if(proveedoresCargados.size() > 0)
			ferreteria.getListaProveedor().addAll(proveedoresCargados);
		
		
//		guardarRecursoBancoEnBinario(banco);
		
		//cargar archivo objetos
		
		//cargar archivo empleados
		
		//cargar archivo prestamo
		
	}
	
	
	
	/**
	 * Guarda en un archivo de texto todos la información de las personas almacenadas en el ArrayList
	 * @param objetos
	 * @param ruta
	 * @throws IOException
	 */
	public static void guardarProveedores(ArrayList<Proveedor> listaProveedores) throws IOException {
		// TODO Auto-generated method stub
		String contenido = "";
		
		for(Proveedor proveedor:listaProveedores) 
		{
			contenido+= proveedor.getNombreProveedor()+","+proveedor.getCodigoProveedor()+","+proveedor.getDireccionProveedor()+","+proveedor.getDireccionProveedor()
		     +","+proveedor.getTelefonoProveedor()+"\n";
		}
		ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_PROVEEDORES, contenido, false);
		
	}
	
	
	
//	----------------------LOADS------------------------
	
	/**
	 * 
	 * @param tipoPersona
	 * @param ruta
	 * @return un Arraylist de personas con los datos obtenidos del archivo de texto indicado
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static ArrayList<Proveedor> cargarProveedores() throws FileNotFoundException, IOException 
	{
		ArrayList<Proveedor> proveedores =new ArrayList<Proveedor>();
		
		ArrayList<String> contenido = ArchivoUtil.leerArchivo(RUTA_ARCHIVO_PROVEEDORES);
		String linea="";
		
		for (int i = 0; i < contenido.size(); i++)
		{
			linea = contenido.get(i);
			Proveedor proveedor = new Proveedor();
			proveedor.setNombreProveedor(linea.split(",")[0]);
			proveedor.setCodigoProveedor(Integer.parseInt((linea.split(",")[1])));
			proveedor.setDireccionProveedor(linea.split(",")[2]);
			proveedor.setTelefonoProveedor(Integer.parseInt((linea.split(",")[3])));
			proveedores.add(proveedor);
		}
		return proveedores;
	}


	public static void guardaRegistroLog(String mensajeLog, int nivel, String accion)
	{
		
		ArchivoUtil.guardarRegistroLog(mensajeLog, nivel, accion, RUTA_ARCHIVO_LOG);
	}


	public static boolean iniciarSesion(String usuario, String contrasenia) throws FileNotFoundException, IOException, UsuarioExcepcion {
		
		if(validarUsuario(usuario,contrasenia)) {
			return true;
		}else {
			throw new UsuarioExcepcion("Usuario no existe");
		}
		
	}
	
	private static boolean validarUsuario(String usuario, String contrasenia) throws FileNotFoundException, IOException 
	{
		ArrayList<Usuario> usuarios = Persistencia.cargarUsuarios(RUTA_ARCHIVO_USUARIOS);
		
		for (int indiceUsuario = 0; indiceUsuario < usuarios.size(); indiceUsuario++) 
		{
			Usuario usuarioAux = usuarios.get(indiceUsuario);
			if(usuarioAux.getUsuario().equalsIgnoreCase(usuario) && usuarioAux.getContrasenia().equalsIgnoreCase(contrasenia)) {
				return true;
			}
		}
		return false;
	}

	public static ArrayList<Usuario> cargarUsuarios(String ruta) throws FileNotFoundException, IOException {
		ArrayList<Usuario> usuarios =new ArrayList<Usuario>();
		
		ArrayList<String> contenido = ArchivoUtil.leerArchivo(ruta);
		String linea="";
		
		for (int i = 0; i < contenido.size(); i++) {
			linea = contenido.get(i);
			
			Usuario usuario = new Usuario();
			usuario.setUsuario(linea.split(",")[0]);
			usuario.setContrasenia(linea.split(",")[1]);
			
			usuarios.add(usuario);
		}
		return usuarios;
	}
	
	
//	----------------------SAVES------------------------
	
	/**
	 * Guarda en un archivo de texto todos la información de los productos almacenados en el ArrayList
	 * @param objetos
	 * @param ruta
	 * @throws IOException
	 */
	
//	public static void guardarProductos(ArrayList<Producto> listaProductos, String ruta) throws IOException  {
//		String contenido = "";
//		
//		for(Producto productoAux: listaProductos) {
//			contenido+= productoAux.getNombreProducto()+","+productoAux.getCategoria()+","+productoAux.getCodigoProducto()+productoAux.getMarca()
//					     +","+productoAux.getPrecio()+"\n";
//		}
//		ArchivoUtil.guardarArchivo(ruta, contenido, true);
//	}


	//------------------------------------SERIALIZACIÓN  y XML
	
	
	public static Ferreteria cargarRecursoBancoBinario() {
		
		Ferreteria ferreteria = null;
		
		try {
			ferreteria = (Ferreteria)ArchivoUtil.cargarRecursoSerializado(RUTA_ARCHIVO_MODELO_FERRETERIA_BINARIO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ferreteria;
	}
	
	
	public static void guardarRecursoBancoBinario(Ferreteria ferreteria) {
		
		try {
			ArchivoUtil.salvarRecursoSerializado(RUTA_ARCHIVO_MODELO_FERRETERIA_BINARIO, ferreteria);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static Ferreteria cargarRecursoBancoXML() {
		
		Ferreteria ferreteria = null;
		
		try {
			ferreteria = (Ferreteria)ArchivoUtil.cargarRecursoSerializadoXML(RUTA_ARCHIVO_MODELO_FERRETERIA_XML);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ferreteria;

	}

	
	
	public static void guardarRecursoBancoXML(Ferreteria ferreteria) {
		
		try {
			ArchivoUtil.salvarRecursoSerializadoXML(RUTA_ARCHIVO_MODELO_FERRETERIA_XML, ferreteria);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
