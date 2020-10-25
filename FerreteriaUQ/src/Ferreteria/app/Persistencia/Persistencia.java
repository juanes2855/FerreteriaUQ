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
import Ferreteria.app.controller.ModelFactoryController;
import Ferreteria.app.model.Compra;
import Ferreteria.app.model.Empleado;
import Ferreteria.app.model.Factura_Compra;
import Ferreteria.app.model.Ferreteria;
import Ferreteria.app.model.Producto;
import Ferreteria.app.model.Proveedor;
import Ferreteria.app.model.Usuario;

public class Persistencia {

	public static final String RUTA_ARCHIVO_PROVEEDORES = "C:/td/persistencia/archivos/archivoProveedor.txt";
	public static final String RUTA_TRANSACCION_COMPRAS = "C:/td/persistencia/archivos/archivoTransaccion.txt";
	public static final String RUTA_ARCHIVO_EMPLEADOS = "C:/td/persistencia/archivos/archivoEmpleado.txt";
	public static final String RUTA_ARCHIVO_FACTURAS = "C:/td/persistencia/archivos/archivoFacturas.txt";
	public static final String RUTA_ARCHIVO_LOG = "C:/td/persistencia/log/archivoLog.txt";
	public static final String RUTA_ARCHIVO_USUARIOS = "C:/td/persistencia/archivos/archivoUsuario.txt";
	public static final String RUTA_ARCHIVO_PRODUCTOS = "C:/td/persistencia/archivos/archivoProducto.txt";
	public static final String RUTA_ARCHIVO_MODELO_FERRETERIA_BINARIO = "C:/td/persistencia/model.dat";
	public static final String RUTA_ARCHIVO_MODELO_FERRETERIA_XML = "C:/td/persistencia/model.xml";

	public static void cargarDatosArchivos(Ferreteria ferreteria) throws FileNotFoundException, IOException {

		ArrayList<Proveedor> proveedoresCargados = cargarProveedores();

		if (proveedoresCargados.size() > 0)
			ferreteria.getListaProveedor().addAll(proveedoresCargados);
        
		ArrayList<Empleado> empleadosCargados = cargarEmpleado();

		if (empleadosCargados.size() > 0)
			ferreteria.getListaEmpleados().addAll(empleadosCargados);

		ArrayList<Producto> productosCargados = cargarProductos();

		if (productosCargados.size() > 0)
			ferreteria.getListaProductos().addAll(productosCargados);

		ArrayList<Compra> comprasCargados = cargarCompras();
		if (comprasCargados.size() > 0)
			ferreteria.getListaCompras().addAll(comprasCargados);
	}

	
	/**
	 * Guarda en un archivo de texto todos la información de las personas
	 * almacenadas en el ArrayList
	 * 
	 * @param objetos
	 * @param ruta
	 * @throws IOException
	 */
	public static void guardarProveedores(ArrayList<Proveedor> listaProveedores) throws IOException {
		// TODO Auto-generated method stub
		String contenido = "";

		for (Proveedor proveedor : listaProveedores) {
			contenido += "<" + proveedor.getNombreProveedor() + ">@@<" + proveedor.getCodigoProveedor() + ">@@<"
					+ proveedor.getDireccionProveedor() + ">@@<"+ proveedor.getTelefonoProveedor() + ">" + "\n";
		}
		ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_PROVEEDORES, contenido, false);

	}

	public static void guardarEmpleadox(ArrayList<Empleado> listaEmpleados) throws IOException {
		String contenido = "";

		for (Empleado empleado : listaEmpleados) {
			contenido += "<" + empleado.getNombreEmpleado() + ">@@<" + empleado.getCodigoEmpleado() + ">@@<"
					+ empleado.getDireccion() + ">@@<" + empleado.getCargo() + ">@@<" + empleado.getSalario() + ">"
					+ "\n";
		}
		ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_EMPLEADOS, contenido, false);
	}

	public static void guardarProducto(ArrayList<Producto> listaProductos) throws IOException {
		String contenido = "";

		for (Producto producto : listaProductos) {
			contenido += "<" + producto.getNombreProducto() + ">@@<" + producto.getCodigoProducto() + ">@@<"
					+ producto.getPrecio() + ">@@<" + producto.getCategoria() + ">@@<"
					+ producto.getProveedorAsociado().getNombreProveedor() + ">" + "\n";
		}
		ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_PRODUCTOS, contenido, false);
	}

	public static void guardarCompra(ArrayList<Compra> listaCompras) throws IOException {
		String contenido = "";

		for (Compra compra : listaCompras) {
			contenido += "<" + compra.getCantidadCompra() + ">@@<" + compra.getCodigoCompra() + ">@@<"
					+ compra.getFechaCompra() + ">@@<" + compra.getEmpleadoAsociado().getNombreEmpleado() + ">@@<"
					+ compra.getProveedorAsociado().getNombreProveedor() + ">@@<" +compra.getFactura_Compra().getTotalCompra()+">"+"\n";
		}
		ArchivoUtil.guardarArchivo(RUTA_TRANSACCION_COMPRAS, contenido, false);
	}
	public static void guardarFactura(ArrayList<Factura_Compra> listaFacturas) throws IOException{
		String contenido ="";
		for (Factura_Compra factura_Compra : listaFacturas) {
			contenido+= "<"+factura_Compra.getCantidadCompra()+""+factura_Compra.getNumeroFactura()+""
		+factura_Compra.getTotalCompra();
		}
		ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_FACTURAS, contenido, false);
	}
	

	// ----------------------LOADS------------------------

	/**
	 * 
	 * @param tipoPersona
	 * @param ruta
	 * @return un Arraylist de personas con los datos obtenidos del archivo de
	 *         texto indicado
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static ArrayList<Proveedor> cargarProveedores() throws FileNotFoundException, IOException {
		ArrayList<Proveedor> proveedores = new ArrayList<Proveedor>();

		ArrayList<String> contenido = ArchivoUtil.leerArchivo(RUTA_ARCHIVO_PROVEEDORES);
		String linea = "";

		for (int i = 0; i < contenido.size(); i++) {
			linea = contenido.get(i);
			linea = linea.substring(1, linea.length() - 1);
			Proveedor proveedor = new Proveedor();
			proveedor.setNombreProveedor(linea.split(">@@<")[0]);
			proveedor.setCodigoProveedor(Integer.valueOf(linea.split(">@@<")[1]));
			proveedor.setDireccionProveedor(linea.split(">@@<")[2]);
			proveedor.setTelefonoProveedor(Integer.valueOf(linea.split(">@@<")[3]));
			proveedores.add(proveedor);
		}
		return proveedores;
	}

	public static ArrayList<Producto> cargarProductos() throws FileNotFoundException, IOException {
		ArrayList<Producto> productos = new ArrayList<Producto>();
		ArrayList<String> contenido = ArchivoUtil.leerArchivo(RUTA_ARCHIVO_PRODUCTOS);
		String linea = "";

		for (int i = 0; i < contenido.size(); i++) {
			linea = contenido.get(i);
			linea = linea.substring(1, linea.length() - 1);
			Producto producto = new Producto();
			producto.setNombreProducto(linea.split(">@@<")[0]);
			producto.setCodigoProducto(Integer.valueOf(linea.split(">@@<")[1]));
			producto.setPrecio(Double.valueOf(linea.split(">@@<")[2]));
			producto.setCategoria(linea.split(">@@<")[3]);
			producto.setProveedorAsociado(obtenerProveedor(linea.split(">@@<")[4]));
			productos.add(producto);

		}
		return productos;
	}

	public static ArrayList<Compra> cargarCompras() throws FileNotFoundException, IOException {
		ArrayList<Compra> compras = new ArrayList<Compra>();

		ArrayList<String> contenido = ArchivoUtil.leerArchivo(RUTA_TRANSACCION_COMPRAS);
		String linea = "";
		for (int i = 0; i < contenido.size(); i++) {
			linea = contenido.get(i);
			linea = linea.substring(1, linea.length() - 1);
			Compra compra = new Compra();
			compra.setCantidadCompra(Integer.valueOf(linea.split(">@@<")[0]));
			compra.setCodigoCompra(Integer.valueOf(linea.split(">@@<")[1]));
			compra.setFechaCompra(linea.split(">@@<")[2]);
			compra.setEmpleadoAsociado(obtenerEmpleado(linea.split(">@@<")[3]));
			compra.setProveedorAsociado(obtenerProveedor(linea.split(">@@<")[4]));
			Factura_Compra factura_Compra= new Factura_Compra();
			factura_Compra.setCantidadCompra(compra.getCantidadCompra());
			factura_Compra.setNumeroFactura(compra.getCodigoCompra());
			factura_Compra.setTotalCompra(Double.valueOf(linea.split(">@@<")[5]));
			compra.setFactura_Compra(factura_Compra);
			compras.add(compra);
		}

		return compras;
	}

	public static ArrayList<Empleado> cargarEmpleado() throws FileNotFoundException, IOException {
		ArrayList<Empleado> empleados = new ArrayList<Empleado>();

		ArrayList<String> contenido = ArchivoUtil.leerArchivo(RUTA_ARCHIVO_EMPLEADOS);
		String linea = "";

		for (int i = 0; i < contenido.size(); i++) {
			linea = contenido.get(i);
			linea = linea.substring(1, linea.length() - 1);
			Empleado empleado = new Empleado();
			empleado.setNombreEmpleado(linea.split(">@@<")[0]);
			empleado.setCodigoEmpleado(Integer.parseInt(linea.split(">@@<")[1]));
			empleado.setDireccion(linea.split(">@@<")[2]);
			empleado.setCargo(linea.split(">@@<")[3]);
			empleado.setSalario(Double.valueOf(linea.split(">@@<")[4]));
			empleados.add(empleado);

		}
		return empleados;
	}

	public static void guardaRegistroLog(String mensajeLog, int nivel, String accion) {

		ArchivoUtil.guardarRegistroLog(mensajeLog, nivel, accion, RUTA_ARCHIVO_LOG);
	}

	public static boolean iniciarSesion(String usuario, String contrasenia)
			throws FileNotFoundException, IOException, UsuarioExcepcion {

		if (validarUsuario(usuario, contrasenia)) {
			return true;
		} else {
			throw new UsuarioExcepcion("Usuario no existe");
		}

	}

	public static boolean validarUsuario(String usuario, String contrasenia)
			throws FileNotFoundException, IOException {
		ArrayList<Usuario> usuarios = Persistencia.cargarUsuarios(RUTA_ARCHIVO_USUARIOS);

		for (int indiceUsuario = 0; indiceUsuario < usuarios.size(); indiceUsuario++) {
			Usuario usuarioAux = usuarios.get(indiceUsuario);
			if (usuarioAux.getUsuario().equalsIgnoreCase(usuario)
					&& usuarioAux.getContrasenia().equalsIgnoreCase(contrasenia)) {
				return true;
			}
		}
		return false;
	}

	public static ArrayList<Usuario> cargarUsuarios(String ruta) throws FileNotFoundException, IOException {
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

		ArrayList<String> contenido = ArchivoUtil.leerArchivo(ruta);
		String linea = "";

		for (int i = 0; i < contenido.size(); i++) {
			linea = contenido.get(i);

			Usuario usuario = new Usuario();
			usuario.setUsuario(linea.split(",")[0]);
			usuario.setContrasenia(linea.split(",")[1]);

			usuarios.add(usuario);
		}
		return usuarios;
	}

	// ----------------------SAVES------------------------

	

	// ------------------------------------SERIALIZACIÓN y XML

	public static Ferreteria cargarRecursoFerreteriaBinario() {

		Ferreteria ferreteria = null;

		try {
			ferreteria = (Ferreteria) ArchivoUtil.cargarRecursoSerializado(RUTA_ARCHIVO_MODELO_FERRETERIA_BINARIO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ferreteria;
	}

	public static void guardarRecursoFerreteriaBinario(Ferreteria ferreteria) {

		try {
			ArchivoUtil.salvarRecursoSerializado(RUTA_ARCHIVO_MODELO_FERRETERIA_BINARIO, ferreteria);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Ferreteria cargarRecursoFerreteriaXML() {

		Ferreteria ferreteria = null;

		try {
			ferreteria = (Ferreteria) ArchivoUtil.cargarRecursoSerializadoXML(RUTA_ARCHIVO_MODELO_FERRETERIA_XML);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ferreteria;

	}
	public static void guardarRespaldoXML(Ferreteria ferreteria){
		String fecha= ArchivoUtil.cargarFechaSistema2();
		String ruta= "C:/td/persistencia/respaldo/respaldoXMl_"+fecha+".xml";
		try {
			ArchivoUtil.generarRespaldoXml(ruta, ferreteria);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void guardarRespaldoBinario(Ferreteria ferreteria){
		String fecha= ArchivoUtil.cargarFechaSistema2();
		String ruta= "C:/td/persistencia/respaldo/respaldoBinario_"+fecha+".dat";
		try{
			ArchivoUtil.generarRespaldoBinario(ruta, ferreteria);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void guardarRecursoFerreteriaXML(Ferreteria ferreteria) {

		try {
			ArchivoUtil.salvarRecursoSerializadoXML(RUTA_ARCHIVO_MODELO_FERRETERIA_XML, ferreteria);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Proveedor obtenerProveedor(String nombre) throws FileNotFoundException, IOException {
		Proveedor proveedor = null;
		for (int i = 0; i < cargarProveedores().size(); i++) {
			if (cargarProveedores().get(i).getNombreProveedor().equals(nombre)) {
				proveedor = cargarProveedores().get(i);
			}
		}
		return proveedor;
	}

	public static Empleado obtenerEmpleado(String nombre) throws FileNotFoundException, IOException {
		Empleado empleado = null;
		for (int i = 0; i < cargarEmpleado().size(); i++) {
			if (cargarEmpleado().get(i).getNombreEmpleado().equalsIgnoreCase(nombre)) {
				empleado = cargarEmpleado().get(i);
			}
		}
		return empleado;
	}


	public static void respaldoCompra(ArrayList<Compra> listaCompras) throws IOException {
		String contenido = "";
		String fecha= ArchivoUtil.cargarFechaSistema2();
		String ruta= "C:/td/persistencia/respaldo/respaldoCompraPlano_"+fecha+".txt";

		for (Compra compra : listaCompras) {
			contenido += "<" + compra.getCantidadCompra() + ">@@<" + compra.getCodigoCompra() + ">@@<"
					+ compra.getFechaCompra() + ">@@<" + compra.getEmpleadoAsociado().getNombreEmpleado() + ">@@<"
					+ compra.getProveedorAsociado().getNombreProveedor() + ">" + "\n";
		}
		ArchivoUtil.guardarArchivo(ruta, contenido, false);
	}

}
