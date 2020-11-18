package Ferreteria.app.model;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import Ferreteria.app.Excepciones.soloUnProveedor;
import Ferreteria.app.Excepciones.yaExiste;

public class Ferreteria implements Serializable, IFerreteria {

	private static final long serialVersionUID = 1L;
	private String nombreFerreteria;
	private String direccionFerreteria;
	private int telefonoFerreteria;
	private Inventario inventario;
	private Proveedor proveedor;
	private ArrayList<Empleado> listaEmpleados;
	private ArrayList<Producto> listaProductos;
	private ArrayList<Proveedor> listaProveedor;
	private ArrayList<Compra> listaCompras;
	private ArrayList<Factura_Compra> listaFacturas;
	private ArrayList<Detalle_Inventario>listaObjetosAcomprar;
	ArrayList<Usuario> listaUsuario= new ArrayList<Usuario>();


	public Ferreteria(String nombreFerreteria, String direccionFerreteria, int telefonoFerreteria) {
		super();
		this.nombreFerreteria = nombreFerreteria;
		this.direccionFerreteria = direccionFerreteria;
		this.telefonoFerreteria = telefonoFerreteria;
		listaEmpleados = new ArrayList<Empleado>();
		listaProductos = new ArrayList<Producto>();
		listaProveedor = new ArrayList<Proveedor>();
		listaCompras= new ArrayList<Compra>();
		listaFacturas= new ArrayList<Factura_Compra>();
		//listaProductosOfProveedor= new ArrayList<Producto>();
		listaObjetosAcomprar= new ArrayList<Detalle_Inventario>();
	}

	public Ferreteria() {

	}
	


	@Override
	public void crearEmpleado(Empleado nuevoEmpleado) throws yaExiste {
		Empleado empleadoExistente = obtenerEmpleado(nuevoEmpleado.getCodigoEmpleado());

		if (empleadoExistente != null) {
			throw new yaExiste("Ya existe el empleado");
		} else {
			getListaEmpleados().add(nuevoEmpleado);
		}

	}
    @Override
	public void actualizarEmpleado(String nombreEmpleado, String cargo, int codigoEmpleado, String direccion,
			double salario, int codigoEmpleadoAnterior) throws yaExiste {

		Empleado empleado = obtenerEmpleado(codigoEmpleadoAnterior);
		Empleado empleadoExistente = obtenerEmpleado(codigoEmpleado);
		if (empleado != null && empleadoExistente==null) {
			
			empleado.setCargo(cargo);
			empleado.setCodigoEmpleado(codigoEmpleado);
			empleado.setDireccion(direccion);
			empleado.setNombreEmpleado(nombreEmpleado);
			empleado.setSalario(salario);
//aqui
		}else if(empleadoExistente != null){
			throw new yaExiste("Ya existe el empleado");
		}
		

	}

	public Empleado obtenerEmpleado(int codigoEmpleado) {

		Empleado empleadoE = null;

		for (Empleado empleado : getListaEmpleados()) {
			if (empleado.getCodigoEmpleado() == codigoEmpleado) {
				empleadoE = empleado;
				break;
			}
		}
		return empleadoE;
	}

	@Override
	public Boolean eliminarEmpleado(int codigoEmpleado) {
		Boolean Eliminado = false;
		Empleado empleado = obtenerEmpleado(codigoEmpleado);

		if (empleado != null) {
			listaEmpleados.remove(empleado);
			Eliminado = true;
		}
		return Eliminado;
	}

	@Override
	public void crearProducto(Producto producto) throws yaExiste {
		Producto productoExistente = obtenerProducto(producto.getCodigoProducto());

		if (productoExistente != null) {
			throw new yaExiste("Ya existe el producto");
		} else {
			getListaProductos().add(producto);
			
		}
	}

	public Producto obtenerProducto(int codigoProducto) {
		Producto productoE = null;

		for (Producto producto : getListaProductos()) {
			if (producto.getCodigoProducto() == codigoProducto) {
				productoE = producto;
				break;
			}
		}
		return productoE;
	}

	@Override
	public void modificarProducto(String nombreProducto, int codigoProducto, double precio, String categoria,
			Proveedor proveedor, int codigoAnterior) throws yaExiste {

		Producto producto = obtenerProducto(codigoAnterior);
		Producto productoExistente = obtenerProducto(codigoProducto);
		if (producto != null && productoExistente==null) {

			producto.setCategoria(categoria);
			producto.setProveedorAsociado(proveedor);
			producto.setCodigoProducto(codigoProducto);
			producto.setNombreProducto(nombreProducto);
			producto.setPrecio(precio);
		}else if(productoExistente !=null){
			throw new yaExiste("Ya existe el producto");
		}

	}

	@Override
	public Boolean eliminarProducto(int codigoProducto) {
		Boolean Eliminado = false;
		Producto producto = obtenerProducto(codigoProducto);

		if (producto != null) {
			listaProductos.remove(producto);
			Eliminado = true;
		}
		return Eliminado;
	}

	@Override
	public void anadirProveedor(Proveedor proveedor) throws yaExiste {
		Proveedor proveedorExistente = obtenerProveedor(proveedor.getCodigoProveedor());

		if (proveedorExistente != null) {
			throw new yaExiste("Ya existe el proveedor");
		} else {
			getListaProveedor().add(proveedor);
		}

	}

	@Override
	public void modificarProveedor(String nombreProveedor, int codigoProveedor, int telefonoProveedor,
			String direccionProveedor, int codigoAnterior) throws yaExiste {

		Proveedor proveedor = obtenerProveedor(codigoAnterior);
		Proveedor proveedorExistente = obtenerProveedor(codigoProveedor);
		if (proveedor != null && proveedorExistente ==null) {
			proveedor.setCodigoProveedor(codigoProveedor);
			proveedor.setDireccionProveedor(direccionProveedor);
			proveedor.setNombreProveedor(nombreProveedor);
			proveedor.setTelefonoProveedor(telefonoProveedor);
		}else if(proveedorExistente != null){
			throw new yaExiste("Ya existe el proveedor");
		}

	}

	@Override
	public Boolean eliminarProveedor(int codigoProveedor) {

		Boolean Eliminado = false;
		Proveedor proveedor = obtenerProveedor(codigoProveedor);

		if (proveedor != null) {
			listaProveedor.remove(proveedor);
			Eliminado = true;
		}
		return Eliminado;
	}

	public Proveedor obtenerProveedor(int codigoProveedor) {
		Proveedor proveedorE = null;

		for (Proveedor proveedor : getListaProveedor()) {
			if (proveedor.getCodigoProveedor() == codigoProveedor) {
				proveedorE = proveedor;
				break;
			}
		}
		return proveedorE;
	}
	public Boolean consultarPerteneceAtransaccion(Object object){
		Boolean consulta=false;
		
		for (Compra compra : listaCompras) {
			if(compra.getEmpleadoAsociado().equals(object) || compra.getProveedorAsociado().equals(object)|| 
					compra.getEmpleadoAsociado().equals(object)){
				consulta=true;
				break;
			}
		}
		
		return consulta;
	}
	@Override
	public void modificarCompra(int codigoCompra, String fechaCompra, int cantidadCompra,
			Factura_Compra facturaCompra)throws yaExiste {
	
	  Compra compra = obtenerCompra(codigoCompra);
	  
	  if(compra != null){
		  compra.setCantidadCompra(cantidadCompra);
		  compra.setCodigoCompra(codigoCompra);
		  compra.setFactura_Compra(facturaCompra);
		  compra.setFechaCompra(fechaCompra);
	  }
		
	}
	@Override
	public Boolean eliminarCompra(int codigoCompra){
		Boolean Eliminado= false;
		Compra Compra = obtenerCompra(codigoCompra);
		
		if(Compra!= null){
			listaCompras.remove(Compra);
			Eliminado= true;
		}
		return Eliminado;
	}
	@Override
	public void crearCompra(Compra compraNueva) throws yaExiste{
		Compra compra= obtenerCompra(compraNueva.getCodigoCompra());
		
		if(compra != null){
			throw new yaExiste("Ya existe esta compra");
		}else{
			getListaCompras().add(compraNueva);
			getListaFacturas().add(compraNueva.getFactura_Compra());
			for (int i = 0; i < compraNueva.getProductosCompra().size(); i++) {
				inventario.anadirDetalleInventario(compraNueva.getCantidadCompra(), 
				compraNueva.getProductosCompra().get(i));
				crearProducto(compraNueva.getProductosCompra().get(i));
			}
		}
	}
	
	public Compra obtenerCompra(int codigoCompra){
		Compra compraE= null;
		
		for (Compra compra : listaCompras) {
			if(compra.getCodigoCompra()==codigoCompra){
				compraE = compra;
				break;
			}
		}
		return compraE;
	}
	

	public String getNombreFerreteria() {
		return nombreFerreteria;
	}

	public void setNombreFerreteria(String nombreFerreteria) {
		this.nombreFerreteria = nombreFerreteria;
	}

	public String getDireccionFerreteria() {
		return direccionFerreteria;
	}

	public void setDireccionFerreteria(String direccionFerreteria) {
		this.direccionFerreteria = direccionFerreteria;
	}

	public int getTelefonoFerreteria() {
		return telefonoFerreteria;
	}

	public void setTelefonoFerreteria(int telefonoFerreteria) {
		this.telefonoFerreteria = telefonoFerreteria;
	}

	public ArrayList<Empleado> getListaEmpleados() {
		return listaEmpleados;
	}

	public void setListaEmpleados(ArrayList<Empleado> listaEmpleados) {
		this.listaEmpleados = listaEmpleados;
	}

	public ArrayList<Producto> getListaProductos() {
		return listaProductos;
	}

	public void setListaProductos(ArrayList<Producto> listaProductos) {
		this.listaProductos = listaProductos;
	}

	public ArrayList<Proveedor> getListaProveedor() {
		return listaProveedor;
	}

	public void setListaProveedor(ArrayList<Proveedor> listaProveedor) {
		this.listaProveedor = listaProveedor;
	}

	public ArrayList<Compra> getListaCompras() {
		return listaCompras;
	}

	public void setListaCompras(ArrayList<Compra> listaCompras) {
		this.listaCompras = listaCompras;
	}

	public ArrayList<Factura_Compra> getListaFacturas() {
		return listaFacturas;
	}

	public void setListaFacturas(ArrayList<Factura_Compra> listaFacturas) {
		this.listaFacturas = listaFacturas;
	}

	public Inventario getInventario() {
		return inventario;
	}

	public void setInventario(Inventario inventario) {
		this.inventario = inventario;
	}

	public ArrayList<Detalle_Inventario> getListaObjetosAcomprar() {
		return listaObjetosAcomprar;
	}

	public void setListaObjetosAcomprar(ArrayList<Detalle_Inventario> listaObjetosAcomprar) {
		this.listaObjetosAcomprar = listaObjetosAcomprar;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public void anadirDetalleAlista(Producto productoSeleccionado, int cantidad) throws soloUnProveedor {
		Detalle_Inventario detalle = new Detalle_Inventario(cantidad, productoSeleccionado);
		if(listaObjetosAcomprar.size()==0){
			listaObjetosAcomprar.add(detalle);
		}else if(productoSeleccionado.getProveedorAsociado()==listaObjetosAcomprar.get(0).getProducto().getProveedorAsociado()){
			listaObjetosAcomprar.add(detalle);
		}else{	
			throw new soloUnProveedor("Solo se puede un proveedor");	
		}
		
	}

	public void quitarDetalleLista(Detalle_Inventario detalleSeleccionado) {
	    listaObjetosAcomprar.remove(detalleSeleccionado);	
	}

	public void limpiarLista() {
		listaObjetosAcomprar.clear();
	}
}
