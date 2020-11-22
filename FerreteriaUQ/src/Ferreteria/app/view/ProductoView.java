package Ferreteria.app.view;

import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.swt.widgets.Composite;

import Ferreteria.app.Excepciones.EstaVinculado;
import Ferreteria.app.Excepciones.nadaSeleccionado;
import Ferreteria.app.Excepciones.numeroErroneo;
import Ferreteria.app.Excepciones.yaExiste;
import Ferreteria.app.controller.CrudProductoViewController;
import Ferreteria.app.controller.ModelFactoryController;
import Ferreteria.app.model.Ferreteria;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider;
import org.eclipse.core.databinding.observable.map.IObservableMap;
import org.eclipse.core.databinding.beans.PojoObservables;
import Ferreteria.app.model.Producto;
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;

import Ferreteria.app.model.Proveedor;

public class ProductoView extends Composite {

	
	CrudProductoViewController crudProductoViewController = new CrudProductoViewController();
	Ferreteria ferreteria = crudProductoViewController.getFerreteria();
	ModelFactoryController model = new ModelFactoryController();

	private DataBindingContext dataBindingContext = null;
	private Table table;
	private Text textNombreProd;
	private Text textCodigoProd;
	private Text textPrecioProd;
	private Text textCategoriaProd;
	private TableViewer tableProductos;
	private Text text_busqueda;
    private int codigoAnterior;
	private String busquedad = "";
    String [] botones={"  Si", "  No"};
	Producto productoSeleccionado;
	Proveedor proveedorSeleccionadoCombo;
	private ComboViewer comboViewerMarca;

	public ProductoView(Composite parent, int style) {
		super(parent, style);

		Group grpAcciones = new Group(this, SWT.NONE);
		grpAcciones.setText("Acciones");
		grpAcciones.setBounds(10, 10, 918, 92);

		Button btnNuevoProducto = new Button(grpAcciones, SWT.NONE);
		btnNuevoProducto.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				limpiarCampoTexto();
			}
		});
		btnNuevoProducto.setBounds(523, 38, 167, 31);
		btnNuevoProducto.setText("Nuevo Producto");

		Button btnEliminarProducto = new Button(grpAcciones, SWT.NONE);
		btnEliminarProducto.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Boolean flag = false;
				if (productoSeleccionado != null) {
					if(!crudProductoViewController.consultarSiExiste(productoSeleccionado)){
						int variable=JOptionPane.showOptionDialog (null, " Confirmar eliminación", "Confirmación", 
								JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null/*icono*/, botones, botones[0]);
				    if(variable==0){
					flag = crudProductoViewController.eliminarProducto(productoSeleccionado.getCodigoProducto());
					limpiarCampoTexto();
					initDataBindings();
					crudProductoViewController.salvarDatos();
					crudProductoViewController.guardaTextoPlano();
					crudProductoViewController.guardarArchivoLog("Se ha eliminado el producto: "+productoSeleccionado.getNombreProducto(),
							2, "ProductoEliminado");
				    }else{
				    	flag=false;
				    }
					if (flag == true) {
						JOptionPane.showMessageDialog(null, "El producto se elimino con exito");
					} else {
						JOptionPane.showMessageDialog(null, "No se pudo eliminar");
					}
				}
					else{
						try {
							throw new EstaVinculado("Esta Vinculado con una transacción");
						} catch (EstaVinculado e1) {
							crudProductoViewController.guardarArchivoLog("El producto seleccionado esta vinculado con una transacción: "+productoSeleccionado.getNombreProducto(),
									2, "ProductoVinculado");	
						}
						
					}
		    	}	
			}
		});
		btnEliminarProducto.setBounds(717, 38, 167, 31);
		btnEliminarProducto.setText("Eliminar Producto");

		Group grpListaProductos = new Group(this, SWT.NONE);
		grpListaProductos.setText("Lista Productos");
		grpListaProductos.setBounds(10, 108, 918, 274);

		tableProductos = new TableViewer(grpListaProductos, SWT.BORDER | SWT.FULL_SELECTION);
		table = tableProductos.getTable();
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				productoSeleccionado = (Producto) table.getItem(table.getSelectionIndex()).getData();

				textNombreProd.setText(productoSeleccionado.getNombreProducto());
				textCategoriaProd.setText(productoSeleccionado.getCategoria());
				textCodigoProd.setText(Integer.toString(productoSeleccionado.getCodigoProducto()));
				textPrecioProd.setText(Double.toString(productoSeleccionado.getPrecio()));
				comboViewerMarca.setSelection(new StructuredSelection(productoSeleccionado.getProveedorAsociado()));
				codigoAnterior= productoSeleccionado.getCodigoProducto();
			}
		});
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setBounds(10, 28, 898, 236);

		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableProductos, SWT.NONE);
		TableColumn tblclmnNombre = tableViewerColumn.getColumn();
		tblclmnNombre.setWidth(232);
		tblclmnNombre.setText("Nombre ");

		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableProductos, SWT.NONE);
		TableColumn tblclmnCdigo = tableViewerColumn_1.getColumn();
		tblclmnCdigo.setWidth(128);
		tblclmnCdigo.setText("C\u00F3digo");

		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(tableProductos, SWT.NONE);
		TableColumn tblclmnPrecio = tableViewerColumn_2.getColumn();
		tblclmnPrecio.setWidth(163);
		tblclmnPrecio.setText("Precio");

		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(tableProductos, SWT.NONE);
		TableColumn tblclmnCategoria = tableViewerColumn_3.getColumn();
		tblclmnCategoria.setWidth(185);
		tblclmnCategoria.setText("Categoria");

		TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(tableProductos, SWT.NONE);
		TableColumn tblclmnMarca = tableViewerColumn_4.getColumn();
		tblclmnMarca.setWidth(186);
		tblclmnMarca.setText("Marca");

		Group grpDetalles = new Group(this, SWT.NONE);
		grpDetalles.setText("Detalles");
		grpDetalles.setBounds(10, 388, 918, 189);

		Label lblNombre = new Label(grpDetalles, SWT.NONE);
		lblNombre.setBounds(10, 35, 81, 25);
		lblNombre.setText("Nombre");

		Label lblCdigo = new Label(grpDetalles, SWT.NONE);
		lblCdigo.setBounds(10, 83, 81, 25);
		lblCdigo.setText("C\u00F3digo");

		Label lblPrecio = new Label(grpDetalles, SWT.NONE);
		lblPrecio.setBounds(10, 133, 81, 25);
		lblPrecio.setText("Precio");

		textNombreProd = new Text(grpDetalles, SWT.BORDER);
		textNombreProd.setBounds(91, 35, 248, 31);

		textCodigoProd = new Text(grpDetalles, SWT.BORDER);
		textCodigoProd.setBounds(91, 83, 248, 31);

		textPrecioProd = new Text(grpDetalles, SWT.BORDER);
		textPrecioProd.setBounds(91, 133, 248, 31);

		Label lblCategoria = new Label(grpDetalles, SWT.NONE);
		lblCategoria.setBounds(482, 35, 81, 25);
		lblCategoria.setText("Categoria");

		Label lblMarca = new Label(grpDetalles, SWT.NONE);
		lblMarca.setBounds(482, 83, 81, 25);
		lblMarca.setText("Marca");

		textCategoriaProd = new Text(grpDetalles, SWT.BORDER);
		textCategoriaProd.setBounds(582, 35, 248, 31);

		text_busqueda = new Text(grpAcciones, SWT.BORDER);
		text_busqueda.setBounds(111, 38, 223, 31);

		Label lblBusqueda = new Label(grpAcciones, SWT.NONE);
		lblBusqueda.setBounds(10, 42, 81, 25);
		lblBusqueda.setText("Busqueda");
		
		Button btnConsultar = new Button(grpAcciones, SWT.NONE);
		btnConsultar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				if(productoSeleccionado != null){
					String salida="";
					salida += "Nombre: "+ productoSeleccionado.getNombreProducto()+ "\n";
					salida += "Categoria: "+ productoSeleccionado.getCategoria()+ "\n";
					salida += "Codigo: "+ productoSeleccionado.getCodigoProducto()+"\n";
					salida += "Precio: "+ productoSeleccionado.getPrecio()+ "\n";
					salida += "Proveedor: "+ productoSeleccionado.getProveedorAsociado().getNombreProveedor()+"\n";
					JOptionPane.showMessageDialog(null, salida);
					
				}
				else{
					try {
						throw new nadaSeleccionado();
					} catch (nadaSeleccionado e1) {
						JOptionPane.showMessageDialog(null, "Debe seleccionar un producto");
						crudProductoViewController.guardarArchivoLog("No ha selecionado el producto ",
								1, "NadaSeleccionado");
					}
					
				}
			}
		});
		btnConsultar.setBounds(340, 38, 167, 31);
		btnConsultar.setText("Consultar");

		text_busqueda.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				Text source = (Text) e.getSource();
				busquedad = source.getText();
				tableProductos.refresh();
			}
		});
		text_busqueda.addSelectionListener(new SelectionAdapter() {
			public void widgetDefaultSelected(SelectionEvent e) {
				if (e.detail == SWT.CANCEL) {
					Text text = (Text) e.getSource();
					text.setText(""); //
				}
			}
		});

		tableProductos.addFilter(new ViewerFilter() {

			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element) {

				Producto productoBusqueda = (Producto) element;
				try {
					return productoBusqueda.getNombreProducto().contains(busquedad)
							|| productoBusqueda.getNombreProducto().toLowerCase().contains(busquedad)
							|| productoBusqueda.getNombreProducto().toUpperCase().contains(busquedad)
							|| String.valueOf(productoBusqueda.getCodigoProducto()).contains((busquedad));

				} catch (Exception e) {

					return false;
				}
			}
		});
		comboViewerMarca = new ComboViewer(grpDetalles, SWT.READ_ONLY);
		Combo comboMarca = comboViewerMarca.getCombo();
		comboMarca.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				IStructuredSelection x = (IStructuredSelection) comboViewerMarca.getSelection();
				proveedorSeleccionadoCombo = (Proveedor) x.getFirstElement();

			}
		});
		comboMarca.setBounds(582, 83, 248, 33);
		dataBindingContext = initDataBindings();

		Button btnAgregarProducto = new Button(grpDetalles, SWT.NONE);
		btnAgregarProducto.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				if (verificarCamposVacios() == true && proveedorSeleccionadoCombo !=null) {

					Producto productoNuevo = new Producto();

					try {

						productoNuevo.setCategoria(textCategoriaProd.getText());
						productoNuevo.setCodigoProducto(Integer.parseInt(textCodigoProd.getText()));
						productoNuevo.setProveedorAsociado(proveedorSeleccionadoCombo);
						productoNuevo.setNombreProducto(textNombreProd.getText());
						productoNuevo.setPrecio(Double.valueOf(textPrecioProd.getText()));

						crudProductoViewController.crearProducto(productoNuevo);
						initDataBindings();
						limpiarCampoTexto();
						crudProductoViewController.salvarDatos();
					    crudProductoViewController.guardaTextoPlano();
						crudProductoViewController.guardarArchivoLog("Se ha añadido el producto: "+productoNuevo.getNombreProducto(),
								2, "ProductoAñadido");

					} catch (yaExiste e1) {
						JOptionPane.showMessageDialog(null, "El producto ya existe", null, JOptionPane.WARNING_MESSAGE,
								null);
						crudProductoViewController.guardarArchivoLog("El producto: "+productoNuevo.getNombreProducto()+" ya existe",
								1, "ProductoRepetido");
					} catch (NumberFormatException e2) {
						JOptionPane.showMessageDialog(null, "No se aceptan letras en código ó precio", null,
								JOptionPane.WARNING_MESSAGE, null);
						crudProductoViewController.guardarArchivoLog("Valor erroneo ingresado no se aceptan letras en codigo o precio ",
								1, "DatoErroneo");
					}
				}else{
					JOptionPane.showMessageDialog(null, "Debe completar todos los campos", null,
							JOptionPane.WARNING_MESSAGE, null);
				}
			}
		});
		btnAgregarProducto.setBounds(556, 144, 166, 35);
		btnAgregarProducto.setText("Agregar Producto");

		Button btnActualizarProducto = new Button(grpDetalles, SWT.NONE);
		btnActualizarProducto.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				if (productoSeleccionado != null) {
					try {
						crudProductoViewController.actualizarProducto(textNombreProd.getText(),
								Integer.valueOf(textCodigoProd.getText()), Double.valueOf(textPrecioProd.getText()),
								textCategoriaProd.getText(), proveedorSeleccionadoCombo, codigoAnterior);
						crudProductoViewController.salvarDatos();
						crudProductoViewController.guardaTextoPlano();
						crudProductoViewController.guardarArchivoLog("Se ha actualizado el producto: "+productoSeleccionado.getNombreProducto(),
								2, "ProductoActualizado");
					} catch (numeroErroneo e1) {
						JOptionPane.showMessageDialog(null, "No se aceptan letras en código ó precio", null,
								JOptionPane.WARNING_MESSAGE, null);
						crudProductoViewController.guardarArchivoLog("Valor erroneo ingresado no se aceptan letras en codigo o precio ",
								1, "DatoErroneo");
					} catch (yaExiste e4){
						JOptionPane.showMessageDialog(null, "El producto ya existe", null, JOptionPane.WARNING_MESSAGE,
								null);
						crudProductoViewController.guardarArchivoLog("El codigo ya le pertenece a otro producto "+ " ya existe",
								1, "ProductoRepetido");
					}
					limpiarCampoTexto();
					initDataBindings();
					
				}
			}
		});
		btnActualizarProducto.setBounds(728, 144, 166, 35);
		btnActualizarProducto.setText("Actualizar Producto");
		
		Button btnReporte = new Button(grpDetalles, SWT.NONE);
		btnReporte.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				JFileChooser f = new JFileChooser();
		           f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
		           f.showSaveDialog(null);
                String seleccion=f.getSelectedFile().toString();
		           int producto=2;
		           
		           try{
		        	   crudProductoViewController.guardarReporte(producto, seleccion);
		        	   JOptionPane.showMessageDialog(null, "Reporte generado con exito");
		        	   crudProductoViewController.guardarArchivoLog("Se ha generado un reporte de Productos", 1, "ReporteProducto");
		           }catch(IOException e1){
		        	   crudProductoViewController.guardarArchivoLog("El reporte ha fallado de productos", 2, "ReporteFallido");
		        	   JOptionPane.showMessageDialog(null, "Reporte generado falló");
		           }
			}
		});
		btnReporte.setBounds(377, 144, 166, 35);
		btnReporte.setText("Reporte");

	}

	public void limpiarCampoTexto() {
		textCategoriaProd.setText("");
		textCodigoProd.setText("");
		comboViewerMarca.setSelection(null);// ojo
		textNombreProd.setText("");
		textPrecioProd.setText("");
	}

	public Boolean verificarCamposVacios() {

		if (textCategoriaProd.getText().equalsIgnoreCase("") || textCodigoProd.getText().equalsIgnoreCase("")
				|| textNombreProd.getText().equalsIgnoreCase("") || textPrecioProd.getText().equalsIgnoreCase("")) {

			return false;
		} else {

			return true;
		}
	}
	

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();
		//
		ObservableListContentProvider listContentProvider = new ObservableListContentProvider();
		IObservableMap[] observeMaps = PojoObservables.observeMaps(listContentProvider.getKnownElements(),
				Producto.class, new String[] { "nombreProducto", "codigoProducto", "precio", "categoria",
						"proveedorAsociado.nombreProveedor" });
		tableProductos.setLabelProvider(new ObservableMapLabelProvider(observeMaps));
		tableProductos.setContentProvider(listContentProvider);
		//
		IObservableList listaProductosFerreteriaObserveList = PojoProperties.list("listaProductos").observe(ferreteria);
		tableProductos.setInput(listaProductosFerreteriaObserveList);
		//
		ObservableListContentProvider listContentProvider_1 = new ObservableListContentProvider();
		IObservableMap observeMap = PojoObservables.observeMap(listContentProvider_1.getKnownElements(),
				Proveedor.class, "nombreProveedor");
		comboViewerMarca.setLabelProvider(new ObservableMapLabelProvider(observeMap));
		comboViewerMarca.setContentProvider(listContentProvider_1);
		//
		IObservableList listaProveedorFerreteriaObserveList = PojoProperties.list("listaProveedor").observe(ferreteria);
		comboViewerMarca.setInput(listaProveedorFerreteriaObserveList);
		//
		return bindingContext;
	}
}
